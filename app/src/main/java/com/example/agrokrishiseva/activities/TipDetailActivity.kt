package com.example.agrokrishiseva.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agrokrishiseva.R
import com.example.agrokrishiseva.adapters.TipStepAdapter
import com.example.agrokrishiseva.data.TipsRepository
import com.example.agrokrishiseva.models.Tip
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class TipDetailActivity : AppCompatActivity() {
    
    private lateinit var toolbar: MaterialToolbar
    private lateinit var ivTipImage: ImageView
    private lateinit var tvTipTitle: TextView
    private lateinit var tvCategory: TextView
    private lateinit var tvDifficulty: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvSeason: TextView
    private lateinit var tvRating: TextView
    private lateinit var tvViews: TextView
    private lateinit var ivBookmark: ImageView
    private lateinit var tvDescription: TextView
    private lateinit var cgTags: ChipGroup
    private lateinit var rvSteps: RecyclerView
    private lateinit var llBenefits: LinearLayout
    private lateinit var llRequirements: LinearLayout
    
    private lateinit var stepAdapter: TipStepAdapter
    private var currentTip: Tip? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tip_detail)
        
        initViews()
        setupToolbar()
        setupRecyclerView()
        loadTipData()
    }
    
    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        ivTipImage = findViewById(R.id.ivTipImage)
        tvTipTitle = findViewById(R.id.tvTipTitle)
        tvCategory = findViewById(R.id.tvCategory)
        tvDifficulty = findViewById(R.id.tvDifficulty)
        tvTime = findViewById(R.id.tvTime)
        tvSeason = findViewById(R.id.tvSeason)
        tvRating = findViewById(R.id.tvRating)
        tvViews = findViewById(R.id.tvViews)
        ivBookmark = findViewById(R.id.ivBookmark)
        tvDescription = findViewById(R.id.tvDescription)
        cgTags = findViewById(R.id.cgTags)
        rvSteps = findViewById(R.id.rvSteps)
        llBenefits = findViewById(R.id.llBenefits)
        llRequirements = findViewById(R.id.llRequirements)
    }
    
    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""
    }
    
    private fun setupRecyclerView() {
        rvSteps.layoutManager = LinearLayoutManager(this)
        stepAdapter = TipStepAdapter(emptyList())
        rvSteps.adapter = stepAdapter
    }
    
    private fun loadTipData() {
        val tipId = intent.getStringExtra("tip_id")
        if (tipId != null) {
            currentTip = TipsRepository.getTipById(tipId)
            currentTip?.let { tip ->
                displayTipData(tip)
            }
        }
    }
    
    private fun displayTipData(tip: Tip) {
        // Header information
        ivTipImage.setImageResource(tip.imageResource)
        tvTipTitle.text = tip.title
        tvCategory.text = tip.category.displayName
        tvDifficulty.text = tip.difficulty.displayName
        tvTime.text = tip.estimatedTime
        tvSeason.text = tip.season
        tvRating.text = String.format("%.1f", tip.rating)
        
        // Format view count
        val viewText = when {
            tip.viewCount >= 1000 -> "${tip.viewCount / 1000}.${(tip.viewCount % 1000) / 100}k views"
            else -> "${tip.viewCount} views"
        }
        tvViews.text = viewText
        
        // Set difficulty color
        val difficultyColor = when (tip.difficulty) {
            com.example.agrokrishiseva.models.TipDifficulty.BEGINNER -> R.color.success_green
            com.example.agrokrishiseva.models.TipDifficulty.INTERMEDIATE -> android.R.color.holo_orange_light
            com.example.agrokrishiseva.models.TipDifficulty.ADVANCED -> R.color.error_red
        }
        tvDifficulty.setTextColor(ContextCompat.getColor(this, difficultyColor))
        
        // Bookmark state
        ivBookmark.setImageResource(
            if (tip.isBookmarked) R.drawable.ic_favorite else R.drawable.ic_favorite
        )
        ivBookmark.setColorFilter(
            ContextCompat.getColor(this, if (tip.isBookmarked) R.color.error_red else R.color.text_hint)
        )
        
        // Description
        tvDescription.text = tip.fullDescription
        
        // Tags
        cgTags.removeAllViews()
        tip.tags.forEach { tag ->
            val chip = Chip(this)
            chip.text = "#$tag"
            chip.isClickable = false
            chip.setChipBackgroundColorResource(R.color.very_light_green)
            chip.setTextColor(ContextCompat.getColor(this, R.color.primary_green))
            cgTags.addView(chip)
        }
        
        // Steps
        stepAdapter.updateSteps(tip.steps)
        
        // Benefits
        llBenefits.removeAllViews()
        tip.benefits.forEach { benefit ->
            val benefitView = createBulletPointView(benefit, R.color.success_green)
            llBenefits.addView(benefitView)
        }
        
        // Requirements
        llRequirements.removeAllViews()
        tip.requirements.forEach { requirement ->
            val requirementView = createBulletPointView(requirement, R.color.primary_green)
            llRequirements.addView(requirementView)
        }
        
        // Bookmark click listener
        ivBookmark.setOnClickListener {
            toggleBookmark()
        }
    }
    
    private fun createBulletPointView(text: String, colorRes: Int): LinearLayout {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.HORIZONTAL
        layout.setPadding(0, 8, 0, 8)
        
        val bullet = TextView(this)
        bullet.text = "•"
        bullet.setTextColor(ContextCompat.getColor(this, colorRes))
        bullet.textSize = 16f
        bullet.setPadding(0, 0, 16, 0)
        
        val textView = TextView(this)
        textView.text = text
        textView.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
        textView.textSize = 14f
        textView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        
        layout.addView(bullet)
        layout.addView(textView)
        
        return layout
    }
    
    private fun toggleBookmark() {
        currentTip?.let { tip ->
            // In a real app, this would update the database
            // For now, we'll just update the UI
            val newBookmarkState = !tip.isBookmarked
            
            ivBookmark.setImageResource(
                if (newBookmarkState) R.drawable.ic_favorite else R.drawable.ic_favorite
            )
            ivBookmark.setColorFilter(
                ContextCompat.getColor(this, if (newBookmarkState) R.color.error_red else R.color.text_hint)
            )
        }
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right)
    }
}
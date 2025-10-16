package com.example.agrokrishiseva.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agrokrishiseva.MainActivity
import com.example.agrokrishiseva.R
import com.example.agrokrishiseva.adapters.TipAdapter
import com.example.agrokrishiseva.adapters.TipCategoryAdapter
import com.example.agrokrishiseva.data.TipsRepository
import com.example.agrokrishiseva.models.Tip
import com.example.agrokrishiseva.models.TipCategory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class TipsActivity : AppCompatActivity() {
    
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var ivSearch: ImageView
    private lateinit var tilSearch: TextInputLayout
    private lateinit var etSearch: TextInputEditText
    private lateinit var tvTotalTips: TextView
    private lateinit var rvCategories: RecyclerView
    private lateinit var tvSectionTitle: TextView
    private lateinit var tvTipCount: TextView
    private lateinit var rvTips: RecyclerView
    private lateinit var llEmptyState: LinearLayout
    private lateinit var fabPopularTips: com.google.android.material.floatingactionbutton.FloatingActionButton
    
    private lateinit var tipAdapter: TipAdapter
    private lateinit var categoryAdapter: TipCategoryAdapter
    
    private var allTips: List<Tip> = emptyList()
    private var filteredTips: List<Tip> = emptyList()
    private var selectedCategory: TipCategory? = null
    private var searchQuery: String = ""
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips)
        
        initViews()
        setupRecyclerViews()
        loadTips()
        setupBottomNavigation()
        setupSearchFunctionality()
        setupFAB()
    }
    
    private fun initViews() {
        bottomNavigation = findViewById(R.id.bottomNavigation)
        ivSearch = findViewById(R.id.ivSearch)
        tilSearch = findViewById(R.id.tilSearch)
        etSearch = findViewById(R.id.etSearch)
        tvTotalTips = findViewById(R.id.tvTotalTips)
        rvCategories = findViewById(R.id.rvCategories)
        tvSectionTitle = findViewById(R.id.tvSectionTitle)
        tvTipCount = findViewById(R.id.tvTipCount)
        rvTips = findViewById(R.id.rvTips)
        llEmptyState = findViewById(R.id.llEmptyState)
        fabPopularTips = findViewById(R.id.fabPopularTips)
    }
    
    private fun setupRecyclerViews() {
        // Setup categories RecyclerView
        rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = TipCategoryAdapter(TipCategory.values().toList()) { category ->
            selectedCategory = category
            filterTips()
            updateSectionTitle()
        }
        rvCategories.adapter = categoryAdapter
        
        // Setup tips RecyclerView
        rvTips.layoutManager = LinearLayoutManager(this)
        tipAdapter = TipAdapter(
            tips = emptyList(),
            onTipClick = { tip ->
                openTipDetail(tip)
            },
            onBookmarkClick = { tip ->
                toggleBookmark(tip)
            }
        )
        rvTips.adapter = tipAdapter
    }
    
    private fun loadTips() {
        allTips = TipsRepository.getAllTips()
        filteredTips = allTips
        
        tvTotalTips.text = "${allTips.size}+"
        updateTipsList()
        updateSectionTitle()
    }
    
    private fun setupSearchFunctionality() {
        ivSearch.setOnClickListener {
            toggleSearchBar()
        }
        
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchQuery = s?.toString()?.trim() ?: ""
                filterTips()
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
    }
    
    private fun toggleSearchBar() {
        if (tilSearch.visibility == View.GONE) {
            tilSearch.visibility = View.VISIBLE
            etSearch.requestFocus()
        } else {
            tilSearch.visibility = View.GONE
            etSearch.text?.clear()
            searchQuery = ""
            filterTips()
        }
    }
    
    private fun filterTips() {
        filteredTips = allTips.filter { tip ->
            val matchesCategory = selectedCategory == null || tip.category == selectedCategory
            val matchesSearch = searchQuery.isEmpty() || 
                tip.title.contains(searchQuery, ignoreCase = true) ||
                tip.shortDescription.contains(searchQuery, ignoreCase = true) ||
                tip.tags.any { it.contains(searchQuery, ignoreCase = true) }
            
            matchesCategory && matchesSearch
        }
        
        updateTipsList()
    }
    
    private fun updateTipsList() {
        tipAdapter.updateTips(filteredTips)
        
        if (filteredTips.isEmpty()) {
            rvTips.visibility = View.GONE
            llEmptyState.visibility = View.VISIBLE
        } else {
            rvTips.visibility = View.VISIBLE
            llEmptyState.visibility = View.GONE
        }
        
        tvTipCount.text = "${filteredTips.size} tips"
    }
    
    private fun updateSectionTitle() {
        tvSectionTitle.text = when {
            selectedCategory != null -> "${selectedCategory!!.displayName} Tips"
            searchQuery.isNotEmpty() -> "Search Results"
            else -> "All Tips"
        }
    }
    
    private fun openTipDetail(tip: Tip) {
        val intent = Intent(this, TipDetailActivity::class.java)
        intent.putExtra("tip_id", tip.id)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
    
    private fun toggleBookmark(tip: Tip) {
        // In a real app, this would update the database
        // For now, we'll just show a visual feedback
        // You can implement SharedPreferences or Room database for persistence
    }
    
    private fun setupFAB() {
        fabPopularTips.setOnClickListener {
            showPopularTips()
        }
        
        fabPopularTips.setOnLongClickListener {
            showQuickActionsBottomSheet()
            true
        }
    }
    
    private fun showPopularTips() {
        selectedCategory = null
        searchQuery = ""
        etSearch.text?.clear()
        tilSearch.visibility = View.GONE
        
        filteredTips = TipsRepository.getPopularTips()
        updateTipsList()
        tvSectionTitle.text = "Popular Tips"
        
        // Reset category selection
        categoryAdapter.notifyDataSetChanged()
    }
    
    private fun showQuickActionsBottomSheet() {
        val bottomSheetDialog = com.google.android.material.bottomsheet.BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_tip_filters, null)
        bottomSheetDialog.setContentView(bottomSheetView)
        
        // Setup click listeners
        bottomSheetView.findViewById<LinearLayout>(R.id.llPopularTips).setOnClickListener {
            showPopularTips()
            bottomSheetDialog.dismiss()
        }
        
        bottomSheetView.findViewById<LinearLayout>(R.id.llRecentTips).setOnClickListener {
            showRecentTips()
            bottomSheetDialog.dismiss()
        }
        
        bottomSheetView.findViewById<LinearLayout>(R.id.llBookmarkedTips).setOnClickListener {
            showBookmarkedTips()
            bottomSheetDialog.dismiss()
        }
        
        bottomSheetView.findViewById<LinearLayout>(R.id.llSortByRating).setOnClickListener {
            sortTipsByRating()
            bottomSheetDialog.dismiss()
        }
        
        bottomSheetView.findViewById<LinearLayout>(R.id.llSortByViews).setOnClickListener {
            sortTipsByViews()
            bottomSheetDialog.dismiss()
        }
        
        bottomSheetDialog.show()
    }
    
    private fun showRecentTips() {
        selectedCategory = null
        searchQuery = ""
        etSearch.text?.clear()
        tilSearch.visibility = View.GONE
        
        filteredTips = TipsRepository.getRecentTips()
        updateTipsList()
        tvSectionTitle.text = "Recent Tips"
        categoryAdapter.notifyDataSetChanged()
    }
    
    private fun showBookmarkedTips() {
        selectedCategory = null
        searchQuery = ""
        etSearch.text?.clear()
        tilSearch.visibility = View.GONE
        
        filteredTips = allTips.filter { it.isBookmarked }
        updateTipsList()
        tvSectionTitle.text = "Bookmarked Tips"
        categoryAdapter.notifyDataSetChanged()
    }
    
    private fun sortTipsByRating() {
        filteredTips = filteredTips.sortedByDescending { it.rating }
        updateTipsList()
        tvSectionTitle.text = "${tvSectionTitle.text} (by Rating)"
    }
    
    private fun sortTipsByViews() {
        filteredTips = filteredTips.sortedByDescending { it.viewCount }
        updateTipsList()
        tvSectionTitle.text = "${tvSectionTitle.text} (by Views)"
    }
    
    private fun setupBottomNavigation() {
        bottomNavigation.selectedItemId = R.id.nav_tips
        
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_products -> {
                    startActivity(Intent(this, ProductsActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_tips -> {
                    // Already on tips page
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
package com.example.agrokrishiseva

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.agrokrishiseva.activities.LoginActivity
import com.example.agrokrishiseva.activities.ProductsActivity
import com.example.agrokrishiseva.activities.TipsActivity
import com.example.agrokrishiseva.activities.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    
    // UI Components
    private lateinit var toolbar: Toolbar
    private lateinit var ivProfile: ImageView
    private lateinit var etSearch: EditText
    private lateinit var tvTimeGreeting: TextView
    private lateinit var tvGreeting: TextView
    private lateinit var tvNotificationCount: TextView
    private lateinit var tvWeatherToday: TextView
    private lateinit var tvFarmStatus: TextView
    private lateinit var tvFarmingRecommendation: TextView
    private lateinit var tvQuickTip: TextView
    private lateinit var cardProducts: MaterialCardView
    private lateinit var cardTips: MaterialCardView
    private lateinit var cardWeather: MaterialCardView
    private lateinit var cardSupport: MaterialCardView
    private lateinit var cardCropCalendar: MaterialCardView
    private lateinit var cardMarketPrices: MaterialCardView
    private lateinit var cardSoilHealth: MaterialCardView
    private lateinit var cardPestControl: MaterialCardView
    private lateinit var bottomNavigation: BottomNavigationView
    
    // Quick tips array
    private val quickTips = arrayOf(
        "Water your crops early morning for better absorption",
        "Check soil moisture before watering",
        "Rotate crops to maintain soil health",
        "Use organic fertilizers for better yield",
        "Monitor weather conditions regularly",
        "Keep your farming tools clean and sharp"
    )
    
    // Farming recommendations array
    private val farmingRecommendations = arrayOf(
        "Good conditions for watering crops",
        "Perfect weather for planting seeds",
        "Ideal time for harvesting",
        "Avoid pesticide spraying today",
        "Great day for soil preparation",
        "Monitor crops for pest activity"
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        
        // Check if user is logged in
        if (auth.currentUser == null) {
            // User not logged in, redirect to login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
        
        setContentView(R.layout.activity_main)
        
        initViews()
        setupToolbar()
        setupSearchBar()
        setupClickListeners()
        setupBottomNavigation()
        loadUserData()
        setupEnhancedWelcomeCard()
        showRandomTip()
        showRandomRecommendation()
    }
    
    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        ivProfile = findViewById(R.id.ivProfile)
        etSearch = findViewById(R.id.etSearch)
        tvTimeGreeting = findViewById(R.id.tvTimeGreeting)
        tvGreeting = findViewById(R.id.tvGreeting)
        tvNotificationCount = findViewById(R.id.tvNotificationCount)
        tvWeatherToday = findViewById(R.id.tvWeatherToday)
        tvFarmStatus = findViewById(R.id.tvFarmStatus)
        tvFarmingRecommendation = findViewById(R.id.tvFarmingRecommendation)
        tvQuickTip = findViewById(R.id.tvQuickTip)
        cardProducts = findViewById(R.id.cardProducts)
        cardTips = findViewById(R.id.cardTips)
        cardWeather = findViewById(R.id.cardWeather)
        cardSupport = findViewById(R.id.cardSupport)
        cardCropCalendar = findViewById(R.id.cardCropCalendar)
        cardMarketPrices = findViewById(R.id.cardMarketPrices)
        cardSoilHealth = findViewById(R.id.cardSoilHealth)
        cardPestControl = findViewById(R.id.cardPestControl)
        bottomNavigation = findViewById(R.id.bottomNavigation)
    }
    
    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        
        // Handle profile icon click
        ivProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
    
    private fun setupSearchBar() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                if (query.isNotEmpty()) {
                    performSearch(query)
                }
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
    }
    
    private fun performSearch(query: String) {
        when {
            query.contains("product", ignoreCase = true) || 
            query.contains("buy", ignoreCase = true) ||
            query.contains("shop", ignoreCase = true) -> {
                Toast.makeText(this, "Searching products for: $query", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProductsActivity::class.java))
            }
            query.contains("tip", ignoreCase = true) || 
            query.contains("advice", ignoreCase = true) ||
            query.contains("guide", ignoreCase = true) -> {
                Toast.makeText(this, "Searching tips for: $query", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, TipsActivity::class.java))
            }
            query.contains("weather", ignoreCase = true) ||
            query.contains("rain", ignoreCase = true) ||
            query.contains("temperature", ignoreCase = true) -> {
                Toast.makeText(this, "Weather information for: $query", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Searching for: $query", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun setupClickListeners() {
        // Original cards
        cardProducts.setOnClickListener {
            startActivity(Intent(this, ProductsActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        
        cardTips.setOnClickListener {
            startActivity(Intent(this, TipsActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        
        cardWeather.setOnClickListener {
            Toast.makeText(this, "Weather feature coming soon! ☀️", Toast.LENGTH_SHORT).show()
        }
        
        cardSupport.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        
        // New feature cards
        cardCropCalendar.setOnClickListener {
            Toast.makeText(this, "Crop Calendar feature coming soon! 📅", Toast.LENGTH_SHORT).show()
        }
        
        cardMarketPrices.setOnClickListener {
            Toast.makeText(this, "Market Prices feature coming soon! 📈", Toast.LENGTH_SHORT).show()
        }
        
        cardSoilHealth.setOnClickListener {
            Toast.makeText(this, "Soil Health feature coming soon! 🌱", Toast.LENGTH_SHORT).show()
        }
        
        cardPestControl.setOnClickListener {
            Toast.makeText(this, "Pest Control feature coming soon! 🐛", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun setupBottomNavigation() {
        bottomNavigation.selectedItemId = R.id.nav_home
        
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Already on home, do nothing or scroll to top
                    true
                }
                R.id.nav_products -> {
                    startActivity(Intent(this, ProductsActivity::class.java))
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    true
                }
                R.id.nav_tips -> {
                    startActivity(Intent(this, TipsActivity::class.java))
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    true
                }
                else -> false
            }
        }
    }
    
    private fun loadUserData() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Show loading state
            tvGreeting.text = getString(R.string.loading_user_data)
            
            // Fetch user data from Firestore
            firestore.collection("users")
                .document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val userName = document.getString("name")
                        if (!userName.isNullOrEmpty()) {
                            tvGreeting.text = getString(R.string.hello_user, userName)
                        } else {
                            tvGreeting.text = getString(R.string.welcome_back)
                        }
                    } else {
                        tvGreeting.text = getString(R.string.welcome_back)
                    }
                }
                .addOnFailureListener { exception ->
                    // Fallback to default greeting
                    tvGreeting.text = getString(R.string.welcome_back)
                    Toast.makeText(
                        this,
                        "Failed to load user data: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            tvGreeting.text = getString(R.string.welcome_back)
        }
    }
    
    private fun setupEnhancedWelcomeCard() {
        // Set time-based greeting
        val currentHour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        val timeGreeting = when (currentHour) {
            in 5..11 -> getString(R.string.good_morning)
            in 12..16 -> getString(R.string.good_afternoon)
            else -> getString(R.string.good_evening)
        }
        tvTimeGreeting.text = timeGreeting
        
        // Set notification count
        tvNotificationCount.text = "3"
        
        // Set weather info
        tvWeatherToday.text = "Today: 28°C, Sunny"
        
        // Set farm status
        tvFarmStatus.text = getString(R.string.crops_healthy)
    }
    
    private fun showRandomTip() {
        val randomTip = quickTips.random()
        tvQuickTip.text = randomTip
    }
    
    private fun showRandomRecommendation() {
        val randomRecommendation = farmingRecommendations.random()
        tvFarmingRecommendation.text = randomRecommendation
    }
    
    override fun onResume() {
        super.onResume()
        // Ensure home is selected when returning to this activity
        bottomNavigation.selectedItemId = R.id.nav_home
        // Show new random content when returning to home
        showRandomTip()
        showRandomRecommendation()
        setupEnhancedWelcomeCard()
    }
}
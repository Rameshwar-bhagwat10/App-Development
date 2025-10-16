package com.example.agrokrishiseva.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.agrokrishiseva.MainActivity
import com.example.agrokrishiseva.R
import com.example.agrokrishiseva.data.UserRepository
import com.example.agrokrishiseva.models.User
import com.example.agrokrishiseva.models.UserStats
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() {
    
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var ivProfileImage: ImageView
    private lateinit var tvUserName: TextView
    private lateinit var tvUserEmail: TextView
    private lateinit var tvJoinDate: TextView
    private lateinit var ivEditProfile: ImageView
    private lateinit var ivEmailVerified: ImageView
    private lateinit var ivPhoneVerified: ImageView
    private lateinit var tvPhoneStatus: TextView
    private lateinit var tvTipsViewed: TextView
    private lateinit var tvProductsViewed: TextView
    private lateinit var tvDaysActive: TextView
    private lateinit var cvFarmInfo: MaterialCardView
    private lateinit var ivEditFarmInfo: ImageView
    private lateinit var tvFarmLocation: TextView
    private lateinit var tvFarmSize: TextView
    private lateinit var tvFarmType: TextView
    private lateinit var tvExperience: TextView
    private lateinit var llBookmarkedTips: LinearLayout
    private lateinit var tvBookmarkCount: TextView
    private lateinit var llSettings: LinearLayout
    private lateinit var llHelpSupport: LinearLayout
    private lateinit var llShareApp: LinearLayout
    private lateinit var btnLogout: MaterialButton
    
    private val userRepository = UserRepository()
    private var currentUser: User? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        
        initViews()
        setupBottomNavigation()
        setupClickListeners()
        loadUserData()
    }
    
    override fun onResume() {
        super.onResume()
        loadUserData() // Refresh data when returning from edit profile
    }
    
    private fun initViews() {
        bottomNavigation = findViewById(R.id.bottomNavigation)
        ivProfileImage = findViewById(R.id.ivProfileImage)
        tvUserName = findViewById(R.id.tvUserName)
        tvUserEmail = findViewById(R.id.tvUserEmail)
        tvJoinDate = findViewById(R.id.tvJoinDate)
        ivEditProfile = findViewById(R.id.ivEditProfile)
        ivEmailVerified = findViewById(R.id.ivEmailVerified)
        ivPhoneVerified = findViewById(R.id.ivPhoneVerified)
        tvPhoneStatus = findViewById(R.id.tvPhoneStatus)
        tvTipsViewed = findViewById(R.id.tvTipsViewed)
        tvProductsViewed = findViewById(R.id.tvProductsViewed)
        tvDaysActive = findViewById(R.id.tvDaysActive)
        cvFarmInfo = findViewById(R.id.cvFarmInfo)
        ivEditFarmInfo = findViewById(R.id.ivEditFarmInfo)
        tvFarmLocation = findViewById(R.id.tvFarmLocation)
        tvFarmSize = findViewById(R.id.tvFarmSize)
        tvFarmType = findViewById(R.id.tvFarmType)
        tvExperience = findViewById(R.id.tvExperience)
        llBookmarkedTips = findViewById(R.id.llBookmarkedTips)
        tvBookmarkCount = findViewById(R.id.tvBookmarkCount)
        llSettings = findViewById(R.id.llSettings)
        llHelpSupport = findViewById(R.id.llHelpSupport)
        llShareApp = findViewById(R.id.llShareApp)
        btnLogout = findViewById(R.id.btnLogout)
    }
    
    private fun setupClickListeners() {
        ivEditProfile.setOnClickListener {
            openEditProfile()
        }
        
        ivEditFarmInfo.setOnClickListener {
            openEditProfile()
        }
        
        cvFarmInfo.setOnClickListener {
            openEditProfile()
        }
        
        llBookmarkedTips.setOnClickListener {
            openBookmarkedTips()
        }
        
        llSettings.setOnClickListener {
            showSettingsDialog()
        }
        
        llHelpSupport.setOnClickListener {
            showHelpDialog()
        }
        
        llShareApp.setOnClickListener {
            shareApp()
        }
        
        btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }
    }
    
    private fun loadUserData() {
        lifecycleScope.launch {
            try {
                currentUser = userRepository.getCurrentUser()
                val userStats = userRepository.getUserStats()
                
                runOnUiThread {
                    displayUserData(currentUser, userStats)
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@ProfileActivity, "Error loading profile data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun displayUserData(user: User?, stats: UserStats) {
        if (user != null) {
            // Basic Info
            tvUserName.text = user.fullName.ifEmpty { "User" }
            tvUserEmail.text = user.email
            
            // Join Date
            val dateFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())
            val joinDateText = "Member since ${dateFormat.format(Date(user.joinDate))}"
            tvJoinDate.text = joinDateText
            
            // Verification Status
            if (user.isEmailVerified) {
                ivEmailVerified.setColorFilter(getColor(R.color.success_green))
            } else {
                ivEmailVerified.setColorFilter(getColor(R.color.text_hint))
            }
            
            if (user.phoneNumber.isNotEmpty()) {
                if (user.isPhoneVerified) {
                    ivPhoneVerified.setColorFilter(getColor(R.color.success_green))
                    tvPhoneStatus.text = "Phone Verified"
                    tvPhoneStatus.setTextColor(getColor(R.color.success_green))
                } else {
                    ivPhoneVerified.setColorFilter(getColor(R.color.secondary_green))
                    tvPhoneStatus.text = "Verify Phone"
                    tvPhoneStatus.setTextColor(getColor(R.color.secondary_green))
                }
            } else {
                ivPhoneVerified.setColorFilter(getColor(R.color.text_hint))
                tvPhoneStatus.text = "Add Phone"
                tvPhoneStatus.setTextColor(getColor(R.color.text_hint))
            }
            
            // Stats
            tvTipsViewed.text = stats.tipsViewed.toString()
            tvProductsViewed.text = stats.productsViewed.toString()
            tvDaysActive.text = stats.daysActive.toString()
            
            // Farm Information
            tvFarmLocation.text = user.farmLocation.ifEmpty { "Not specified" }
            tvFarmSize.text = user.farmSize.ifEmpty { "Not specified" }
            tvFarmType.text = user.farmType.ifEmpty { "Not specified" }
            tvExperience.text = user.experience.ifEmpty { "Not specified" }
            
            // Bookmark Count
            tvBookmarkCount.text = "${stats.bookmarkedTips} tips"
        } else {
            // Default values if user data is not available
            tvUserName.text = "User"
            tvUserEmail.text = userRepository.getCurrentUserId() ?: "No email"
            tvJoinDate.text = "New member"
        }
    }
    
    private fun openEditProfile() {
        val intent = Intent(this, EditProfileActivity::class.java)
        startActivity(intent)
    }
    
    private fun openBookmarkedTips() {
        // Navigate to tips activity with bookmarked filter
        val intent = Intent(this, TipsActivity::class.java)
        intent.putExtra("show_bookmarked", true)
        startActivity(intent)
    }
    
    private fun showSettingsDialog() {
        val options = arrayOf(
            "Notifications",
            "Language",
            "Theme",
            "Privacy Policy",
            "Terms of Service"
        )
        
        AlertDialog.Builder(this)
            .setTitle("Settings")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> Toast.makeText(this, "Notification settings coming soon", Toast.LENGTH_SHORT).show()
                    1 -> Toast.makeText(this, "Language settings coming soon", Toast.LENGTH_SHORT).show()
                    2 -> Toast.makeText(this, "Theme settings coming soon", Toast.LENGTH_SHORT).show()
                    3 -> Toast.makeText(this, "Privacy Policy coming soon", Toast.LENGTH_SHORT).show()
                    4 -> Toast.makeText(this, "Terms of Service coming soon", Toast.LENGTH_SHORT).show()
                }
            }
            .show()
    }
    
    private fun showHelpDialog() {
        val options = arrayOf(
            "FAQ",
            "Contact Support",
            "Report a Bug",
            "Feature Request",
            "About App"
        )
        
        AlertDialog.Builder(this)
            .setTitle("Help & Support")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> Toast.makeText(this, "FAQ coming soon", Toast.LENGTH_SHORT).show()
                    1 -> contactSupport()
                    2 -> Toast.makeText(this, "Bug reporting coming soon", Toast.LENGTH_SHORT).show()
                    3 -> Toast.makeText(this, "Feature request coming soon", Toast.LENGTH_SHORT).show()
                    4 -> showAboutDialog()
                }
            }
            .show()
    }
    
    private fun contactSupport() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("support@agrokrishiseva.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Support Request - Agro Krishi Seva")
            putExtra(Intent.EXTRA_TEXT, "Hi Support Team,\n\nI need help with:\n\n")
        }
        
        try {
            startActivity(Intent.createChooser(intent, "Send Email"))
        } catch (e: Exception) {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showAboutDialog() {
        AlertDialog.Builder(this)
            .setTitle("About Agro Krishi Seva")
            .setMessage("Version 1.0.0\n\nAgro Krishi Seva is your comprehensive farming companion app providing expert tips, quality products, and farming guidance.\n\nDeveloped with ❤️ for farmers")
            .setPositiveButton("OK", null)
            .show()
    }
    
    private fun shareApp() {
        val shareText = "🌱 Check out Agro Krishi Seva - Your ultimate farming companion!\n\n" +
                "Get expert farming tips, quality agricultural products, and personalized guidance.\n\n" +
                "Download now: [App Store Link]"
        
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        
        startActivity(Intent.createChooser(intent, "Share Agro Krishi Seva"))
    }
    
    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout") { _, _ ->
                logout()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun logout() {
        lifecycleScope.launch {
            try {
                userRepository.signOut()
                
                runOnUiThread {
                    Toast.makeText(this@ProfileActivity, "Logged out successfully", Toast.LENGTH_SHORT).show()
                    
                    // Navigate back to login
                    val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@ProfileActivity, "Error logging out", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun setupBottomNavigation() {
        bottomNavigation.selectedItemId = R.id.nav_profile
        
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
                    startActivity(Intent(this, TipsActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_profile -> {
                    // Already on profile page
                    true
                }
                else -> false
            }
        }
    }
}
package com.example.agrokrishiseva.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.agrokrishiseva.R
import com.example.agrokrishiseva.data.UserRepository
import com.example.agrokrishiseva.models.ExperienceLevel
import com.example.agrokrishiseva.models.FarmType
import com.example.agrokrishiseva.models.User
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class EditProfileActivity : AppCompatActivity() {
    
    private lateinit var toolbar: MaterialToolbar
    private lateinit var etFullName: TextInputEditText
    private lateinit var etPhoneNumber: TextInputEditText
    private lateinit var etFarmLocation: TextInputEditText
    private lateinit var etFarmSize: TextInputEditText
    private lateinit var actvFarmType: AutoCompleteTextView
    private lateinit var actvExperience: AutoCompleteTextView
    private lateinit var btnSaveProfile: MaterialButton
    
    private val userRepository = UserRepository()
    private var currentUser: User? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        
        initViews()
        setupToolbar()
        setupDropdowns()
        loadUserData()
        setupClickListeners()
    }
    
    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        etFullName = findViewById(R.id.etFullName)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etFarmLocation = findViewById(R.id.etFarmLocation)
        etFarmSize = findViewById(R.id.etFarmSize)
        actvFarmType = findViewById(R.id.actvFarmType)
        actvExperience = findViewById(R.id.actvExperience)
        btnSaveProfile = findViewById(R.id.btnSaveProfile)
    }
    
    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Edit Profile"
    }
    
    private fun setupDropdowns() {
        // Farm Type Dropdown
        val farmTypes = FarmType.values().map { it.displayName }
        val farmTypeAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, farmTypes)
        actvFarmType.setAdapter(farmTypeAdapter)
        
        // Experience Dropdown
        val experienceLevels = ExperienceLevel.values().map { "${it.displayName} (${it.years})" }
        val experienceAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, experienceLevels)
        actvExperience.setAdapter(experienceAdapter)
    }
    
    private fun loadUserData() {
        lifecycleScope.launch {
            try {
                currentUser = userRepository.getCurrentUser()
                currentUser?.let { user ->
                    runOnUiThread {
                        etFullName.setText(user.fullName)
                        etPhoneNumber.setText(user.phoneNumber)
                        etFarmLocation.setText(user.farmLocation)
                        etFarmSize.setText(user.farmSize)
                        
                        // Set farm type if exists
                        if (user.farmType.isNotEmpty()) {
                            val farmType = FarmType.values().find { it.displayName == user.farmType }
                            farmType?.let { actvFarmType.setText(it.displayName, false) }
                        }
                        
                        // Set experience if exists
                        if (user.experience.isNotEmpty()) {
                            val experience = ExperienceLevel.values().find { it.displayName == user.experience }
                            experience?.let { 
                                actvExperience.setText("${it.displayName} (${it.years})", false) 
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@EditProfileActivity, "Error loading profile data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun setupClickListeners() {
        btnSaveProfile.setOnClickListener {
            saveProfile()
        }
    }
    
    private fun saveProfile() {
        val fullName = etFullName.text?.toString()?.trim() ?: ""
        val phoneNumber = etPhoneNumber.text?.toString()?.trim() ?: ""
        val farmLocation = etFarmLocation.text?.toString()?.trim() ?: ""
        val farmSize = etFarmSize.text?.toString()?.trim() ?: ""
        val farmTypeText = actvFarmType.text?.toString()?.trim() ?: ""
        val experienceText = actvExperience.text?.toString()?.trim() ?: ""
        
        // Validation
        if (fullName.isEmpty()) {
            etFullName.error = "Full name is required"
            etFullName.requestFocus()
            return
        }
        
        // Extract farm type
        val farmType = FarmType.values().find { it.displayName == farmTypeText }?.displayName ?: ""
        
        // Extract experience level
        val experience = ExperienceLevel.values().find { 
            experienceText.startsWith(it.displayName) 
        }?.displayName ?: ""
        
        // Update user object
        val updatedUser = currentUser?.copy(
            fullName = fullName,
            phoneNumber = phoneNumber,
            farmLocation = farmLocation,
            farmSize = farmSize,
            farmType = farmType,
            experience = experience
        ) ?: return
        
        // Save to Firebase
        lifecycleScope.launch {
            try {
                btnSaveProfile.isEnabled = false
                btnSaveProfile.text = "Saving..."
                
                val success = userRepository.updateUser(updatedUser)
                
                runOnUiThread {
                    btnSaveProfile.isEnabled = true
                    btnSaveProfile.text = "Save Profile"
                    
                    if (success) {
                        Toast.makeText(this@EditProfileActivity, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@EditProfileActivity, "Failed to update profile", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    btnSaveProfile.isEnabled = true
                    btnSaveProfile.text = "Save Profile"
                    Toast.makeText(this@EditProfileActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
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
}
package com.example.agrokrishiseva.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agrokrishiseva.MainActivity
import com.example.agrokrishiseva.R
import com.example.agrokrishiseva.models.User
import com.example.agrokrishiseva.utils.ValidationUtils
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.ProgressBar
import android.widget.TextView

class RegisterActivity : AppCompatActivity() {
    
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    
    // UI Components
    private lateinit var tilFullName: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var tilConfirmPassword: TextInputLayout
    private lateinit var etFullName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var btnRegister: MaterialButton
    private lateinit var progressBar: ProgressBar
    private lateinit var tvLoginLink: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        
        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        
        initViews()
        setupClickListeners()
    }
    
    private fun initViews() {
        tilFullName = findViewById(R.id.tilFullName)
        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword)
        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        progressBar = findViewById(R.id.progressBar)
        tvLoginLink = findViewById(R.id.tvLoginLink)
    }
    
    private fun setupClickListeners() {
        btnRegister.setOnClickListener {
            if (validateInputs()) {
                registerUser()
            }
        }
        
        tvLoginLink.setOnClickListener {
            navigateToLogin()
        }
    }
    
    private fun validateInputs(): Boolean {
        val fullName = etFullName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()
        
        var isValid = true
        
        // Validate full name
        val nameError = ValidationUtils.getNameError(fullName)
        if (nameError != null) {
            tilFullName.error = nameError
            isValid = false
        } else {
            tilFullName.error = null
        }
        
        // Validate email
        val emailError = ValidationUtils.getEmailError(email)
        if (emailError != null) {
            tilEmail.error = emailError
            isValid = false
        } else {
            tilEmail.error = null
        }
        
        // Validate password
        val passwordError = ValidationUtils.getPasswordError(password)
        if (passwordError != null) {
            tilPassword.error = passwordError
            isValid = false
        } else {
            tilPassword.error = null
        }
        
        // Validate confirm password
        val confirmPasswordError = ValidationUtils.getConfirmPasswordError(password, confirmPassword)
        if (confirmPasswordError != null) {
            tilConfirmPassword.error = confirmPasswordError
            isValid = false
        } else {
            tilConfirmPassword.error = null
        }
        
        return isValid
    }
    
    private fun registerUser() {
        val fullName = etFullName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        
        setLoadingState(true)
        
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration successful, now save user data to Firestore
                    val firebaseUser = auth.currentUser
                    if (firebaseUser != null) {
                        saveUserToFirestore(firebaseUser.uid, fullName, email)
                    } else {
                        setLoadingState(false)
                        Toast.makeText(this, getString(R.string.error_registration_failed), Toast.LENGTH_LONG).show()
                    }
                } else {
                    setLoadingState(false)
                    // Registration failed
                    val errorMessage = when (task.exception?.message) {
                        "The email address is already in use by another account." -> getString(R.string.error_email_already_exists)
                        "The given password is invalid. [ Password should be at least 6 characters ]" -> getString(R.string.error_weak_password)
                        "The email address is badly formatted." -> getString(R.string.error_invalid_email)
                        "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> getString(R.string.error_network)
                        else -> getString(R.string.error_registration_failed)
                    }
                    
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
    }
    
    private fun saveUserToFirestore(uid: String, fullName: String, email: String) {
        val user = User(
            uid = uid,
            name = fullName,
            email = email,
            createdAt = System.currentTimeMillis()
        )
        
        firestore.collection("users")
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                setLoadingState(false)
                Toast.makeText(
                    this,
                    getString(R.string.registration_successful),
                    Toast.LENGTH_SHORT
                ).show()
                navigateToMain()
            }
            .addOnFailureListener { exception ->
                setLoadingState(false)
                Toast.makeText(
                    this,
                    "Failed to save user data: ${exception.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }
    
    private fun setLoadingState(isLoading: Boolean) {
        if (isLoading) {
            btnRegister.text = ""
            btnRegister.isEnabled = false
            progressBar.visibility = View.VISIBLE
        } else {
            btnRegister.text = getString(R.string.register)
            btnRegister.isEnabled = true
            progressBar.visibility = View.GONE
        }
    }
    
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    private fun navigateToLogin() {
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
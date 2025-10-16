package com.example.agrokrishiseva.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agrokrishiseva.MainActivity
import com.example.agrokrishiseva.R
import com.example.agrokrishiseva.utils.ValidationUtils
import com.example.agrokrishiseva.data.UserRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    
    private lateinit var auth: FirebaseAuth
    private val userRepository = UserRepository()
    
    // UI Components
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var progressBar: ProgressBar
    private lateinit var tvRegisterLink: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        
        // Check if user is already logged in
        if (auth.currentUser != null) {
            navigateToMain()
            return
        }
        
        initViews()
        setupClickListeners()
    }
    
    private fun initViews() {
        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        progressBar = findViewById(R.id.progressBar)
        tvRegisterLink = findViewById(R.id.tvRegisterLink)
    }
    
    private fun setupClickListeners() {
        btnLogin.setOnClickListener {
            if (validateInputs()) {
                loginUser()
            }
        }
        
        tvRegisterLink.setOnClickListener {
            navigateToRegister()
        }
    }
    
    private fun validateInputs(): Boolean {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        
        var isValid = true
        
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
        
        return isValid
    }
    
    private fun loginUser() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        
        setLoadingState(true)
        
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                setLoadingState(false)
                
                if (task.isSuccessful) {
                    // Login successful - update last login date
                    lifecycleScope.launch {
                        userRepository.updateLastLoginDate()
                    }
                    
                    Toast.makeText(
                        this,
                        getString(R.string.login_successful),
                        Toast.LENGTH_SHORT
                    ).show()
                    navigateToMain()
                } else {
                    // Login failed
                    val errorMessage = when (task.exception?.message) {
                        "The email address is badly formatted." -> getString(R.string.error_invalid_email)
                        "There is no user record corresponding to this identifier. The user may have been deleted." -> "No account found with this email"
                        "The password is invalid or the user does not have a password." -> "Invalid password"
                        "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> getString(R.string.error_network)
                        else -> getString(R.string.error_login_failed)
                    }
                    
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
    }
    
    private fun setLoadingState(isLoading: Boolean) {
        if (isLoading) {
            btnLogin.text = ""
            btnLogin.isEnabled = false
            progressBar.visibility = View.VISIBLE
        } else {
            btnLogin.text = getString(R.string.login)
            btnLogin.isEnabled = true
            progressBar.visibility = View.GONE
        }
    }
    
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
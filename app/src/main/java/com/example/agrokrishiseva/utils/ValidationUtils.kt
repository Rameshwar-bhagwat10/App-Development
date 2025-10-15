package com.example.agrokrishiseva.utils

import android.util.Patterns

object ValidationUtils {
    
    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    
    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
    
    fun isValidName(name: String): Boolean {
        return name.trim().length >= 2
    }
    
    fun doPasswordsMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
    
    fun getEmailError(email: String): String? {
        return when {
            email.isEmpty() -> "Email is required"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Please enter a valid email"
            else -> null
        }
    }
    
    fun getPasswordError(password: String): String? {
        return when {
            password.isEmpty() -> "Password is required"
            password.length < 6 -> "Password must be at least 6 characters"
            else -> null
        }
    }
    
    fun getNameError(name: String): String? {
        return when {
            name.trim().isEmpty() -> "Name is required"
            name.trim().length < 2 -> "Name must be at least 2 characters"
            else -> null
        }
    }
    
    fun getConfirmPasswordError(password: String, confirmPassword: String): String? {
        return when {
            confirmPassword.isEmpty() -> "Please confirm your password"
            password != confirmPassword -> "Passwords do not match"
            else -> null
        }
    }
}
package com.example.agrokrishiseva.models

data class User(
    val uid: String = "",
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val farmLocation: String = "",
    val farmSize: String = "",
    val farmType: String = "",
    val experience: String = "",
    val profileImageUrl: String = "",
    val joinDate: Long = System.currentTimeMillis(),
    val lastLoginDate: Long = System.currentTimeMillis(),
    val totalTipsViewed: Int = 0,
    val totalProductsViewed: Int = 0,
    val bookmarkedTips: List<String> = emptyList(),
    val favoriteProducts: List<String> = emptyList(),
    val achievements: List<String> = emptyList(),
    val isEmailVerified: Boolean = false,
    val isPhoneVerified: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val language: String = "English",
    val theme: String = "Light"
) {
    // No-argument constructor for Firebase
    constructor() : this(
        uid = "",
        fullName = "",
        email = "",
        phoneNumber = "",
        farmLocation = "",
        farmSize = "",
        farmType = "",
        experience = "",
        profileImageUrl = "",
        joinDate = System.currentTimeMillis(),
        lastLoginDate = System.currentTimeMillis(),
        totalTipsViewed = 0,
        totalProductsViewed = 0,
        bookmarkedTips = emptyList(),
        favoriteProducts = emptyList(),
        achievements = emptyList(),
        isEmailVerified = false,
        isPhoneVerified = false,
        notificationsEnabled = true,
        language = "English",
        theme = "Light"
    )
}

data class UserStats(
    val tipsViewed: Int = 0,
    val productsViewed: Int = 0,
    val bookmarkedTips: Int = 0,
    val favoriteProducts: Int = 0,
    val daysActive: Int = 0,
    val achievementsUnlocked: Int = 0
)

enum class FarmType(val displayName: String) {
    ORGANIC("Organic Farming"),
    TRADITIONAL("Traditional Farming"),
    HYDROPONIC("Hydroponic Farming"),
    MIXED("Mixed Farming"),
    LIVESTOCK("Livestock Farming"),
    DAIRY("Dairy Farming"),
    POULTRY("Poultry Farming"),
    OTHER("Other")
}

enum class ExperienceLevel(val displayName: String, val years: String) {
    BEGINNER("Beginner", "0-2 years"),
    INTERMEDIATE("Intermediate", "3-5 years"),
    EXPERIENCED("Experienced", "6-10 years"),
    EXPERT("Expert", "10+ years")
}
package com.example.agrokrishiseva.models

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val currentPrice: Double,
    val originalPrice: Double = 0.0,
    val imageUrl: String = "",
    val rating: Float = 0.0f,
    val reviewCount: Int = 0,
    val inStock: Boolean = true,
    val discount: Int = 0,
    var isFavorite: Boolean = false,
    val features: List<String> = emptyList(),
    val unit: String = "kg"
) {
    val hasDiscount: Boolean
        get() = originalPrice > 0 && originalPrice > currentPrice
    
    val discountPercentage: Int
        get() = if (hasDiscount) {
            ((originalPrice - currentPrice) / originalPrice * 100).toInt()
        } else 0
    
    val savings: Double
        get() = if (hasDiscount) originalPrice - currentPrice else 0.0
}
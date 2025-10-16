package com.example.agrokrishiseva.models

data class Category(
    val id: String,
    val name: String,
    val iconRes: Int,
    val productCount: Int,
    val isSelected: Boolean = false
)
package com.example.agrokrishiseva.models

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val createdAt: Long = System.currentTimeMillis()
) {
    // No-argument constructor for Firestore
    constructor() : this("", "", "", 0L)
}
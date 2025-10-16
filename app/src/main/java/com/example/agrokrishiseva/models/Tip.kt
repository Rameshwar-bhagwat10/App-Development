package com.example.agrokrishiseva.models

data class Tip(
    val id: String,
    val title: String,
    val shortDescription: String,
    val fullDescription: String,
    val category: TipCategory,
    val imageResource: Int,
    val difficulty: TipDifficulty,
    val estimatedTime: String,
    val season: String,
    val tags: List<String>,
    val steps: List<TipStep>,
    val benefits: List<String>,
    val requirements: List<String>,
    val isBookmarked: Boolean = false,
    val rating: Float = 0f,
    val viewCount: Int = 0
)

data class TipStep(
    val stepNumber: Int,
    val title: String,
    val description: String,
    val imageResource: Int? = null
)

enum class TipCategory(val displayName: String, val icon: Int) {
    CROPS("Crops", android.R.drawable.ic_menu_gallery),
    FERTILIZERS("Fertilizers", android.R.drawable.ic_menu_info_details),
    SEEDS("Seeds", android.R.drawable.ic_menu_gallery),
    IRRIGATION("Irrigation", android.R.drawable.ic_menu_compass),
    PEST_CONTROL("Pest Control", android.R.drawable.ic_menu_delete),
    SOIL_HEALTH("Soil Health", android.R.drawable.ic_menu_mapmode),
    WEATHER("Weather", android.R.drawable.ic_menu_day),
    HARVESTING("Harvesting", android.R.drawable.ic_menu_crop)
}

enum class TipDifficulty(val displayName: String, val color: Int) {
    BEGINNER("Beginner", android.R.color.holo_green_light),
    INTERMEDIATE("Intermediate", android.R.color.holo_orange_light),
    ADVANCED("Advanced", android.R.color.holo_red_light)
}
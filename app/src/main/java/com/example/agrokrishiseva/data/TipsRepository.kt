package com.example.agrokrishiseva.data

import com.example.agrokrishiseva.R
import com.example.agrokrishiseva.models.*

object TipsRepository {
    
    fun getAllTips(): List<Tip> {
        return listOf(
            // Crops Tips
            Tip(
                id = "crop_001",
                title = "Rice Cultivation Best Practices",
                shortDescription = "Learn the essential techniques for successful rice farming",
                fullDescription = "Rice is one of the most important staple crops. Proper cultivation techniques can significantly increase your yield and quality. This comprehensive guide covers everything from seed selection to harvesting.",
                category = TipCategory.CROPS,
                imageResource = R.drawable.ic_eco,
                difficulty = TipDifficulty.INTERMEDIATE,
                estimatedTime = "3-4 months",
                season = "Monsoon",
                tags = listOf("rice", "paddy", "monsoon", "irrigation"),
                steps = listOf(
                    TipStep(1, "Land Preparation", "Prepare the field by plowing and leveling. Ensure proper drainage systems."),
                    TipStep(2, "Seed Selection", "Choose high-quality, disease-resistant rice varieties suitable for your region."),
                    TipStep(3, "Transplanting", "Transplant 20-25 day old seedlings with proper spacing."),
                    TipStep(4, "Water Management", "Maintain 2-3 cm water level throughout the growing period."),
                    TipStep(5, "Fertilizer Application", "Apply fertilizers in split doses - basal, tillering, and panicle stages.")
                ),
                benefits = listOf(
                    "Higher yield potential",
                    "Better grain quality",
                    "Reduced pest and disease incidence",
                    "Efficient water usage"
                ),
                requirements = listOf(
                    "Well-leveled field",
                    "Quality seeds",
                    "Adequate water supply",
                    "Fertilizers (NPK)",
                    "Basic farming tools"
                ),
                rating = 4.5f,
                viewCount = 1250
            ),
            
            Tip(
                id = "crop_002",
                title = "Wheat Growing Guide",
                shortDescription = "Complete guide for wheat cultivation and management",
                fullDescription = "Wheat is a major cereal crop. This guide provides detailed information on growing wheat successfully with maximum yield and quality.",
                category = TipCategory.CROPS,
                imageResource = R.drawable.ic_trending_up,
                difficulty = TipDifficulty.BEGINNER,
                estimatedTime = "4-5 months",
                season = "Winter",
                tags = listOf("wheat", "winter", "rabi", "cereal"),
                steps = listOf(
                    TipStep(1, "Soil Preparation", "Prepare well-drained, fertile soil with pH 6.0-7.5"),
                    TipStep(2, "Sowing", "Sow seeds at proper depth and spacing during optimal time"),
                    TipStep(3, "Irrigation", "Provide irrigation at critical growth stages"),
                    TipStep(4, "Weed Control", "Control weeds through mechanical or chemical methods"),
                    TipStep(5, "Harvesting", "Harvest when grains are fully mature and dry")
                ),
                benefits = listOf(
                    "High nutritional value",
                    "Good market demand",
                    "Suitable for mechanization",
                    "Multiple varieties available"
                ),
                requirements = listOf(
                    "Well-drained soil",
                    "Quality wheat seeds",
                    "Irrigation facility",
                    "Fertilizers",
                    "Harvesting equipment"
                ),
                rating = 4.3f,
                viewCount = 980
            ),
            
            // Fertilizer Tips
            Tip(
                id = "fert_001",
                title = "Organic Fertilizer Preparation",
                shortDescription = "Make your own organic fertilizers at home",
                fullDescription = "Learn how to prepare nutrient-rich organic fertilizers using kitchen waste and farm residues. This eco-friendly approach improves soil health and reduces costs.",
                category = TipCategory.FERTILIZERS,
                imageResource = R.drawable.ic_eco,
                difficulty = TipDifficulty.BEGINNER,
                estimatedTime = "2-3 weeks",
                season = "All seasons",
                tags = listOf("organic", "compost", "eco-friendly", "sustainable"),
                steps = listOf(
                    TipStep(1, "Collect Materials", "Gather kitchen waste, dry leaves, and farm residues"),
                    TipStep(2, "Layer Formation", "Create alternating layers of green and brown materials"),
                    TipStep(3, "Add Activators", "Add cow dung or compost activator for faster decomposition"),
                    TipStep(4, "Maintain Moisture", "Keep the pile moist but not waterlogged"),
                    TipStep(5, "Turn Regularly", "Turn the pile every 2 weeks for proper aeration")
                ),
                benefits = listOf(
                    "Improves soil structure",
                    "Increases water retention",
                    "Provides slow-release nutrients",
                    "Reduces chemical dependency",
                    "Cost-effective solution"
                ),
                requirements = listOf(
                    "Kitchen waste",
                    "Dry leaves/straw",
                    "Cow dung",
                    "Water source",
                    "Composting area"
                ),
                rating = 4.7f,
                viewCount = 2100
            ),
            
            // Seeds Tips
            Tip(
                id = "seed_001",
                title = "Seed Treatment Techniques",
                shortDescription = "Protect your seeds from diseases and pests",
                fullDescription = "Proper seed treatment is crucial for healthy crop establishment. Learn various seed treatment methods to protect against soil-borne diseases and pests.",
                category = TipCategory.SEEDS,
                imageResource = R.drawable.ic_favorite,
                difficulty = TipDifficulty.INTERMEDIATE,
                estimatedTime = "1-2 hours",
                season = "Before sowing",
                tags = listOf("seed treatment", "disease control", "fungicide", "protection"),
                steps = listOf(
                    TipStep(1, "Seed Selection", "Choose healthy, certified seeds from reliable sources"),
                    TipStep(2, "Physical Treatment", "Remove damaged, discolored, or lightweight seeds"),
                    TipStep(3, "Chemical Treatment", "Treat seeds with appropriate fungicides or insecticides"),
                    TipStep(4, "Biological Treatment", "Use bio-agents like Trichoderma for organic treatment"),
                    TipStep(5, "Drying", "Dry treated seeds in shade before storage or sowing")
                ),
                benefits = listOf(
                    "Prevents seed-borne diseases",
                    "Improves germination rate",
                    "Reduces crop losses",
                    "Better plant establishment",
                    "Higher yield potential"
                ),
                requirements = listOf(
                    "Quality seeds",
                    "Fungicides/insecticides",
                    "Protective equipment",
                    "Clean water",
                    "Drying facility"
                ),
                rating = 4.4f,
                viewCount = 850
            ),
            
            // Irrigation Tips
            Tip(
                id = "irr_001",
                title = "Drip Irrigation Setup",
                shortDescription = "Install efficient drip irrigation system",
                fullDescription = "Drip irrigation is the most efficient water delivery system. Learn how to set up and maintain a drip irrigation system for your crops.",
                category = TipCategory.IRRIGATION,
                imageResource = R.drawable.ic_trending_up,
                difficulty = TipDifficulty.ADVANCED,
                estimatedTime = "1-2 days",
                season = "All seasons",
                tags = listOf("drip irrigation", "water efficiency", "modern farming", "technology"),
                steps = listOf(
                    TipStep(1, "System Design", "Plan the layout based on crop spacing and field dimensions"),
                    TipStep(2, "Main Line Installation", "Install main water supply line with pressure regulator"),
                    TipStep(3, "Lateral Lines", "Connect lateral lines with emitters at plant locations"),
                    TipStep(4, "Filtration System", "Install filters to prevent clogging of emitters"),
                    TipStep(5, "Testing", "Test the system for uniform water distribution")
                ),
                benefits = listOf(
                    "90% water use efficiency",
                    "Reduced weed growth",
                    "Better nutrient uptake",
                    "Labor saving",
                    "Higher crop quality"
                ),
                requirements = listOf(
                    "Water source",
                    "Drip irrigation kit",
                    "Pressure pump",
                    "Filters",
                    "Basic tools"
                ),
                rating = 4.6f,
                viewCount = 1500
            ),
            
            // Pest Control Tips
            Tip(
                id = "pest_001",
                title = "Integrated Pest Management",
                shortDescription = "Eco-friendly approach to pest control",
                fullDescription = "Learn sustainable pest management strategies that combine biological, cultural, and chemical methods for effective pest control while protecting the environment.",
                category = TipCategory.PEST_CONTROL,
                imageResource = R.drawable.ic_search,
                difficulty = TipDifficulty.INTERMEDIATE,
                estimatedTime = "Ongoing",
                season = "All seasons",
                tags = listOf("IPM", "sustainable", "biological control", "pest management"),
                steps = listOf(
                    TipStep(1, "Monitoring", "Regular field monitoring to identify pest presence and damage"),
                    TipStep(2, "Cultural Control", "Use crop rotation, resistant varieties, and proper sanitation"),
                    TipStep(3, "Biological Control", "Introduce beneficial insects and use bio-pesticides"),
                    TipStep(4, "Mechanical Control", "Use traps, barriers, and physical removal methods"),
                    TipStep(5, "Chemical Control", "Use pesticides only when necessary and as last resort")
                ),
                benefits = listOf(
                    "Reduced pesticide use",
                    "Environmental protection",
                    "Cost-effective",
                    "Sustainable farming",
                    "Preserved beneficial insects"
                ),
                requirements = listOf(
                    "Monitoring tools",
                    "Knowledge of pests",
                    "Beneficial insects",
                    "Traps and barriers",
                    "Selective pesticides"
                ),
                rating = 4.8f,
                viewCount = 1800
            )
        )
    }
    
    fun getTipsByCategory(category: TipCategory): List<Tip> {
        return getAllTips().filter { it.category == category }
    }
    
    fun getTipById(id: String): Tip? {
        return getAllTips().find { it.id == id }
    }
    
    fun getPopularTips(): List<Tip> {
        return getAllTips().sortedByDescending { it.viewCount }.take(5)
    }
    
    fun getRecentTips(): List<Tip> {
        return getAllTips().take(3)
    }
}
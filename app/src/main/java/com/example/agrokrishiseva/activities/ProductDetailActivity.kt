package com.example.agrokrishiseva.activities

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.agrokrishiseva.R
import com.example.agrokrishiseva.models.Product
import com.google.android.material.button.MaterialButton

class ProductDetailActivity : AppCompatActivity() {
    
    private lateinit var ivBack: ImageView
    private lateinit var ivShare: ImageView
    private lateinit var ivFavorite: ImageView
    private lateinit var tvDiscountBadge: TextView
    private lateinit var tvProductCategory: TextView
    private lateinit var tvStockStatus: TextView
    private lateinit var tvProductName: TextView
    private lateinit var tvRating: TextView
    private lateinit var tvReviewsCount: TextView
    private lateinit var tvCurrentPrice: TextView
    private lateinit var tvOriginalPrice: TextView
    private lateinit var tvPriceUnit: TextView
    private lateinit var tvSavings: TextView
    private lateinit var tvProductDescription: TextView
    private lateinit var ivDecreaseQuantity: ImageView
    private lateinit var tvQuantity: TextView
    private lateinit var ivIncreaseQuantity: ImageView
    private lateinit var tvTotalPrice: TextView
    private lateinit var btnAddToCart: MaterialButton
    private lateinit var btnBuyNow: MaterialButton
    
    private var currentProduct: Product? = null
    private var quantity = 1
    private var isFavorite = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        
        initViews()
        setupClickListeners()
        loadProductData()
    }
    
    private fun initViews() {
        ivBack = findViewById(R.id.ivBack)
        ivShare = findViewById(R.id.ivShare)
        ivFavorite = findViewById(R.id.ivFavorite)
        tvDiscountBadge = findViewById(R.id.tvDiscountBadge)
        tvProductCategory = findViewById(R.id.tvProductCategory)
        tvStockStatus = findViewById(R.id.tvStockStatus)
        tvProductName = findViewById(R.id.tvProductName)
        tvRating = findViewById(R.id.tvRating)
        tvReviewsCount = findViewById(R.id.tvReviewsCount)
        tvCurrentPrice = findViewById(R.id.tvCurrentPrice)
        tvOriginalPrice = findViewById(R.id.tvOriginalPrice)
        tvPriceUnit = findViewById(R.id.tvPriceUnit)
        tvSavings = findViewById(R.id.tvSavings)
        tvProductDescription = findViewById(R.id.tvProductDescription)
        ivDecreaseQuantity = findViewById(R.id.ivDecreaseQuantity)
        tvQuantity = findViewById(R.id.tvQuantity)
        ivIncreaseQuantity = findViewById(R.id.ivIncreaseQuantity)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        btnAddToCart = findViewById(R.id.btnAddToCart)
        btnBuyNow = findViewById(R.id.btnBuyNow)
    }
    
    private fun setupClickListeners() {
        ivBack.setOnClickListener {
            onBackPressed()
        }
        
        ivShare.setOnClickListener {
            shareProduct()
        }
        
        ivFavorite.setOnClickListener {
            toggleFavorite()
        }
        
        ivDecreaseQuantity.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateQuantityDisplay()
            }
        }
        
        ivIncreaseQuantity.setOnClickListener {
            quantity++
            updateQuantityDisplay()
        }
        
        btnAddToCart.setOnClickListener {
            addToCart()
        }
        
        btnBuyNow.setOnClickListener {
            buyNow()
        }
    }
    
    private fun loadProductData() {
        // Get product data from intent
        val productId = intent.getStringExtra("product_id") ?: "1"
        val productName = intent.getStringExtra("product_name")
        val productDescription = intent.getStringExtra("product_description")
        val productCategory = intent.getStringExtra("product_category")
        val productCurrentPrice = intent.getDoubleExtra("product_current_price", 0.0)
        val productOriginalPrice = intent.getDoubleExtra("product_original_price", 0.0)
        val productRating = intent.getFloatExtra("product_rating", 0.0f)
        val productReviewCount = intent.getIntExtra("product_review_count", 0)
        val productInStock = intent.getBooleanExtra("product_in_stock", true)
        val productDiscount = intent.getIntExtra("product_discount", 0)
        val productUnit = intent.getStringExtra("product_unit") ?: "piece"
        
        // Create product from intent data or fallback to sample data
        currentProduct = if (productName != null) {
            Product(
                id = productId,
                name = productName,
                description = productDescription ?: "High-quality agricultural product",
                category = productCategory ?: "General",
                currentPrice = productCurrentPrice,
                originalPrice = productOriginalPrice,
                imageUrl = "",
                rating = productRating,
                reviewCount = productReviewCount,
                inStock = productInStock,
                discount = productDiscount,
                isFavorite = false,
                features = getProductFeatures(productId),
                unit = productUnit
            )
        } else {
            createSampleProduct(productId)
        }
        
        currentProduct?.let { product ->
            displayProductDetails(product)
        }
    }
    
    private fun getProductFeatures(productId: String): List<String> {
        return when (productId) {
            "1" -> listOf(
                "High germination rate (95%+)",
                "Disease resistant variety",
                "Suitable for all seasons",
                "Maximum yield potential"
            )
            "2" -> listOf(
                "100% organic and natural",
                "Improves soil fertility",
                "Safe for all crops",
                "Environmentally friendly"
            )
            "3" -> listOf(
                "Heavy-duty steel construction",
                "Ergonomic handle design",
                "Rust-resistant coating",
                "Professional grade quality"
            )
            "4" -> listOf(
                "Authentic basmati variety",
                "Aromatic long grains",
                "High market value",
                "Disease resistant"
            )
            "5" -> listOf(
                "Balanced NPK formula",
                "Promotes healthy growth",
                "Suitable for all crops",
                "Easy to apply"
            )
            "6" -> listOf(
                "Sharp stainless steel blades",
                "Precision cutting design",
                "Comfortable grip handles",
                "Durable construction"
            )
            else -> listOf(
                "High quality",
                "Reliable performance",
                "Good value for money"
            )
        }
    }
    
    private fun createSampleProduct(productId: String): Product {
        return when (productId) {
            "1" -> Product(
                id = productId,
                name = "Premium Wheat Seeds - High Yield Variety",
                description = "High-quality wheat seeds with excellent germination rate. Suitable for all soil types and weather conditions. These premium seeds ensure maximum yield and disease resistance. Perfect for both small-scale and commercial farming operations.",
                category = "Seeds",
                currentPrice = 299.0,
                originalPrice = 399.0,
                imageUrl = "",
                rating = 4.2f,
                reviewCount = 128,
                inStock = true,
                discount = 25,
                isFavorite = false,
                features = listOf(
                    "High germination rate (95%+)",
                    "Disease resistant variety",
                    "Suitable for all seasons",
                    "Maximum yield potential"
                ),
                unit = "kg"
            )
            "2" -> Product(
                id = productId,
                name = "Organic Fertilizer - 100% Natural",
                description = "Premium organic fertilizer made from natural compost and bio-waste. Enriches soil with essential nutrients and improves soil structure. Safe for all crops and environmentally friendly. Promotes healthy plant growth and increases yield naturally.",
                category = "Fertilizers",
                currentPrice = 450.0,
                originalPrice = 500.0,
                imageUrl = "",
                rating = 4.5f,
                reviewCount = 89,
                inStock = true,
                discount = 10,
                isFavorite = false,
                features = listOf(
                    "100% organic and natural",
                    "Improves soil fertility",
                    "Safe for all crops",
                    "Environmentally friendly"
                ),
                unit = "kg"
            )
            "3" -> Product(
                id = productId,
                name = "Professional Garden Spade",
                description = "Heavy-duty steel spade designed for professional farming and gardening. Ergonomic handle reduces strain during long working hours. Rust-resistant coating ensures durability. Perfect for digging, planting, and soil preparation.",
                category = "Tools",
                currentPrice = 199.0,
                originalPrice = 249.0,
                imageUrl = "",
                rating = 4.0f,
                reviewCount = 45,
                inStock = true,
                discount = 20,
                isFavorite = false,
                features = listOf(
                    "Heavy-duty steel construction",
                    "Ergonomic handle design",
                    "Rust-resistant coating",
                    "Professional grade quality"
                ),
                unit = "piece"
            )
            "4" -> Product(
                id = productId,
                name = "Basmati Rice Seeds - Premium Quality",
                description = "Authentic basmati rice seeds known for their aromatic fragrance and long grains. High-quality seeds with excellent cooking properties. Suitable for commercial cultivation with good market demand. Disease-resistant variety with consistent yield.",
                category = "Seeds",
                currentPrice = 599.0,
                originalPrice = 699.0,
                imageUrl = "",
                rating = 4.7f,
                reviewCount = 203,
                inStock = true,
                discount = 14,
                isFavorite = false,
                features = listOf(
                    "Authentic basmati variety",
                    "Aromatic long grains",
                    "High market value",
                    "Disease resistant"
                ),
                unit = "kg"
            )
            "5" -> Product(
                id = productId,
                name = "NPK Fertilizer - Balanced Nutrition",
                description = "Complete NPK fertilizer providing balanced nutrition for all types of crops. Contains essential nitrogen, phosphorus, and potassium in optimal ratios. Promotes healthy root development, flowering, and fruiting. Suitable for both indoor and outdoor cultivation.",
                category = "Fertilizers",
                currentPrice = 350.0,
                originalPrice = 400.0,
                imageUrl = "",
                rating = 4.3f,
                reviewCount = 156,
                inStock = false,
                discount = 12,
                isFavorite = false,
                features = listOf(
                    "Balanced NPK formula",
                    "Promotes healthy growth",
                    "Suitable for all crops",
                    "Easy to apply"
                ),
                unit = "kg"
            )
            "6" -> Product(
                id = productId,
                name = "Professional Pruning Shears",
                description = "High-quality pruning shears designed for precision cutting and trimming. Sharp stainless steel blades ensure clean cuts without damaging plants. Comfortable grip handles reduce hand fatigue. Essential tool for maintaining healthy plants and trees.",
                category = "Tools",
                currentPrice = 299.0,
                originalPrice = 0.0,
                imageUrl = "",
                rating = 4.1f,
                reviewCount = 67,
                inStock = true,
                discount = 0,
                isFavorite = false,
                features = listOf(
                    "Sharp stainless steel blades",
                    "Precision cutting design",
                    "Comfortable grip handles",
                    "Durable construction"
                ),
                unit = "piece"
            )
            else -> Product(
                id = productId,
                name = "Premium Agricultural Product",
                description = "High-quality agricultural product for modern farming needs.",
                category = "General",
                currentPrice = 199.0,
                originalPrice = 0.0,
                imageUrl = "",
                rating = 4.0f,
                reviewCount = 50,
                inStock = true,
                discount = 0,
                isFavorite = false,
                features = listOf(
                    "High quality",
                    "Reliable performance",
                    "Good value for money"
                ),
                unit = "piece"
            )
        }
    }
    
    private fun displayProductDetails(product: Product) {
        // Set discount badge
        if (product.hasDiscount) {
            tvDiscountBadge.visibility = View.VISIBLE
            tvDiscountBadge.text = "${product.discountPercentage}% OFF"
        } else {
            tvDiscountBadge.visibility = View.GONE
        }
        
        // Set product details
        tvProductCategory.text = product.category
        tvProductName.text = product.name
        tvRating.text = product.rating.toString()
        tvReviewsCount.text = "(${product.reviewCount} reviews)"
        tvProductDescription.text = product.description
        
        // Set stock status
        if (product.inStock) {
            tvStockStatus.text = "✓ In Stock"
            tvStockStatus.setTextColor(getColor(R.color.success_green))
            btnAddToCart.isEnabled = true
            btnBuyNow.isEnabled = true
        } else {
            tvStockStatus.text = "✗ Out of Stock"
            tvStockStatus.setTextColor(getColor(R.color.error_red))
            btnAddToCart.isEnabled = false
            btnBuyNow.isEnabled = false
        }
        
        // Set prices
        tvCurrentPrice.text = "₹${product.currentPrice.toInt()}"
        tvPriceUnit.text = "/${product.unit}"
        
        if (product.hasDiscount) {
            tvOriginalPrice.visibility = View.VISIBLE
            tvOriginalPrice.text = "₹${product.originalPrice.toInt()}"
            tvOriginalPrice.paintFlags = tvOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            
            tvSavings.visibility = View.VISIBLE
            tvSavings.text = "Save ₹${product.savings.toInt()}"
        } else {
            tvOriginalPrice.visibility = View.GONE
            tvSavings.visibility = View.GONE
        }
        
        // Set favorite status
        isFavorite = product.isFavorite
        updateFavoriteIcon()
        
        // Update quantity display
        updateQuantityDisplay()
    }
    
    private fun updateQuantityDisplay() {
        tvQuantity.text = quantity.toString()
        currentProduct?.let { product ->
            val totalPrice = product.currentPrice * quantity
            tvTotalPrice.text = "₹${totalPrice.toInt()}"
        }
    }
    
    private fun toggleFavorite() {
        isFavorite = !isFavorite
        updateFavoriteIcon()
        
        // Update product in database/preferences
        currentProduct?.isFavorite = isFavorite
    }
    
    private fun updateFavoriteIcon() {
        ivFavorite.setImageResource(
            if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        )
        ivFavorite.setColorFilter(
            if (isFavorite) getColor(R.color.error_red) else getColor(R.color.text_primary)
        )
    }
    
    private fun shareProduct() {
        currentProduct?.let { product ->
            val shareText = "Check out this amazing product: ${product.name}\n" +
                    "Price: ₹${product.currentPrice.toInt()}/${product.unit}\n" +
                    "Rating: ${product.rating}/5 (${product.reviewCount} reviews)\n" +
                    "Get it from Agro Krishi Seva app!"
            
            val shareIntent = android.content.Intent().apply {
                action = android.content.Intent.ACTION_SEND
                type = "text/plain"
                putExtra(android.content.Intent.EXTRA_TEXT, shareText)
            }
            startActivity(android.content.Intent.createChooser(shareIntent, "Share Product"))
        }
    }
    
    private fun addToCart() {
        currentProduct?.let { product ->
            // Add product to cart with selected quantity
            // In a real app, you would save this to a database or shared preferences
            
            // Show success message
            val message = "Added ${quantity} ${product.unit} of ${product.name} to cart"
            // You can show a Snackbar or Toast here
            
            // Optionally navigate back or show cart
            finish()
        }
    }
    
    private fun buyNow() {
        currentProduct?.let { product ->
            // Navigate to checkout with this product
            // val intent = Intent(this, CheckoutActivity::class.java)
            // intent.putExtra("product_id", product.id)
            // intent.putExtra("quantity", quantity)
            // startActivity(intent)
        }
    }
}
package com.example.agrokrishiseva.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agrokrishiseva.R
import com.example.agrokrishiseva.adapters.CategoryAdapter
import com.example.agrokrishiseva.adapters.ProductAdapter
import com.example.agrokrishiseva.models.Category
import com.example.agrokrishiseva.models.Product
import android.widget.LinearLayout
import android.widget.Toast

class ProductsActivity : AppCompatActivity() {
    
    private lateinit var rvCategories: RecyclerView
    private lateinit var rvProducts: RecyclerView
    private lateinit var etSearchProducts: EditText
    private lateinit var tvCartCount: TextView
    private lateinit var ivBack: ImageView
    private lateinit var cardFilter: LinearLayout
    private lateinit var layoutCart: LinearLayout
    private lateinit var cardBulkDiscount: LinearLayout
    private lateinit var cardFreeDelivery: LinearLayout
    
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter
    
    private var cartItemCount = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        
        initViews()
        setupRecyclerViews()
        setupClickListeners()
        setupSearchFunctionality()
        loadSampleData()
    }
    
    private fun initViews() {
        rvCategories = findViewById(R.id.rvCategories)
        rvProducts = findViewById(R.id.rvProducts)
        etSearchProducts = findViewById(R.id.etSearchProducts)
        tvCartCount = findViewById(R.id.tvCartCount)
        ivBack = findViewById(R.id.ivBack)
        cardFilter = findViewById(R.id.cardFilter)
        layoutCart = findViewById(R.id.layoutCart)
        cardBulkDiscount = findViewById(R.id.cardBulkDiscount)
        cardFreeDelivery = findViewById(R.id.cardFreeDelivery)
    }
    
    private fun setupRecyclerViews() {
        // Categories RecyclerView
        rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryAdapter { category ->
            // Handle category click
            filterProductsByCategory(category)
        }
        rvCategories.adapter = categoryAdapter
        
        // Products RecyclerView
        val gridLayoutManager = GridLayoutManager(this, 2)
        rvProducts.layoutManager = gridLayoutManager
        productAdapter = ProductAdapter(
            onProductClick = { product ->
                // Navigate to product detail
                val intent = Intent(this, ProductDetailActivity::class.java)
                intent.putExtra("product_id", product.id)
                intent.putExtra("product_name", product.name)
                intent.putExtra("product_description", product.description)
                intent.putExtra("product_category", product.category)
                intent.putExtra("product_current_price", product.currentPrice)
                intent.putExtra("product_original_price", product.originalPrice)
                intent.putExtra("product_rating", product.rating)
                intent.putExtra("product_review_count", product.reviewCount)
                intent.putExtra("product_in_stock", product.inStock)
                intent.putExtra("product_discount", product.discount)
                intent.putExtra("product_unit", product.unit)
                startActivity(intent)
            },
            onAddToCartClick = { product ->
                // Add to cart
                addToCart(product)
            },
            onFavoriteClick = { product ->
                // Toggle favorite
                toggleFavorite(product)
            }
        )
        rvProducts.adapter = productAdapter
    }
    
    private fun setupClickListeners() {
        ivBack.setOnClickListener {
            onBackPressed()
        }
        
        cardFilter.setOnClickListener {
            // Show filter dialog
            showFilterDialog()
        }
        
        layoutCart.setOnClickListener {
            // Navigate to cart
            navigateToCart()
        }
        
        cardBulkDiscount.setOnClickListener {
            // Handle bulk discount click
        }
        
        cardFreeDelivery.setOnClickListener {
            // Handle free delivery click
        }
    }
    
    private fun setupSearchFunctionality() {
        etSearchProducts.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterProducts(s.toString())
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
    }
    
    private fun loadSampleData() {
        // Load sample categories
        val categories = listOf(
            Category("1", "Seeds", R.drawable.ic_eco, 25),
            Category("2", "Fertilizers", R.drawable.ic_trending_up, 18),
            Category("3", "Tools", R.drawable.ic_support, 12),
            Category("4", "Pesticides", R.drawable.ic_eco, 8),
            Category("5", "Equipment", R.drawable.ic_support, 15)
        )
        categoryAdapter.updateCategories(categories)
        
        // Load sample products
        val products = listOf(
            Product(
                id = "1",
                name = "Premium Wheat Seeds",
                description = "High yield variety suitable for all seasons",
                category = "Seeds",
                currentPrice = 299.0,
                originalPrice = 399.0,
                imageUrl = "",
                rating = 4.2f,
                reviewCount = 128,
                inStock = true,
                discount = 25
            ),
            Product(
                id = "2",
                name = "Organic Fertilizer",
                description = "100% natural fertilizer for healthy crops",
                category = "Fertilizers",
                currentPrice = 450.0,
                originalPrice = 500.0,
                imageUrl = "",
                rating = 4.5f,
                reviewCount = 89,
                inStock = true,
                discount = 10
            ),
            Product(
                id = "3",
                name = "Garden Spade",
                description = "Durable steel spade for farming",
                category = "Tools",
                currentPrice = 199.0,
                originalPrice = 249.0,
                imageUrl = "",
                rating = 4.0f,
                reviewCount = 45,
                inStock = true,
                discount = 20
            ),
            Product(
                id = "4",
                name = "Rice Seeds - Basmati",
                description = "Premium basmati rice seeds",
                category = "Seeds",
                currentPrice = 599.0,
                originalPrice = 699.0,
                imageUrl = "",
                rating = 4.7f,
                reviewCount = 203,
                inStock = true,
                discount = 14
            ),
            Product(
                id = "5",
                name = "NPK Fertilizer",
                description = "Balanced nutrition for all crops",
                category = "Fertilizers",
                currentPrice = 350.0,
                originalPrice = 400.0,
                imageUrl = "",
                rating = 4.3f,
                reviewCount = 156,
                inStock = false,
                discount = 12
            ),
            Product(
                id = "6",
                name = "Pruning Shears",
                description = "Professional grade pruning tool",
                category = "Tools",
                currentPrice = 299.0,
                originalPrice = 0.0,
                imageUrl = "",
                rating = 4.1f,
                reviewCount = 67,
                inStock = true,
                discount = 0
            )
        )
        productAdapter.updateProducts(products)
    }
    
    private fun filterProductsByCategory(category: Category) {
        // Filter products by selected category
        productAdapter.filterByCategory(category.name)
    }
    
    private fun filterProducts(query: String) {
        // Filter products by search query
        productAdapter.filterByQuery(query)
    }
    
    private fun addToCart(product: Product) {
        cartItemCount++
        tvCartCount.text = cartItemCount.toString()
        
        // Show success message
        Toast.makeText(
            this, 
            "${product.name} added to cart!", 
            Toast.LENGTH_SHORT
        ).show()
    }
    
    private fun toggleFavorite(product: Product) {
        // Toggle favorite status
        product.isFavorite = !product.isFavorite
        productAdapter.notifyDataSetChanged()
    }
    
    private fun showFilterDialog() {
        // Show filter options dialog
        // Implementation for filter dialog
    }
    
    private fun navigateToCart() {
        // Navigate to cart activity
        // val intent = Intent(this, CartActivity::class.java)
        // startActivity(intent)
    }
}
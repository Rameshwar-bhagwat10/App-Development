package com.example.agrokrishiseva.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agrokrishiseva.R
import com.example.agrokrishiseva.models.Product
import android.widget.Button

class ProductAdapter(
    private val onProductClick: (Product) -> Unit,
    private val onAddToCartClick: (Product) -> Unit,
    private val onFavoriteClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    
    private var allProducts = listOf<Product>()
    private var filteredProducts = listOf<Product>()
    
    fun updateProducts(products: List<Product>) {
        allProducts = products
        filteredProducts = products
        notifyDataSetChanged()
    }
    
    fun filterByCategory(category: String) {
        filteredProducts = if (category.isEmpty()) {
            allProducts
        } else {
            allProducts.filter { it.category == category }
        }
        notifyDataSetChanged()
    }
    
    fun filterByQuery(query: String) {
        filteredProducts = if (query.isEmpty()) {
            allProducts
        } else {
            allProducts.filter { 
                it.name.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true) ||
                it.category.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_card, parent, false)
        return ProductViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(filteredProducts[position])
    }
    
    override fun getItemCount(): Int = filteredProducts.size
    
    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivProductImage: ImageView = itemView.findViewById(R.id.ivProductImage)
        private val tvDiscountBadge: TextView = itemView.findViewById(R.id.tvDiscountBadge)
        private val ivFavorite: ImageView = itemView.findViewById(R.id.ivFavorite)
        private val tvProductCategory: TextView = itemView.findViewById(R.id.tvProductCategory)
        private val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        private val tvProductDescription: TextView = itemView.findViewById(R.id.tvProductDescription)
        private val tvRating: TextView = itemView.findViewById(R.id.tvRating)
        private val tvReviewsCount: TextView = itemView.findViewById(R.id.tvReviewsCount)
        private val tvStockStatus: TextView = itemView.findViewById(R.id.tvStockStatus)
        private val tvCurrentPrice: TextView = itemView.findViewById(R.id.tvCurrentPrice)
        private val tvOriginalPrice: TextView = itemView.findViewById(R.id.tvOriginalPrice)
        private val tvPriceUnit: TextView = itemView.findViewById(R.id.tvPriceUnit)
        private val btnAddToCart: Button = itemView.findViewById(R.id.btnAddToCart)
        
        fun bind(product: Product) {
            // Set product image (placeholder for now)
            ivProductImage.setImageResource(R.drawable.ic_eco)
            
            // Set discount badge
            if (product.hasDiscount) {
                tvDiscountBadge.visibility = View.VISIBLE
                tvDiscountBadge.text = "${product.discountPercentage}% OFF"
            } else {
                tvDiscountBadge.visibility = View.GONE
            }
            
            // Set favorite icon
            ivFavorite.setImageResource(
                if (product.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )
            
            // Set product details
            tvProductCategory.text = product.category
            tvProductName.text = product.name
            tvProductDescription.text = product.description
            tvRating.text = product.rating.toString()
            tvReviewsCount.text = "(${product.reviewCount})"
            
            // Set stock status
            if (product.inStock) {
                tvStockStatus.text = "In Stock"
                tvStockStatus.setTextColor(itemView.context.getColor(R.color.success_green))
                btnAddToCart.isEnabled = true
            } else {
                tvStockStatus.text = "Out of Stock"
                tvStockStatus.setTextColor(itemView.context.getColor(R.color.error_red))
                btnAddToCart.isEnabled = false
            }
            
            // Set prices
            tvCurrentPrice.text = "₹${product.currentPrice.toInt()}"
            tvPriceUnit.text = "/${product.unit}"
            
            if (product.hasDiscount) {
                tvOriginalPrice.visibility = View.VISIBLE
                tvOriginalPrice.text = "₹${product.originalPrice.toInt()}"
                tvOriginalPrice.paintFlags = tvOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                tvOriginalPrice.visibility = View.GONE
            }
            
            // Set click listeners
            itemView.setOnClickListener {
                onProductClick(product)
            }
            
            btnAddToCart.setOnClickListener {
                onAddToCartClick(product)
            }
            
            ivFavorite.setOnClickListener {
                onFavoriteClick(product)
            }
        }
    }
}
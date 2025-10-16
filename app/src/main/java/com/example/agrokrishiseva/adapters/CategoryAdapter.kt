package com.example.agrokrishiseva.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agrokrishiseva.R
import com.example.agrokrishiseva.models.Category

class CategoryAdapter(
    private val onCategoryClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    
    private var categories = listOf<Category>()
    private var selectedCategoryId: String? = null
    
    fun updateCategories(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged()
    }
    
    fun selectCategory(categoryId: String?) {
        selectedCategoryId = categoryId
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }
    
    override fun getItemCount(): Int = categories.size
    
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivCategoryIcon: ImageView = itemView.findViewById(R.id.ivCategoryIcon)
        private val tvCategoryName: TextView = itemView.findViewById(R.id.tvCategoryName)
        private val tvProductCount: TextView = itemView.findViewById(R.id.tvProductCount)
        
        fun bind(category: Category) {
            ivCategoryIcon.setImageResource(category.iconRes)
            tvCategoryName.text = category.name
            tvProductCount.text = "${category.productCount} items"
            
            // Highlight selected category
            val isSelected = category.id == selectedCategoryId
            if (isSelected) {
                itemView.setBackgroundColor(itemView.context.getColor(R.color.very_light_green))
                ivCategoryIcon.setColorFilter(itemView.context.getColor(R.color.primary_green))
            } else {
                itemView.setBackgroundColor(itemView.context.getColor(R.color.white))
                ivCategoryIcon.setColorFilter(itemView.context.getColor(R.color.text_secondary))
            }
            
            itemView.setOnClickListener {
                selectedCategoryId = if (selectedCategoryId == category.id) null else category.id
                onCategoryClick(category)
                notifyDataSetChanged()
            }
        }
    }
}
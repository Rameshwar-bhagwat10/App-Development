package com.example.agrokrishiseva.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.agrokrishiseva.R
import com.example.agrokrishiseva.models.TipCategory
import com.google.android.material.card.MaterialCardView

class TipCategoryAdapter(
    private val categories: List<TipCategory>,
    private val onCategoryClick: (TipCategory?) -> Unit
) : RecyclerView.Adapter<TipCategoryAdapter.CategoryViewHolder>() {

    private var selectedPosition = -1 // -1 means "All" is selected

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: MaterialCardView = itemView as MaterialCardView
        val ivCategoryIcon: ImageView = itemView.findViewById(R.id.ivCategoryIcon)
        val tvCategoryName: TextView = itemView.findViewById(R.id.tvCategoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tip_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        if (position == 0) {
            // "All" category
            holder.ivCategoryIcon.setImageResource(R.drawable.ic_menu)
            holder.tvCategoryName.text = "All"
            
            val isSelected = selectedPosition == -1
            updateCategoryAppearance(holder, isSelected)
            
            holder.itemView.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = -1
                notifyItemChanged(previousPosition + 1) // +1 because of "All" item
                notifyItemChanged(0)
                onCategoryClick(null)
            }
        } else {
            val category = categories[position - 1]
            holder.ivCategoryIcon.setImageResource(category.icon)
            holder.tvCategoryName.text = category.displayName
            
            val isSelected = selectedPosition == position - 1
            updateCategoryAppearance(holder, isSelected)
            
            holder.itemView.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = position - 1
                if (previousPosition == -1) {
                    notifyItemChanged(0) // "All" item
                } else {
                    notifyItemChanged(previousPosition + 1)
                }
                notifyItemChanged(position)
                onCategoryClick(category)
            }
        }
    }

    private fun updateCategoryAppearance(holder: CategoryViewHolder, isSelected: Boolean) {
        val context = holder.itemView.context
        
        if (isSelected) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.primary_green))
            holder.cardView.strokeColor = ContextCompat.getColor(context, R.color.primary_green)
            holder.tvCategoryName.setTextColor(ContextCompat.getColor(context, R.color.white))
            holder.ivCategoryIcon.setColorFilter(ContextCompat.getColor(context, R.color.white))
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
            holder.cardView.strokeColor = ContextCompat.getColor(context, R.color.input_border)
            holder.tvCategoryName.setTextColor(ContextCompat.getColor(context, R.color.text_secondary))
            holder.ivCategoryIcon.setColorFilter(ContextCompat.getColor(context, R.color.text_secondary))
        }
    }

    override fun getItemCount(): Int = categories.size + 1 // +1 for "All" category
}
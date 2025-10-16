package com.example.agrokrishiseva.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agrokrishiseva.R
import com.example.agrokrishiseva.models.Tip

class TipAdapter(
    private var tips: List<Tip>,
    private val onTipClick: (Tip) -> Unit,
    private val onBookmarkClick: (Tip) -> Unit
) : RecyclerView.Adapter<TipAdapter.TipViewHolder>() {

    class TipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivTipImage: ImageView = itemView.findViewById(R.id.ivTipImage)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvTipTitle: TextView = itemView.findViewById(R.id.tvTipTitle)
        val tvDifficulty: TextView = itemView.findViewById(R.id.tvDifficulty)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val tvShortDescription: TextView = itemView.findViewById(R.id.tvShortDescription)
        val tvRating: TextView = itemView.findViewById(R.id.tvRating)
        val tvViews: TextView = itemView.findViewById(R.id.tvViews)
        val ivBookmark: ImageView = itemView.findViewById(R.id.ivBookmark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tip_card, parent, false)
        return TipViewHolder(view)
    }

    override fun onBindViewHolder(holder: TipViewHolder, position: Int) {
        val tip = tips[position]
        
        holder.ivTipImage.setImageResource(tip.imageResource)
        holder.tvCategory.text = tip.category.displayName
        holder.tvTipTitle.text = tip.title
        holder.tvDifficulty.text = tip.difficulty.displayName
        holder.tvTime.text = tip.estimatedTime
        holder.tvShortDescription.text = tip.shortDescription
        holder.tvRating.text = String.format("%.1f", tip.rating)
        
        // Format view count
        val viewText = when {
            tip.viewCount >= 1000 -> "${tip.viewCount / 1000}.${(tip.viewCount % 1000) / 100}k views"
            else -> "${tip.viewCount} views"
        }
        holder.tvViews.text = viewText
        
        // Set bookmark state
        holder.ivBookmark.setImageResource(
            if (tip.isBookmarked) R.drawable.ic_favorite else R.drawable.ic_favorite
        )
        holder.ivBookmark.setColorFilter(
            holder.itemView.context.getColor(
                if (tip.isBookmarked) R.color.error_red else R.color.text_hint
            )
        )
        
        // Set difficulty color
        val difficultyColor = when (tip.difficulty) {
            com.example.agrokrishiseva.models.TipDifficulty.BEGINNER -> R.color.success_green
            com.example.agrokrishiseva.models.TipDifficulty.INTERMEDIATE -> android.R.color.holo_orange_light
            com.example.agrokrishiseva.models.TipDifficulty.ADVANCED -> R.color.error_red
        }
        holder.tvDifficulty.setTextColor(holder.itemView.context.getColor(difficultyColor))
        
        // Click listeners
        holder.itemView.setOnClickListener {
            onTipClick(tip)
        }
        
        holder.ivBookmark.setOnClickListener {
            onBookmarkClick(tip)
        }
    }

    override fun getItemCount(): Int = tips.size

    fun updateTips(newTips: List<Tip>) {
        tips = newTips
        notifyDataSetChanged()
    }
}
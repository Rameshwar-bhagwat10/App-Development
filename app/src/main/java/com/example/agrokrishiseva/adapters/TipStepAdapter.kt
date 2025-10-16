package com.example.agrokrishiseva.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agrokrishiseva.R
import com.example.agrokrishiseva.models.TipStep

class TipStepAdapter(
    private var steps: List<TipStep>
) : RecyclerView.Adapter<TipStepAdapter.StepViewHolder>() {

    class StepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvStepNumber: TextView = itemView.findViewById(R.id.tvStepNumber)
        val tvStepTitle: TextView = itemView.findViewById(R.id.tvStepTitle)
        val tvStepDescription: TextView = itemView.findViewById(R.id.tvStepDescription)
        val ivStepImage: ImageView = itemView.findViewById(R.id.ivStepImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tip_step, parent, false)
        return StepViewHolder(view)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val step = steps[position]
        
        holder.tvStepNumber.text = step.stepNumber.toString()
        holder.tvStepTitle.text = step.title
        holder.tvStepDescription.text = step.description
        
        // Show/hide image based on availability
        if (step.imageResource != null) {
            holder.ivStepImage.visibility = View.VISIBLE
            holder.ivStepImage.setImageResource(step.imageResource)
        } else {
            holder.ivStepImage.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = steps.size

    fun updateSteps(newSteps: List<TipStep>) {
        steps = newSteps
        notifyDataSetChanged()
    }
}
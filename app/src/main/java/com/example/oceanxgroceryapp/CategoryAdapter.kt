package com.example.oceanxgroceryapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Data class to hold category information
data class Category(val name: String, val iconRes: Int)

class CategoryAdapter(
    private val categories: List<Category>,
    private val onCategoryClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    // Track the currently selected category (Defaults to 0 for "All")
    private var selectedPosition = 0

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvCategoryName)
        val ivIcon: ImageView = view.findViewById(R.id.ivCategoryIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.tvName.text = category.name
        holder.ivIcon.setImageResource(category.iconRes)

        // --- PREMIUM UI POLISH: HIGHLIGHT SELECTED CATEGORY ---
        if (position == selectedPosition) {
            // Selected state: Blinkit Green, Bold text, Full opacity image
            holder.tvName.setTextColor(Color.parseColor("#0C831F"))
            holder.tvName.paint.isFakeBoldText = true
            holder.ivIcon.alpha = 1.0f
        } else {
            // Unselected state: Gray text, Normal text, Slightly dimmed image
            holder.tvName.setTextColor(Color.parseColor("#757575"))
            holder.tvName.paint.isFakeBoldText = false
            holder.ivIcon.alpha = 0.5f
        }

        // Handle the click event
        holder.itemView.setOnClickListener {
            // 1. Update the UI to highlight the new selection
            val previousSelected = selectedPosition
            selectedPosition = holder.adapterPosition

            // Refresh only the two items that changed (for smooth performance)
            notifyItemChanged(previousSelected)
            notifyItemChanged(selectedPosition)

            // 2. Tell HomeActivity to filter the products
            onCategoryClick(category.name)
        }
    }

    override fun getItemCount(): Int = categories.size
}
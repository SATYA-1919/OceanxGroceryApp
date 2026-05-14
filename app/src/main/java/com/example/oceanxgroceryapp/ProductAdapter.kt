package com.example.oceanxgroceryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private var productList: List<Product>,
    private val onIncrease: (Product) -> Unit,
    private val onDecrease: (Int) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    // Keeps track of how many of each item are in the cart
    private var cartQuantities: Map<Int, Int> = emptyMap()

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.ivProductImage)
        val tvName: TextView = view.findViewById(R.id.tvProductName)
        val tvPrice: TextView = view.findViewById(R.id.tvProductPrice)
        
        val btnInitialAdd: Button = view.findViewById(R.id.btnInitialAdd)
        val llQuantityControls: LinearLayout = view.findViewById(R.id.llQuantityControls)
        val btnMinus: TextView = view.findViewById(R.id.btnMinus)
        val btnPlus: TextView = view.findViewById(R.id.btnPlus)
        val tvQuantity: TextView = view.findViewById(R.id.tvQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        val quantity = cartQuantities[product.id] ?: 0

        holder.tvName.text = product.name
        holder.tvPrice.text = "₹${product.price}"
        holder.ivImage.setImageResource(product.imageRes)

        // Show/Hide inline cart controls based on Room DB state
        if (quantity > 0) {
            holder.btnInitialAdd.visibility = View.GONE
            holder.llQuantityControls.visibility = View.VISIBLE
            holder.tvQuantity.text = quantity.toString()
        } else {
            holder.btnInitialAdd.visibility = View.VISIBLE
            holder.llQuantityControls.visibility = View.GONE
        }

        // Click Listeners
        holder.btnInitialAdd.setOnClickListener { onIncrease(product) }
        holder.btnPlus.setOnClickListener { onIncrease(product) }
        holder.btnMinus.setOnClickListener { onDecrease(product.id) }
    }

    override fun getItemCount(): Int = productList.size

    fun updateList(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }

    // Called by HomeActivity whenever the StateFlow updates
    fun updateCartState(newQuantities: Map<Int, Int>) {
        cartQuantities = newQuantities
        notifyDataSetChanged()
    }
}

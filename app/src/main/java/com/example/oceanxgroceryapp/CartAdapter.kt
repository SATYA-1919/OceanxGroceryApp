package com.example.oceanxgroceryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private var cartItems: List<CartItem>,
    private val onCartUpdated: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvCartName)
        val tvPrice: TextView = view.findViewById(R.id.tvCartPrice)
        val tvQuantity: TextView = view.findViewById(R.id.tvQuantity)
        val btnPlus: Button = view.findViewById(R.id.btnPlus)
        val btnMinus: Button = view.findViewById(R.id.btnMinus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.tvName.text = item.product.name
        holder.tvPrice.text = "₹${item.product.price}"
        holder.tvQuantity.text = item.quantity.toString()

        holder.btnPlus.setOnClickListener {
            CartManager.addItem(item.product)
            updateData()
        }

        holder.btnMinus.setOnClickListener {
            CartManager.removeItem(item.product.id)
            updateData()
        }
    }

    override fun getItemCount(): Int = cartItems.size

    private fun updateData() {
        cartItems = CartManager.getCartItems()
        notifyDataSetChanged()
        onCartUpdated() // Refresh the total bill on the screen
    }
}

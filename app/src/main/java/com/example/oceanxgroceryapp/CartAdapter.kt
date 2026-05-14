package com.example.oceanxgroceryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val onIncrease: (Int) -> Unit,
    private val onDecrease: (Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartList: List<CartEntity> = emptyList()

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvCartName)
        val tvPrice: TextView = view.findViewById(R.id.tvCartPrice)
        val tvQuantity: TextView = view.findViewById(R.id.tvQuantity)
        val btnPlus: View = view.findViewById(R.id.btnPlus)
        val btnMinus: View = view.findViewById(R.id.btnMinus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartList[position]
        holder.tvName.text = item.name

        // Formats the integer price back into a display string for the UI
        holder.tvPrice.text = "₹${item.price}"
        holder.tvQuantity.text = item.quantity.toString()

        holder.btnPlus.setOnClickListener { onIncrease(item.id) }
        holder.btnMinus.setOnClickListener { onDecrease(item.id) }
    }

    override fun getItemCount(): Int = cartList.size

    // This is the missing function that the activity was looking for!
    fun submitList(newList: List<CartEntity>) {
        cartList = newList
        notifyDataSetChanged()
    }
}
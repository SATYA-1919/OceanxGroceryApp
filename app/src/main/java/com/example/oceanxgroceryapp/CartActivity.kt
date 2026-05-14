package com.example.oceanxgroceryapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var tvTotal: TextView
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val rvCart = findViewById<RecyclerView>(R.id.rvCartItems)
        val btnCheckout = findViewById<Button>(R.id.btnCheckout)
        tvTotal = findViewById<TextView>(R.id.tvTotalBill)

        rvCart.layoutManager = LinearLayoutManager(this)

        // Setup Adapter with actual global Cart Manager data
        adapter = CartAdapter(CartManager.getCartItems()) {
            updateTotal()
        }
        rvCart.adapter = adapter
        updateTotal()

        btnCheckout.setOnClickListener {
            if (CartManager.getTotalItems() > 0) {
                val intent = Intent(this, CheckoutActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateTotal() {
        val total = CartManager.getTotalPrice()
        tvTotal.text = "To Pay: ₹$total"
        if(total == 0) finish() // Close cart if empty
    }
}

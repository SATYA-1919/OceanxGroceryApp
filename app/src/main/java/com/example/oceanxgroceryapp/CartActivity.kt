package com.example.oceanxgroceryapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity() {

    private lateinit var viewModel: CartViewModel
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // 1. Initialize the new ViewModel (Connecting to Room Database)
        viewModel = ViewModelProvider(this)[CartViewModel::class.java]

        val rvCart = findViewById<RecyclerView>(R.id.rvCartItems)
        val btnCheckout = findViewById<Button>(R.id.btnCheckout)
        val tvTotal = findViewById<TextView>(R.id.tvTotalBill)

        rvCart.layoutManager = LinearLayoutManager(this)

        // 2. Setup Adapter to talk to the ViewModel
        adapter = CartAdapter(
            onIncrease = { id ->
                viewModel.addToCart(Product(id, "", 0, 0, ""))
            },
            onDecrease = { id -> viewModel.removeOrDecrease(id) }
        )
        rvCart.adapter = adapter

        // 3. LiveData Observation (Instantly updates UI when database changes)
        lifecycleScope.launch {
            viewModel.cartItems.collect { items ->
                adapter.submitList(items)

                val total = viewModel.getTotalPrice(items)

                // --- THE EMPTY CART FIX ---
                if (items.isEmpty()) {
                    btnCheckout.isEnabled = false
                    btnCheckout.alpha = 0.5f // Make button look faded
                    tvTotal.text = "Cart is Empty"
                } else {
                    btnCheckout.isEnabled = true
                    btnCheckout.alpha = 1.0f // Restore button look
                    tvTotal.text = "To Pay: ₹$total"
                }
            }
        }

        // 4. Checkout Navigation
        btnCheckout.setOnClickListener {
            startActivity(Intent(this, CheckoutActivity::class.java))
        }
    }
}
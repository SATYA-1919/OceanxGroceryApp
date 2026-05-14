package com.example.oceanxgroceryapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val etAddress = findViewById<EditText>(R.id.etAddress)
        val btnPlaceOrder = findViewById<Button>(R.id.btnPlaceOrder)

        btnPlaceOrder.setOnClickListener {
            val address = etAddress.text.toString()

            if (address.isNotEmpty()) {
                // Navigate to Order Success Screen
                val intent = Intent(this, OrderSuccessActivity::class.java)
                startActivity(intent)
                finishAffinity() // Clears the activity stack so user can't go back to checkout
            } else {
                Toast.makeText(this, "Please enter an address", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
package com.example.oceanxgroceryapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip

class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val btnPlaceOrder = findViewById<Button>(R.id.btnPlaceOrder)
        val chipHome = findViewById<Chip>(R.id.chipHome)
        val chipWork = findViewById<Chip>(R.id.chipWork)

        val etHouseNo = findViewById<EditText>(R.id.etHouseNo)
        val etArea = findViewById<EditText>(R.id.etArea)
        val etPincode = findViewById<EditText>(R.id.etPincode)

        // 🟢 Functional Saved Address Chips 🟢
        chipHome.setOnClickListener {
            etHouseNo.setText("Apt 4B")
            etArea.setText("Sector 5")
            etPincode.setText("500043")
        }

        chipWork.setOnClickListener {
            etHouseNo.setText("Block C")
            etArea.setText("Tech Park")
            etPincode.setText("500081")
        }

        // 🟢 Place Order Navigation 🟢
        btnPlaceOrder.setOnClickListener {
            val intent = Intent(this, OrderSuccessActivity::class.java)
            startActivity(intent)
            finish() // Prevents user from pressing "back" to return to this screen
        }
    }
}
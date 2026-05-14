package com.example.oceanxgroceryapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class OrderSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_success)

        val btnHome = findViewById<Button>(R.id.btnBackToHome)

        btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
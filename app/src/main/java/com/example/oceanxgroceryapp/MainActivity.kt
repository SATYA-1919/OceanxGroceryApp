package com.example.oceanxgroceryapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etPhone = findViewById<EditText>(R.id.etPhoneNumber)
        val btnGetOtp = findViewById<Button>(R.id.btnGetOtp)

        btnGetOtp.setOnClickListener {
            val number = etPhone.text.toString()

            // Validation for a 10-digit number
            if (number.length == 10) {
                // This line opens the OtpActivity screen
                val intent = Intent(this, OtpActivity::class.java)
                startActivity(intent)
            } else {
                etPhone.error = "Enter a valid 10-digit number"
            }
        }
    }
}
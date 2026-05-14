package com.example.oceanxgroceryapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OtpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        val etOtpInput = findViewById<EditText>(R.id.etOtpInput)
        val btnVerify = findViewById<Button>(R.id.btnVerifyOtp)

        btnVerify.setOnClickListener {
            val enteredOtp = etOtpInput.text.toString()

            // Fake OTP check: 1234 (Requirement from Oceanx Intent Assignment.jpg)
            if (enteredOtp == "1234") {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish() // Prevents user from going back to OTP screen
            } else {
                etOtpInput.error = "Wrong OTP! Use 1234"
            }
        }
    }
}
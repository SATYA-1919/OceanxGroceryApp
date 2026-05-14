package com.example.oceanxgroceryapp

data class Product(
    val id: Int,
    val name: String,
    val price: Int, // Changed to Int based on your new code
    val imageRes: Int,
    val categoryId: String // Added to support your category filtering
)
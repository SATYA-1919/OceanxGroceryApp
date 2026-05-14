package com.example.oceanxgroceryapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// 1. THE VIEWMODEL: Handles logic and provides StateFlow to the UI
class CartViewModel(application: Application) : AndroidViewModel(application) {
    
    private val dao = AppDatabase.getDatabase(application).cartDao()

    // StateFlow automatically pushes the latest cart data to HomeActivity & CartActivity
    val cartItems: StateFlow<List<CartEntity>> = dao.getAllCartItems()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addToCart(product: Product) {
        viewModelScope.launch {
            val existingItem = dao.getItemById(product.id)
            if (existingItem != null) {
                dao.increaseQuantity(product.id)
            } else {
                dao.insertItem(CartEntity(product.id, product.name, product.price, product.imageRes, 1))
            }
        }
    }

    fun removeOrDecrease(productId: Int) {
        viewModelScope.launch {
            val existingItem = dao.getItemById(productId)
            if (existingItem != null) {
                if (existingItem.quantity > 1) {
                    dao.decreaseQuantity(productId)
                } else {
                    dao.deleteItem(productId)
                }
            }
        }
    }

    // Helper functions to calculate totals
    fun getTotalItems(items: List<CartEntity>): Int = items.sumOf { it.quantity }
    
    fun getTotalPrice(items: List<CartEntity>): Int {
        return items.sumOf {
            it.price * it.quantity
        }
    }
}

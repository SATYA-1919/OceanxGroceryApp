package com.example.oceanxgroceryapp

// Data class to hold cart item details
data class CartItem(val product: Product, var quantity: Int = 1)

// Singleton object to manage the cart globally
object CartManager {
    private val cartItems = mutableMapOf<Int, CartItem>()

    fun addItem(product: Product) {
        if (cartItems.containsKey(product.id)) {
            cartItems[product.id]!!.quantity += 1
        } else {
            cartItems[product.id] = CartItem(product, 1)
        }
    }

    fun removeItem(productId: Int) {
        if (cartItems.containsKey(productId)) {
            val currentQty = cartItems[productId]!!.quantity
            if (currentQty > 1) {
                cartItems[productId]!!.quantity -= 1
            } else {
                cartItems.remove(productId)
            }
        }
    }

    fun getTotalItems(): Int {
        return cartItems.values.sumOf { it.quantity }
    }

    fun getTotalPrice(): Int {
        return cartItems.values.sumOf { 
            it.product.price * it.quantity
        }
    }

    fun getCartItems(): List<CartItem> = cartItems.values.toList()
    
    fun getQuantity(product: Product): Int {
        return cartItems[product.id]?.quantity ?: 0
    }
}

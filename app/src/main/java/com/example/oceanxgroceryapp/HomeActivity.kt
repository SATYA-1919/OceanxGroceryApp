package com.example.oceanxgroceryapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var adapter: ProductAdapter
    private lateinit var viewModel: CartViewModel

    // Product list mapping to your real drawable images
    private val allProducts = listOf(
        Product(1, "Fresh Apples (Shimla)", 180, R.drawable.product_apples, "Fruits"),
        Product(2, "Robusta Bananas", 60, R.drawable.product_bananas, "Fruits"),
        Product(3, "Amul Taaza Milk", 30, R.drawable.product_milk, "Dairy"),
        Product(4, "Organic Carrots", 40, R.drawable.product_carrots, "Vegetables"),
        Product(5, "Chocolate Truffle Cake", 450, R.drawable.product_cake, "Cakes"),
        Product(6, "Farm Fresh Eggs", 80, R.drawable.product_eggs, "Grocery essentials"),
        Product(7, "Lay's Classic Salted", 20, R.drawable.product_chips, "Snacks"),
        Product(8, "Coca Cola (750ml)", 40, R.drawable.product_cola, "Beverages")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(this)[CartViewModel::class.java]

        setupCategories()
        setupProducts()

        val etSearch = findViewById<EditText>(R.id.etSearch)
        val flCartIcon = findViewById<FrameLayout>(R.id.flCartIcon)
        val tvCartBadge = findViewById<TextView>(R.id.tvCartBadge)

        // 🟢 Functional Cart Navigation 🟢
        flCartIcon.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        // LiveData Observation for Inline Cart Controls & Top Cart Badge
        lifecycleScope.launch {
            viewModel.cartItems.collect { items ->
                val totalItems = viewModel.getTotalItems(items)

                // Update Top Cart Badge
                if (totalItems > 0) {
                    tvCartBadge.visibility = View.VISIBLE
                    tvCartBadge.text = totalItems.toString()
                } else {
                    tvCartBadge.visibility = View.GONE
                }

                // Update Inline Quantity Controls dynamically (+ / -)
                val cartMap = items.associate { it.id to it.quantity }
                adapter.updateCartState(cartMap)
            }
        }

        // Search Bar Filtering
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.updateList(allProducts.filter { it.name.contains(s.toString(), ignoreCase = true) })
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupProducts() {
        val rvProducts = findViewById<RecyclerView>(R.id.rvProducts)
        rvProducts.layoutManager = GridLayoutManager(this, 2)
        adapter = ProductAdapter(
            allProducts,
            onIncrease = { product -> viewModel.addToCart(product) },
            onDecrease = { id -> viewModel.removeOrDecrease(id) }
        )
        rvProducts.adapter = adapter
    }

    private fun setupCategories() {
        val rvCategories = findViewById<RecyclerView>(R.id.rvCategories)
        rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        val catList = listOf(
            Category("All", R.drawable.ic_cat_all),
            Category("Vegetables", R.drawable.product_carrots),
            Category("Fruits", R.drawable.product_apples),
            Category("Cakes", R.drawable.product_cake),
            Category("Dairy", R.drawable.product_milk)
        )

        // Category Click Listener handles filtering the products instantly
        rvCategories.adapter = CategoryAdapter(catList) { categoryName ->
            if (categoryName == "All") {
                adapter.updateList(allProducts)
            } else {
                adapter.updateList(allProducts.filter { it.categoryId == categoryName })
            }
        }
    }
}
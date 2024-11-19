package com.example.sonia_emporium

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.toObject

class CatalogActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private val productList = mutableListOf<Product>()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        auth = FirebaseAuth.getInstance()

        recyclerView = findViewById(R.id.productRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchProducts()

        findViewById<Button>(R.id.cartButton).setOnClickListener {
            if (auth.currentUser != null) {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, SignInActivity::class.java)
                Toast.makeText(this, "User not logged in", Toast.LENGTH_LONG).show()
                startActivity(intent)
            }
        }
    }

    private fun fetchProducts() {
        db.collection("product")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val product = document.toObject<Product>().apply {
                        productId = document.id // Store document name as productId
                    }
                    productList.add(product)
                }
                productAdapter = ProductAdapter(this, productList) { product, quantity, size ->
                    checkUserSignIn(product, quantity, size) // Pass all data to checkUserSignIn
                }
                recyclerView.adapter = productAdapter
            }
            .addOnFailureListener { exception ->
                Log.w("CatalogActivity", "Error getting documents: ", exception)
            }
    }

    private fun checkUserSignIn(product: Product, quantity: Int, size: String) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            addToCart(currentUser.uid, product, quantity, size)
        } else {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addToCart(userId: String, product: Product, quantity: Int, size: String) {
        val cartItem = hashMapOf(
            "name" to product.name,
            "price" to product.price,
            "productDocName" to product.productId, // Store product document name
            "size" to size,
            "quantity" to quantity
        )

        // Store the cart item in the 'cart item' collection
        db.collection("cart item").document(userId)
            .set(cartItem)
            .addOnSuccessListener {
                Toast.makeText(this, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e("CatalogActivity", "Failed to add to cart (cart item): ${e.message}")
                Toast.makeText(this, "Failed to add to cart: ${e.message}", Toast.LENGTH_LONG)
                    .show()
            }

        // Now also add to 'total items in cart' collection
        val cartItemForTotal = hashMapOf(
            "name" to product.name,
            "price" to product.price,
            "productDocName" to product.productId, // Store product document name
            "size" to size,
            "quantity" to quantity
        )

        // Use set with merge to either create or update the document
        db.collection("total items in cart").document(userId)
            .set(
                hashMapOf("product" to FieldValue.arrayUnion(cartItemForTotal)),
                SetOptions.merge()  // This will merge the "products" field with the new cart item
            )
            .addOnSuccessListener {
                Log.d("CatalogActivity", "Product added to total items in cart.")
            }
            .addOnFailureListener { e ->
                Log.e("CatalogActivity", "Failed to add to total items in cart: ${e.message}")
            }
    }
}

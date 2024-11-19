package com.example.sonia_emporium

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CartActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var cartAdapter: CartAdapter
    private val cartItems = mutableListOf<CartItem>()
    private lateinit var totalPriceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        auth = FirebaseAuth.getInstance()

        val recyclerView: RecyclerView = findViewById(R.id.cartRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        cartAdapter = CartAdapter(cartItems)
        recyclerView.adapter = cartAdapter

        totalPriceTextView = findViewById(R.id.totalPriceTextView)

        fetchCartItems()

        findViewById<Button>(R.id.payButton).setOnClickListener {
            processPayment()
        }

        findViewById<Button>(R.id.signOutButton).setOnClickListener {
            signOutUser()
        }

        findViewById<Button>(R.id.clearCartButton).setOnClickListener {
            clearCart()
        }
    }

    private fun signOutUser() {
        auth.signOut()
        Toast.makeText(this, "Signed out successfully", Toast.LENGTH_SHORT).show()

        // Redirect to SignInActivity after sign out
        val intent = Intent(this, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    // Fetch cart items from the 'total items in cart' collection
    private fun fetchCartItems() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("total items in cart").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val productList = document.get("product") as? List<HashMap<String, Any>> ?: emptyList()

                    // Clear existing cart items
                    cartItems.clear()

                    // Add items to the cartItems list for displaying
                    productList.forEach { product ->
                        val name = product["name"] as? String ?: ""
                        val price = product["price"] as? Long ?: 0L
                        val size = product["size"] as? String ?: ""
                        val quantity = product["quantity"] as? Long ?: 1L

                        // Create CartItem from the fetched data and add it to the list
                        val cartItem = CartItem(
                            productDocName = "",  // You can leave this empty for display purposes
                            name = name,
                            price = price.toInt(),
                            size = size,
                            quantity = quantity.toInt()
                        )
                        cartItems.add(cartItem)
                    }

                    // Notify adapter that the data has changed
                    cartAdapter.notifyDataSetChanged()
                    updateTotalPrice()  // Update total price when the cart is loaded
                } else {
                    // Handle case where the cart is empty
                    Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Log.w("CartActivity", "Error fetching cart items: ", e)
            }
    }

    // Update total price in the UI
    private fun updateTotalPrice() {
        val totalPrice = cartItems.sumOf { it.price * it.quantity }
        totalPriceTextView.text = "Total: R$totalPrice"
    }

    private fun processPayment() {
        // Check if the cart is empty before proceeding with payment
        if (cartItems.isEmpty()) {
            Toast.makeText(this, "Your cart is empty! Please add items to proceed with payment.", Toast.LENGTH_SHORT).show()
            return // Exit the function if the cart is empty
        }

        val userId = auth.currentUser?.uid ?: return
        val orderTotal = cartItems.sumOf { it.price * it.quantity }

        // Clear the cart after payment
        db.collection("cart item").document(userId).delete()
        db.collection("total items in cart").document(userId)
            .update("product", emptyList<String>())
            .addOnSuccessListener {
                Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show()

                // Navigate to the Payment Confirmation page with the order total
                val intent = Intent(this, PaymentConfirmationActivity::class.java)
                intent.putExtra("ORDER_TOTAL", orderTotal.toString())
                startActivity(intent)
                finish() // Optionally, finish cart activity to prevent back navigation
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to process payment: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun clearCart() {
        val userId = auth.currentUser?.uid ?: return

        // Clear cart from Firebase
        db.collection("total items in cart").document(userId)
            .update("product", emptyList<String>())
            .addOnSuccessListener {
                Toast.makeText(this, "Cart cleared", Toast.LENGTH_SHORT).show()
                cartItems.clear()  // Clear the cart items from the UI
                cartAdapter.notifyDataSetChanged()
                updateTotalPrice()  // Update total price after clearing cart
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to clear cart: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

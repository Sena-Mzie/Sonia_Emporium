package com.example.sonia_emporium

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(private val cartItems: List<CartItem>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        val totalPrice = cartItem.price * cartItem.quantity // Calculate total price based on quantity
        holder.nameTextView.text = cartItem.name
        holder.priceTextView.text = "Price: R$totalPrice" // Display total price
        holder.sizeTextView.text = "Size: ${cartItem.size}"
        holder.quantityTextView.text = "Quantity: ${cartItem.quantity}"
    }

    override fun getItemCount(): Int = cartItems.size

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.cartItemName)
        val priceTextView: TextView = view.findViewById(R.id.cartItemPrice)
        val sizeTextView: TextView = view.findViewById(R.id.cartItemSize)
        val quantityTextView: TextView = view.findViewById(R.id.cartItemQuantity)
    }
}

package com.example.sonia_emporium

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(
    private val context: Context,
    private val productList: List<Product>,
    private val onAddToCart: (Product, Int, String) -> Unit // Callback for adding to cart with selected size and quantity
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.productImage)
        val productName: TextView = view.findViewById(R.id.productName)
        val productDescription: TextView = view.findViewById(R.id.productDescription)
        val productPrice: TextView = view.findViewById(R.id.productPrice)
        val sizeSpinner: Spinner = view.findViewById(R.id.sizeSpinner)
        val quantitySpinner: Spinner = view.findViewById(R.id.quantitySpinner)
        val addToCartButton: Button = view.findViewById(R.id.addToCartButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.productName.text = product.name
        holder.productDescription.text = product.description
        holder.productPrice.text = "Price: R${product.price}"

        // Load product image
        Glide.with(context).load(product.image).into(holder.productImage)

        // Populate size spinner
        val sizeAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, product.sizeList)
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.sizeSpinner.adapter = sizeAdapter

        // Populate quantity spinner
        val quantities = (1..10).toList()
        val quantityAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, quantities)
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.quantitySpinner.adapter = quantityAdapter

        // Add to Cart button click
        holder.addToCartButton.setOnClickListener {
            val selectedQuantity = holder.quantitySpinner.selectedItem as Int
            val selectedSize = holder.sizeSpinner.selectedItem as String
            onAddToCart(product, selectedQuantity, selectedSize) // Pass the product, quantity, and size
        }
    }

    override fun getItemCount(): Int = productList.size
}

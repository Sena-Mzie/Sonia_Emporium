package com.example.sonia_emporium

data class Product(
    val inStock: Boolean = true,
    val category: String = "",
    val description: String = "",
    val image: String = "",
    val name: String = "",
    val price: Int = 0,
    var productId: String = "",
    val size: Any? = null, // Temporarily store size as Any type
    var selectedSize: String = "Regular" // Default to "Regular" if no size is selected
) {
    // Custom getter to handle both List and String cases
    val sizeList: List<String>
        get() = when (size) {
            is List<*> -> size as List<String>
            is String -> listOf(size) // Wrap single string in a list
            else -> emptyList()

        }
}


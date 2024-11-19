package com.example.sonia_emporium

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PaymentConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_confirmation)

        val thankYouMessage = findViewById<TextView>(R.id.thankYouMessage)
        val orderTotalTextView = findViewById<TextView>(R.id.orderTotalTextView)
        val backToHomeButton = findViewById<Button>(R.id.backToHomeButton)

        // Retrieve order total from intent
        val orderTotal = intent.getStringExtra("ORDER_TOTAL")
        orderTotalTextView.text = "Order Total: R$orderTotal"

        // Handle "Back to Home" button click
        backToHomeButton.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(homeIntent)
            finish()
        }
    }
}

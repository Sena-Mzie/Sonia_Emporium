package com.example.sonia_emporium

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val catalogButton: Button = findViewById(R.id.catalogButton)
        catalogButton.setOnClickListener {
            // Replace CatalogActivity::class.java with the actual catalog activity class
            val intent = Intent(this, CatalogActivity::class.java)
            startActivity(intent)
        }
    }
}

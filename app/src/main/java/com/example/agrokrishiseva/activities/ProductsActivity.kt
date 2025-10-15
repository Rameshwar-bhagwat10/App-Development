package com.example.agrokrishiseva.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.agrokrishiseva.R

class ProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        
        // Set up toolbar
        supportActionBar?.apply {
            title = "Products"
            setDisplayHomeAsUpEnabled(true)
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
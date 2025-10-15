package com.example.agrokrishiseva.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.agrokrishiseva.R

class TipsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips)
        
        // Set up toolbar
        supportActionBar?.apply {
            title = "Tips & Guidance"
            setDisplayHomeAsUpEnabled(true)
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
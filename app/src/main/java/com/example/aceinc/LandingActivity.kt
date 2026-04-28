package com.example.aceinc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        val startBtn = findViewById<Button>(R.id.startBtn)

        startBtn.setOnClickListener {
            
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
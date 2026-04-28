package com.example.aceinc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<Button>(R.id.backBtn).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.accountBtn).setOnClickListener {
            startActivity(Intent(this, AccountActivity::class.java))
        }

        findViewById<Button>(R.id.budgetBtn).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        findViewById<Button>(R.id.gameBtn).setOnClickListener {
            // placeholder for game screen
        }
    }
}
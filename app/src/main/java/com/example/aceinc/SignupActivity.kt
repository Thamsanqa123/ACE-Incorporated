package com.example.aceinc

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        db = DatabaseHelper(this)

        val email = findViewById<EditText>(R.id.email)
        val username = findViewById<EditText>(R.id.newUsername)
        val password = findViewById<EditText>(R.id.newPassword)
        val btn = findViewById<Button>(R.id.createAccountBtn)

        btn.setOnClickListener {

            val emailText = email.text.toString().trim()
            val userText = username.text.toString().trim()
            val passText = password.text.toString().trim()

            if (emailText.isEmpty() || userText.isEmpty() || passText.isEmpty()) {
                Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passText.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //  SAVE TO DATABASE
            val success = db.registerUser(userText, emailText, passText)

            if (success) {
                Toast.makeText(this, "Account created successfully ", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Username already exists ❌", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
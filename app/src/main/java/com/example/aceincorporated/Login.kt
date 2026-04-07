package com.example.aceincorporated


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

class Login : AppCompatActivity() {

    // UI Components
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var signupRedirect: TextView

    // Database
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize views
        initializeViews()

        // Initialize Database Helper
        databaseHelper = DatabaseHelper(this)

        // Set up click listeners
        setupClickListeners()

        // Set up text change listeners for real-time validation (optional)
        setupTextChangeListeners()
    }

    private fun initializeViews() {
        email = findViewById(R.id.emailText)
        password = findViewById(R.id.passwordText)
        loginBtn = findViewById(R.id.loginBtn)
        signupRedirect = findViewById(R.id.signupRedirect)
    }

    private fun setupClickListeners() {
        // Navigate to Signup
        signupRedirect.setOnClickListener {
            navigateToSignup()
        }

        // Login button
        loginBtn.setOnClickListener {
            attemptLogin()
        }
    }

    private fun setupTextChangeListeners() {
        // Clear errors when user starts typing
        email.addTextChangedListener {
            email.error = null
        }

        password.addTextChangedListener {
            password.error = null
        }
    }

    private fun attemptLogin() {
        // Get input values
        val emailText = email.text.toString().trim()
        val passwordText = password.text.toString().trim()

        // Validate inputs
        if (!validateInputs(emailText, passwordText)) {
            return
        }

        // Perform login
        performLogin(emailText, passwordText)
    }

    private fun validateInputs(email: String, password: String): Boolean {
        var isValid = true

        // Validate email
        if (TextUtils.isEmpty(email)) {
            this.email.error = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.email.error = "Enter a valid email address"
            isValid = false
        }

        // Validate password
        if (TextUtils.isEmpty(password)) {
            this.password.error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            this.password.error = "Password must be at least 6 characters"
            isValid = false
        }

        return isValid
    }

    private fun performLogin(email: String, password: String) {
        try {
            val userExists = databaseHelper.checkUser(email, password)

            if (userExists) {
                // Login successful
                showToast("Login Successful", Toast.LENGTH_SHORT)

                // Clear sensitive data
                this.password.text?.clear()

                // Navigate to MainActivity
                navigateToMainActivity()
            } else {
                // Login failed
                showToast("Invalid Email or Password", Toast.LENGTH_SHORT)

                // Clear password field for security
                this.password.text?.clear()

                // Set focus to email field
                this.email.requestFocus()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            showToast("An error occurred. Please try again.", Toast.LENGTH_LONG)
        }
    }

    private fun navigateToSignup() {
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)
        // Don't finish here to allow back navigation to login
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)

        // Clear back stack so user can't go back to loginPage
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

    // Handle back button press
//    override fun onBackPressed() {
//        // If you want to confirm exit or just call super
//        super.onBackPressed()
//    }
}

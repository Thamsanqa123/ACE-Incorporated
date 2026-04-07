package com.example.aceincorporated



import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Signup : AppCompatActivity() {

    // UI Components
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnLoginRedirect: Button

    // Database
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize views
        initializeViews()

        // Initialize Database Helper
        databaseHelper = DatabaseHelper(this)

        // Set up click listeners
        setupClickListeners()
    }

    private fun initializeViews() {
        etFirstName = findViewById(R.id.textName)
        etLastName = findViewById(R.id.textSurname)
        etEmail = findViewById(R.id.textEmail)
        etPassword = findViewById(R.id.textPassword)
        btnRegister = findViewById(R.id.signupBtn)
        btnLoginRedirect = findViewById(R.id.loginRedirect)
    }

    private fun setupClickListeners() {
        btnLoginRedirect.setOnClickListener {
            navigateToLogin()
        }

        btnRegister.setOnClickListener {
            attemptRegistration()
        }
    }

    private fun attemptRegistration() {
        // Reset errors
        resetErrors()

        // Get input values
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        // Validate inputs
        if (!validateInputs(firstName, lastName, email, password)) {
            return
        }

        // Attempt to register user
        performRegistration(firstName, lastName, email, password)
    }

    private fun validateInputs(firstName: String, lastName: String, email: String, password: String): Boolean {
        var isValid = true

        if (TextUtils.isEmpty(firstName)) {
            etFirstName.error = "First name is required"
            isValid = false
        }

        if (TextUtils.isEmpty(lastName)) {
            etLastName.error = "Last name is required"
            isValid = false
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.error = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Enter a valid email address"
            isValid = false
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            etPassword.error = "Password must be at least 6 characters"
            isValid = false
        }

        return isValid
    }

    private fun resetErrors() {
        etFirstName.error = null
        etLastName.error = null
        etEmail.error = null
        etPassword.error = null
    }

    private fun performRegistration(firstName: String, lastName: String, email: String, password: String) {
        try {
            // Check if user already exists
            if (databaseHelper.checkUser(email, password)) {
                showToast("Email already registered", Toast.LENGTH_LONG)
                return
            }

            val insertRowId = databaseHelper.insertUser(firstName, lastName, email, password)

            if (insertRowId != -1L) {
                showToast("Registration successful!", Toast.LENGTH_LONG)
                navigateToLogin()
            } else {
                showToast("Registration failed. Please try again.", Toast.LENGTH_LONG)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            showToast("An error occurred during registration", Toast.LENGTH_LONG)
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, Login::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }
}

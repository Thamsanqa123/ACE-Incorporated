package com.example.aceinc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        db = DatabaseHelper(this)

        // GET USER ID
        userId = intent.getIntExtra("userId", -1)

        val balanceText = findViewById<TextView>(R.id.balanceText)

        //  SHOW  TOTAL
        val total = db.getTotalExpenses(userId)
        balanceText.text = "R %.2f".format(total)

        // ADD EXPENSE
        findViewById<Button>(R.id.addExpenseBtn).setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        // VIEW EXPENSES
        findViewById<Button>(R.id.statsBtn).setOnClickListener {
            val intent = Intent(this, ViewExpensesActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        // ACCOUNT
        findViewById<Button>(R.id.accountBtn).setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.backBtn).setOnClickListener {
            finish()
        }
    }

    //  refresh when coming back from add/edit
    override fun onResume() {
        super.onResume()
        val total = db.getTotalExpenses(userId)
        findViewById<TextView>(R.id.balanceText).text = "R %.2f".format(total)
    }
}
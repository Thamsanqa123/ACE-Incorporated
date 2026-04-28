package com.example.aceinc

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewExpensesActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpenseAdapter

    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expenses)

        db = DatabaseHelper(this)

        userId = intent.getIntExtra("userId", -1)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadExpenses()
    }

    private fun loadExpenses() {

        val expenses = db.getExpenses(userId)

        adapter = ExpenseAdapter(
            expenses,
            db,
            onDelete = {
                loadExpenses() //  refresh after delete
            },
            onEdit = { expense ->
                Toast.makeText(this, "Editing ${expense.title}", Toast.LENGTH_SHORT).show()

                //  NEXT STEP: open edit screen
                // val intent = Intent(this, EditExpenseActivity::class.java)
                // intent.putExtra("expenseId", expense.id)
                // startActivity(intent)
            }
        )

        recyclerView.adapter = adapter
    }
}
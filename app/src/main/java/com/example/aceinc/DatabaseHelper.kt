package com.example.aceinc


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "AceDB", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("""
            CREATE TABLE users(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE,
                email TEXT,
                password TEXT
            )
        """)

        db.execSQL("""
            CREATE TABLE expenses(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                userId INTEGER,
                title TEXT,
                amount REAL DEFAULT 0,
                category TEXT,
                date TEXT,
                startTime TEXT,
                endTime TEXT,
                imageUri TEXT,
                FOREIGN KEY(userId) REFERENCES users(id)
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS expenses")
        onCreate(db)
    }

    // REGISTER
    fun registerUser(username: String, email: String, password: String): Boolean {
        val db = writableDatabase
        val values = ContentValues()

        values.put("username", username)
        values.put("email", email)
        values.put("password", password)

        return db.insert("users", null, values) != -1L
    }

    // LOGIN
    fun loginUser(username: String, password: String): Int {
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT id FROM users WHERE username=? AND password=?",
            arrayOf(username, password)
        )

        val userId = if (cursor.moveToFirst()) cursor.getInt(0) else -1
        cursor.close()
        return userId
    }

    // GET USER
    fun getUser(userId: Int): Pair<String, String> {
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT username, email FROM users WHERE id=?",
            arrayOf(userId.toString())
        )

        var username = ""
        var email = ""

        if (cursor.moveToFirst()) {
            username = cursor.getString(0)
            email = cursor.getString(1)
        }

        cursor.close()
        return Pair(username, email)
    }

    // INSERT EXPENSE
    fun insertExpense(
        userId: Int,
        title: String,
        amount: Double,
        category: String,
        date: String,
        startTime: String,
        endTime: String,
        imageUri: String
    ): Boolean {

        val db = writableDatabase
        val values = ContentValues()

        values.put("userId", userId)
        values.put("title", title)
        values.put("amount", amount)
        values.put("category", category)
        values.put("date", date)
        values.put("startTime", startTime)
        values.put("endTime", endTime)
        values.put("imageUri", imageUri)

        return db.insert("expenses", null, values) != -1L
    }

    // GET EXPENSES
    fun getExpenses(userId: Int): MutableList<ExpenseModel> {

        val list = mutableListOf<ExpenseModel>()
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM expenses WHERE userId=? ORDER BY id DESC",
            arrayOf(userId.toString())
        )

        while (cursor.moveToNext()) {

            val expense = ExpenseModel(
                id = cursor.getInt(0),
                userId = cursor.getInt(1),
                title = cursor.getString(2),
                amount = cursor.getDouble(3),
                category = cursor.getString(4),
                date = cursor.getString(5),
                startTime = cursor.getString(6),
                endTime = cursor.getString(7),
                imageUri = cursor.getString(8) ?: ""
            )

            list.add(expense)
        }

        cursor.close()
        return list
    }

    // DELETE
    fun deleteExpense(id: Int): Boolean {
        val db = writableDatabase
        return db.delete("expenses", "id=?", arrayOf(id.toString())) > 0
    }

    // UPDATE
    fun updateExpense(
        id: Int,
        title: String,
        amount: Double,
        category: String,
        date: String,
        startTime: String,
        endTime: String
    ): Boolean {

        val db = writableDatabase
        val values = ContentValues()

        values.put("title", title)
        values.put("amount", amount)
        values.put("category", category)
        values.put("date", date)
        values.put("startTime", startTime)
        values.put("endTime", endTime)

        return db.update("expenses", values, "id=?", arrayOf(id.toString())) > 0
    }

    // TOTAL
    fun getTotalExpenses(userId: Int): Double {
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT SUM(amount) FROM expenses WHERE userId=?",
            arrayOf(userId.toString())
        )

        val total = if (cursor.moveToFirst() && !cursor.isNull(0)) {
            cursor.getDouble(0)
        } else 0.0

        cursor.close()
        return total
    }
}
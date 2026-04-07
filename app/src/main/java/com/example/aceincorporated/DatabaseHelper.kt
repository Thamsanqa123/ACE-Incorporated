package com.example.aceincorporated



import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(private val context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "userDatabase.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "data"
        private const val COLUMN_ID = "id"
        private const val COLUMN_FIRSTNAME = "firstname"
        private const val COLUMN_LASTNAME = "lastname"
        private const val COLUMN_EMAIL= "email"
        private const val COLUMN_PASSWORD = "password"

//        const val TABLE_CATEGORY = "categories"
//        const val TABLE_EXPENSE = "expenses"

//        object CategoryEntry : BaseColumns {
//
//            const val COLUMN_ID = "id"
//            const val TABLE_NAME = "categories"
//            const val COLUMN_NAME = "name"
//            const val COLUMN_ICON = "icon"
//            const val COLUMN_COLOR = "color"
//            const val COLUMN_CREATED_AT = "created_at"
//        }

        // Expense table columns
//        object ExpenseEntry : BaseColumns {
//            const val COLUMN_ID = "id"
//            const val TABLE_NAME = "expenses"
//            const val COLUMN_DATE = "date"
//            const val COLUMN_START_TIME = "start_time"
//            const val COLUMN_END_TIME = "end_time"
//            const val COLUMN_DESCRIPTION = "description"
//            const val COLUMN_AMOUNT = "amount"
//            const val COLUMN_CATEGORY_ID = "category_id"
//            const val COLUMN_PHOTO_PATH = "photo_path"
//            const val COLUMN_CREATED_AT = "created_at"
//        }




    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_FIRSTNAME TEXT, " +
                "$COLUMN_LASTNAME TEXT," +
                "$COLUMN_EMAIL TEXT, "+
                "$COLUMN_PASSWORD TEXT)")
        db?.execSQL(createTableQuery)
//
//        val createCategoryTable = """
//            CREATE TABLE ${CategoryEntry.TABLE_NAME} (
//                ${CategoryEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
//                ${CategoryEntry.COLUMN_NAME} TEXT NOT NULL,
//                ${CategoryEntry.COLUMN_ICON} TEXT,
//                ${CategoryEntry.COLUMN_COLOR} TEXT,
//                ${CategoryEntry.COLUMN_CREATED_AT} INTEGER DEFAULT (strftime('%s', 'now'))
//            )
//        """.trimIndent()

//        // Create Expense table
//        val createExpenseTable = """
//            CREATE TABLE ${ExpenseEntry.TABLE_NAME} (
//                ${ExpenseEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
//                ${ExpenseEntry.COLUMN_DATE} INTEGER NOT NULL,
//                ${ExpenseEntry.COLUMN_START_TIME} INTEGER NOT NULL,
//                ${ExpenseEntry.COLUMN_END_TIME} INTEGER NOT NULL,
//                ${ExpenseEntry.COLUMN_DESCRIPTION} TEXT NOT NULL,
//                ${ExpenseEntry.COLUMN_AMOUNT} REAL NOT NULL,
//                ${ExpenseEntry.COLUMN_CATEGORY_ID} INTEGER NOT NULL,
//                ${ExpenseEntry.COLUMN_PHOTO_PATH} TEXT,
//                ${ExpenseEntry.COLUMN_CREATED_AT} INTEGER DEFAULT (strftime('%s', 'now')),
//                FOREIGN KEY (${ExpenseEntry.COLUMN_CATEGORY_ID}) REFERENCES ${CategoryEntry.TABLE_NAME}(${BaseColumns._ID}) ON DELETE RESTRICT
//            )
//        """.trimIndent()
//
//        db?.execSQL(createCategoryTable)
//        db?.execSQL(createExpenseTable)
//
//        // Insert default categories
//        insertDefaultCategories(db)


    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
//        db?.execSQL("DROP TABLE IF EXISTS ${ExpenseEntry.TABLE_NAME}")
//        db?.execSQL("DROP TABLE IF EXISTS ${CategoryEntry.TABLE_NAME}")

        onCreate(db)
    }

//    private fun insertDefaultCategories(db: SQLiteDatabase?) {
//        val defaultCategories = listOf(
//            arrayOf("Food & Dining","#FF5722"),
//            arrayOf("Transportation","#4CAF50"),
//            arrayOf("Shopping", "#2196F3"),
//            arrayOf("Entertainment", "#9C27B0"),
//            arrayOf("Bills & Utilities", "#FF9800"),
//            arrayOf("Healthcare", "#E91E63"),
//            arrayOf("Education", "#00BCD4"),
//            arrayOf("Travel", "#3F51B5"),
//            arrayOf("Other", "#9E9E9E")
//        )
//
//        val contentValues = ContentValues()
//        for (category in defaultCategories) {
//            contentValues.clear()
//            contentValues.put(CategoryEntry.COLUMN_NAME, category[0])
//            contentValues.put(CategoryEntry.COLUMN_ICON, category[1])
//            contentValues.put(CategoryEntry.COLUMN_COLOR, category[2])
//            db?.insert(CategoryEntry.TABLE_NAME, null, contentValues)
//        }
//    }

    fun insertUser(firstname: String, lastname: String, email: String, password: String): Long {
        val values = ContentValues().apply {
            put(COLUMN_FIRSTNAME, firstname)
            put(COLUMN_LASTNAME, lastname)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)

        }
        val db = writableDatabase
        return db.insert(TABLE_NAME, null, values)
    }
    fun checkUser(email: String, password: String): Boolean {
        val db = readableDatabase
        val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(email, password)
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)

        val userExists = cursor.count > 0
        cursor.close()
        return userExists
    }

}
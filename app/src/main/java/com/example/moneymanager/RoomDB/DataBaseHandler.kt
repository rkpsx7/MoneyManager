package com.example.moneymanager.RoomDB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.moneymanager.Models.ExpenseModel

class DataBaseHandler(private val context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERsION) {

    companion object {
        const val DB_NAME = "expensesDB"
        const val DB_VERsION = 1
        const val TABLE_NAME = "tasks"
        const val ID = "id"
        const val ExpenseAmount = "expenseAmount"
        const val ExpenseDesc = "expenseDesc"
        const val ExpenseDate = "expenseDate"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val create_table_query = "CREATE TABLE $TABLE_NAME (" +
                "$ID INTEGER PRIMARY KEY," +
                "$ExpenseAmount INTEGER," +
                "$ExpenseDesc TEXT," +
                "$ExpenseDate TEXT)"
        db?.execSQL(create_table_query)
    }

    fun insertExpensesData(ex_Amount: Int, ex_Desc: String, ex_Date: String) {
        val db = writableDatabase

        val values = ContentValues()
        values.put(ExpenseAmount, ex_Amount)
        values.put(ExpenseDesc, ex_Desc)
        values.put(ExpenseDate, ex_Date)
        //Log.d("akashValues", values.toString())
        val idValue = db.insert(TABLE_NAME, null, values)

        if (idValue.toInt() == -1) {
            Toast.makeText(context, "Failed to insert data", Toast.LENGTH_LONG).show()
           // Log.d("akashID", idValue.toString())
        } else {
            Toast.makeText(context, "Data inserted successfully", Toast.LENGTH_LONG).show()
           // Log.d("akashID", idValue.toString())
        }

    }

    fun getExpensesData(): ArrayList<ExpenseModel> {
        val expenseDataList = ArrayList<ExpenseModel>()

        val db = readableDatabase

        val query = "Select * from $TABLE_NAME"

        val cursor: Cursor? = db?.rawQuery(query, null)

        if (cursor != null) {
            cursor.moveToFirst()
            do {
                //val id = cursor.getInt(cursor.getColumnIndex(ID))
                val amount = cursor.getInt(cursor.getColumnIndex(ExpenseAmount))
                val desc = cursor.getString(cursor.getColumnIndex(ExpenseDesc))
                val date = cursor.getString(cursor.getColumnIndex(ExpenseDate))

                val model = ExpenseModel()
                model.ExpenseAmount = amount
                model.ExpenseDesc = desc
                model.ExpenseDate = date

                expenseDataList.add(model)

            } while (cursor.moveToNext())
        }
        cursor?.close()
        return expenseDataList
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}
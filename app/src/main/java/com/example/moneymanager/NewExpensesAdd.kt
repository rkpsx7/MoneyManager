package com.example.moneymanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moneymanager.RoomDB.DAO
import com.example.moneymanager.Models.ExpenseEntity
import com.example.moneymanager.RoomDB.MainRoomDb
import kotlinx.android.synthetic.main.fragment_new_expense_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewExpensesAdd : AppCompatActivity() {

    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO

    //private var expensesList = mutableListOf<ExpenseEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_new_expense_add)
        mainRoomDb = MainRoomDb.getMainRoomDb(this)
        dao = mainRoomDb.getDao()


        btnAddExp.setOnClickListener {
            val title = etExpTitle.text.toString()
            val desc = etExpDesc.text.toString()
            val date = etExpDate.text.toString()
            val amount = etExpAmount.text.toString()

            val expEntry = ExpenseEntity(amount.toDouble(), title, desc, date)
            CoroutineScope(Dispatchers.IO).launch {
                dao.insertExpense(expEntry)
            }
            goBack()
        }
    }

    private fun goBack() {
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
        onBackPressed()
    }


}




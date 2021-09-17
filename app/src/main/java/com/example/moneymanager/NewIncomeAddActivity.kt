package com.example.moneymanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moneymanager.Models.IncomeEntity
import com.example.moneymanager.RoomDB.DAO
import com.example.moneymanager.RoomDB.MainRoomDb
import kotlinx.android.synthetic.main.activity_new_income_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewIncomeAddActivity : AppCompatActivity() {
    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_income_add)
        mainRoomDb = MainRoomDb.getMainRoomDb(this)
        dao = mainRoomDb.getDao()

        btnAddIncome.setOnClickListener {
            val title = etInTitle.text.toString()
            val desc = etInTitle.text.toString()
            val date = etInDate.text.toString()
            val amount = etInAmount.text.toString()

            val incomeEntry = IncomeEntity(amount.toDouble(), title, desc, date)
            CoroutineScope(Dispatchers.IO).launch {
                dao.insertIncome(incomeEntry)
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
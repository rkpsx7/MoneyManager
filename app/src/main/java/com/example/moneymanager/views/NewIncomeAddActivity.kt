package com.example.moneymanager.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moneymanager.R
import com.example.moneymanager.models.DAO
import com.example.moneymanager.models.IncomeEntity
import com.example.moneymanager.models.MainRoomDb
import com.example.moneymanager.repository.MoneyManagerRepo
import com.example.moneymanager.viewModels.MoneyManagerViewModel
import kotlinx.android.synthetic.main.activity_new_income_add.*

class NewIncomeAddActivity : AppCompatActivity() {
    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_income_add)
        mainRoomDb = MainRoomDb.getMainRoomDb(this)
        dao = mainRoomDb.getDao()
        val repo = MoneyManagerRepo(dao)
        val viewModel = MoneyManagerViewModel(repo)

        btnAddIncome.setOnClickListener {
            val title = etInTitle.text.toString()
            val desc = etInTitle.text.toString()
            val date = etInDate.text.toString()
            val amount = etInAmount.text.toString()

            val incomeEntry = IncomeEntity(amount.toDouble(), title, desc, date)
            viewModel.insertIncome(incomeEntry)
            goBack()
        }
    }

    private fun goBack() {
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
        onBackPressed()
    }
}
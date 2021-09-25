package com.example.moneymanager.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moneymanager.R
import com.example.moneymanager.models.DAO
import com.example.moneymanager.models.ExpenseEntity
import com.example.moneymanager.models.MainRoomDb
import com.example.moneymanager.repository.MoneyManagerRepo
import com.example.moneymanager.viewModels.MoneyManagerViewModel
import kotlinx.android.synthetic.main.fragment_new_expense_add.*

class NewExpensesAdd : AppCompatActivity() {

    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_new_expense_add)
        mainRoomDb = MainRoomDb.getMainRoomDb(this)
        dao = mainRoomDb.getDao()
        val repo = MoneyManagerRepo(dao)
        val viewModel: MoneyManagerViewModel = MoneyManagerViewModel(repo)

        btnAddExp.setOnClickListener {
            val title = etExpTitle.text.toString()
            val desc = etExpDesc.text.toString()
            val date = etExpDate.text.toString()
            val amount = etExpAmount.text.toString()

            val expEntry = ExpenseEntity(amount.toDouble(), title, desc, date)
            viewModel.insertExpense(expEntry)
            goBack()
        }
    }

    private fun goBack() {
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
        onBackPressed()
    }


}




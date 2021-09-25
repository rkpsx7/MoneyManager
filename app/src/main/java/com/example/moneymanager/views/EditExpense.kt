package com.example.moneymanager.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moneymanager.R
import com.example.moneymanager.models.DAO
import com.example.moneymanager.models.ExpenseEntity
import com.example.moneymanager.models.MainRoomDb
import com.example.moneymanager.repository.MoneyManagerRepo
import com.example.moneymanager.viewModels.MoneyManagerViewModel
import kotlinx.android.synthetic.main.activity_edit_expense.*

class EditExpense : AppCompatActivity() {
    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO
    var id: Int? = null
    lateinit var repo: MoneyManagerRepo
    lateinit var viewModel: MoneyManagerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_expense)
        mainRoomDb = MainRoomDb.getMainRoomDb(this)
        dao = mainRoomDb.getDao()
        repo = MoneyManagerRepo(dao)
        viewModel = MoneyManagerViewModel(repo)

        applyOldDataToViews()
        btnUpdateExp.setOnClickListener {
            updateNewData()
        }
    }

    private fun applyOldDataToViews() {

//        etEditExpTitle.setText(exp?.expTitle.toString())
//        etEditExpDesc.setText(exp?.expDesc.toString())
//        etEditExpDate.setText(exp?.expDate.toString())
//        etEditExpAmount.setText(exp?.expAmt.toString())

        etEditExpTitle.setText(intent.getStringExtra("title"))
        etEditExpDesc.setText(intent.getStringExtra("desc"))
        etEditExpDate.setText(intent.getStringExtra("date"))
        etEditExpAmount.setText(intent.getStringExtra("amount"))
        id = intent.getIntExtra("id", 0)
    }

    private fun updateNewData() {
        val title = etEditExpTitle.text.toString()
        val desc = etEditExpDesc.text.toString()
        val date = etEditExpDate.text.toString()
        val amount = etEditExpAmount.text.toString().toDouble()
        val exp = ExpenseEntity(amount, title, desc, date)

        exp.expTitle = title
        exp.expDesc = desc
        exp.expDate = date
        exp.expAmt = amount
        exp.id = id

        viewModel.updateExpense(exp)
        onBackPressed()
    }
}
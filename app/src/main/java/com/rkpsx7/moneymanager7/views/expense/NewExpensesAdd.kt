package com.rkpsx7.moneymanager7.views.expense

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rkpsx7.moneymanager7.R
import com.rkpsx7.moneymanager7.models.DAO
import com.rkpsx7.moneymanager7.models.ExpenseEntity
import com.rkpsx7.moneymanager7.models.MainRoomDb
import com.rkpsx7.moneymanager7.repository.MoneyManagerRepo
import com.rkpsx7.moneymanager7.viewModels.MoneyManagerViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.fragment_new_expense_add.*
import java.text.SimpleDateFormat
import java.util.*

class NewExpensesAdd : AppCompatActivity() {

    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO
    private var curr_date = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        setContentView(R.layout.fragment_new_expense_add)
        mainRoomDb = MainRoomDb.getMainRoomDb(this)
        dao = mainRoomDb.getDao()
        val repo = MoneyManagerRepo(dao)
        val viewModel = MoneyManagerViewModel(repo)
        initDate()

        btnAddExp.setOnClickListener {
            val title = etExpTitle.text.toString()
            val desc = etExpDesc.text.toString()
            val date = etExpDate.text.toString()
            val amount = etExpAmount.text.toString()

            if (title.isNotEmpty() && date.isNotEmpty() && amount.isNotEmpty()) {
                val expEntry = ExpenseEntity(amount.toDouble(), title, desc, date)
                viewModel.insertExpense(expEntry)
                onBackPressed()
            } else {
                toast("Please Fill The Essential Fields")
            }
        }
    }

    private fun initDate() {
        curr_date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        etExpDate.setText(curr_date)

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTheme(R.style.calender_theme)
                .build()




        btn_select_date_exp.setOnClickListener {
            datePicker.show(supportFragmentManager, "DatePicker")
        }

        datePicker.addOnPositiveButtonClickListener { selecton ->
            val timeZoneUTC = TimeZone.getDefault()
            val offsetFromUTC = timeZoneUTC.getOffset(Date().time) * -1

            val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.UK)
            val date = Date(selecton + offsetFromUTC)
            etExpDate.setText(simpleDateFormat.format(date))
        }

    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}




package com.rkpsx7.moneymanager.views.income

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rkpsx7.moneymanager.R
import com.rkpsx7.moneymanager.models.DAO
import com.rkpsx7.moneymanager.models.IncomeEntity
import com.rkpsx7.moneymanager.models.MainRoomDb
import com.rkpsx7.moneymanager.repository.MoneyManagerRepo
import com.rkpsx7.moneymanager.viewModels.MoneyManagerViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.activity_new_income_add.*
import java.text.SimpleDateFormat
import java.util.*

class NewIncomeAdd : AppCompatActivity() {
    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO
    private var curr_date = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        setContentView(R.layout.activity_new_income_add)
        mainRoomDb = MainRoomDb.getMainRoomDb(this)
        dao = mainRoomDb.getDao()
        val repo = MoneyManagerRepo(dao)
        val viewModel = MoneyManagerViewModel(repo)
        initDate()

        btnAddIncome.setOnClickListener {
            val title = etInTitle.text.toString()
            val desc = etInDesc.text.toString()
            val date = etInDate.text.toString()
            val amount = etInAmount.text.toString()

            if (title.isNotEmpty() && date.isNotEmpty() && amount.isNotEmpty()) {
                val incomeEntry = IncomeEntity(amount.toDouble(), title, desc, date)
                viewModel.insertIncome(incomeEntry)
                onBackPressed()
            } else {
                toast("Please Fill The Essential Fields")
            }
        }
    }

    private fun initDate() {
        curr_date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        etInDate.setText(curr_date)

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTheme(R.style.calender_theme)
                .build()




        btn_select_date_inc.setOnClickListener {
            datePicker.show(supportFragmentManager, "DatePicker")
        }

        datePicker.addOnPositiveButtonClickListener { selecton ->
            val timeZoneUTC = TimeZone.getDefault()
            val offsetFromUTC = timeZoneUTC.getOffset(Date().time) * -1

            val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.UK)
            val date = Date(selecton + offsetFromUTC)
            etInDate.setText(simpleDateFormat.format(date))
        }

    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
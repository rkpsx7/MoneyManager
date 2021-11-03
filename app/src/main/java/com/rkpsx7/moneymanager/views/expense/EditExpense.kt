package com.rkpsx7.moneymanager.views.expense

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rkpsx7.moneymanager.R
import com.rkpsx7.moneymanager.models.DAO
import com.rkpsx7.moneymanager.models.ExpenseEntity
import com.rkpsx7.moneymanager.models.MainRoomDb
import com.rkpsx7.moneymanager.repository.MoneyManagerRepo
import com.rkpsx7.moneymanager.viewModels.MoneyManagerViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.activity_edit_expense.*
import java.text.SimpleDateFormat
import java.util.*

class EditExpense : AppCompatActivity() {
    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO
    private var id: Int = 0
    private var curr_date = ""
    lateinit var repo: MoneyManagerRepo
    lateinit var viewModel: MoneyManagerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        setContentView(R.layout.activity_edit_expense)
        mainRoomDb = MainRoomDb.getMainRoomDb(this)
        dao = mainRoomDb.getDao()
        repo = MoneyManagerRepo(dao)
        viewModel = MoneyManagerViewModel(repo)

        initDate()
        applyOldDataToViews()
        btnUpdateExp.setOnClickListener {
            updateNewData()
        }
    }

    private fun applyOldDataToViews() {

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
        val amount = etEditExpAmount.text.toString()

        if (title.isNotEmpty() && date.isNotEmpty() && amount.isNotEmpty()) {
            val exp = ExpenseEntity(amount.toDouble(), title, desc, date)
            exp.id = id

            viewModel.updateExpense(exp)
            onBackPressed()
        } else {
            toast("Please Fill The Essential Fields")
        }
    }

    private fun initDate() {
        curr_date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        etEditExpDate.setText(curr_date)

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTheme(R.style.calender_theme)
                .build()




        btn_select_date_exp_edit.setOnClickListener {
            datePicker.show(supportFragmentManager, "DatePicker")
        }

        datePicker.addOnPositiveButtonClickListener { selecton ->
            val timeZoneUTC = TimeZone.getDefault()
            val offsetFromUTC = timeZoneUTC.getOffset(Date().time) * -1

            val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.UK)
            val date = Date(selecton + offsetFromUTC)
            etEditExpDate.setText(simpleDateFormat.format(date))
        }

    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
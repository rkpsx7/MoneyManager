package com.rkpsx7.moneymanager.views.income

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rkpsx7.moneymanager.R
import com.rkpsx7.moneymanager.models.DAO
import com.rkpsx7.moneymanager.models.IncomeEntity
import com.rkpsx7.moneymanager.models.MainRoomDb
import com.rkpsx7.moneymanager.repository.MoneyManagerRepo
import com.rkpsx7.moneymanager.viewModels.MoneyManagerViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.activity_edit_income.*
import java.text.SimpleDateFormat
import java.util.*

class EditIncome : AppCompatActivity() {
    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO
    private var id: Int = -1
    private var curr_date = ""
    lateinit var repo: MoneyManagerRepo
    lateinit var viewModel: MoneyManagerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        setContentView(R.layout.activity_edit_income)

        mainRoomDb = MainRoomDb.getMainRoomDb(this)
        dao = mainRoomDb.getDao()
        repo = MoneyManagerRepo(dao)
        viewModel = MoneyManagerViewModel(repo)

        initDate()
        applyOldDataToViews()
        btnUpdateIn.setOnClickListener {
            updateNewData()
        }
    }

    private fun applyOldDataToViews() {
        etEditInTitle.setText(intent.getStringExtra("title"))
        etEditInDesc.setText(intent.getStringExtra("desc"))
        etIncEditDate.setText(intent.getStringExtra("date"))
        etEditInAmount.setText(intent.getStringExtra("amount"))
        id = intent.getIntExtra("id", 0)
    }

    private fun updateNewData() {
        val title = etEditInTitle.text.toString()
        val desc = etEditInDesc.text.toString()
        val date = etIncEditDate.text.toString()
        val amount = etEditInAmount.text.toString().toDouble()
        val inc = IncomeEntity(amount, title, desc, date)

        inc.inTitle = title
        inc.inDesc = desc
        inc.inDate = date
        inc.incomeAmt = amount
        inc.id = id

        viewModel.updateIncome(inc)
        onBackPressed()
    }

    private fun initDate() {
//        curr_date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
//        etIncEditDate.setText(curr_date)

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTheme(R.style.calender_theme)
                .build()




        btn_select_date_inc_edit.setOnClickListener {
            datePicker.show(supportFragmentManager, "DatePicker")
        }

        datePicker.addOnPositiveButtonClickListener { selecton ->
            val timeZoneUTC = TimeZone.getDefault()
            val offsetFromUTC = timeZoneUTC.getOffset(Date().time) * -1

            val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.UK)
            val date = Date(selecton + offsetFromUTC)
            etIncEditDate.setText(simpleDateFormat.format(date))
        }

    }
}
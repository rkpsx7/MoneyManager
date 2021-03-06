package com.rkpsx7.moneymanager7.views.profile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.rkpsx7.moneymanager7.R
import com.rkpsx7.moneymanager7.models.DAO
import com.rkpsx7.moneymanager7.models.MainRoomDb
import com.rkpsx7.moneymanager7.repository.MoneyManagerRepo
import com.rkpsx7.moneymanager7.viewModels.MoneyManagerViewModel
import com.rkpsx7.moneymanager7.views.SettingsActivity
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO
    private var totalExp = 0
    private var totalInc = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainRoomDb = MainRoomDb.getMainRoomDb(requireActivity())
        dao = mainRoomDb.getDao()
        val repo = MoneyManagerRepo(dao)
        val viewModel = MoneyManagerViewModel(repo)

        btn_settings.setOnClickListener {
            startActivity(Intent(requireActivity(), SettingsActivity::class.java))
        }

        viewModel.getUserDetailsFromDB().observe(requireActivity(), {
            if (it != null) {
                if (it.profile_img != "N/A") {
                    val img = stringToBitMap(it.profile_img)
                    Glide.with(iv_user_pic).load(img).into(iv_user_pic)
                }
                tv_user_name.text = it.name
                tv_email_id.text = it.email
            }
        })

        viewModel.getTotalExpenses().observe(viewLifecycleOwner, {
            totalExp = it ?: 0
            tvExpenseTotal.text = "??? $totalExp"
            setPieChart()
        })

        viewModel.getTotalIncomes().observe(viewLifecycleOwner, {
            totalInc = it ?: 0
//            if (it != null)
//                totalInc = it
//            else
//                totalInc = 0
            tvIncomeTotal.text = "??? $totalInc"
            setPieChart()
        })


    }

    private fun setPieChart() {
        val pieChartData = ArrayList<PieEntry>()
        pieChartData.add(PieEntry(totalExp.toFloat(), "Expense (in ???)"))
        pieChartData.add(PieEntry(totalInc.toFloat(), "Income (in ???)"))

        val dataSet = PieDataSet(pieChartData, "")
        dataSet.valueTextSize = 15f
        dataSet.setValueTextColors(mutableListOf(R.color.white))
        dataSet.setColors(intArrayOf(R.color.red_chart, R.color.blue_chart), context)
        dataSet.valueTextColor = Color.BLACK

        val pieData = PieData(dataSet)
        val desc = Description()
        desc.text = ""
        pie_chart.data = pieData
        pie_chart.centerText = "STATS"
        pie_chart.setCenterTextColor(Color.WHITE)
        pie_chart.description.isEnabled = true
        pie_chart.animation
        pie_chart.description = desc
        pie_chart.setEntryLabelColor(Color.BLACK)
        pie_chart.legend.textColor = Color.WHITE
        pie_chart.setHoleColor(Color.TRANSPARENT)
        pie_chart.invalidate()

    }

    private fun stringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte =
                Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: java.lang.Exception) {
            e.message
            null
        }
    }


}
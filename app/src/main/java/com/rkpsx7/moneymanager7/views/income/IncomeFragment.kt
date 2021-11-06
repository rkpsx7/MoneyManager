package com.rkpsx7.moneymanager7.views.income

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rkpsx7.moneymanager7.R
import com.rkpsx7.moneymanager7.models.DAO
import com.rkpsx7.moneymanager7.models.IncomeEntity
import com.rkpsx7.moneymanager7.models.MainRoomDb
import com.rkpsx7.moneymanager7.repository.MoneyManagerRepo
import com.rkpsx7.moneymanager7.viewModels.MoneyManagerViewModel
import kotlinx.android.synthetic.main.alert_dialog_delete.view.*
import kotlinx.android.synthetic.main.alt_diag_view_details.view.*
import kotlinx.android.synthetic.main.fragment_income.*

class IncomeFragment : Fragment(R.layout.fragment_income),
    com.rkpsx7.moneymanager7.interfaces.onIncomeItemClickListener {
    private lateinit var adapter: IncomeAdapter
    private val incomeList = mutableListOf<IncomeEntity>()
    private lateinit var mainRoomDb: MainRoomDb
    private lateinit var dao: DAO
    lateinit var repo: MoneyManagerRepo
    lateinit var viewModel: MoneyManagerViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainRoomDb = MainRoomDb.getMainRoomDb(this.requireActivity())
        dao = mainRoomDb.getDao()
        repo = MoneyManagerRepo(dao)
        viewModel = MoneyManagerViewModel(repo)

        newIncomeButton.setOnClickListener {
            val intent = Intent(context, NewIncomeAdd::class.java)
            activity?.startActivity(intent)
        }

        viewModel.getIncomes().observe(viewLifecycleOwner, Observer {
            incomeList.clear()
            incomeList.addAll(it)
            setAdapter()
            updateIncomeDataToServer(incomeList)
        })
    }

    private fun updateIncomeDataToServer(incomeList: MutableList<IncomeEntity>) {
        viewModel.updateIncomeToServer(incomeList)
    }

    private fun setAdapter() {
        incomeList.reverse()
        adapter = IncomeAdapter(incomeList, this)
        recyclerViewIncome.adapter = adapter
        recyclerViewIncome.layoutManager = LinearLayoutManager(context)
    }


    override fun onEditClick(incomeItem: IncomeEntity, position: Int) {
        val intent = Intent(context, EditIncome::class.java)
        intent.putExtra("title", incomeItem.inTitle)
        intent.putExtra("desc", incomeItem.inDesc)
        intent.putExtra("date", incomeItem.inDate)
        intent.putExtra("amount", incomeItem.incomeAmt.toString())
        intent.putExtra("id", incomeItem.id)
        startActivity(intent)
    }

    override fun onClick(incomeItem: IncomeEntity, position: Int) {
        val layoutInflater = LayoutInflater.from(this.requireActivity())
        val view = layoutInflater.inflate(R.layout.alt_diag_view_details, null)

        val alertDialog = AlertDialog.Builder(requireActivity())
            .setView(view)
            .create()

        var desc = incomeItem.inDesc
        if (desc.isEmpty())
            desc = "N/A"
        view.tv_details_header.text = getString(R.string.Income_detail_header)
        view.tv_view_title.text = incomeItem.inTitle
        view.tv_view_desc.text = desc
        view.tv_view_date.text = incomeItem.inDate
        view.tv_view_amount.text = "₹ ${incomeItem.incomeAmt}"

        alertDialog.show()

        view.alt_view_ok_btn.setOnClickListener {
            alertDialog.cancel()
        }
    }

    override fun onLongClick(incomeItem: IncomeEntity, position: Int) {
        val layoutInflater = LayoutInflater.from(this.requireActivity())
        val view = layoutInflater.inflate(R.layout.alert_dialog_delete, null)

        val alertDialog = AlertDialog.Builder(this.requireActivity())
            .setView(view)
            .create()
        alertDialog.show()

        view.alt_dig_yes_btn.setOnClickListener {
            viewModel.deleteIncome(incomeItem)
            alertDialog.cancel()
        }
        view.alt_dig_no_btn.setOnClickListener {
            alertDialog.cancel()
        }
    }
}
package com.example.moneymanager.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneymanager.R
import com.example.moneymanager.models.DAO
import com.example.moneymanager.models.IncomeEntity
import com.example.moneymanager.models.MainRoomDb
import com.example.moneymanager.onIncomeItemClickListener
import com.example.moneymanager.repository.MoneyManagerRepo
import com.example.moneymanager.viewModels.MoneyManagerViewModel
import kotlinx.android.synthetic.main.fragment_income.*

class IncomeFragment(private val actContext: Context) : Fragment(R.layout.fragment_income),
    onIncomeItemClickListener {
    private lateinit var adapter: IncomeAdapter
    private val incomeList = mutableListOf<IncomeEntity>()
    private lateinit var mainRoomDb: MainRoomDb
    private lateinit var dao: DAO
    lateinit var repo: MoneyManagerRepo
    lateinit var viewModel: MoneyManagerViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainRoomDb = MainRoomDb.getMainRoomDb(actContext)
        dao = mainRoomDb.getDao()
        repo = MoneyManagerRepo(dao)
        viewModel = MoneyManagerViewModel(repo)

        newIncomeButton.setOnClickListener {
            val intent = Intent(context, NewIncomeAddActivity::class.java)
            activity?.startActivity(intent)
        }
        setAdapter()
        viewModel.getIncomes().observe(viewLifecycleOwner, Observer {
            incomeList.clear()
            incomeList.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun setAdapter() {
        adapter = IncomeAdapter(incomeList, this)
        recyclerViewIncome.adapter = adapter
        recyclerViewIncome.layoutManager = LinearLayoutManager(context)

    }


    override fun onClick(incomeItem: IncomeEntity, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onLongClick(incomeItem: IncomeEntity, position: Int) {
        viewModel.deleteIncome(incomeItem)
    }
}
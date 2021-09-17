package com.example.moneymanager.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneymanager.Adapters.IncomeAdapter
import com.example.moneymanager.Models.IncomeEntity
import com.example.moneymanager.NewIncomeAddActivity
import com.example.moneymanager.R
import com.example.moneymanager.RoomDB.DAO
import com.example.moneymanager.RoomDB.MainRoomDb
import kotlinx.android.synthetic.main.fragment_income.*

class IncomeFragment(context1: Context) : Fragment(R.layout.fragment_income) {
    private val actContext: Context = context1
    private lateinit var adapter: IncomeAdapter
    private val incomeList = mutableListOf<IncomeEntity>()
    private lateinit var mainRoomDb: MainRoomDb
    private lateinit var dao: DAO

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainRoomDb = MainRoomDb.getMainRoomDb(actContext)
        dao = mainRoomDb.getDao()

        newIncomeButton.setOnClickListener{
            val intent = Intent(context, NewIncomeAddActivity::class.java)
            activity?.startActivity(intent)
        }
        setAdapter()
        dao.getIncome().observe(viewLifecycleOwner, Observer {
            val income = it
            incomeList.clear()
            incomeList.addAll(income)
            adapter.notifyDataSetChanged()
        })
    }

    private fun setAdapter() {
        adapter = IncomeAdapter(incomeList)
        recyclerViewIncome.adapter = adapter
        recyclerViewIncome.layoutManager = LinearLayoutManager(context)

    }
}
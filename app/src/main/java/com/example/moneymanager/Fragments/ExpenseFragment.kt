package com.example.moneymanager.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneymanager.Adapters.ExpensesAdapter
import com.example.moneymanager.NewExpensesAdd
import com.example.moneymanager.R
import com.example.moneymanager.RoomDB.DAO
import com.example.moneymanager.Models.ExpenseEntity
import com.example.moneymanager.RoomDB.MainRoomDb
import kotlinx.android.synthetic.main.fragment_expense.*

class ExpenseFragment(context1: Context) : Fragment(R.layout.fragment_expense) {

    private val actContext: Context = context1
    lateinit var adapter: ExpensesAdapter

    //private val dbHandler = DataBaseHandler(context1)
    private var expenseList = mutableListOf<ExpenseEntity>()
    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //dbHandler.insertExpensesData(70, "Pizza", "Pizza at Domino's")

        mainRoomDb = MainRoomDb.getMainRoomDb(actContext)
        dao = mainRoomDb.getDao()


        newExpenseButton.setOnClickListener {

//            val exp1 = ExpenseEntity(100.0, "Pizza", "Pizza at Domino`s", "12-12-2021")
//
//            CoroutineScope(Dispatchers.IO).launch {
//                dao.insertExpense(exp1)
//            }

            val intent = Intent(context, NewExpensesAdd::class.java)
            activity?.startActivity(intent)


//        newExpenseButton.setOnClickListener {
//            val newExpenseAddFragment = NewExpenseAddFragment()
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.add(R.id.tab_viewPager, newExpenseAddFragment)?.addToBackStack("")?.commit()
//        }
        }


        //        dataList.clear()
//        dataList = dbHandler.getExpensesData()
        setAdapter()


        dao.getExpense().observe(viewLifecycleOwner, Observer {
            val expense = it
            expenseList.clear()
            expenseList.addAll(expense)
            adapter.notifyDataSetChanged()
        })


    }

    private fun setAdapter() {
        adapter = ExpensesAdapter(expenseList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
        Log.d("rkpsx7","onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("rkpsx7","onDestroy")
    }

}
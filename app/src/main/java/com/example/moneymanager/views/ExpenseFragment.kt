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
import com.example.moneymanager.models.ExpenseEntity
import com.example.moneymanager.models.MainRoomDb
import com.example.moneymanager.onExpenseItemClickListener
import com.example.moneymanager.repository.MoneyManagerRepo
import com.example.moneymanager.viewModels.MoneyManagerViewModel
import kotlinx.android.synthetic.main.fragment_expense.*
import java.io.Serializable


class ExpenseFragment(private val contextAct: Context) :
    Fragment(R.layout.fragment_expense), onExpenseItemClickListener, Serializable {

    lateinit var adapter: ExpensesAdapter
    private var expenseList = mutableListOf<ExpenseEntity>()
    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO
    lateinit var repo: MoneyManagerRepo
    lateinit var viewModel: MoneyManagerViewModel
    private var deletedItem: ExpenseEntity? = null

//    private var simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//        override fun onMove(
//            recyclerView: RecyclerView,
//            viewHolder: RecyclerView.ViewHolder,
//            target: RecyclerView.ViewHolder
//        ): Boolean {
//        }
//
//        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            var position = viewHolder.adapterPosition
//
//            when (direction) {
//                ItemTouchHelper.LEFT -> {
//                    deletedItem = expenseList[position]
//                    expenseList.removeAt(position)
//                    adapter.notifyItemRemoved(position)
//                }
//            }
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainRoomDb = MainRoomDb.getMainRoomDb(contextAct)
        dao = mainRoomDb.getDao()
        repo = MoneyManagerRepo(dao)
        viewModel = MoneyManagerViewModel(repo)


        newExpenseButton.setOnClickListener {
            val intent = Intent(context, NewExpensesAdd::class.java)
            activity?.startActivity(intent)
//        newExpenseButton.setOnClickListener {
//            val newExpenseAddFragment = NewExpenseAddFragment()
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.add(R.id.tab_viewPager, newExpenseAddFragment)?.addToBackStack("")?.commit()
//        }
        }

        setAdapter()
        viewModel.getExpenses().observe(viewLifecycleOwner, Observer {
            expenseList.clear()
            expenseList.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun setAdapter() {
        adapter = ExpensesAdapter(expenseList, this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun onClick(exp: ExpenseEntity, position: Int) {
        val intent = Intent(context, EditExpense::class.java)
//        intent.putExtra("title", exp.expTitle)
//        intent.putExtra("desc", exp.expDesc)
//        intent.putExtra("date", exp.expDate)
//        intent.putExtra("amount", exp.expAmt.toString())
        startActivity(intent)

//        cvEditExpenseLayout.visibility = View.VISIBLE
//
//        etEditExpTitle.setText(exp.expTitle.toString())
//        etEditExpDesc.setText(exp.expDesc.toString())
//        etEditExpDate.setText(exp.expDate.toString())
//        etEditExpAmount.setText(exp.expAmt.toString())
//
//        btnUpdateExp.setOnClickListener {
//
//            val newtitle = etEditExpTitle.text.toString()
//            val newdesc = etEditExpDesc.text.toString()
//            val date = etEditExpDate.text.toString()
//            val amount = etEditExpAmount.text.toString().toDouble()
//
//            exp.expTitle = newtitle
//            exp.expDesc = newdesc
//            exp.expAmt = amount
//            exp.expDate = date
//            viewModel.updateExpense(exp)
//            cvEditExpenseLayout.visibility = View.GONE
//        }

    }



    override fun onLongClick(expenseItem: ExpenseEntity, position: Int) {
        viewModel.deleteExpense(expenseItem)
    }

}



package com.rkpsx7.moneymanager7.views.expense

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.rkpsx7.moneymanager7.R
import com.rkpsx7.moneymanager7.interfaces.onExpenseItemClickListener
import com.rkpsx7.moneymanager7.models.DAO
import com.rkpsx7.moneymanager7.models.ExpenseEntity
import com.rkpsx7.moneymanager7.models.MainRoomDb
import com.rkpsx7.moneymanager7.repository.MoneyManagerRepo
import com.rkpsx7.moneymanager7.viewModels.MoneyManagerViewModel
import kotlinx.android.synthetic.main.alert_dialog_delete.view.*
import kotlinx.android.synthetic.main.alt_diag_view_details.view.*
import kotlinx.android.synthetic.main.fragment_expense.*
import java.io.Serializable


class ExpenseFragment :
    Fragment(R.layout.fragment_expense), onExpenseItemClickListener, Serializable {

    lateinit var adapter: ExpensesAdapter
    private var expenseList = ArrayList<ExpenseEntity>()
    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO
    lateinit var repo: MoneyManagerRepo
    lateinit var viewModel: MoneyManagerViewModel
    //private lateinit var auth:FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainRoomDb = MainRoomDb.getMainRoomDb(this.requireActivity())
        dao = mainRoomDb.getDao()
        repo = MoneyManagerRepo(dao)
        viewModel = MoneyManagerViewModel(repo)
        //auth = FirebaseAuth.getInstance()

        //Log.i("rrrrrrrrr", "${auth.currentUser?.uid}  ${auth.currentUser?.email}  pic ${auth.currentUser?.photoUrl} name: ${auth.currentUser?.displayName}")
        newExpenseButton.setOnClickListener {
            val intent = Intent(context, NewExpensesAdd::class.java)
            activity?.startActivity(intent)
        }


        viewModel.getExpenses().observe(viewLifecycleOwner, Observer {
            expenseList.clear()
            expenseList.addAll(it)
            setAdapter()
            updateExpenseDataToServer(expenseList)
        })


    }

    private fun updateExpenseDataToServer(expenseList: ArrayList<ExpenseEntity>) {
        viewModel.updateExpensesToServer(expenseList)
    }

    private fun setAdapter() {
        expenseList.reverse()
        adapter = ExpensesAdapter(expenseList, this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun onClick(expenseItem: ExpenseEntity, position: Int) {
        val layoutInflater = LayoutInflater.from(this.requireActivity())
        val view = layoutInflater.inflate(R.layout.alt_diag_view_details, null)

        val alertDialog = AlertDialog.Builder(this.requireActivity())
            .setView(view)
            .create()

        var desc = expenseItem.expDesc
        if (desc.isEmpty())
            desc = "N/A"

        view.tv_details_header.text = getString(R.string.expense_detail_header)
        view.tv_view_title.text = expenseItem.expTitle
        view.tv_view_desc.text = desc
        view.tv_view_date.text = expenseItem.expDate
        view.tv_view_amount.text = "₹ ${expenseItem.expAmt}"

        alertDialog.show()

        view.alt_view_ok_btn.setOnClickListener {
            alertDialog.cancel()
        }
    }


    override fun onEditClick(expenseItem: ExpenseEntity, position: Int) {
        val intent = Intent(context, EditExpense::class.java)
        intent.putExtra("title", expenseItem.expTitle)
        intent.putExtra("desc", expenseItem.expDesc)
        intent.putExtra("date", expenseItem.expDate)
        intent.putExtra("amount", expenseItem.expAmt.toString())
        intent.putExtra("id", expenseItem.id)
        startActivity(intent)
    }


    override fun onLongClick(expenseItem: ExpenseEntity, position: Int) {
        val layoutInflater = LayoutInflater.from(this.requireActivity())
        val view = layoutInflater.inflate(R.layout.alert_dialog_delete, null)

        val alertDialog = AlertDialog.Builder(this.requireActivity())
            .setView(view)
            .create()

        alertDialog.show()


        view.alt_dig_yes_btn.setOnClickListener {
            viewModel.deleteExpense(expenseItem)
            alertDialog.cancel()

        }

        view.alt_dig_no_btn.setOnClickListener {
            alertDialog.cancel()
        }
    }

}



package com.example.moneymanager.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.moneymanager.R
import com.example.moneymanager.models.DAO
import com.example.moneymanager.models.MainRoomDb
import com.example.moneymanager.repository.MoneyManagerRepo
import com.example.moneymanager.viewModels.MoneyManagerViewModel
import kotlinx.android.synthetic.main.fragment_balance.*


class BalanceFragment(private val actContext: Context) : Fragment(R.layout.fragment_balance) {

    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO
    private var totalExp = 0
    private var totalInc = 0
    private var finalBalance = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("rkpsx7","onViewCreate")
        mainRoomDb = MainRoomDb.getMainRoomDb(actContext)
        dao = mainRoomDb.getDao()
        val repo = MoneyManagerRepo(dao)
        val viewModel = MoneyManagerViewModel(repo)


        viewModel.getTotalExpenses().observe(viewLifecycleOwner, Observer {
            if (it != null)
                totalExp = it
            tvExpenseTotal.text = totalExp.toString()
        })

        viewModel.getTotalIncomes().observe(viewLifecycleOwner, Observer {
            if (it != null)
                totalInc = it
            tvIncomeTotal.text = totalInc.toString()

            finalBalance = totalInc - totalExp
            //if (finalBalance < 0)
                //finalBalance = 0
            tvFinalBalance.text = finalBalance.toString()
        })

    }

    override fun onPause() {
        super.onPause()
        Log.d("rkpsx7","onPause")
    }
    override fun onResume() {
        super.onResume()
        Log.d("rkpsx7","onResume")
    }
}
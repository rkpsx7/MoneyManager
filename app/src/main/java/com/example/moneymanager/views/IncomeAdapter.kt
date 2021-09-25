package com.example.moneymanager.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanager.R
import com.example.moneymanager.models.IncomeEntity
import com.example.moneymanager.onIncomeItemClickListener

class IncomeAdapter(
    var incomeList: MutableList<IncomeEntity>,
    private val onIncomeItemClickListener: onIncomeItemClickListener
) :
    RecyclerView.Adapter<IncomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.income_item_layout, parent, false)
        return IncomeViewHolder(v, onIncomeItemClickListener)
    }

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        val income: IncomeEntity = incomeList[position]
        holder.setIncomeData(income)
    }

    override fun getItemCount(): Int {
        return incomeList.size
    }
}
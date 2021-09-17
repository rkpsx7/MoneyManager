package com.example.moneymanager.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanager.Models.IncomeEntity
import com.example.moneymanager.R
import com.example.moneymanager.ViewHolders.IncomeViewHolder

class IncomeAdapter(var incomeList: MutableList<IncomeEntity>) :
    RecyclerView.Adapter<IncomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.income_item_layout, parent, false)
        return IncomeViewHolder(v)
    }

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        val income: IncomeEntity = incomeList[position]
        holder.setIncomeData(income)
    }

    override fun getItemCount(): Int {
        return incomeList.size
    }
}
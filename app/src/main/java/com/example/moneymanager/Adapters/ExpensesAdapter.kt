package com.example.moneymanager.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanager.R
import com.example.moneymanager.Models.ExpenseEntity
import com.example.moneymanager.ViewHolders.ExpenseViewHolder

class ExpensesAdapter(private var dataList: MutableList<ExpenseEntity>) :
    RecyclerView.Adapter<ExpenseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.expenses_item_layout, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val item: ExpenseEntity = dataList[position]
        holder.setData(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
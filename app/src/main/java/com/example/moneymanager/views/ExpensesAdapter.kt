package com.example.moneymanager.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanager.R
import com.example.moneymanager.models.ExpenseEntity
import com.example.moneymanager.onExpenseItemClickListener

class ExpensesAdapter(
    private var dataList: MutableList<ExpenseEntity>,
    private val onExpenseItemClickListener: onExpenseItemClickListener
) :
    RecyclerView.Adapter<ExpenseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.expenses_item_layout, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val item: ExpenseEntity = dataList[position]
        holder.setData(item)

        holder.cvExpenseItem.setOnClickListener {
            onExpenseItemClickListener.onClick(item, position)
        }

        holder.cvExpenseItem.setOnLongClickListener {
            onExpenseItemClickListener.onLongClick(item, position)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
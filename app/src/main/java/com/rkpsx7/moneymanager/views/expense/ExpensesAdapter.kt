package com.rkpsx7.moneymanager.views.expense

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rkpsx7.moneymanager.R
import com.rkpsx7.moneymanager.models.ExpenseEntity
import com.rkpsx7.moneymanager.interfaces.onExpenseItemClickListener
import kotlinx.android.synthetic.main.expenses_item_layout.view.*

class ExpensesAdapter(
    private var dataList: MutableList<ExpenseEntity>,
    private val onExpenseItemClickListener: com.rkpsx7.moneymanager.interfaces.onExpenseItemClickListener
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

        holder.iv_expense_edit.setOnClickListener {
            onExpenseItemClickListener.onEditClick(item, position)
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
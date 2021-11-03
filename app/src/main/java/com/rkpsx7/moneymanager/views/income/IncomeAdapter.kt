package com.rkpsx7.moneymanager.views.income

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rkpsx7.moneymanager.R
import com.rkpsx7.moneymanager.models.IncomeEntity
import com.rkpsx7.moneymanager.interfaces.onIncomeItemClickListener
import kotlinx.android.synthetic.main.income_item_layout.view.*

class IncomeAdapter(
    var incomeList: MutableList<IncomeEntity>,
    private val onIncomeItemClickListener: com.rkpsx7.moneymanager.interfaces.onIncomeItemClickListener
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

        holder.iv_edit_income.setOnClickListener {
            onIncomeItemClickListener.onEditClick(income, position)
        }

        holder.itemView.cvIncomeItem.setOnClickListener {
            onIncomeItemClickListener.onClick(income,position)
        }
    }

    override fun getItemCount(): Int {
        return incomeList.size
    }
}
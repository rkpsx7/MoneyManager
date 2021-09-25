package com.example.moneymanager.views

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanager.models.IncomeEntity
import com.example.moneymanager.onIncomeItemClickListener
import kotlinx.android.synthetic.main.income_item_layout.view.*

class IncomeViewHolder(view: View, private val onIncomeItemClickListener: onIncomeItemClickListener) :
    RecyclerView.ViewHolder(view) {

    fun setIncomeData(model: IncomeEntity) {
        itemView.apply {
            tvIncomeAmount.text = "₹ ${model.incomeAmt.toString()}"
            tvIncomeTitle.text = model.inTitle
            tvIncomeDesc.text = model.inDesc
            tvIncomeDate.text = model.inDate
            itemView.setOnClickListener {
                onIncomeItemClickListener.onLongClick(model, adapterPosition)
            }
        }
    }
}
package com.example.moneymanager.ViewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanager.Models.IncomeEntity
import kotlinx.android.synthetic.main.income_item_layout.view.*

class IncomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun setIncomeData(model: IncomeEntity) {
        itemView.apply {
            tvIncomeAmount.text = "₹ ${model.incomeAmt.toString()}"
            tvIncomeTitle.text = model.inTitle
            tvIncomeDesc.text = model.inDesc
            tvIncomeDate.text = model.inDate
        }
    }
}
package com.example.moneymanager.ViewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanager.Models.ExpenseEntity
import kotlinx.android.synthetic.main.expenses_item_layout.view.*

class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setData(model: ExpenseEntity) {
        itemView.apply {
            tvExpenseAmount.text = "₹ ${model.expAmt.toString()}"
            tvExpenseTitle.text = model.expTitle
            tvExpenseDesc.text = model.expDesc
            tvExpenseDate.text = model.expDate
        }
    }
}
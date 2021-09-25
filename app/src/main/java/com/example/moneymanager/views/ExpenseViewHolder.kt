package com.example.moneymanager.views

import android.view.View
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanager.R
import com.example.moneymanager.models.ExpenseEntity
import kotlinx.android.synthetic.main.expenses_item_layout.view.*

class ExpenseViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
    var cvExpenseItem: CardView = itemView.findViewById(R.id.cvExpenseItem)


    fun setData(model: ExpenseEntity) {
        itemView.apply {
            tvExpenseAmount.text = "₹ ${model.expAmt.toString()}"
            tvExpenseTitle.text = model.expTitle
            tvExpenseDesc.text = model.expDesc
            tvExpenseDate.text = model.expDate
        }
    }
}
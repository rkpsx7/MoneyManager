package com.rkpsx7.moneymanager.views.expense

import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.rkpsx7.moneymanager.R
import com.rkpsx7.moneymanager.models.ExpenseEntity
import kotlinx.android.synthetic.main.expenses_item_layout.view.*

class ExpenseViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
    var cvExpenseItem: ConstraintLayout = itemView.findViewById(R.id.cvExpenseItem)
    var iv_expense_edit = itemView.findViewById<LinearLayout>(R.id.iv_expense_edit)


    fun setData(model: ExpenseEntity) {
        itemView.apply {
            tvExpenseAmount.text = "₹ ${model.expAmt.toString()}"
            tvExpenseTitle.text = model.expTitle
            //tvExpenseDesc.text = model.expDesc
            tvExpenseDate.text = model.expDate
        }
    }
}
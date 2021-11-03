package com.rkpsx7.moneymanager.views.income

import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.rkpsx7.moneymanager.models.IncomeEntity
import com.rkpsx7.moneymanager.interfaces.onIncomeItemClickListener
import kotlinx.android.synthetic.main.income_item_layout.view.*

class IncomeViewHolder(view: View, private val onIncomeItemClickListener: com.rkpsx7.moneymanager.interfaces.onIncomeItemClickListener) :
    RecyclerView.ViewHolder(view) {

    var iv_edit_income: LinearLayout = itemView.iv_income_edit


    fun setIncomeData(model: IncomeEntity) {
        itemView.apply {
            tvIncomeAmount.text = "₹ ${model.incomeAmt.toString()}"
            tvIncomeTitle.text = model.inTitle
            //tvIncomeDesc.text = model.inDesc
            tvIncomeDate.text = model.inDate
            itemView.setOnLongClickListener {
                onIncomeItemClickListener.onLongClick(model, adapterPosition)
                return@setOnLongClickListener true
            }
        }
    }
}
package com.rkpsx7.moneymanager.interfaces

import com.rkpsx7.moneymanager.models.ExpenseEntity
import com.rkpsx7.moneymanager.models.IncomeEntity

interface onIncomeItemClickListener {
    fun onClick(incomeItem: IncomeEntity, position: Int);
    fun onEditClick(incomeItem: IncomeEntity, position: Int);
    fun onLongClick(incomeItem: IncomeEntity, position: Int);
}
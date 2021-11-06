package com.rkpsx7.moneymanager7.interfaces

import com.rkpsx7.moneymanager7.models.IncomeEntity

interface onIncomeItemClickListener {
    fun onClick(incomeItem: IncomeEntity, position: Int);
    fun onEditClick(incomeItem: IncomeEntity, position: Int);
    fun onLongClick(incomeItem: IncomeEntity, position: Int);
}
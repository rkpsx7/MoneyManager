package com.example.moneymanager

import com.example.moneymanager.models.ExpenseEntity
import com.example.moneymanager.models.IncomeEntity

interface onIncomeItemClickListener {
    fun onClick(incomeItem: IncomeEntity, position: Int);
    fun onLongClick(incomeItem: IncomeEntity, position: Int);
}
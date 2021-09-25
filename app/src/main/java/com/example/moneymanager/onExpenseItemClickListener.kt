package com.example.moneymanager

import com.example.moneymanager.models.ExpenseEntity
import java.text.FieldPosition

interface onExpenseItemClickListener {
    fun onClick(expenseItem:ExpenseEntity,position: Int);
    fun onLongClick(expenseItem:ExpenseEntity,position: Int);
}
package com.rkpsx7.moneymanager7.interfaces

import com.rkpsx7.moneymanager7.models.ExpenseEntity

interface onExpenseItemClickListener {
    fun onEditClick(expenseItem:ExpenseEntity,position: Int);
    fun onClick(expenseItem:ExpenseEntity,position: Int);
    fun onLongClick(expenseItem:ExpenseEntity,position: Int);
}
package com.rkpsx7.moneymanager.interfaces

import com.rkpsx7.moneymanager.models.ExpenseEntity
import java.text.FieldPosition

interface onExpenseItemClickListener {
    fun onEditClick(expenseItem:ExpenseEntity,position: Int);
    fun onClick(expenseItem:ExpenseEntity,position: Int);
    fun onLongClick(expenseItem:ExpenseEntity,position: Int);
}
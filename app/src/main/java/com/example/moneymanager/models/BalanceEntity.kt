package com.example.moneymanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BalanceTable")
class BalanceEntity(
    @ColumnInfo(name = "OverAllIncome") var totalIncomeAmt: Double,
    @ColumnInfo(name = "OverAllExpense") var totalExpenseAmt: Double,
    @ColumnInfo(name = "FinalBalance") var finalBalance: Double
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}
package com.rkpsx7.moneymanager7.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ExpenseTable")
data class ExpenseEntity(
    @ColumnInfo(name = "Amount") var expAmt: Double = 0.0,
    @ColumnInfo(name = "title") var expTitle: String = "",
    @ColumnInfo(name = "desc") var expDesc: String = "",
    @ColumnInfo(name = "date") var expDate: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}


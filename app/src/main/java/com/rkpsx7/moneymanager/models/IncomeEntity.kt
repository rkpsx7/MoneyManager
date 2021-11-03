package com.rkpsx7.moneymanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "IncomeTable")
data class IncomeEntity(
    @ColumnInfo(name = "Amount") var incomeAmt: Double=0.0,
    @ColumnInfo(name = "Title") var inTitle: String="",
    @ColumnInfo(name = "Desc") var inDesc: String="",
    @ColumnInfo(name = "Date") var inDate: String=""
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

}
package com.example.moneymanager.RoomDB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moneymanager.Models.BalanceEntity
import com.example.moneymanager.Models.ExpenseEntity
import com.example.moneymanager.Models.IncomeEntity

@Dao
interface DAO {
    //Insert fn
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpense(expObj: ExpenseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIncome(inObj: IncomeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBalance(balObj: BalanceEntity)

    //Fetch fn
    @Query("select * from ExpenseTable")
    fun getExpense(): LiveData<List<ExpenseEntity>>

    @Query("select * from IncomeTable")
    fun getIncome(): LiveData<List<IncomeEntity>>

    @Query("select * from BalanceTable")
    fun getBalance(): LiveData<List<BalanceEntity>>


    //Update fn
    @Update
    fun updateExpense(expObj: ExpenseEntity)

    @Update
    fun updateIncome(inObj: IncomeEntity)

    @Update
    fun updateExpense(balObj: BalanceEntity)

    //Delete fn
    @Delete
    fun deleteExpense(expObj: ExpenseEntity)

    @Delete
    fun deleteIncome(inObj: IncomeEntity)

    @Delete
    fun deleteBalance(balObj: BalanceEntity)

}
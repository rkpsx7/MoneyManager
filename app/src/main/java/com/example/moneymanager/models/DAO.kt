package com.example.moneymanager.models

import androidx.lifecycle.LiveData
import androidx.room.*

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
    @Update()
    fun updateExpense(expObj: ExpenseEntity)

    @Update
    fun updateIncome(inObj: IncomeEntity)

    @Update
    fun updateBalance(balObj: BalanceEntity)

    //Delete fn
    @Delete
    fun deleteExpense(expObj: ExpenseEntity)

    @Delete
    fun deleteIncome(inObj: IncomeEntity)

    @Delete
    fun deleteBalance(balObj: BalanceEntity)

    //get total of money
    @Query("select sum(Amount) from ExpenseTable")
    fun getTotalExpense():LiveData<Int>

    @Query("select sum(Amount) from IncomeTable")
    fun getTotalIncome():LiveData<Int>

}
package com.rkpsx7.moneymanager7.models

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface DAO {
    //Insert fn
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpense(expObj: ExpenseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIncome(inObj: IncomeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserDetails(user: UserEntity)

    //Fetch fn
    @Query("select * from ExpenseTable")
    fun getExpense(): LiveData<List<ExpenseEntity>>

    @Query("select * from IncomeTable")
    fun getIncome(): LiveData<List<IncomeEntity>>

    @Query("select * from User")
    fun getUserDetails(): LiveData<UserEntity>

    //Update fn
    @Update()
    fun updateExpense(expObj: ExpenseEntity)

    @Update
    fun updateIncome(inObj: IncomeEntity)

    @Update
    fun updateUser(user: UserEntity)

    //Delete fn
    @Delete
    fun deleteExpense(expObj: ExpenseEntity)

    @Delete
    fun deleteIncome(inObj: IncomeEntity)

    //get total of money
    @Query("select sum(Amount) from ExpenseTable")
    fun getTotalExpense():LiveData<Int>

    @Query("select sum(Amount) from IncomeTable")
    fun getTotalIncome():LiveData<Int>

}
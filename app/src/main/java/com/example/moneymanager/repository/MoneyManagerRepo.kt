package com.example.moneymanager.repository

import androidx.lifecycle.LiveData
import com.example.moneymanager.models.BalanceEntity
import com.example.moneymanager.models.DAO
import com.example.moneymanager.models.ExpenseEntity
import com.example.moneymanager.models.IncomeEntity
import com.example.moneymanager.models.remote.Network
import com.example.moneymanager.models.remote.requests.LoginRequestModel
import com.example.moneymanager.models.remote.responses.LoginResponse
import com.masai.taskmanagerapp.models.remote.Resource
import com.masai.taskmanagerapp.models.remote.ResponseHandler
import com.example.moneymanager.models.remote.API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoneyManagerRepo(private val dao: DAO) {

    private val api: API = Network.getRetrofit().create(API::class.java)
    private val responseHandler = ResponseHandler()
    private val token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MGE0YmI3OTAzMjdlN2MwNmE2MTk1ODYiLCJpYXQiOjE2MzIxMzg2ODR9.cTxpYQrTfvramIOSPih6b1hJO_x1G-V2GmaRnTYSjU0"



    suspend fun login(loginRequestModel: LoginRequestModel): Resource<LoginResponse> {
        return try {
            val response = api.login(loginRequestModel)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

//    fun createTask(createTaskRequest: CreateTaskRequest){
//        CoroutineScope(Dispatchers.IO).launch {
//            val response = api.createTask(token,createTaskRequest)
//            val newtask = Task(response.title,response.description)
//            taskDAO.addTask(newtask)
//        }
//
//    }

    fun insertExpense(exp: ExpenseEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertExpense(exp)
        }
    }

    fun insertIncome(income: IncomeEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertIncome(income)
        }
    }

    fun insertBalance(bal: BalanceEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertBalance(bal)
        }
    }

    //fetch from DB
    fun getExpenses(): LiveData<List<ExpenseEntity>> {
        return dao.getExpense()
    }

    fun getIncomes(): LiveData<List<IncomeEntity>> {
        return dao.getIncome()
    }

    //balance
    fun getTotalExpenses(): LiveData<Int> {
        return dao.getTotalExpense()
    }

    fun getTotalIncomes(): LiveData<Int> {
        return dao.getTotalIncome()
    }

    //delete
    fun deleteExpense(expObj: ExpenseEntity){
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteExpense(expObj)
        }
    }

    fun deleteIncome(incObj: IncomeEntity){
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteIncome(incObj)
        }
    }

    //update
    fun updateExpense(exp: ExpenseEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.updateExpense(exp)
        }
    }

}
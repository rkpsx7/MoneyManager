package com.rkpsx7.moneymanager.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rkpsx7.moneymanager.models.*
import com.rkpsx7.moneymanager.repository.MoneyManagerRepo

class MoneyManagerViewModel(private val repo: MoneyManagerRepo) : ViewModel() {


    fun getUserDetailsFromDB(): LiveData<UserEntity> {
        return repo.getUserDetailsFromDB()
    }

    fun insertUserToDB(user: UserEntity) {
        repo.insertUserToDB(user)
    }

    fun addUserToServer(user: UserObjForServer) {
        repo.addUserToServer(user)
    }

    fun updateIncomeToServer(income: MutableList<IncomeEntity>) {
        repo.updateIncomeToServer(income)
    }

    fun getRecordsFromServer() {
        repo.getRecordsFromServer()
    }


    fun insertExpense(exp: ExpenseEntity) {
        repo.insertExpense(exp)
    }

    fun insertIncome(income: IncomeEntity) {
        repo.insertIncome(income)
    }


    fun updateExpensesToServer(expenses: ArrayList<ExpenseEntity>) {
        repo.updateExpensesToServer(expenses)
    }

    //update
    fun updateExpense(exp: ExpenseEntity) {
        repo.updateExpense(exp)
    }

    fun updateIncome(Inc: IncomeEntity) {
        repo.updateIncome(Inc)
    }

    //fetch data
    fun getExpenses(): LiveData<List<ExpenseEntity>> {
        return repo.getExpenses()
    }

    fun getIncomes(): LiveData<List<IncomeEntity>> {
        return repo.getIncomes()
    }

    //balance
    fun getTotalExpenses(): LiveData<Int> {
        return repo.getTotalExpenses()
    }

    fun getTotalIncomes(): LiveData<Int> {
        return repo.getTotalIncomes()
    }

    fun deleteExpense(expObj: ExpenseEntity) {
        repo.deleteExpense(expObj)
    }

    fun deleteIncome(incObj: IncomeEntity) {
        repo.deleteIncome(incObj)
    }


//    fun userSignUp(signUpRequestModel: SignUpRequestModel): LiveData<Resource<SignUpResponseModel>> {
//        return liveData(Dispatchers.IO) {
//            val result = repo.signup(signUpRequestModel)
//            emit(result)
//        }
//    }

}
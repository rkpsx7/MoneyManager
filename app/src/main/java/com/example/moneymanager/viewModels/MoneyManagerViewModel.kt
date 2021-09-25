package com.example.moneymanager.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.moneymanager.models.BalanceEntity
import com.example.moneymanager.models.ExpenseEntity
import com.example.moneymanager.models.IncomeEntity
import com.example.moneymanager.models.remote.requests.LoginRequestModel
import com.example.moneymanager.models.remote.responses.LoginResponse
import com.example.moneymanager.repository.MoneyManagerRepo
import com.masai.taskmanagerapp.models.remote.Resource
import kotlinx.coroutines.Dispatchers

class MoneyManagerViewModel(private val repo: MoneyManagerRepo) : ViewModel() {

    fun insertExpense(exp: ExpenseEntity) {
        repo.insertExpense(exp)
    }

    fun insertIncome(income: IncomeEntity) {
        repo.insertIncome(income)
    }

    fun insertBalance(bal: BalanceEntity) {
        repo.insertBalance(bal)
    }

    //update
    fun updateExpense(exp: ExpenseEntity) {
        repo.updateExpense(exp)
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

    fun userLogin(loginRequestModel: LoginRequestModel): LiveData<Resource<LoginResponse>> {
        return liveData(Dispatchers.IO) {
            val result = repo.login(loginRequestModel)
            emit(result)
        }

    }
}
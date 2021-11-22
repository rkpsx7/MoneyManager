package com.rkpsx7.moneymanager7.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rkpsx7.moneymanager7.models.ExpenseEntity
import com.rkpsx7.moneymanager7.models.IncomeEntity
import com.rkpsx7.moneymanager7.models.UserEntity
import com.rkpsx7.moneymanager7.models.UserObjForServer
import com.rkpsx7.moneymanager7.repository.MoneyManagerRepo

class MoneyManagerViewModel(private val repo: MoneyManagerRepo) : ViewModel() {


    fun getUserDetailsFromDB(): LiveData<UserEntity> {
        return repo.getUserDetailsFromDB()
    }

    fun isUserExists(email: String): Boolean {
        return repo.isUserExists(email)
    }

    fun insertUserToDB(user: UserEntity) {
        repo.insertUserToDB(user)
    }

    fun updateUserToDB(user: UserEntity) {
        repo.updateUserToDB(user)
    }

    fun updateUserToServer(map: HashMap<String, String>, email: String) {
        repo.updateUserToServer(map, email)
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
package com.rkpsx7.moneymanager.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.rkpsx7.moneymanager.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MoneyManagerRepo(private val dao: DAO) {

    private val fsRoot = FirebaseFirestore.getInstance()
    private val userUID = FirebaseAuth.getInstance().currentUser?.email!!
    private val userRef = fsRoot.collection("Users")
    private val expenseRef = userRef.document(userUID).collection("expenses")
    private val incomeRef = userRef.document(userUID).collection("incomes")


    fun addUserToServer(user: UserObjForServer) {
        userRef.document(user.email).set(user)
    }

    fun getRecordsFromServer() {
        //expenses
        expenseRef.addSnapshotListener { snapshot, e ->
            if (snapshot != null && !snapshot.isEmpty) {
                val expenses = snapshot.documents
                for (doc in expenses) {
                    val exp = doc.toObject(ExpenseEntity::class.java)
                    Log.d("rkpsx7", exp?.id.toString())
                    CoroutineScope(IO).launch {
                        dao.insertExpense(exp!!)
                    }
                }
            }
        }

        incomeRef.addSnapshotListener { snapshot, e ->
            if (snapshot != null && !snapshot.isEmpty) {
                val incomes = snapshot.documents
                for (doc in incomes) {
                    val inc = doc.toObject(IncomeEntity::class.java)
                    CoroutineScope(IO).launch {
                        dao.insertIncome(inc!!)
                    }
                }
            }
        }
    }


    fun updateExpensesToServer(expenses: ArrayList<ExpenseEntity>) {
        CoroutineScope(IO).launch {
            for (i in 0 until expenses.size) {
                expenseRef.document(expenses[i].id.toString()).set(expenses[i])
            }
        }
    }

    fun updateIncomeToServer(incomes: MutableList<IncomeEntity>) {
        CoroutineScope(IO).launch {
            for (i in 0 until incomes.size) {
                incomeRef.document(incomes[i].id.toString()).set(incomes[i])
            }
        }
    }

    fun getUserDetailsFromDB(): LiveData<UserEntity> {
        return dao.getUserDetails()
    }

    fun insertUserToDB(user: UserEntity) {
        CoroutineScope(IO).launch {
            dao.insertUserDetails(user)
        }
    }

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
            expenseRef.document(expObj.id.toString()).delete()
        }
    }

    fun deleteIncome(incObj: IncomeEntity){
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteIncome(incObj)
            incomeRef.document(incObj.id.toString()).delete()
        }
    }

    //update
    fun updateExpense(exp: ExpenseEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.updateExpense(exp)
        }
    }

    fun updateIncome(Inc: IncomeEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.updateIncome(Inc)
        }
    }

}
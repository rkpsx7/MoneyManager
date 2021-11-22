package com.rkpsx7.moneymanager7.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.rkpsx7.moneymanager7.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MoneyManagerRepo(private val dao: DAO) {

    private val fsRoot = FirebaseFirestore.getInstance()
    private val userID = FirebaseAuth.getInstance().currentUser?.email!!
    private val userRef = fsRoot.collection("Users").document(userID)
    private val expenseRef = userRef.collection("expenses")
    private val incomeRef = userRef.collection("incomes")

    fun isUserExists(email: String): Boolean {
        var isExt = false
        fsRoot.collection("Users").document(email).collection("UserDetails")
            .addSnapshotListener { snapshot, e ->
                if (snapshot != null && !snapshot.isEmpty) {
                    isExt = true
                }
            }
        Log.i("ssss", "isUserExists: $isExt ")
        return isExt

    }

    fun addUserToServer(user: UserObjForServer) {
        fsRoot.collection("Users").document(user.email).collection("UserDetails")
            .document("account").set(user, SetOptions.merge())
    }

    fun updateUserToServer(map: HashMap<String, String>, email: String) {
        CoroutineScope(IO).launch {
            fsRoot.collection("Users").document(email).collection("UserDetails")
                .document("account").set(map, SetOptions.merge())
        }
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
                expenseRef.document(expenses[i].id.toString()).set(expenses[i], SetOptions.merge())
            }
        }
    }

    fun updateIncomeToServer(incomes: MutableList<IncomeEntity>) {
        CoroutineScope(IO).launch {
            for (i in 0 until incomes.size) {
                incomeRef.document(incomes[i].id.toString()).set(incomes[i], SetOptions.merge())
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

    fun updateUserToDB(user: UserEntity) {
        CoroutineScope(IO).launch {
            dao.updateUser(user)
            Log.i("updateuserr", "updateUserToDB:reached ")
        }
    }


    fun insertExpense(exp: ExpenseEntity) {
        CoroutineScope(IO).launch {
            dao.insertExpense(exp)
        }
    }

    fun insertIncome(income: IncomeEntity) {
        CoroutineScope(IO).launch {
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
    fun deleteExpense(expObj: ExpenseEntity) {
        CoroutineScope(IO).launch {
            dao.deleteExpense(expObj)
            expenseRef.document(expObj.id.toString()).delete()
        }
    }

    fun deleteIncome(incObj: IncomeEntity) {
        CoroutineScope(IO).launch {
            dao.deleteIncome(incObj)
            incomeRef.document(incObj.id.toString()).delete()
        }
    }

    //update
    fun updateExpense(exp: ExpenseEntity) {
        CoroutineScope(IO).launch {
            dao.updateExpense(exp)
        }
    }

    fun updateIncome(Inc: IncomeEntity) {
        CoroutineScope(IO).launch {
            dao.updateIncome(Inc)
        }
    }

}
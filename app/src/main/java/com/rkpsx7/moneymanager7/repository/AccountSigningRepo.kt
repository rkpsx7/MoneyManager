package com.rkpsx7.moneymanager7.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.rkpsx7.moneymanager7.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountSigningRepo(val dao: DAO) {
    private val fsRoot = FirebaseFirestore.getInstance()

    suspend fun getUserDetailsFromServer(ID: String) {
        val accDetailsRef =
            fsRoot.collection("Users").document(ID).collection("UserDetails")
        withContext(Dispatchers.IO) {
            accDetailsRef.addSnapshotListener { snapshot, e ->
                if (snapshot != null && !snapshot.isEmpty) {
                    val user = snapshot.documents
                    for (i in user) {
                        val userX = i.toObject(UserObjForServer::class.java)!!
                        val userDB = UserEntity(userX.z_img, userX.name, userX.email)
                        CoroutineScope(IO).launch {
                            dao.insertUserDetails(userDB)
                        }
                    }
                }
            }
        }
    }


    suspend fun getRecordsFromServer(ID: String) {
        withContext(IO) {
            val expenseRef =
                FirebaseFirestore.getInstance().collection("Users").document(ID)
                    .collection("expenses")
            val incomeRef =
                FirebaseFirestore.getInstance().collection("Users").document(ID)
                    .collection("incomes")
            //expenses
            expenseRef.addSnapshotListener { snapshot, e ->
                if (snapshot != null && !snapshot.isEmpty) {
                    val expenses = snapshot.documents
                    for (doc in expenses) {
                        val exp = doc.toObject(ExpenseEntity::class.java)
                        CoroutineScope(IO).launch {
                            dao.insertExpense(exp!!)
                        }
                    }
                }
            }
            //incomes
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
    }

}
package com.rkpsx7.moneymanager7.viewModels

import androidx.lifecycle.ViewModel
import com.rkpsx7.moneymanager7.repository.AccountSigningRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountSigningViewModel(val repo: AccountSigningRepo) : ViewModel() {

    fun getUserDetailsFromServer(ID: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.getUserDetailsFromServer(ID)
        }
    }

    suspend fun getRecordsFromServer(ID: String) {
        repo.getRecordsFromServer(ID)
    }
}
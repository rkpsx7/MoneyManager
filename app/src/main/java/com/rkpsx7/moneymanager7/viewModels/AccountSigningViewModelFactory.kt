package com.rkpsx7.moneymanager7.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rkpsx7.moneymanager7.repository.AccountSigningRepo

class AccountSigningViewModelFactory(private val repo: AccountSigningRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AccountSigningViewModel(repo) as T
    }

}
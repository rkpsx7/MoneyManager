package com.rkpsx7.moneymanager7.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rkpsx7.moneymanager7.repository.MoneyManagerRepo

class ViewModelFactory(private val repo:MoneyManagerRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoneyManagerViewModel(repo) as T
    }
}
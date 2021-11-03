package com.rkpsx7.moneymanager.views

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rkpsx7.moneymanager.views.expense.ExpenseFragment
import com.rkpsx7.moneymanager.views.income.IncomeFragment
import com.rkpsx7.moneymanager.views.profile.ProfileFragment

class viewPagerAdapter(
    val context: Context,
    fm: FragmentManager,
    private var totalTabs: Int,
) :
    FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ExpenseFragment()
            1 -> IncomeFragment()
            2 -> ProfileFragment()

            else -> ExpenseFragment()
        }
    }


}
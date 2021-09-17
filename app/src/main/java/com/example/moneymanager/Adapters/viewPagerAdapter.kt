package com.example.moneymanager.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.moneymanager.Fragments.BalanceFragment
import com.example.moneymanager.Fragments.ExpenseFragment
import com.example.moneymanager.Fragments.IncomeFragment

class viewPagerAdapter(val context: Context, fm: FragmentManager, private var totalTabs: Int) :
    FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ExpenseFragment(context)
            1 -> IncomeFragment(context)
            2 -> BalanceFragment()

            else -> ExpenseFragment(context)
        }
    }


}
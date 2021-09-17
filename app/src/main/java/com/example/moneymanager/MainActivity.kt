package com.example.moneymanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.moneymanager.Adapters.viewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var viewPagerAdapter: viewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.tab_viewPager)

        setSupportActionBar(toolBar)


        tabLayout!!.addTab(tabLayout!!.newTab().setText("Expenses"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Income"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Balance"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        viewPagerAdapter = viewPagerAdapter(this, supportFragmentManager, tabLayout!!.tabCount)
        viewPager?.adapter = viewPagerAdapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))


        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager!!.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

}
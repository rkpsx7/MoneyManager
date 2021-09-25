package com.example.moneymanager.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.moneymanager.R
import com.example.moneymanager.models.DAO
import com.example.moneymanager.models.MainRoomDb
import com.example.moneymanager.repository.MoneyManagerRepo
import com.example.moneymanager.viewModels.MoneyManagerViewModel
import com.example.moneymanager.viewModels.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var viewPagerAdapter: viewPagerAdapter? = null

    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO

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

        mainRoomDb = MainRoomDb.getMainRoomDb(this)
        dao = mainRoomDb.getDao()

        //viewModel...
        //val viewModel = ViewModelProviders.of(this).get(MoneyManagerViewModel::class.java)
        val repo = MoneyManagerRepo(dao)
        val viewModelFactory = ViewModelFactory(repo)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MoneyManagerViewModel::class.java)

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
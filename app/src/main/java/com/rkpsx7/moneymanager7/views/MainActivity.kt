package com.rkpsx7.moneymanager7.views

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.rkpsx7.moneymanager7.R
import com.rkpsx7.moneymanager7.models.DAO
import com.rkpsx7.moneymanager7.models.MainRoomDb
import com.rkpsx7.moneymanager7.repository.MoneyManagerRepo
import com.rkpsx7.moneymanager7.viewModels.MoneyManagerViewModel
import com.rkpsx7.moneymanager7.viewModels.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var viewPagerAdapter: viewPagerAdapter? = null

    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.tab_viewPager)

        setSupportActionBar(toolBar)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Expense"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Income"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Profile"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        mainRoomDb = MainRoomDb.getMainRoomDb(this)
        dao = mainRoomDb.getDao()

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

        app_bar_icon_refresh.setOnClickListener {
            toast("Syncing records with other devices...")
            viewModel.getRecordsFromServer()
            toast("Records are up to date now")
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}
package com.example.moneymanager.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.moneymanager.R
import com.example.moneymanager.models.DAO
import com.example.moneymanager.models.MainRoomDb
import com.example.moneymanager.models.remote.requests.LoginRequestModel
import com.example.moneymanager.repository.MoneyManagerRepo
import com.example.moneymanager.viewModels.MoneyManagerViewModel
import com.example.moneymanager.viewModels.ViewModelFactory
import com.masai.taskmanagerapp.models.remote.Status
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.longToast

class LoginActivity : AppCompatActivity() {
    private lateinit var roomDB: MainRoomDb
    lateinit var dao: DAO
    private lateinit var viewModel: MoneyManagerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        roomDB = MainRoomDb.getMainRoomDb(this)
        dao = roomDB.getDao()
        val repo = MoneyManagerRepo(dao)
        val viewModelFactory = ViewModelFactory(repo)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MoneyManagerViewModel::class.java)

        val loginRequestModel = LoginRequestModel(
            userName = "pradeep1706108@gmail.com",
            password = "dhankhar"
        )

        btnLogin.setOnClickListener {


            viewModel.userLogin(loginRequestModel).observe(this, Observer {
                val response = it

                when (response.status) {
                    Status.SUCCESS -> {
                        val intent = Intent(this, MainActivity::class.java)
                        val name = response.data?.user?.name
                        val email = response.data?.user?.email
                        longToast("$name and $email")
                    }

                    Status.ERROR -> {
                        val error = response.message
                        longToast("$error")
                    }
                    Status.LOADING -> {

                    }
                }

            })
        }

    }
}
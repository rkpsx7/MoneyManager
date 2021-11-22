package com.rkpsx7.moneymanager7.views.create_account_frags

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.rkpsx7.moneymanager7.R
import com.rkpsx7.moneymanager7.models.DAO
import com.rkpsx7.moneymanager7.models.MainRoomDb
import com.rkpsx7.moneymanager7.repository.AccountSigningRepo
import com.rkpsx7.moneymanager7.viewModels.AccountSigningViewModel
import com.rkpsx7.moneymanager7.views.MainActivity
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ActivitySignIn : AppCompatActivity() {
    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO
    lateinit var repo: AccountSigningRepo
    lateinit var viewModel: AccountSigningViewModel
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        setContentView(R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()

        btn_forgot_password.setOnClickListener {
            val resetEmail = et_email_in.text.toString()
            if (isEmailValid()) {
                progressBar_sign_in.visibility = View.VISIBLE
                auth.sendPasswordResetEmail(resetEmail)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            progressBar_sign_in.visibility = View.GONE
                            toast("Please check your email and proceed further.")
                        } else {
                            progressBar_sign_in.visibility = View.GONE
                            toast("${it.exception?.localizedMessage}")
                        }
                    }
            } else
                et_email_in.error = "Please provide an email address"
        }

        btn_start_sign_in.setOnClickListener {
            val email = et_email_in.text.toString()
            val pass = et_password_in.text.toString()
            if (email.isNotEmpty()) {
                if (isEmailValid() && isPasswordValid()) {
                    mainRoomDb = MainRoomDb.getMainRoomDb(this)
                    dao = mainRoomDb.getDao()
                    repo = AccountSigningRepo(dao)
                    viewModel = AccountSigningViewModel(repo)
                    btn_start_sign_in.visibility = View.GONE
                    progressBar_sign_in.visibility = View.VISIBLE
                    auth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                CoroutineScope(IO).launch {
                                    viewModel.getUserDetailsFromServer(email)
                                    viewModel.getRecordsFromServer(email)
                                }
                                startActivity(Intent(this, MainActivity::class.java))
                                finishAffinity()
                            } else {
                                progressBar_sign_in.visibility = View.GONE
                                btn_start_sign_in.visibility = View.VISIBLE
                                toast("${it.exception?.localizedMessage}")
                            }
                        }
                }
            } else {
                toast("Please enter the credentials")
            }
        }
    }

    private fun isPasswordValid(): Boolean {
        return if (et_password_in.text.toString().trim().isNotEmpty()) {
            true
        } else {
            et_password_in.error = "Please provide password"
            false
        }
    }

    private fun isEmailValid(): Boolean {
        return if (et_email_in.text.toString().isNotEmpty() && et_email_in.text.toString()
                .matches(ActivitySignUp.emailValidation.toRegex())
        ) {
            true
        } else {
            et_email_in.error = "Invalid Email"
            false
        }
    }

    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
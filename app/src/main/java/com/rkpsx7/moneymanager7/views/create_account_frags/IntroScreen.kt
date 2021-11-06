package com.rkpsx7.moneymanager7.views.create_account_frags

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.rkpsx7.moneymanager7.R
import com.rkpsx7.moneymanager7.views.MainActivity
import kotlinx.android.synthetic.main.activity_intro_screen.*

class IntroScreen : AppCompatActivity() {
    private lateinit var gAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.BLACK
        setContentView(R.layout.activity_intro_screen)
        gAuth = FirebaseAuth.getInstance()


        Handler().postDelayed({
            if (gAuth.currentUser != null) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                btn_sign_in.visibility = View.VISIBLE
                btn_sign_in.setOnClickListener {
                    viewSignInBottomSheet()
                }
            }
        }, 1500)

    }

    private fun viewSignInBottomSheet() {
        val signInBottomSheet = BottomSheetSignInOptions()
        signInBottomSheet.show(this)
    }
}
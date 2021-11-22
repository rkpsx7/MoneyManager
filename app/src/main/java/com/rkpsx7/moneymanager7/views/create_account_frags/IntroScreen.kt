package com.rkpsx7.moneymanager7.views.create_account_frags

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.rkpsx7.moneymanager7.R
import com.rkpsx7.moneymanager7.models.DAO
import com.rkpsx7.moneymanager7.models.MainRoomDb
import com.rkpsx7.moneymanager7.models.UserEntity
import com.rkpsx7.moneymanager7.models.UserObjForServer
import com.rkpsx7.moneymanager7.repository.AccountSigningRepo
import com.rkpsx7.moneymanager7.repository.MoneyManagerRepo
import com.rkpsx7.moneymanager7.viewModels.MoneyManagerViewModel
import com.rkpsx7.moneymanager7.viewModels.ViewModelFactory
import com.rkpsx7.moneymanager7.views.MainActivity
import kotlinx.android.synthetic.main.activity_intro_screen.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import android.database.CursorWindow




class IntroScreen : AppCompatActivity() {
    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO
    lateinit var repo: MoneyManagerRepo
    lateinit var viewModel: MoneyManagerViewModel
    private lateinit var googleSignInClient: GoogleSignInClient
    private var rcSignIn = 7
    private lateinit var gAuth: FirebaseAuth
    private val fsRoot = FirebaseFirestore.getInstance()
    override fun onStart() {
        super.onStart()
        if (gAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.BLACK
        setContentView(R.layout.activity_intro_screen)
        gAuth = FirebaseAuth.getInstance()
        try {
            val field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.isAccessible = true
            field.set(null, 100 * 1024 * 1024) //the 100MB is the new size
        } catch (e: java.lang.Exception) {
                e.printStackTrace()
        }
        processReqToGoogle()



        Handler().postDelayed({
            layout_sign_options.visibility = View.VISIBLE


            btn_continue_with_google.setOnClickListener {
                executeLoginProcess()
            }


            btn_more_options.setOnClickListener {
                viewSignInBottomSheet()
            }
        }, 1500)
    }

    private fun viewSignInBottomSheet() {
        val signInBottomSheet = BottomSheetSignInOptions()
        signInBottomSheet.show(this)
    }

    private fun processReqToGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))//ignore this error and run the app.
            .requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun executeLoginProcess() {
        val signingIntent = googleSignInClient.signInIntent
        startActivityForResult(signingIntent, rcSignIn)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        progressBar.visibility = View.VISIBLE
        if (requestCode == rcSignIn) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: Exception) {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        gAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    mainRoomDb = MainRoomDb.getMainRoomDb(this)
                    dao = mainRoomDb.getDao()
                    repo = MoneyManagerRepo(dao)
                    val viewModelFactory = ViewModelFactory(repo)
                    viewModel = ViewModelProviders.of(this, viewModelFactory)
                        .get(MoneyManagerViewModel::class.java)

                    CoroutineScope(IO).launch {
                        withContext(IO) {
                            val user = gAuth.currentUser
                            val name = user?.displayName
                            val email = user?.email

                            if (isUserExists(email!!)) {
                                val crt = AccountSigningRepo(dao)
                                crt.getUserDetailsFromServer(email)
                                crt.getRecordsFromServer(email)
                            } else {
                                val userEntity = UserEntity("N/A", name!!, email)
                                viewModel.insertUserToDB(userEntity)
                                val us = UserObjForServer(user.uid, name, email, "N/A")
                                viewModel.addUserToServer(us)
                            }
                        }
                    }

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    toast("Please check your internet connection or try again")
                    progressBar.visibility = View.GONE
                }
            }
    }
    private fun isUserExists(email: String): Boolean {
        var isExt = false
        Log.d("qwaszx", "isUserExists: $email")
        fsRoot.collection("Users").document(email).collection("UserDetails")
            .addSnapshotListener { snapshot, e ->
                if (snapshot!=null && !snapshot!!.isEmpty) {
                    Log.d("qwaszx", "snp: ${snapshot.isEmpty}")
                    isExt = true
                }
            }
        Log.d("qwaszx", "isUserExists: $isExt ")
        return isExt

    }

//    private suspend fun getImage(img_url: String): Bitmap {//coil
//        val loader = ImageLoader(this)
//        val request = ImageRequest.Builder(this)
//            .data(img_url)
//            .build()
//
//        val result = (loader.execute(request) as SuccessResult).drawable
//        return (result as BitmapDrawable).bitmap
//    }
//
//    private fun bitMapToString(bitmap: Bitmap): String? {
//        val baos = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
//        val b: ByteArray = baos.toByteArray()
//        return Base64.encodeToString(b, Base64.DEFAULT)
//    }

//    private fun stringToBitMap(encodedString: String?): Bitmap? {
//        return try {
//            val encodeByte =
//                Base64.decode(encodedString, Base64.DEFAULT)
//            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
//        } catch (e: java.lang.Exception) {
//            e.message
//            null
//        }
//    }


    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
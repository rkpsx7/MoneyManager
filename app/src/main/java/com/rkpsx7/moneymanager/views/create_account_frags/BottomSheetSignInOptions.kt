package com.rkpsx7.moneymanager.views.create_account_frags

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.rkpsx7.moneymanager.R
import com.rkpsx7.moneymanager.models.DAO
import com.rkpsx7.moneymanager.models.MainRoomDb
import com.rkpsx7.moneymanager.models.UserEntity
import com.rkpsx7.moneymanager.models.UserObjForServer
import com.rkpsx7.moneymanager.repository.MoneyManagerRepo
import com.rkpsx7.moneymanager.ui_fixes.CustomBottomSheet
import com.rkpsx7.moneymanager.viewModels.MoneyManagerViewModel
import com.rkpsx7.moneymanager.views.MainActivity
import kotlinx.android.synthetic.main.activity_bottom_sheet_sign_in.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


class BottomSheetSignInOptions : CustomBottomSheet() {

    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO
    lateinit var repo: MoneyManagerRepo
    lateinit var viewModel: MoneyManagerViewModel
    private lateinit var googleSignInClient: GoogleSignInClient
    private var rcSignIn = 7
    private lateinit var gAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_bottom_sheet_sign_in, container, false)

    }

    override fun onStart() {
        super.onStart()
        val currentUser = gAuth.currentUser
        if (currentUser != null) {
            startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gAuth = FirebaseAuth.getInstance()
        processReqToGoogle()
        btn_continue_with_google.setOnClickListener {
            executeLoginProcess()
        }
    }



    private fun processReqToGoogle() {
// Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))//ignore this error and run the app.
            .requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)
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
            .addOnCompleteListener(this.requireActivity()) { task ->
                if (task.isSuccessful) {
                    mainRoomDb = MainRoomDb.getMainRoomDb(this.requireActivity())
                    dao = mainRoomDb.getDao()
                    repo = MoneyManagerRepo(dao)
                    viewModel = MoneyManagerViewModel(repo)
                    CoroutineScope(IO).launch {
                        val user = gAuth.currentUser
                        val name = user?.displayName
                        val email = user?.email
                        val profilePic = getImage(user?.photoUrl.toString())
                        val userEntity = UserEntity(profilePic, name!!, email!!)
                        viewModel.insertUserToDB(userEntity)
                        addUserToServer(name, email, profilePic)
                        viewModel.getRecordsFromServer()
                    }
                    startActivity(Intent(context, MainActivity::class.java))

                } else {
                    toast("Please check your internet connection or try again")
                    progressBar.visibility = View.GONE
                }
            }
    }

    private fun addUserToServer(name: String, email: String, pic: Bitmap) {
        val imgB = bitMapToString(pic)
        val us = UserObjForServer(name, email, imgB.toString())
        viewModel.addUserToServer(us)
        activity?.finish()
    }

    private suspend fun getImage(img_url: String): Bitmap {//coil
        val loader = ImageLoader(this.requireActivity())
        val request = ImageRequest.Builder(this.requireActivity())
            .data(img_url)
            .build()

        val result = (loader.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    private fun bitMapToString(bitmap: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

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
        Toast.makeText(this.requireActivity(), msg, Toast.LENGTH_LONG).show()
    }
}
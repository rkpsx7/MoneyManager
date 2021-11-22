package com.rkpsx7.moneymanager7.views.create_account_frags

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.rkpsx7.moneymanager7.R
import com.rkpsx7.moneymanager7.models.DAO
import com.rkpsx7.moneymanager7.models.MainRoomDb
import com.rkpsx7.moneymanager7.models.UserEntity
import com.rkpsx7.moneymanager7.models.UserObjForServer
import com.rkpsx7.moneymanager7.repository.MoneyManagerRepo
import com.rkpsx7.moneymanager7.viewModels.MoneyManagerViewModel
import com.rkpsx7.moneymanager7.views.MainActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivitySignUp : AppCompatActivity() {
    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO
    lateinit var repo: MoneyManagerRepo
    lateinit var viewModel: MoneyManagerViewModel
    private lateinit var auth: FirebaseAuth

    companion object {
        var emailValidation = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()


        btn_start_sign_up.setOnClickListener {
            val emailEt = et_email_up.text.toString()
            val pass = et_password_up.text.toString()
            val name = et_name_up.text.toString()
            performSignUp(emailEt, pass, name)
        }
    }

    private fun performSignUp(emailF: String, pass: String, name: String) {
        if (name.isNotEmpty() && emailF.isNotEmpty() && isEmailValid() && isPasswordValid()) {
            progressBar_sign_up.visibility = View.VISIBLE
            btn_start_sign_up.visibility = View.GONE
            auth.createUserWithEmailAndPassword(emailF, pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        toast("Please Verify your email")
                        auth.currentUser?.sendEmailVerification()
                        mainRoomDb = MainRoomDb.getMainRoomDb(this)
                        dao = mainRoomDb.getDao()
                        repo = MoneyManagerRepo(dao)
                        viewModel = MoneyManagerViewModel(repo)
                        CoroutineScope(IO).launch {
                            withContext(IO) {
                                val userEntity = UserEntity("N/A", name, emailF)
                                viewModel.insertUserToDB(userEntity)
                                val us =
                                    UserObjForServer(auth.currentUser?.uid!!, name, emailF, "N/A")
                                viewModel.addUserToServer(us)
                            }
                        }
                        startActivity(Intent(this, MainActivity::class.java))
                        finishAffinity()
                    } else {
                        progressBar_sign_up.visibility = View.GONE
                        btn_start_sign_up.visibility = View.VISIBLE
                        toast("${it.exception?.localizedMessage}")
                    }
                }
        } else {
            toast("Please enter all required details")
        }
    }

    private fun isPasswordValid(): Boolean {
        return if (et_password_up.text.toString().trim().length >= 6) {
            true
        } else {
            et_password_up.error = "Password Length is less than 6"
            false
        }
    }

    private fun isEmailValid(): Boolean {
        return if (et_email_up.text.toString().matches(emailValidation.toRegex())) {
            true
        } else {
            et_email_up.error = "Invalid Email"
            false
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
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


/*
/**
 * reduces the size of the image
 * @param image
 * @param maxSize
 * @return
 */
public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
    int width = image.getWidth();
    int height = image.getHeight();

    float bitmapRatio = (float)width / (float) height;
    if (bitmapRatio > 1) {
        width = maxSize;
        height = (int) (width / bitmapRatio);
    } else {
        height = maxSize;
        width = (int) (height * bitmapRatio);
    }
    return Bitmap.createScaledBitmap(image, width, height, true);
}
 */
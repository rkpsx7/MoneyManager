package com.rkpsx7.moneymanager7.views

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.rkpsx7.moneymanager7.R
import com.rkpsx7.moneymanager7.models.DAO
import com.rkpsx7.moneymanager7.models.MainRoomDb
import com.rkpsx7.moneymanager7.models.UserEntity
import com.rkpsx7.moneymanager7.repository.MoneyManagerRepo
import com.rkpsx7.moneymanager7.viewModels.MoneyManagerViewModel
import com.rkpsx7.moneymanager7.views.create_account_frags.IntroScreen
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.alt_diag_edit_name.view.*
import java.io.ByteArrayOutputStream


class SettingsActivity : AppCompatActivity() {
    private lateinit var mainRoomDb: MainRoomDb
    private lateinit var dao: DAO
    private lateinit var repo: MoneyManagerRepo
    private lateinit var viewModel: MoneyManagerViewModel

    private var imgStr = "N/A"
    private var name = ""
    private var email = ""
    private var dbID = 0

    val GALLARY_REQ_CODE = 7
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        setContentView(R.layout.activity_settings)
        mainRoomDb = MainRoomDb.getMainRoomDb(this)
        dao = mainRoomDb.getDao()
        repo = MoneyManagerRepo(dao)
        viewModel = MoneyManagerViewModel(repo)
        auth = FirebaseAuth.getInstance()

        viewModel.getUserDetailsFromDB().observe(this, {
            if (it != null) {
                if (it.profile_img != "N/A") {
                    imgStr = it.profile_img
                    val img = stringToBitMap(imgStr)
                    Glide.with(iv_user_pic_edit).load(img).into(iv_user_pic_edit)
                }
                dbID = it.id!!
                name = it.name
                email = it.email
                tv_name_edit.text = name
                tv_email_show_only.text = email
            }
        })

        btn_logout.setOnClickListener {
            executeSignOutProcess()
        }

        btn_edit_pic.setOnClickListener {
            imageChooser()
        }

        btn_edit_name.setOnClickListener {
            editName()
        }

    }

    private fun editName() {
        val layoutInflater = LayoutInflater.from(this)
        val view = layoutInflater.inflate(R.layout.alt_diag_edit_name, null)
        val updateNameDialogue = AlertDialog.Builder(this)
            .setView(view)
            .create()
        view.et_new_name_alt.setText(name)
        updateNameDialogue.show()

        view.alt_btn_update_name.setOnClickListener {
            if (view.et_new_name_alt.text.toString().isNotEmpty()) {
                val name = view.et_new_name_alt.text.toString()
                val userDB = UserEntity(imgStr, name, email)
                userDB.id = dbID
                val map = hashMapOf<String, String>()
                map["name"] = name
                viewModel.updateUserToDB(userDB)
                viewModel.updateUserToServer(map, email)
                updateNameDialogue.cancel()
            }
        }

        view.alt_btn_cancel_name.setOnClickListener {
            updateNameDialogue.cancel()
        }

    }

    private fun setImage(uri: Uri) {
        val img = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        imgStr = bitMapToString(img)!!
        val userDB = UserEntity(imgStr, name, email)
        userDB.id = dbID
        val map = hashMapOf<String, String>()
        map["z_img"] = imgStr
        viewModel.updateUserToDB(userDB)
        viewModel.updateUserToServer(map, email)
    }


    private fun imageChooser() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(intent, GALLARY_REQ_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLARY_REQ_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { uri ->
                        launchImageCroper(uri)
                    }
                } else {
                    Log.i("rkpsx7.d", "onActivityResult: Unable to select image")
                }
            }

            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    result.uri?.let {
                        setImage(it)
                    }
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Log.i("rkpsx7.d", "Crop Image: ${result.error}")
                }
            }
        }
    }


    private fun launchImageCroper(uri: Uri) {
        CropImage.activity(uri)
            .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
            .setAspectRatio(1, 1)
            .setCropShape(CropImageView.CropShape.RECTANGLE)
            .start(this)
    }

    private fun executeSignOutProcess() {
        auth.signOut()
        val intent = Intent(this, IntroScreen::class.java)
        GoogleSignIn.getClient(
            this,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        )
            .signOut()
        startActivity(intent)
        finishAffinity()
    }

    private fun bitMapToString(bitmap: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun stringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte =
                Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: java.lang.Exception) {
            e.message
            null
        }
    }
}
package com.rkpsx7.moneymanager7.views.create_account_frags

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.rkpsx7.moneymanager7.R
import com.rkpsx7.moneymanager7.models.DAO
import com.rkpsx7.moneymanager7.models.MainRoomDb
import com.rkpsx7.moneymanager7.repository.AccountSigningRepo
import com.rkpsx7.moneymanager7.ui_fixes.CustomBottomSheet
import com.rkpsx7.moneymanager7.viewModels.AccountSigningViewModel
import com.rkpsx7.moneymanager7.viewModels.AccountSigningViewModelFactory
import kotlinx.android.synthetic.main.activity_bottom_sheet_sign_in.*


class BottomSheetSignInOptions : CustomBottomSheet() {
    lateinit var mainRoomDb: MainRoomDb
    lateinit var dao: DAO
    lateinit var repo: AccountSigningRepo
    lateinit var viewModel: AccountSigningViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_bottom_sheet_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainRoomDb = MainRoomDb.getMainRoomDb(requireContext())
        dao = mainRoomDb.getDao()
        repo = AccountSigningRepo(dao)
        val viewModelFactory = AccountSigningViewModelFactory(repo)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory)
                .get(AccountSigningViewModel::class.java)

        btn_sign_up.setOnClickListener {
            startActivity(Intent(requireContext(), ActivitySignUp::class.java))
        }

        btn_sign_in.setOnClickListener {
            startActivity(Intent(requireContext(), ActivitySignIn::class.java))
        }

    }

    private fun toast(msg: String) {
        Toast.makeText(this.requireActivity(), msg, Toast.LENGTH_LONG).show()
    }
}
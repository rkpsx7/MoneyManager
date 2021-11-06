package com.rkpsx7.moneymanager7.ui_fixes

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


open class CustomBottomSheet() : BottomSheetDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(
            context,
            com.rkpsx7.moneymanager7.R.layout.activity_bottom_sheet_sign_in,
            null
        )
        dialog.setContentView(view)
        return dialog
    }

    fun show(fragmentActivity: FragmentActivity) {
        show(fragmentActivity.supportFragmentManager, "")
    }
}
package com.nhatle.workmangement.ui.main.action.add.actionSmall.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.nhatle.workmangement.R
import kotlinx.android.synthetic.main.custion_dialog_member.*

class CustomDialog( context: Context,internal var adapter: MemberAdapter
): Dialog(context) {
    var dialog:Dialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custion_dialog_member)
        recyclerDialog.adapter = adapter
        buttonChoose.setOnClickListener{
            dismiss()
        }
    }

}
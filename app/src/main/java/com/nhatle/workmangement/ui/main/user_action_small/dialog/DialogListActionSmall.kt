package com.nhatle.workmangement.ui.main.user_action_small.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.reponsitory.remote.ActionSmallRemoteRepository
import com.nhatle.workmangement.data.source.remote.ActionSmallRemoteDataSource
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonAction
import kotlinx.android.synthetic.main.custom_dialog_action_small.*

class DialogListActionSmall(val call: SendActionSmall) : DialogFragment(),
    DialogActionSmallContract.View {
    private var presenter: DialogActionSmallPresenter? = null
    private val adapter: ListActionSmallAdapter by lazy {
        ListActionSmallAdapter(object : ListActionSmallAdapter.SendData {
            override fun sendUserTeam(data: ActionSmall) {
                call.sendActionSmall(data)
                this@DialogListActionSmall.dismiss()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.custom_dialog_action_small, container, false)
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = ActionSmallRemoteDataSource.getInstance(userService)
        val repository = ActionSmallRemoteRepository(dataSource)
        presenter = DialogActionSmallPresenter(this, repository)
        presenter!!.getAllActionSmallByAction(CommonAction.getInstance().action!!.actionId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()

        this.dialog!!.setTitle("Chọn nhiệm vụ")
    }

    override fun loadFailed(error: String) {

    }

    override fun getAllActionSmall(data: List<ActionSmall>) {
        if (data.isNotEmpty()) {
            adapter.setData(data as ArrayList<ActionSmall>)
            recyclerActionSmall.adapter = adapter
        }
    }

}
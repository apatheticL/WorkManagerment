package com.nhatle.workmangement.ui.main.action.add.actionSmall.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.source.remote.ActionRemoteDataSource
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonAction
import kotlinx.android.synthetic.main.custion_dialog_member.*

class CustomDialog(val actionId:Int,val groupId:Int,val call: SendUserTeam) : DialogFragment(), DialogUserTeamContract.View {
    private var presenter: DialogUserTeamPresenter? = null
    private lateinit var userTeamResponse: UserTeamResponse
    private val adapter: MemberAdapter by lazy {
        MemberAdapter(object : MemberAdapter.SendData {
            override fun sendUserTeam(data: UserTeamResponse) {
                sendData(data)
            }

        })
    }

    private fun sendData(data: UserTeamResponse) {
        this.userTeamResponse = data
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.custion_dialog_member, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        this.dialog!!.setTitle("Chọn người thực hiện")
        this.dialog!!.setCancelable(true)
        buttonChoose.setOnClickListener {
            call.sendUserTeam(userTeamResponse)
            this.dialog!!.dismiss()
        }
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = ActionRemoteDataSource.getInstance(userService)
        val repository = ActionRemoteRepository(dataSource)
        presenter = DialogUserTeamPresenter(this, repository)
        presenter!!.getAllUserTeamAction(
            actionId,
            groupId
        )

    }

    override fun loadFailed(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    override fun getAllUserTeam(data: List<UserTeamResponse>) {
        if (data.isNotEmpty()) {
            adapter.setData(data as ArrayList<UserTeamResponse>)
            recyclerDialog.adapter = adapter
        }
    }

}
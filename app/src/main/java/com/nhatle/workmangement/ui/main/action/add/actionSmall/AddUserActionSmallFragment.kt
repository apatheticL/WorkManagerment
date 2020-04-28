package com.nhatle.workmangement.ui.main.action.add.actionSmall

import android.app.DatePickerDialog
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.model.UserActionSmall
import com.nhatle.workmangement.data.model.UserTeam
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.ActionSmallRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserActionSmallRepository
import com.nhatle.workmangement.data.source.remote.ActionRemoteDataSource
import com.nhatle.workmangement.data.source.remote.ActionSmallRemoteDataSource
import com.nhatle.workmangement.data.source.remote.UserActionSmallRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.add.actionSmall.dialog.CustomDialog
import com.nhatle.workmangement.ui.main.action.add.actionSmall.dialog.MemberAdapter
import com.nhatle.workmangement.until.Common
import kotlinx.android.synthetic.main.fragment_add_user_action_small.*
import kotlinx.android.synthetic.main.item_add_user_action_small.*
import java.text.SimpleDateFormat
import java.util.*

class AddUserActionSmallFragment : BaseFragment(), AddUserActionSmallContract.View,
    View.OnClickListener {
    override val layoutResource: Int
        get() = R.layout.fragment_add_user_action_small
    private var actionId = -1
    private var groupId = -1
    private var adapter: ActionSmallAdapter? = null
    private var adapterDialog: MemberAdapter? = null
    private lateinit var actionSmall: ActionSmall
    private var userTeam: UserTeam? = null
    private var presenter: AddUserActionSmallPresenter? = null
    override fun initData() {
        configView()
        registerListener()

    }

    private fun getAllActionSmall() {
        presenter?.getAllActionSmall(actionId)
    }

    private fun addPresenter() {
        val userService = Common.getUserService()
        val dataSource = ActionSmallRemoteDataSource.getInstance(userService)
        val dataSource1 = UserActionSmallRemoteDataSource.getInstance(userService)
        val dataSource2 = ActionRemoteDataSource.getInstance(userService)
        val actionRepository = ActionSmallRemoteRepository(dataSource)
        val userActionSmallRepository = UserActionSmallRepository(dataSource1)
        val action = ActionRemoteRepository(dataSource2)
        presenter = AddUserActionSmallPresenter(
            this
            , userActionSmallRepository,
            actionRepository,
            action
        )

    }

    override fun initComponents() {
        addPresenter()
        getAllActionSmall()
    }

    private fun registerListener() {
        buttonAddStartDate.setOnClickListener(this)
        buttonAddDateEnd.setOnClickListener(this)
        buttonAddUser.setOnClickListener(this)
        buttonAddUserActionSmall.setOnClickListener(this)
        buttonCancelAddUserActionSmall.setOnClickListener(this)

    }

    private fun configView() {
        adapter = ActionSmallAdapter(object : ActionSmallAdapter.DataActionSmall {
            override fun sendData(actionSmall: ActionSmall) {
                sendDataToInsert(actionSmall)
            }
        })
    }

    private fun sendDataToInsert(actionSmall: ActionSmall) {
        this.actionSmall = actionSmall
        nameActionSmall.text = actionSmall.description
    }

    fun sendActionId(actionId: Int, groupId: Int) {
        this.actionId = actionId
        this.groupId = groupId
    }

    override fun insertUserActionSmallSuccess() {
        Toast.makeText(context, "Insert success", Toast.LENGTH_SHORT).show()
    }

    override fun showAllActionSmall(actionSmallResponse: List<ActionSmall>) {
    }

    override fun onFail(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    override fun getAllMemberOnAction(list: List<UserTeamResponse>) {
        adapterDialog?.setData(list = list as ArrayList<UserTeamResponse>)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonAddStartDate -> {
                showDatetimeDialog(texttimeStartAdd)
            }
            R.id.buttonAddDateEnd -> {
                showDatetimeDialog(texttimEndAdd)
            }
            R.id.buttonAddUser -> {
                showGetMemberDialog()
            }
            R.id.buttonAddUserActionSmall -> {
                insterUserActionSmall()
            }

        }
    }

    private fun insterUserActionSmall() {
        if (editPart.text.toString().isNotEmpty()) {
            val userActionSmall = UserActionSmall(
                0
                ,
                userTeam!!.groupId
                ,
                userTeam!!.profileId
                ,
                actionSmall.actionSmallId
                ,
                editPart.text.toString(),
                texttimeStartAdd.text.toString(),
                texttimEndAdd.text.toString()
            )
            presenter!!.insertUserActionSmall(userActionSmall =userActionSmall )
        }

    }

    private fun showGetMemberDialog() {
        presenter?.getAllMemberOnAction(actionId, groupId)
        adapterDialog = MemberAdapter(object : MemberAdapter.SendData {
            override fun sendUserTeam(data: UserTeamResponse) {
                userTeam = UserTeam(data.groupId, data.profileId)
            }
        })
        val customDialog: CustomDialog = CustomDialog(context!!, adapterDialog!!)
        customDialog.show()
        customDialog.setCanceledOnTouchOutside(false)
    }

    private fun showDatetimeDialog(textView: TextView) {
        val fmDateAndTime = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        val datePickerDialog: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                textView.text = fmDateAndTime.format(calendar.time)
            }
        activity?.let {
            DatePickerDialog(
                it, datePickerDialog,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}
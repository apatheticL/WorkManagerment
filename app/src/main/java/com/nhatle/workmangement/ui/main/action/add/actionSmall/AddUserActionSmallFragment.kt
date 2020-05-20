package com.nhatle.workmangement.ui.main.action.add.actionSmall

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.model.UserActionSmall
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.data.reponsitory.remote.ActionSmallRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserActionSmallRepository
import com.nhatle.workmangement.data.source.remote.ActionSmallRemoteDataSource
import com.nhatle.workmangement.data.source.remote.UserActionSmallRemoteDataSource
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.ActionFragment
import com.nhatle.workmangement.ui.main.action.add.actionSmall.dialog.CustomDialog
import com.nhatle.workmangement.ui.main.action.add.actionSmall.dialog.SendUserTeam
import com.nhatle.workmangement.until.*
import kotlinx.android.synthetic.main.fragment_add_user_action_small.*
import kotlinx.android.synthetic.main.item_add_user_action_small.*
import java.text.SimpleDateFormat
import java.util.*

class AddUserActionSmallFragment(
    val actionId: Int,
    val groupId: Int,
    val timeEnd: String,
    val timeStart: String
) :
    BaseFragment(),
    AddUserActionSmallContract.View,
    View.OnClickListener {
    private var timeStartClick = false
    override val layoutResource: Int
        get() = R.layout.fragment_add_user_action_small
    private val adapter: ActionSmallAdapter by lazy {
        ActionSmallAdapter(object : ActionSmallAdapter.DataActionSmall {
            override fun sendData(actionSmall: ActionSmall, position: Int) {
                sendDataToInsert(actionSmall, position)
            }
        })
    }

    private lateinit var actionSmall: ActionSmall
    private var userTeam: UserTeamResponse? = null
    private var presenter: AddUserActionSmallPresenter? = null
    override fun initData() {
        registerListener()
    }

    private fun getAllActionSmall() {
        presenter?.getAllActionSmall(actionId)
    }

    private fun addPresenter() {
        val userService = Common.getUserService()
        val dataSource = ActionSmallRemoteDataSource.getInstance(userService)
        val dataSource1 = UserActionSmallRemoteDataSource.getInstance(userService)
        val actionRepository = ActionSmallRemoteRepository(dataSource)
        val userActionSmallRepository = UserActionSmallRepository(dataSource1)
        presenter = AddUserActionSmallPresenter(
            this
            , userActionSmallRepository,
            actionRepository

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
        buttonBack.setOnClickListener(this)
    }

    private fun configView() {
        recyclerActionSmall.adapter = adapter
    }

    private fun sendDataToInsert(
        actionSmall: ActionSmall,
        position: Int

    ) {

        this.actionSmall = actionSmall
        editPart.setText(actionSmall.description)
        adapter.deletePosition(position)
    }

    override fun insertUserActionSmallSuccess() {

        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()

    }

    override fun showAllActionSmall(actionSmallResponse: List<ActionSmall>) {
        if (actionSmallResponse.isNotEmpty()) {
            adapter.setData(actionSmallResponse as ArrayList<ActionSmall>)
            configView()
        }
    }

    override fun onFail(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonAddStartDate -> {
                timeStartClick = true
                showDatetimeDialog(texttimeStartAdd)
            }
            R.id.buttonAddDateEnd -> {
                timeStartClick = false
                showDatetimeDialog(texttimEndAdd)
            }
            R.id.buttonAddUser -> {
                showGetMemberDialog()
            }
            R.id.buttonAddUserActionSmall -> {
                insertUserActionSmall()
                if (adapter.getData().isEmpty()) {
                    val string = "Tất cả các nhiệm vụ đã được giao."
                    showDiaLog(string)
                }
            }
            R.id.buttonCancelAddUserActionSmall -> {

                (activity as MainActivity).hindNavigation(false)
                replaceFragment(R.id.frag_main, ActionFragment(), false)

            }
            R.id.buttonBack -> {
                (activity as MainActivity).hindNavigation(false)
                replaceFragment(R.id.frag_main, ActionFragment(), false)
            }

        }
    }

    private fun insertUserActionSmall() {
        if (editPart.text.toString().isNotEmpty() && nameMemberAction.text.isNotEmpty()) {

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
            presenter!!.insertUserActionSmall(userActionSmall = userActionSmall)
            editPart.text = null
            nameMemberAction.text = null
            texttimeStartAdd.text = null
            texttimEndAdd.text = null
        } else {
            Toast.makeText(context, "Vui long chon thanh vien thuc hien", Toast.LENGTH_SHORT).show()
        }

    }

    private fun showGetMemberDialog() {
        val customDialog = CustomDialog(actionId, groupId, object : SendUserTeam {
            override fun sendUserTeam(actionSmall: UserTeamResponse) {
                nameMemberAction.visibility = View.VISIBLE
                this@AddUserActionSmallFragment.userTeam = actionSmall
                nameMemberAction.text = actionSmall.fullName
            }

        })
        customDialog.show(fragmentManager!!, "Memeber")
    }

    private fun showDatetimeDialog(textView: TextView) {
        val fmDateAndTime = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        val datePickerDialog: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                if (!timeStartClick) {
                    if (checkTimeEndDone(calendar.time, texttimeStartAdd)) {
                        if (checkTimeEndActionSmallToDone(
                                calendar.time,
                                convertStringToDate(timeEnd)
                            )
                        ) {
                            textView.text = fmDateAndTime.format(calendar.time)
                        }
                        if (!checkTimeEndActionSmallToDone(
                                calendar.time,
                                convertStringToDate(timeEnd)
                            )
                        ) {
                            textView.text = null
                            Toast.makeText(
                                context,
                                "Thoi gian ket thuc phai trong khoang thoi gian lam viec la $timeEnd",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    if (!checkTimeEndDone(calendar.time, texttimeStartAdd)) {
                        textView.text = null
                        Toast.makeText(
                            context,
                            "Thoi gian ket thuc khong hop le",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                if (timeStartClick) {
                    if(checkTimeStartActionSmallDone(calendar.time,
                            convertStringToDate(timeStart),
                            convertStringToDate(timeEnd)))
                    {
                        textView.text = fmDateAndTime.format(calendar.time)
                    }
                    if(!checkTimeStartActionSmallDone(calendar.time,
                            convertStringToDate(timeStart),
                            convertStringToDate(timeEnd)))
                    {
                        textView.text =null
                        Toast.makeText(
                            context,
                            "Thoi gian bat dau phai trong khoang tu $timeStart den $timeEnd",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        activity?.let {
            DatePickerDialog(
                it, datePickerDialog,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun showDiaLog(string: String) {
        val layoutInference: LayoutInflater = LayoutInflater.from(context)
        val dialogShow: View = layoutInference.inflate(R.layout.custom_dialog_full_item, null)
        val dialog: AlertDialog = AlertDialog.Builder(context!!).create()
        dialog.setTitle("Thong bao")
        dialog.setView(dialogShow)
        val showMessenger: TextView = dialogShow.findViewById<TextView>(R.id.showMessenger)
        val buttonOk: Button = dialogShow.findViewById<Button>(R.id.buttonOk)
        showMessenger.text = string
        buttonOk.setOnClickListener {
            (activity as MainActivity).hindNavigation(false)
            replaceFragment(R.id.frag_main, ActionFragment(), false)
        }
    }
}
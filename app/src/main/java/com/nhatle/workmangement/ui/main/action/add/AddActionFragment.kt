package com.nhatle.workmangement.ui.main.action.add

import android.app.DatePickerDialog
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.Action
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.model.Team
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.ActionSmallRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.TeamRemoteRepository
import com.nhatle.workmangement.data.source.remote.ActionRemoteDataSource
import com.nhatle.workmangement.data.source.remote.ActionSmallRemoteDataSource
import com.nhatle.workmangement.data.source.remote.TeamRemoteDataSource
import com.nhatle.workmangement.data.until.ActionSmallBefor
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.ActionFragment
import com.nhatle.workmangement.ui.main.action.add.actionSmall.AddUserActionSmallFragment
import com.nhatle.workmangement.ui.main.team.AddGroupFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonData
import kotlinx.android.synthetic.main.custom_add_action.*
import kotlinx.android.synthetic.main.fragment_add_acion_small.*
import kotlinx.android.synthetic.main.fragment_add_work.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddActionFragment : BaseFragment(), AddActionContract.View, View.OnClickListener {
    override val layoutResource: Int = R.layout.fragment_add_work
    private var presenter: AddActionPresenter? = null
    private var listActionSmallName: ArrayList<ActionSmallBefor> = ArrayList()
    private var action: Action? = null
    private var group: Team? = null
    private val adapter: ListActionSmallBeforAddAdapter = ListActionSmallBeforAddAdapter()
    override fun initData() {
        Glide.with(image_avatar)
            .load(CommonData.getInstance().profile!!.avatar)
            .placeholder(R.drawable.bavarian)
            .into(image_avatar)
        textNameUserCreate.text = CommonData.getInstance().profile!!.fullName
        if (group == null) {
            nameGroup.visibility = View.GONE
        }
        nameGroup.text = group?.groupName
        registerListener()
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val actionDataSource = ActionRemoteDataSource.getInstance(userService)
        val actionSmallDataSource = ActionSmallRemoteDataSource.getInstance(userService)
        val teamDataSource = TeamRemoteDataSource.getInstance(userService)
        val teamRepository = TeamRemoteRepository(teamDataSource)
        val actionRepository = ActionRemoteRepository(actionDataSource)
        val actionSmallRepository = ActionSmallRemoteRepository(actionSmallDataSource)
        presenter = AddActionPresenter(
            this, actionRepository,
            actionSmallRepository,
            teamRepository
        )
    }

    override fun initComponents() {
        initPresenter()

    }

    private fun registerListener() {
        buttonAddStartDate.setOnClickListener(this)
        buttonAddDateEnd.setOnClickListener(this)
        buttonAddMember.setOnClickListener(this)
        buttonSaveAdd.setOnClickListener(this)
        buttonAddWork.setOnClickListener(this)
        buttonCancelAddWork.setOnClickListener(this)
    }

    override fun insertActionSuccess(action: Action) {
        this.action = action
        val list: MutableList<ActionSmall> = ArrayList()
        for (i in 0 until listActionSmallName.size step 1) {
            val actionSmall =
                ActionSmall(0, action.actionId, listActionSmallName.get(i).actionSmallName)
            list.add(actionSmall)
        }
        presenter!!.insertActionSmall(list)
    }

    override fun insetFail(error: String) {

    }

    override fun insertActionSmallSuccess() {
        (activity as MainActivity).hindNavigation(true)
        replaceFragment(
            R.id.frag_image,
            AddUserActionSmallFragment(action!!.actionId, action!!.groupId),
            true
        )
    }

    override fun deleteFailed(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    override fun delete() {
        Toast.makeText(context, "susses", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonAddStartDate -> {
                showDatetimeDialog(texttimeStartAdd)
                activity?.let { Common.hideKeyBoard(it) }
            }
            R.id.buttonAddDateEnd -> {
                showDatetimeDialog(texttimEndAdd)
                activity?.let { Common.hideKeyBoard(it) }
            }
            R.id.buttonAddMember -> {
                (activity as MainActivity).hindNavigation(true)
                replaceFragment(R.id.frag_image, AddGroupFragment(), true)
                activity?.let { Common.hideKeyBoard(it) }
            }
            R.id.buttonSaveAdd -> {

                checkAndShowActionSmall()
                activity?.let { Common.hideKeyBoard(it) }
                editActionSmallNameOnAdd.setText("")
            }
            R.id.buttonCancelAddWork -> {
                (activity as MainActivity).hindNavigation(false)
                replaceFragment(R.id.frag_main, ActionFragment(), true)
                activity?.let { Common.hideKeyBoard(it) }
                if (group != null) {
                    presenter!!.deleteGroup(group!!.groupId)
                }
            }
            R.id.buttonAddWork -> {

                if (nameWork.text.isEmpty() || texttimeStartAdd.text.isEmpty() || texttimEndAdd.text.isEmpty()) {
                    Toast.makeText(context, "không hợp lê", Toast.LENGTH_SHORT).show()
                }
                insertAction()
                activity?.let { Common.hideKeyBoard(it) }
//                Toast.makeText(context,"Ban cần tạo nhóm thực hiện",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insertAction() {
        if (nameWork.text.isNotEmpty() && descriptionWork.text.isNotEmpty()) {
            val name = nameWork.text.toString()
            val timeStart = texttimeStartAdd.text.toString()
            val timeEnd = texttimEndAdd.text.toString()
            val discription = descriptionWork.text.toString()
            val action = Action(
                0, name, CommonData.getInstance().profile!!.profileId,
                group!!.groupId, timeStart, timeEnd, null, "Chua hoan thanh", discription
            )
            presenter?.insertAction(action)
        }
    }

    private fun checkAndShowActionSmall() {
        if (editActionSmallNameOnAdd.text.isEmpty()) {
            textError.visibility = View.VISIBLE
            textError.text = "Trường này chưa có thông tin"
        }
        if (editActionSmallNameOnAdd.text.isNotEmpty()) {
            listActionSmallName.add(ActionSmallBefor(editActionSmallNameOnAdd.text.toString()))
            recyclerListActionSmallForAdd.visibility = View.VISIBLE

            adapter.setData(listActionSmallName)
            recyclerListActionSmallForAdd.adapter = adapter
        }
    }

    private fun showDatetimeDialog(textView: TextView) {
        val fmDateAndTime = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        val datePickerDialog =
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

    fun sendGroupId(groupId: Team) {
        this.group = groupId
    }
}
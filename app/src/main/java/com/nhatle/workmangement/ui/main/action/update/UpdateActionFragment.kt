package com.nhatle.workmangement.ui.main.action.update

import android.app.DatePickerDialog
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.Action
import com.nhatle.workmangement.data.model.response.ActionResponse
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.source.remote.ActionRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.ActionFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonData
import kotlinx.android.synthetic.main.fragment_update_work.*
import kotlinx.android.synthetic.main.infor_action.*
import java.text.SimpleDateFormat
import java.util.*

class UpdateActionFragment : BaseFragment(), UpdateActionContract.View, View.OnClickListener {
    override val layoutResource: Int
        get() = R.layout.fragment_update_work
    private var actionResponse: ActionResponse? = null
    private var presenter: UpdateActionPresenter? = null
    override fun initData() {
        //loadData()
        regisListener()

    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = ActionRemoteDataSource.getInstance(userService)
        val repository = ActionRemoteRepository(dataSource)
        presenter = UpdateActionPresenter(this, repository)
    }

    override fun initComponents() {
        initPresenter()
        loadData()
    }

    private fun regisListener() {
        buttonUpdateWork.setOnClickListener(this)
        buttonCancelUpdateWork.setOnClickListener(this)
        buttonAddDateEnd.setOnClickListener(this)
    }

    private fun loadData() {
        Glide.with(image_avatar).load(CommonData.getInstance().profile!!.avatar)
            .into(image_avatar)
        nameCreatorAction.text = CommonData.getInstance().profile!!.fullName
        textNameAction.text = actionResponse!!.actionName
        textDescription.text = actionResponse!!.description
        nameCreator.text = actionResponse!!.nameCreator
        timeStartAction.text = actionResponse!!.timeStart
        timeEndAction.text = actionResponse!!.timeStart
    }

    override fun updateSuccess() {
        replaceFragment(R.id.frag_main, ActionFragment(), true)
    }

    override fun onFail(string: String) {
    }

    fun sendData(data: ActionResponse) {
        this.actionResponse = data
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonUpdateWork -> {
                updateWork()
            }
            R.id.buttonCancelUpdateWork -> {
                replaceFragment(R.id.frag_main,ActionFragment(),true)
            }
            R.id.buttonAddDateEnd->{
                showDatetimeDialog(texttimEndUpdate)
            }
        }
    }
    private fun showDatetimeDialog(textView: TextView) {
        val fmDateAndTime = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        val datePickerDialog =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR,year)
                calendar.set(Calendar.MONTH,month)
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                textView.text = fmDateAndTime.format(calendar.time)
            }
        activity?.let {
            DatePickerDialog(
                it,datePickerDialog,
                calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun updateWork() {
        if (editDescription.text.toString().isNotEmpty() || nameWork.text.toString().isNotEmpty()) {
            val action = Action(
                actionResponse!!.actionId
                ,
                nameWork.text.toString()
                ,
                actionResponse!!.creatorId,
                actionResponse!!.groupId,
                null,
                texttimEndUpdate.text.toString(),
                null,
                null,
                editDescription.text.toString()
            )
            presenter!!.updateAction(action)
        }
    }
}
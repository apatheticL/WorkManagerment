package com.nhatle.workmangement.ui.main.action.add

import android.app.DatePickerDialog
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.Action
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.ActionSmallRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserActionSmallRepository
import com.nhatle.workmangement.data.source.remote.ActionRemoteDataSource
import com.nhatle.workmangement.data.source.remote.ActionSmallRemoteDataSource
import com.nhatle.workmangement.data.source.remote.UserActionSmallRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.ActionFragment
import com.nhatle.workmangement.ui.main.team.AddGroupFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonData
import kotlinx.android.synthetic.main.custom_add_action.*
import kotlinx.android.synthetic.main.fragment_add_acion_small.*
import kotlinx.android.synthetic.main.fragment_add_acion_small.textError
import kotlinx.android.synthetic.main.fragment_add_work.*
import kotlinx.android.synthetic.main.fragment_add_work.buttonAddWork
import kotlinx.android.synthetic.main.fragment_work.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddActionFragment :BaseFragment(), AddActionContract.View, View.OnClickListener {
    override val layoutResource: Int = R.layout.fragment_add_work
    private var presenter:AddActionPresenter?=null
    private var listActionSmallName:ArrayList<String> = ArrayList()
    private var action :Action?=null
    private var adapter :ListActionSmallBeforAddAdapter?=null
    override fun initData() {
        initPresenter()
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val actionDataSource = ActionRemoteDataSource.getInstance(userService)
        val actionSmallDataSource = ActionSmallRemoteDataSource.getInstance(userService)
        val userActionSmallRemoteDataSource = UserActionSmallRemoteDataSource.getInstance(userService)
        val actionRepository= ActionRemoteRepository(actionDataSource)
        val actionSmallRepository = ActionSmallRemoteRepository(actionSmallDataSource)
        val userActionSmallRepository = UserActionSmallRepository(userActionSmallRemoteDataSource)
        presenter = AddActionPresenter(this,actionRepository,
            actionSmallRepository,userActionSmallRepository)
    }

    override fun initComponents() {
        Glide.with(image_avatar)
            .load(CommonData.getInstance().profile!!.avatar)
            .placeholder(R.drawable.bavarian)
            .into(image_avatar)
        textNameUserCreate.text = CommonData.getInstance().profile!!.fullName
        registerListener()
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
        this.action =action
    }

    override fun insetFail(error: String) {
        TODO("Not yet implemented")
    }

    override fun insertActionSmallSuccess(actionSmall: ActionSmall) {
        TODO("Not yet implemented")
    }

    override fun insertUserActionSmallSuccess() {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.buttonAddStartDate->{
                showDatetimeDialog(texttimeStartAdd)
            }
            R.id.buttonAddDateEnd->{
                showDatetimeDialog(texttimEndAdd)
            }
            R.id.buttonAddMember->{
                addFragment(R.id.frag_main, AddGroupFragment(),false)
            }
            R.id.buttonSaveAdd->{
                checkAndShowActionSmall()
            }
            R.id.buttonCancelAddWork->{
                addFragment(R.layout.fragment_work,ActionFragment(),false)
            }
            R.id.buttonAddWork->{
                if (nameWork.text.isEmpty()||texttimeStartAdd.text.isEmpty()||texttimEndAdd.text.isEmpty()){
                    Toast.makeText(context,"không hợp lê",Toast.LENGTH_SHORT).show()
                }

//                presenter?.insertAction()
            }
        }
    }

    private fun checkAndShowActionSmall() {
        if (editActionSmallNameOnAdd.text.isEmpty()){
            textError.visibility = View.VISIBLE
            textError.text = "Trường này chưa có thông tin"
        }
        listActionSmallName.add(editActionSmallNameOnAdd.text.toString())
        recyclerListActionSmallForAdd.visibility = View.VISIBLE
        adapter = ListActionSmallBeforAddAdapter()
        adapter!!.setData(listActionSmallName)
        recyclerListActionSmallForAdd.adapter = adapter
    }

    private fun showDatetimeDialog(textView: TextView) {
        val fmDateAndTime = DateFormat.getDateTimeInstance()
        val calendar = Calendar.getInstance()
        val datePickerDialog = object :DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                calendar.set(Calendar.YEAR,year)
                calendar.set(Calendar.MONTH,month)
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                textView.text = fmDateAndTime.format(calendar.time)
            }
        }
        activity?.let {
            DatePickerDialog(
                it,datePickerDialog,
                calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)
            ,calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }
}
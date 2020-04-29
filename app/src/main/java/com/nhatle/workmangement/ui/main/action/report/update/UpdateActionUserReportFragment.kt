package com.nhatle.workmangement.ui.main.action.report.update

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.UserActionReport
import com.nhatle.workmangement.data.model.response.UserActionReportResponse
import com.nhatle.workmangement.data.model.response.UserActionSmallResponse
import com.nhatle.workmangement.data.reponsitory.remote.UserActionReportRepository
import com.nhatle.workmangement.data.source.remote.UserActionReportRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.report.UserActionReportFragment
import com.nhatle.workmangement.until.Common
import kotlinx.android.synthetic.main.fragment_update_report.*
import java.text.SimpleDateFormat
import java.util.*

class UpdateActionUserReportFragment( val itemData: UserActionReportResponse) : BaseFragment(),
    UpdateActionReportContract.View, View.OnClickListener {
    override val layoutResource: Int = R.layout.fragment_update_report
    private var presenter : UpdateReportPresenter?=null

    override fun initData() {
        configView()
    }

    private fun configView() {
        Glide.with(circleAvatarUserRe)
            .load(itemData.avatar)
            .placeholder(R.drawable.bavarian)
            .into(circleAvatarUserRe)
        textFullnameCreateRe.text = itemData.fullName
        textWorkActual.text = itemData.actionActual
        textWorkNext.text = itemData.actionNext
        textWorkIssua.text = itemData.actionIssua

    }

    override fun initComponents() {
        configPresenter()
        registerListener()
    }

    private fun registerListener() {
        buttonUpdateReport.setOnClickListener(this)
        buttonCancelReport.setOnClickListener(this)
    }

    private fun onUpdate():UserActionReport {
        var status = ""
        if (radioUpdateButtonFinnish.isChecked){
            status = radioUpdateButtonFinnish.text as String
        }
        if (radioUpdateButtonNoneFinish.isChecked){
            status = radioUpdateButtonNoneFinish.text as String
        }
        var date = Calendar.getInstance().time
        val format = SimpleDateFormat("yyyy-MM-dd")
        val dateFormat = format.format(date)
        val userActionReportResponse = UserActionReport(0,itemData.userActionSmallId,
        itemData.actionId,status,editUpdateWorkNext.text.toString(),editUpdateWorkIssue.text.toString(),dateFormat)
        return userActionReportResponse
    }

    private fun configPresenter() {
        val userService = Common.getUserService()
        val dataSource = UserActionReportRemoteDataSource.getInstance(userService)
        val repository = UserActionReportRepository(dataSource)
        presenter = UpdateReportPresenter(this,repository)
    }

    override fun updateSuccess() {
        replaceFragment(R.layout.fragment_report,UserActionReportFragment(itemData.actionId),false)
    }

    override fun updateFail(error: String) {
        Toast.makeText(context,error,Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.buttonUpdateReport->{
                presenter!!.updateReport(onUpdate())
            }
            R.id.buttonCancelUpdate->{
                replaceFragment(R.layout.fragment_report,UserActionReportFragment(itemData.actionId),false)
            }
        }
    }

}

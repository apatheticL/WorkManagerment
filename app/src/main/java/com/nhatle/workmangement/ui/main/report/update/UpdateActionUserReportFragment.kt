package com.nhatle.workmangement.ui.main.report.update

import android.os.Build
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.UserActionReport
import com.nhatle.workmangement.data.model.response.UserActionReportResponse
import com.nhatle.workmangement.data.reponsitory.remote.UserActionReportRepository
import com.nhatle.workmangement.data.source.remote.UserActionReportRemoteDataSource
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.report.UserActionReportFragment
import com.nhatle.workmangement.until.Common
import kotlinx.android.synthetic.main.fragment_update_report.*
import java.text.SimpleDateFormat
import java.util.*

class UpdateActionUserReportFragment(val itemData: UserActionReportResponse) : BaseFragment(),
    UpdateActionReportContract.View, View.OnClickListener {
    override val layoutResource: Int = R.layout.fragment_update_report
    private var status = ""
    private var presenter: UpdateReportPresenter? = null

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
        radio.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.radioUpdateButtonFinnish -> {
                        status = "hoan thanh"
                    }
                    R.id.radioUpdateButtonNoneFinish -> {
                        status = "chua hoan thanh"
                    }
                }
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun onUpdate(): UserActionReport {
        var date = Calendar.getInstance().time
        val format = SimpleDateFormat("yyyy-MM-dd")
        val dateFormat = format.format(date)
        val nextWork = editUpdateWorkNext.text.toString()
        val issue = editUpdateWorkIssue.text.toString()
        return UserActionReport(
            itemData.reportId,
            itemData.userActionSmallId,
            itemData.actionId,
            status,
            nextWork,
            issue, dateFormat
        )
    }

    private fun configPresenter() {
        val userService = Common.getUserService()
        val dataSource = UserActionReportRemoteDataSource.getInstance(userService)
        val repository = UserActionReportRepository(dataSource)
        presenter = UpdateReportPresenter(this, repository)
    }

    override fun updateSuccess() {
        (activity as MainActivity).hindNavigation(true)
        replaceFragment(R.id.frag_main, UserActionReportFragment(), false)
    }

    override fun updateFail(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonUpdateReport -> {
                presenter!!.updateReport(onUpdate())
            }
            R.id.buttonCancelReport -> {
                (activity as MainActivity).hindNavigation(true)
                replaceFragment(
                    R.id.frag_main,
                    UserActionReportFragment(), false
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).hindNavigation(true)
        replaceFragment(R.id.frag_main,UserActionReportFragment(),false)
    }
}

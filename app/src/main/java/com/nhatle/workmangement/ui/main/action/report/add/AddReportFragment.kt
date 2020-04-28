package com.nhatle.workmangement.ui.main.action.report.add

import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.UserActionReport
import com.nhatle.workmangement.data.reponsitory.remote.UserActionReportRepository
import com.nhatle.workmangement.data.source.remote.UserActionReportRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.report.UserActionReportFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonData
import kotlinx.android.synthetic.main.fragment_add_report.*
import java.text.SimpleDateFormat
import java.util.*

class AddReportFragment : BaseFragment(), View.OnClickListener, AddActionReportContract.View {
    override val layoutResource: Int = R.layout.fragment_add_report
    private var presenter: AddReportPresenter? = null
    private var userActionSmallId = 0
    private var actionId = 0
    override fun initData() {
        configPresenter()
    }

    override fun initComponents() {
        Glide.with(circleAvatarUserRe).load(CommonData.getInstance().profile!!.avatar)
            .placeholder(R.drawable.bavarian).into(circleAvatarUserRe)
        textFullnameCreateRe.text = CommonData.getInstance().profile!!.fullName
        val date = Calendar.getInstance().time
        val format = SimpleDateFormat("yyyy-MM-dd")
        textDateCreateReport.text = format.format(date)
        registerListener()
    }

    private fun registerListener() {
        buttonAddReport.setOnClickListener(this)
        buttonCancelReport.setOnClickListener(this)
    }

    fun sendData(id: Int, actionId: Int) {
        this.userActionSmallId = id
        this.actionId = actionId
    }

    private fun configPresenter() {
        val userService = Common.getUserService()
        val dataSource = UserActionReportRemoteDataSource.getInstance(userService)
        val repository = UserActionReportRepository(dataSource)
        presenter = AddReportPresenter(this, repository)
    }

    private fun getUserReport(): UserActionReport {
        var status = ""
        if (radioUpdateButtonFinnish.isChecked){
            status = radioUpdateButtonFinnish.text.toString()
            radioUpdateButtonNoneFinish.isChecked=false
        }
        if (radioUpdateButtonNoneFinish.isChecked){
            status = radioUpdateButtonNoneFinish.text.toString()
            radioUpdateButtonFinnish.isChecked=false
        }
        return UserActionReport(0,userActionSmallId,actionId,
            status,editAddWorkNext.text.toString(),editAddWorkIssue.text.toString(),
            textDateCreateReport.text.toString()
        )
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonAddReport -> {
                presenter!!.insertReport(getUserReport())
            }
            R.id.buttonCancelReport->{
                openFragmentReport()
            }
        }
    }

    override fun interSuccess() {
        Toast.makeText(context, "insert success", Toast.LENGTH_SHORT).show()
        openFragmentReport()
        }

    override fun insertFail(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }
    private fun openFragmentReport(){
        addFragment(R.id.frag_main, UserActionReportFragment(actionId = actionId), false)

    }
}

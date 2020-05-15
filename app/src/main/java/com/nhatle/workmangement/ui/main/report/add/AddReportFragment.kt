package com.nhatle.workmangement.ui.main.report.add

import android.view.View
import android.widget.AdapterView
import android.widget.RadioGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.model.UserActionReport
import com.nhatle.workmangement.data.reponsitory.remote.ActionSmallRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserActionReportRepository
import com.nhatle.workmangement.data.source.remote.ActionSmallRemoteDataSource
import com.nhatle.workmangement.data.source.remote.UserActionReportRemoteDataSource
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.report.UserActionReportFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonAction
import com.nhatle.workmangement.until.CommonData
import kotlinx.android.synthetic.main.fragment_add_report.*
import java.text.SimpleDateFormat
import java.util.*

class AddReportFragment : BaseFragment(), View.OnClickListener, AddActionReportContract.View {
    override val layoutResource: Int = R.layout.fragment_add_report
    private var presenter: AddReportPresenter? = null
    private var userActionSmallId = 0
    private var actionId = 0
    private var status = ""
    private lateinit var adapter: ActionSmallOfUserAdapter
    override fun initData() {
        Glide.with(circleAvatarUserRe).load(CommonData.getInstance().profile!!.avatar)
            .placeholder(R.drawable.bavarian).into(circleAvatarUserRe)
        textFullnameCreateRe.text = CommonData.getInstance().profile!!.fullName
        val date = Calendar.getInstance().time
        val format = SimpleDateFormat("yyyy-MM-dd")
        textDateCreateReport.text = format.format(date)
        registerListener()
    }

    override fun initComponents() {
        configPresenter()
        getAllActionSmallOfUser()
    }

    private fun getAllActionSmallOfUser() {
        presenter!!.getAllActionSmall(
            CommonAction.getInstance().action!!.actionId,
            CommonData.getInstance().profile!!.profileId
        )
    }

    private fun registerListener() {
        buttonAddReport.setOnClickListener(this)
        buttonCancelReport.setOnClickListener(this)
        listActionSmall.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val actionSmall: ActionSmall = parent!!.getItemAtPosition(position) as ActionSmall
                val name = actionSmall.description
                addActionSmallName.text = name
            }

        }
        groupStatus.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.radioUpdateButtonFinnish -> {
                        status = radioUpdateButtonFinnish.text.toString()
                    }
                    R.id.radioUpdateButtonNoneFinish -> {
                        status = radioUpdateButtonNoneFinish.text.toString()
                    }
                }
            }

        })
    }

    fun sendData(id: Int, actionId: Int) {
        this.userActionSmallId = id
        this.actionId = actionId
    }

    private fun configPresenter() {
        val userService = Common.getUserService()
        val dataSource = UserActionReportRemoteDataSource.getInstance(userService)
        val data = ActionSmallRemoteDataSource.getInstance(userService)
        val repository = UserActionReportRepository(dataSource)
        var repositorySmall = ActionSmallRemoteRepository(data)
        presenter = AddReportPresenter(this, repository, repositorySmall)
    }

    private fun getUserReport(): UserActionReport {
        var name = ""
        if (addActionSmallName.text.isNotEmpty()){
             name = addActionSmallName.text.toString()
        }
        return UserActionReport(
            0, userActionSmallId, actionId,
            status,name , editAddWorkIssue.text.toString(),
            textDateCreateReport.text.toString()
        )
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonAddReport -> {
                presenter!!.insertReport(getUserReport())
            }
            R.id.buttonCancelReport -> {
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

    override fun getAllActionSmall(list: List<ActionSmall>) {
        if (list.isNotEmpty()) {
            adapter = ActionSmallOfUserAdapter(context!!, list)
            listActionSmall.adapter = adapter
        }
    }

    override fun loadFail(error: String) {

    }

    private fun openFragmentReport() {
        (activity as MainActivity).hindNavigation(false)
        replaceFragment(R.id.frag_main, UserActionReportFragment(), true)

    }
}

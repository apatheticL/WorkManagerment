package com.nhatle.workmangement.ui.main.action.detail

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.ActionResponse
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.source.remote.ActionRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.report.UserActionReportFragment
import com.nhatle.workmangement.ui.main.action.small.UserActionSmallFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.api.UserService
import kotlinx.android.synthetic.main.custom_infor_work.*
import kotlinx.android.synthetic.main.fragment_user_action_small.*
import kotlinx.android.synthetic.main.fragment_work_detail.*
import kotlinx.android.synthetic.main.fragment_work_detail.textNameAction
import kotlinx.android.synthetic.main.item_my_action_small.*
import java.text.SimpleDateFormat

class ActionDetailFragment:BaseFragment(), ActionDetailContract.View, View.OnClickListener {
    override val layoutResource: Int = R.layout.fragment_work_detail
    private var actionResponse: ActionResponse?=null
    private lateinit var userService:UserService
    private var adapter: ListMemberAdapter?=null
    private var presenter:ActionDetailPresenter?=null
    val format = SimpleDateFormat("dd/MM/yyyy")
    override fun initData() {
        userService = Common.getUserService()
        configView()
        initPresenter()
    }

    private fun initPresenter() {
        val dataSource = ActionRemoteDataSource.getInstance(userService)
        val repository = ActionRemoteRepository(dataSource=dataSource)
        presenter = ActionDetailPresenter(this,repository)
        presenter!!.getAllMemberOnGroup(actionResponse!!.groupId,actionResponse!!.actionId)
    }

    private fun configView() {
        textNameAction.text = actionResponse?.actionName
        textDescription.text = actionResponse?.description
        nameCreator.text = actionResponse?.nameCreator
        timeStartAction.text = format.format(actionResponse?.timeStart)
        timeEndAction.text = format.format(actionResponse?.timeEnd)
    }


    override fun initComponents() {
        initAdapter()
        registerListener()
    }

    private fun registerListener() {
        buttonGoReport.setOnClickListener(this)
        buttonGoActionSmall.setOnClickListener(this)
    }

    private fun initAdapter() {
        adapter = ListMemberAdapter(object :ListMemberAdapter.SendProfile{
            override fun sendProfileId(profileId: Int) {
                // go profile fragment
            }

        })
        recyclerListMember.adapter = adapter

    }

    fun sendData(actionResponse: ActionResponse) {
        this.actionResponse = actionResponse
    }

    override fun showAllMember(list: ArrayList<UserTeamResponse>) {
        adapter!!.setData(list)
    }

    override fun onFailed(string: String) {
    }

    override fun onClick(v: View) {

        when(v.id){
            R.id.buttonGoActionSmall->{
                val fragment = UserActionSmallFragment(actionResponse!!.actionId)
                addFragment(R.id.frag_main,fragment,false)

            }
            R.id.buttonGoReport->{
                addFragment(R.id.frag_main,UserActionReportFragment(actionResponse!!.actionId),false)
            }
        }
    }

}
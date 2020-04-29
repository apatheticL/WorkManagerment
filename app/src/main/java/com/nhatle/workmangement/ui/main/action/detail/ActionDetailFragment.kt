package com.nhatle.workmangement.ui.main.action.detail

import android.view.View
import android.widget.Button
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.ActionResponse
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.source.remote.ActionRemoteDataSource
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.report.UserActionReportFragment
import com.nhatle.workmangement.ui.main.action.small.UserActionSmallFragment
import com.nhatle.workmangement.until.Common
import kotlinx.android.synthetic.main.custom_infor_work.*
import kotlinx.android.synthetic.main.fragment_work_detail.*
import kotlinx.android.synthetic.main.fragment_work_detail.textNameAction
import kotlinx.android.synthetic.main.item_my_action_small.*

class ActionDetailFragment : BaseFragment(), ActionDetailContract.View, View.OnClickListener {

    override val layoutResource: Int = R.layout.fragment_work_detail
    private var actionResponse :ActionResponse?=null
    private val adapter: ListMemberAdapter by lazy{
         ListMemberAdapter(object : ListMemberAdapter.SendProfile {
            override fun sendProfileId(profileId: Int) {
                // go profile fragment
            }

        })
    }
    private var presenter: ActionDetailPresenter? = null
    override fun initData() {
        configView()
        initAdapter()
        registerListener()
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = ActionRemoteDataSource.getInstance(userService)
        val repository = ActionRemoteRepository(dataSource = dataSource)
        presenter = ActionDetailPresenter(this, repository)
        presenter!!.getAllMemberOnGroup(actionResponse!!.groupId, actionResponse!!.actionId)
    }

    private fun configView() {
        textNameAction.text = actionResponse?.actionName
        textDescription.text = actionResponse?.description
        nameCreator.text = actionResponse?.nameCreator
        timeStartAction.text = actionResponse?.timeStart
        timeEndAction.text = actionResponse?.timeEnd
    }


    override fun initComponents() {
        initPresenter()

    }

    private fun registerListener() {
        butonGoActionReport.setOnClickListener(this)
        buttonGoActionSmall.setOnClickListener(this)
    }

    private fun initAdapter() {

        recyclerListMember.adapter = adapter

    }

    fun sendData(actionResponse: ActionResponse) {
        this.actionResponse = actionResponse
    }

    override fun showAllMember(list: ArrayList<UserTeamResponse>) {
        adapter.setData(list)
    }

    override fun onFailed(string: String) {
    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.buttonGoActionSmall -> {
                (activity as MainActivity).hindNavigation(true)
                val fragment = UserActionSmallFragment(actionResponse!!.actionId)
                replaceFragment(R.id.frag_image, fragment, true)

            }
            R.id.butonGoActionReport -> {
                (activity as MainActivity).hindNavigation(true)
                replaceFragment(
                    R.id.frag_image,
                    UserActionReportFragment(actionResponse!!.actionId),
                    true
                )
            }
        }
    }

}
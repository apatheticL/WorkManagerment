package com.nhatle.workmangement.ui.main.action

import android.view.View
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.ActionResponse
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.source.remote.ActionRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.add.AddActionFragment
import com.nhatle.workmangement.ui.main.action.detail.ActionDetailFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonData
import com.nhatle.workmangement.until.api.UserService
import kotlinx.android.synthetic.main.fragment_work.*

class ActionFragment : BaseFragment(), ActionContract.View, ActionAdapter.SendData {
    override val layoutResource: Int = R.layout.fragment_work
    private var presenter: ActionPresenter? = null
    private var userService: UserService? = null
    private var adapter:ActionAdapter?=null
    private var listActionResponse:List<ActionResponse>?=null
    override fun initData() {
        userService = Common.getUserService()
        initPresenter()
        handlerGetAllAction()
        handlerGetAllMembeOnAction()
    }

    private fun handlerGetAllMembeOnAction() {

    }

    private fun handlerGetAllAction() {
        presenter!!.getAllActionIsMember(CommonData.getInstance().profile!!.profileId)
    }

    override fun initComponents() {
        initAdapter()
        registerListener()
    }

    private fun registerListener() {
        buttonAddWork.setOnClickListener{
            addFragment(R.id.frag_main,AddActionFragment(),false)
        }
    }

    private fun initAdapter() {
        adapter = ActionAdapter(this)
    }

    private fun initPresenter() {
        val dataSource = ActionRemoteDataSource.getInstance(userService!!)
        val repository = ActionRemoteRepository(dataSource = dataSource)
        presenter = ActionPresenter(this,repository = repository)
    }

    override fun loadAllActionByUserMember(listAction: ArrayList<ActionResponse>) {
        this.listActionResponse = listAction
        textError.visibility = View.GONE
        adapter?.setData(listAction)
    }

    override fun loadFailed(error: String) {
        textError.visibility = View.VISIBLE
        textError.text = error
    }


    override fun sendData(actionResponse: ActionResponse, position: Int) {
        val fragment= ActionDetailFragment()
        addFragment(R.id.frag_main,
            ActionDetailFragment(),false)
        fragment.sendData(actionResponse)
    }


}
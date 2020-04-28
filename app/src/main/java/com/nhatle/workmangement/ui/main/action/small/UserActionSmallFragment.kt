package com.nhatle.workmangement.ui.main.action.small

import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.UserActionSmallResponse
import com.nhatle.workmangement.data.reponsitory.remote.UserActionSmallRepository
import com.nhatle.workmangement.data.source.remote.UserActionSmallRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.report.add.AddReportFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonData
import kotlinx.android.synthetic.main.fragment_user_action_small.*

class UserActionSmallFragment(val actionId:Int) : BaseFragment(),
    UserActionSmallContract.View {
    override val layoutResource: Int = R.layout.fragment_user_action_small
    private var presenter: UserActionSmallPresenter? = null
    private var userActionSmallAdapter: UserActionSmallAdapter? = null
    private var myUserActionSmallAdapter: MyUserActionSmallAdapter? = null

    override fun initData() {
        recycleListUserAactionSmall()
        recyclerMyActionSmall()
    }

    private fun initShowListUserActionSmall() {
        presenter!!.getAllUserActionSmallByAction(actionId = actionId)
        presenter!!.getAllActionSmallOfUser(
            actionId = actionId,
            profileId = CommonData.getInstance().profile!!.profileId
        )
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = UserActionSmallRemoteDataSource.getInstance(userService = userService)
        val repository = UserActionSmallRepository(dataSource = dataSource)
        presenter = UserActionSmallPresenter(this, repository = repository)

    }

    override fun initComponents() {
        initPresenter()
        initShowListUserActionSmall()
    }

    private fun recyclerMyActionSmall() {
        myUserActionSmallAdapter = MyUserActionSmallAdapter(object :MyUserActionSmallAdapter.AddReport{
            override fun getUserActionSmallId(id: Int) {
                // open fragment add report
                val fragment =
                    AddReportFragment()
                addFragment(R.id.frag_main, fragment,false)
                fragment.sendData(id,actionId)
            }
        })
        recycleMyActionSmall.adapter = myUserActionSmallAdapter

    }

    private fun recycleListUserAactionSmall() {
        userActionSmallAdapter = UserActionSmallAdapter()
        recyclerListUserMakeActionSmall.adapter = userActionSmallAdapter
    }

    override fun loadAllActionSmall(listAction: List<UserActionSmallResponse>) {
        userActionSmallAdapter?.setData(list = listAction as ArrayList<UserActionSmallResponse>)
    }

    override fun loadFailed(error: String) {
    }

    override fun loadAllActionSmallByUser(listUserActionSmall: List<UserActionSmallResponse>) {
        myUserActionSmallAdapter!!.setData(list = listUserActionSmall as ArrayList<UserActionSmallResponse>)
    }

    override fun loadMyActionFailed(error: String) {
    }
}
package com.nhatle.workmangement.ui.main.user_action_small.user

import android.content.Context
import android.view.KeyEvent
import android.view.View
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.UserActionSmallResponse
import com.nhatle.workmangement.data.reponsitory.remote.UserActionSmallRepository
import com.nhatle.workmangement.data.source.remote.UserActionSmallRemoteDataSource
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.detail.ActionDetailFragment
import com.nhatle.workmangement.ui.main.report.add.AddReportFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonAction
import com.nhatle.workmangement.until.CommonData
import kotlinx.android.synthetic.main.fragment_my_user_action_small.*

class MyActionSmallFragment : BaseFragment(), MyActionSmallContrac.View {
    private val myUserActionSmallAdapter: MyUserActionSmallAdapter by lazy {
        MyUserActionSmallAdapter(
            object :
                MyUserActionSmallAdapter.AddReport {
                override fun getUserActionSmallId(id: Int) {
                    // open fragment add report
                    val fragment =
                        AddReportFragment()
                    replaceFragment(R.id.frag_user_action_small, fragment, false)
                    fragment.sendData(id, CommonAction.getInstance().action!!.actionId)
                }

                override fun getContext(): Context {
                    return context!!
                }
            })
    }

    private var presenter: MyActionSmallPresenter? = null
    override val layoutResource: Int
        get() = R.layout.fragment_my_user_action_small

    override fun initData() {
    }

    override fun initComponents() {
        initPresenter()
        getAllData()
    }

    private fun getAllData() {
        presenter!!.getAllActionSmallOfUser(
            actionId = CommonAction.getInstance().action!!.actionId,
            profileId = CommonData.getInstance().profile!!.profileId
        )
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = UserActionSmallRemoteDataSource.getInstance(userService = userService)
        val repository = UserActionSmallRepository(dataSource = dataSource)
        presenter =
            MyActionSmallPresenter(
                this,
                repository = repository
            )

    }

    private fun recyclerMyActionSmall() {
        recycleMyActionSmall.adapter = myUserActionSmallAdapter
    }

    override fun loadAllActionSmallByUser(listUserActionSmall: List<UserActionSmallResponse>) {
        if (listUserActionSmall.isNotEmpty()) {
            myUserActionSmallAdapter.setData(list = listUserActionSmall as ArrayList<UserActionSmallResponse>)
            recyclerMyActionSmall()
        }
    }

    override fun loadMyActionFailed(error: String) {
    }
    override fun onResume() {
        super.onResume()
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
        view!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event!!.action== KeyEvent.ACTION_UP&& keyCode== KeyEvent.KEYCODE_BACK){
                    replaceFragment(R.id.frag_main, ActionDetailFragment(),false)
                    (activity as MainActivity).hindNavigation(true)
                }
                return false
            }

        })
    }
}

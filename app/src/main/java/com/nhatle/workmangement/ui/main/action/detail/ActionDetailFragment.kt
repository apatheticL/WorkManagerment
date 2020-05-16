package com.nhatle.workmangement.ui.main.action.detail

import android.view.KeyEvent
import android.view.View
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.source.remote.ActionRemoteDataSource
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.ActionFragment
import com.nhatle.workmangement.ui.main.report.UserActionReportFragment
import com.nhatle.workmangement.ui.main.user_action_small.UserActionSmallManagerFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonAction
import com.nhatle.workmangement.until.Deferent
import kotlinx.android.synthetic.main.custom_infor_work.*
import kotlinx.android.synthetic.main.fragment_work_detail.*

class ActionDetailFragment : BaseFragment(), ActionDetailContract.View, View.OnClickListener {
    private var numberMember: Int = -1
    override val layoutResource: Int = R.layout.fragment_work_detail
    private val adapter: ListMemberAdapter by lazy {
        ListMemberAdapter(object : ListMemberAdapter.SendProfile {
            override fun sendProfileId(profileId: Int) {
                // go profile fragment
            }

        })
    }
    private var presenter: ActionDetailPresenter? = null
    override fun initData() {
        configView()
        registerListener()
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = ActionRemoteDataSource.getInstance(userService)
        val repository = ActionRemoteRepository(dataSource = dataSource)
        presenter = ActionDetailPresenter(this, repository)
        presenter!!.getAllMemberOnGroup(
            CommonAction.getInstance().action!!.groupId,
            CommonAction.getInstance().action!!.actionId
        )
    }

    private fun configView() {
        textNameAction.text = CommonAction.getInstance().action!!.actionName
        textDescription.text = CommonAction.getInstance().action!!.description
        nameCreator.text = CommonAction.getInstance().action!!.nameCreator
        timeStartAction.text = CommonAction.getInstance().action!!.timeStart
        timeEndAction.text = CommonAction.getInstance().action!!.timeEnd
    }


    override fun initComponents() {
        initPresenter()

    }

    private fun registerListener() {
        butonGoActionReport.setOnClickListener(this)
        buttonGoActionSmall.setOnClickListener(this)
        buttonBack.setOnClickListener(this)
    }

    private fun initAdapter() {
        recyclerListMember.adapter = adapter
    }

    override fun showAllMember(list: ArrayList<UserTeamResponse>) {
        if (list.isNotEmpty()) {
            adapter.setData(list)
            initAdapter()
            this.numberMember = list.size
        }
    }

    override fun onFailed(string: String) {
    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.buttonGoActionSmall -> {
                (activity as MainActivity).hindNavigation(true)
                Deferent.setNumberMember(numberMember)
                val fragment = UserActionSmallManagerFragment()
                replaceFragment(R.id.frag_main, fragment, true)

            }
            R.id.butonGoActionReport -> {
                (activity as MainActivity).hindNavigation(true)
                replaceFragment(
                    R.id.frag_main,
                    UserActionReportFragment(),
                    false
                )
            }
            R.id.buttonBack -> {
                (activity as MainActivity).hindNavigation(false)
                val fragment = ActionFragment()
                replaceFragment(R.id.frag_main, fragment, false)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
        view!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event!!.action==KeyEvent.ACTION_UP&& keyCode==KeyEvent.KEYCODE_BACK){
                    (activity as MainActivity).hindNavigation(false)
                    replaceFragment(R.id.frag_main,ActionFragment(),false)
                }
                return false
            }

        })
    }
}
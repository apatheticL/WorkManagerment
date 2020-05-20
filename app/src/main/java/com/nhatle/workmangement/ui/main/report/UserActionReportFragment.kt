package com.nhatle.workmangement.ui.main.report

import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.UserActionReportResponse
import com.nhatle.workmangement.data.reponsitory.remote.UserActionReportRepository
import com.nhatle.workmangement.data.source.remote.UserActionReportRemoteDataSource
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.detail.ActionDetailFragment
import com.nhatle.workmangement.ui.main.report.update.UpdateActionUserReportFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonAction
import kotlinx.android.synthetic.main.fragment_report.*

class UserActionReportFragment : BaseFragment(), UserActionReportContract.View {
    override val layoutResource: Int = R.layout.fragment_report
    private var position:Int=-1
    private var presenter: UserActionReportPresenter? = null
    private var recyclerView:RecyclerView?=null
    private val adapter: ActionReportAdapter by lazy {
        ActionReportAdapter(object : ActionReportAdapter.ReportManager {
            override fun sendDataToButton(
                itemData: UserActionReportResponse,
                buttonMenu: ImageButton,
                position: Int
            ) {
                configMenu(itemData, buttonMenu,position)
            }
        })
    }
    override fun initComponents() {
        recyclerView = recyclerReport
        initPresenter()
        configView()
    }
    override fun initData() {
        registerListenter()
    }

    private fun registerListenter() {
        buttonBack.setOnClickListener {
            (activity as MainActivity).hindNavigation(true)
            replaceFragment(R.id.frag_main, ActionDetailFragment(), false)
        }
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = UserActionReportRemoteDataSource.getInstance(userService)
        val repository = UserActionReportRepository(dataSource)
        presenter = UserActionReportPresenter(this, repository)
        presenter!!.getAllUserActionRemoteByAction(CommonAction.getInstance().action!!.actionId)

    }



    private fun configView() {
        Glide.with(avatarUserCreate)
            .load(CommonAction.getInstance().action!!.avatar)
            .placeholder(R.drawable.bavarian).into(avatarUserCreate)
    }

    private fun configMenu(
        itemData: UserActionReportResponse,
        buttonMenu: ImageButton,
        position: Int
    ) {
        val popupMenu = PopupMenu(activity, buttonMenu)
        popupMenu.menuInflater.inflate(R.menu.menu_report, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            if (it.itemId == R.id.actionDelete) {
                presenter!!.deleteUserActionReport(itemData.reportId)
                this.position = position
            }
            if (it.itemId == R.id.actionUpdate) {
                (activity as MainActivity).hindNavigation(true)
                replaceFragment(
                    R.id.frag_main,
                    UpdateActionUserReportFragment(
                        itemData
                    ), false
                )
            }
            true
        }
        popupMenu.show()
    }

    override fun loadAllActionReport(listAction: List<UserActionReportResponse>) {
        if (listAction.isNotEmpty()) {
            adapter.setData(listAction as ArrayList<UserActionReportResponse>)
            recyclerView!!.adapter = adapter
        }
    }

    override fun reloadData() {
        adapter.delete(position)
        adapter.notifyDataSetChanged()
    }

    override fun loadFailed(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }
    override fun onResume() {
        super.onResume()
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
        view!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event!!.action== KeyEvent.ACTION_UP&& keyCode== KeyEvent.KEYCODE_BACK){

//                    replaceFragment(R.id.frag_main,ActionDetailFragment(),false)
                    (activity as MainActivity).openFragmentActionDetail()
                }
                return false
            }

        })
    }
}
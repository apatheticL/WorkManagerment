package com.nhatle.workmangement.ui.main.action.report

import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.UserActionReportResponse
import com.nhatle.workmangement.data.reponsitory.remote.UserActionReportRepository
import com.nhatle.workmangement.data.source.remote.UserActionReportRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.report.update.UpdateActionUserReportFragment
import com.nhatle.workmangement.until.Common
import kotlinx.android.synthetic.main.fragment_report.*

class UserActionReportFragment(val actionId: Int) : BaseFragment(), UserActionReportContract.View {
    override val layoutResource: Int = R.layout.fragment_report
    private var presenter: UserActionReportPresenter? = null
    private val adapter: ActionReportAdapter by lazy {
         ActionReportAdapter(object : ActionReportAdapter.ReportManager {
            override fun sendDataToButton(
                itemData: UserActionReportResponse,
                buttonMenu: ImageButton
            ) {
                configMenu(itemData, buttonMenu)
            }

        })
    }
    override fun initData() {
        initRecyclerView()
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = UserActionReportRemoteDataSource.getInstance(userService)
        val repository = UserActionReportRepository(dataSource)
        presenter = UserActionReportPresenter(this, repository)
        presenter!!.getAllUserActionRemoteByAction(actionId)
    }

    override fun initComponents() {
        initPresenter()

    }

    private fun initRecyclerView() {

        recyclerReport.adapter = adapter
    }

    private fun configMenu(itemData: UserActionReportResponse, buttonMenu: ImageButton) {
        val popupMenu = PopupMenu(activity, buttonMenu)
        popupMenu.menuInflater.inflate(R.menu.menu_report, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            if (it.itemId == R.id.actionDelete) {
                presenter!!.deleteUserActionReport(itemData.reportId)
            }
            if (it.itemId == R.id.actionUpdate){
                replaceFragment(R.id.frag_main,
                    UpdateActionUserReportFragment(
                        itemData
                    ),false)
            }
            true
        }
    }

    override fun loadAllActionReport(listAction: List<UserActionReportResponse>) {
        adapter.setData(listAction as ArrayList<UserActionReportResponse>)
    }

    override fun reloadData(boolean: Boolean) {
        if (boolean) {
            presenter!!.getAllUserActionRemoteByAction(actionId)
            adapter.notifyDataSetChanged()
        }
    }

    override fun loadFailed(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }
}
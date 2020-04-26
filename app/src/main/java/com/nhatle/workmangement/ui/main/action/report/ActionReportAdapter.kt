package com.nhatle.workmangement.ui.main.action.report

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.UserActionReportResponse
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import com.nhatle.workmangement.until.CommonData
import kotlinx.android.synthetic.main.item_report.view.*
import java.text.SimpleDateFormat

class ActionReportAdapter(val call: ReportManager) :
    BaseRecyclerViewAdapter<UserActionReportResponse, ActionReportAdapter.ActionReportHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionReportHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_report, parent, false)
        return ActionReportHolder(layout, call)
    }

    class ActionReportHolder(itemView: View, val call: ReportManager) :
        BaseViewHolder<UserActionReportResponse>(itemView) {
        private val format = SimpleDateFormat("dd/MM/yyyy")
        override fun onBindData(itemData: UserActionReportResponse) {
            super.onBindData(itemData)
            initView(itemView, itemData)

        }

        override fun onBindData(itemPosition: Int, itemData: UserActionReportResponse) {
            super.onBindData(itemPosition, itemData)
            registerListener(itemView.buttonMenu, itemData)
            checkHindButtonMenu(itemView,itemData)
        }

        private fun checkHindButtonMenu(itemView: View, itemData: UserActionReportResponse) {
            if (itemData.profileId!=CommonData.getInstance().profile!!.profileId){
                itemView.buttonMenu.visibility  =View.GONE
            }
        }

        private fun registerListener(buttonMenu: ImageButton, itemData: UserActionReportResponse) {
            call.sendDataToButton(itemData, buttonMenu)
        }

        private fun initView(itemView: View, itemData: UserActionReportResponse) {
            Glide.with(itemView.imageAvatarMemberAction)
                .load(itemData.avatar)
                .placeholder(R.drawable.bavarian)
                .into(itemView.imageAvatarMemberAction)
            itemView.textFullnameRe.text = itemData.fullName
            itemView.textDateReport.text = format.format(itemData.timeReport)
            itemView.textWorkActual.text = itemData.actionActual
            itemView.textWorkNext.text = itemData.actionNext
            itemView.textWorkIssua.text = itemData.actionIssua
        }
    }

    interface ReportManager {
        fun sendDataToButton(itemData: UserActionReportResponse, buttonMenu: ImageButton)
    }
}
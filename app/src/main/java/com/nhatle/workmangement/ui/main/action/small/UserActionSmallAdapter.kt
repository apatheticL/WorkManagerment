package com.nhatle.workmangement.ui.main.action.small

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.UserActionSmallResponse
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_user_action_small.view.*
import java.text.SimpleDateFormat


class UserActionSmallAdapter :
    BaseRecyclerViewAdapter<UserActionSmallResponse, UserActionSmallAdapter.UserActionSmallHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserActionSmallHolder {
        val layout = LayoutInflater.from(parent.context).
        inflate(R.layout.item_user_action_small,parent,false)
        return UserActionSmallHolder(layout)
    }

    class UserActionSmallHolder(itemView: View) : BaseViewHolder<UserActionSmallResponse>(itemView){
        val format = SimpleDateFormat("dd/MM/yyyy")
        override fun onBindData(itemData: UserActionSmallResponse) {
            super.onBindData(itemData)
            addDataView(itemView,itemData)
        }

        private fun addDataView(itemView: View, itemData: UserActionSmallResponse) {
            Glide.with(itemView.imageAvatarMember)
                .load(itemData.avatar)
                .placeholder(R.drawable.bavarian)
                .into(itemView.imageAvatarMember)
            itemView.nameMember.text = itemData.fullName
            itemView.textNameAction.text = itemData.actionSmallName
            itemView.timeStart.text = format.format(itemData.timeStart)
            itemView.timeEnd.text = format.format(itemData.timeEnd)
        }
    }

    override fun onBindViewHolder(holder: UserActionSmallHolder, position: Int) {
        holder.onBindData(getData()[position])
    }
}
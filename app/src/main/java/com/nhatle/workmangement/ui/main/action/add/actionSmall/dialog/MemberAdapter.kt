package com.nhatle.workmangement.ui.main.action.add.actionSmall.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_avatar.view.*

class MemberAdapter(val call: SendData) :
    BaseRecyclerViewAdapter<UserTeamResponse, MemberAdapter.UserTeamHolder>() {
    class UserTeamHolder(itemView: View) : BaseViewHolder<UserTeamResponse>(itemView) {
        override fun onBindData(itemData: UserTeamResponse) {
            super.onBindData(itemData)
            cofigView(itemData, itemView)
        }

        private fun cofigView(itemData: UserTeamResponse, itemView: View) {
            Glide.with(itemView.imageAvatarMemberOnAction).load(itemData.avatar)
                .placeholder(R.drawable.bavarian).into(itemView.imageAvatarMemberOnAction)
            itemView.nameUser.text = itemData.fullName
        }
    }

    interface SendData {
        fun sendUserTeam(data: UserTeamResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserTeamHolder {
        val la =
            LayoutInflater.from(parent.context).inflate(R.layout.item_avatar, parent, false)
        return UserTeamHolder(la)
    }

    override fun onBindViewHolder(holder: UserTeamHolder, position: Int) {
        holder.onBindData(getData()[position])
        registerClick(holder.itemView, getData()[position])
    }

    private fun registerClick(itemView: View, itemData: UserTeamResponse) {
        itemView.setOnClickListener {
            call.sendUserTeam(itemData)
        }
    }
}
package com.nhatle.workmangement.ui.main.action.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_avatar.view.*

class ListMemberAdapter(val callback: SendProfile) :
    BaseRecyclerViewAdapter<UserTeamResponse, ListMemberAdapter.UserTeamHolder>() {

    class UserTeamHolder(itemView: View,val callback: SendProfile) : BaseViewHolder<UserTeamResponse>(itemView) {
        override fun onBindData(itemData: UserTeamResponse) {
            super.onBindData(itemData)
            configView(itemView, itemData)
        }

        private fun configView(itemView: View, itemData: UserTeamResponse) {
            Glide.with(itemView.imageAvatar).load(itemData.avatar)
                .error(R.drawable.bavarian)
                .placeholder(R.drawable.bavarian)
                .into(itemView.imageAvatar)
            itemView.nameUser.text = itemData.fullName
        }

        override fun onBindData(itemPosition: Int, itemData: UserTeamResponse) {
            super.onBindData(itemPosition, itemData)
            callback.sendProfileId(itemData.profileId)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserTeamHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_avatar, parent, false)
        return UserTeamHolder(layout,callback)
    }
    interface SendProfile{
        fun sendProfileId(profileId:Int)
    }
}
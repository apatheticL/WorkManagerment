package com.nhatle.workmangement.ui.main.team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_friend.view.*

class AddUserTeamAdapter(val call: SendFriend): BaseRecyclerViewAdapter<UserProfile, AddUserTeamAdapter.FriendHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_friend,parent,
            false)
        return FriendHolder(layout,call)
    }
    class FriendHolder(itemView: View,val call:SendFriend) : BaseViewHolder<UserProfile>(itemView){
        override fun onBindData(itemData: UserProfile) {
            super.onBindData(itemData)
            configView(itemView,itemData)
            registerListener(itemView,itemData)
        }

        private fun registerListener(itemView: View, itemData: UserProfile) {
            itemView.setOnClickListener{
                call.sendData(itemData)
            }
        }

        private fun configView(itemView: View, itemData: UserProfile) {
            Glide.with(itemView.imageAvatarFriend)
                .load(itemData.avatar)
                .placeholder(R.drawable.bavarian)
                .into(itemView.imageAvatarFriend)
            itemView.textNameFriend.text = itemData.fullName
            itemView.textPhonenumber.text = itemData.phoneMumber
        }
    }

    interface SendFriend{
        fun sendData(userProfile: UserProfile)
    }
}
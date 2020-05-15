package com.nhatle.workmangement.ui.main.friend.add

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import com.nhatle.workmangement.ui.main.friend.SendDataProfile
import kotlinx.android.synthetic.main.item_add_friend.view.*

class ListUserNotFriendAdapter(val call: SendDataProfile.FriendAdd) :
    BaseRecyclerViewAdapter<UserProfile, ListUserNotFriendAdapter.UserNotFriendHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserNotFriendHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_add_friend, parent, false)
        return UserNotFriendHolder(layout, call)
    }

    override fun onBindViewHolder(holder: UserNotFriendHolder, position: Int) {
        holder.onBindData(getData()[position])
        holder.registerAddClick(holder.itemView.buttonAddFriend, getData()[position])
        holder.registerCancelClick(
            holder.itemView.buttonCancelFriend,
            getData()[position],
            position
        )
    }

    fun deleteItem(position: Int) {
        if (getData().size!=0){
            getData().removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, getData().size)
        }
        else{
            call.showNotification("not data")
        }
    }

    class UserNotFriendHolder(itemView: View, val call: SendDataProfile.FriendAdd) :
        BaseViewHolder<UserProfile>(itemView) {
        private var checkAddClick: Boolean = false
        override fun onBindData(itemData: UserProfile) {
            super.onBindData(itemData)
            configView(itemView, itemData)
        }

        private fun configView(itemView: View, itemData: UserProfile) {
            Glide.with(itemView.imageAvatarFriend)
                .load(itemData.avatar)
                .placeholder(R.drawable.bavarian)
                .into(itemView.imageAvatarFriend)
            itemView.textNameFriend.text = itemData.fullName
            itemView.textAddressByUser.text = itemData.address
        }

        fun registerCancelClick(
            buttonCancelFriend: Button,
            itemData: UserProfile,
            itemPosition: Int
        ) {
            buttonCancelFriend.setOnClickListener {
                if (checkAddClick) {
                    call.sendDataToDeleteAdd(this.itemPosition, itemData)
                    it.setBackgroundResource(R.drawable.bg_add_done)
                    itemView.buttonAddFriend.visibility = View.VISIBLE
                }
                if (!checkAddClick) {
                    call.deleteItem(itemPosition)
                    it.setBackgroundResource(R.drawable.bg_button)
                }
            }
        }

        fun registerAddClick(buttonAddFriend: Button, itemData: UserProfile) {
            buttonAddFriend.setOnClickListener {
                call.sendDataToAdd(itemData)
                it.setBackgroundResource(R.drawable.bg_add_done)
                checkAddClick = true
            }
        }


    }


}
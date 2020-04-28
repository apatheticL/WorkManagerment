package com.nhatle.workmangement.ui.main.friend.add

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import com.nhatle.workmangement.ui.main.friend.SendDataProfile
import kotlinx.android.synthetic.main.item_add_friend.view.*

class ListUserNotFriendAdapter(val call:SendDataProfile.FriendAdd)
    : BaseRecyclerViewAdapter<UserProfile, ListUserNotFriendAdapter.UserNotFriendHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserNotFriendHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_add_friend,parent,false)
        return UserNotFriendHolder(layout,call)
    }

    fun deleteItem(position: Int) {
        getData().removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,getData().size)
    }

    class UserNotFriendHolder(itemView: View,val call: SendDataProfile.FriendAdd) : BaseViewHolder<UserProfile>(itemView){
        private var checkAddClick :Boolean = false
        override fun onBindData(itemData: UserProfile) {
            super.onBindData(itemData)
            configView(itemView,itemData)
        }

        private fun configView(itemView: View, itemData: UserProfile) {
            Glide.with(itemView.imageAvatarFriend)
                .load(itemData.avatar)
                .placeholder(R.drawable.bavarian)
                .into(itemView.imageAvatarFriend)
            itemView.textNameFriend.text = itemData.fullName
            itemView.textAddressByUser.text = itemData.address
        }

        override fun onBindData(itemPosition: Int, itemData: UserProfile) {
            super.onBindData(itemPosition, itemData)
            registerListener(itemData,itemView,itemPosition)
        }

        private fun registerListener(itemData: UserProfile, itemView: View, itemPosition: Int) {
            itemView.buttonAddFriend.setOnClickListener{
                call.sendDataToAdd(itemData)
                it.visibility = View.INVISIBLE
                checkAddClick =true
            }
            itemView.buttonCancelFriend.setOnClickListener{
                if (checkAddClick){
                    call.sendDataToDeleteAdd(itemPosition,itemData)
                    itemView.buttonAddFriend.visibility = View.VISIBLE
                }
                if (!checkAddClick){
                    call.deleteItem(itemPosition)
                }
            }
        }
    }
}
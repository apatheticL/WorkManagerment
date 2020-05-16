package com.nhatle.workmangement.ui.main.friend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.FriendResponse
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_friend.view.*

class FriendAdapter(val call: SendDataProfile.Friend) :
    BaseRecyclerViewAdapter<FriendResponse, FriendAdapter.FriendHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        return FriendHolder(layout, call)
    }

    override fun onBindViewHolder(holder: FriendHolder, position: Int) {
        holder.onBindData(getData()[position])
        holder.registerListener(holder.itemView.imageAvatarFriend,getData()[position])
    }

    class FriendHolder(itemView: View, val call: SendDataProfile.Friend) :
        BaseViewHolder<FriendResponse>(itemView) {
        override fun onBindData(itemData: FriendResponse) {
            super.onBindData(itemData)
            configView(itemData, itemView)
        }

         fun registerListener(friendAvatar: CircleImageView, friendId: FriendResponse) {
            friendAvatar.setOnClickListener{
                call.sendData(friendId)
            }
        }

        private fun configView(itemData: FriendResponse, itemView: View) {
            Glide.with(itemView.imageAvatarFriend).load(itemData.friendAvatar)
                .placeholder(R.drawable.bavarian)
                .into(itemView.imageAvatarFriend)
            itemView.textNameFriend.text = itemData.friendName
            itemView.textPhonenumber.text = itemData.phoneNumber
        }
    }
}
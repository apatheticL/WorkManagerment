package com.nhatle.workmangement.ui.main.friend.receiver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.FriendResponse
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import com.nhatle.workmangement.ui.main.friend.SendDataProfile
import kotlinx.android.synthetic.main.item_receiver.view.*

class FriendSuggestionsAdapter(val call: SendDataProfile.ReceiverFriend):
    BaseRecyclerViewAdapter<FriendResponse, FriendSuggestionsAdapter.ReceiverFriendHolder>() {
     class ReceiverFriendHolder(itemView: View,val call:SendDataProfile.ReceiverFriend)
         : BaseViewHolder<FriendResponse>(itemView){
        override fun onBindData(itemData: FriendResponse) {
            super.onBindData(itemData)
            configData(itemView,itemData)
        }

         override fun onBindData(itemPosition: Int, itemData: FriendResponse) {
             super.onBindData(itemPosition, itemData)
             registerListener(itemView,itemData,itemPosition)
         }

         private fun registerListener(
             itemView: View,
             itemData: FriendResponse,
             itemPosition: Int
         ) {
             itemView.buttonAccept.setOnClickListener{
                 call.sendDataToAccept(itemPosition,itemData)
             }
             itemView.buttonRmAdd.setOnClickListener{
                 call.sendDataToDeleteInvitation(itemPosition,itemData)
             }
         }

         private fun configData(itemView: View, itemData: FriendResponse) {
            Glide.with(itemView.imageAvatarFriend)
                .load(itemData.friendAvatar)
                .placeholder(R.drawable.bavarian)
                .into(itemView.imageAvatarFriend)
            itemView.textNameFriend.text = itemData.friendName
            itemView.textPhonenumber.text = itemData.phoneNumber

        }


     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiverFriendHolder {
        val la = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_receiver,parent,false)
        return ReceiverFriendHolder(la,call)
    }
    fun deleteItem(position: Int){
        getData().removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,getData().size)
    }
}
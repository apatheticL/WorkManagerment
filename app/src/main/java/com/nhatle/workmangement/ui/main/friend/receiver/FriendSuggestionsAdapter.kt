package com.nhatle.workmangement.ui.main.friend.receiver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.FriendResponse
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import com.nhatle.workmangement.ui.main.friend.SendDataProfile
import kotlinx.android.synthetic.main.item_receiver.view.*

class FriendSuggestionsAdapter(val call: SendDataProfile.ReceiverFriend):
    BaseRecyclerViewAdapter<FriendResponse, FriendSuggestionsAdapter.ReceiverFriendHolder>() {
    override fun onBindViewHolder(holder: ReceiverFriendHolder, position: Int) {
        holder.onBindData(getData()[position])
        holder.registerButtonAccept(holder.itemView.buttonAccept,getData()[position],position)
        holder.registerButtonDelete(holder.itemView.buttonRmAdd,getData()[position],position)
    }
     class ReceiverFriendHolder(itemView: View,val call:SendDataProfile.ReceiverFriend)
         : BaseViewHolder<FriendResponse>(itemView){
        override fun onBindData(itemData: FriendResponse) {
            super.onBindData(itemData)
            configData(itemView,itemData)
        }



          fun registerButtonAccept(
              buttonAccept: Button,
             itemData: FriendResponse,
             itemPosition: Int
         ) {
             buttonAccept.setOnClickListener{
                 call.sendDataToAccept(itemPosition,itemData)
             }

         }
         fun registerButtonDelete(
             buttonDelte: Button,
             itemData: FriendResponse,
             itemPosition: Int
         ) {
             buttonDelte.setOnClickListener{
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
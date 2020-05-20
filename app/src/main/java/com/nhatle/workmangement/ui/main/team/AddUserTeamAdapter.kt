package com.nhatle.workmangement.ui.main.team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.FriendResponse
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_dialog.view.*

class AddUserTeamAdapter(val call: SendFriend): BaseRecyclerViewAdapter<FriendResponse, AddUserTeamAdapter.FriendHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_dialog,parent,
            false)
        return FriendHolder(layout)
    }

    override fun onBindViewHolder(holder: FriendHolder, position: Int) {
        holder.onBindData(getData()[position])
        registerListener(holder.itemView.checkbox,getData()[position],position)
    }

    private fun registerListener(
        checkbox: AppCompatCheckBox,
        friendResponse: FriendResponse,
        position: Int
    ) {
        checkbox.setOnClickListener{

            checkbox.isChecked =true
            checkbox.text = position.toString()
            call.sendData(friendResponse)
        }
    }

    class FriendHolder(itemView: View) : BaseViewHolder<FriendResponse>(itemView){
        override fun onBindData(itemData: FriendResponse) {
            super.onBindData(itemData)
            configView(itemView,itemData)
        }
        private fun configView(itemView: View, itemData: FriendResponse) {
            Glide.with(itemView.imgAvatar)
                .load(itemData.friendAvatar)
                .placeholder(R.drawable.bavarian)
                .into(itemView.imgAvatar)
            itemView.textname.text = itemData.friendName
        }
    }

    interface SendFriend{
        fun sendData(userProfile: FriendResponse)
    }
}
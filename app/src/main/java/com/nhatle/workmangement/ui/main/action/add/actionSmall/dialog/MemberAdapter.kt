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

class MemberAdapter(val call: SendData):
    BaseRecyclerViewAdapter<UserTeamResponse, MemberAdapter.UserteamHolder>() {
    class UserteamHolder(itemView: View,val call:SendData) :BaseViewHolder<UserTeamResponse>(itemView){
        override fun onBindData(itemData: UserTeamResponse) {
            super.onBindData(itemData)
            cofigView(itemData,itemView)
        }

         fun registerClick(itemView: View, itemData: UserTeamResponse) {
            itemView.setOnClickListener{
                call.sendUserTeam(itemData)
            }
        }

        private fun cofigView(itemData: UserTeamResponse, itemView: View) {
            Glide.with(itemView.imageAvatar).load(itemData.avatar).into(itemView.imageAvatar)
            itemView.nameUser.text = itemData.fullName
        }
    }
    interface SendData{
        fun sendUserTeam(data:UserTeamResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserteamHolder {
        val la =
            LayoutInflater.from(parent.context).inflate(R.layout.item_avatar,parent,false)
        return UserteamHolder(la,call)
    }

    override fun onBindViewHolder(holder: UserteamHolder, position: Int) {
        holder.onBindData(getData()[position])
        holder.registerClick(holder.itemView,getData()[position])
    }
}
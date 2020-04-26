package com.nhatle.workmangement.ui.main.action.small

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.response.UserActionSmallResponse
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_my_action_small.view.*
import java.text.SimpleDateFormat

class MyUserActionSmallAdapter(val callback: AddReport):
    BaseRecyclerViewAdapter<UserActionSmallResponse,MyUserActionSmallAdapter.MyActionSmallHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyActionSmallHolder {
        val layout = LayoutInflater.from(parent.context).
        inflate(R.layout.item_my_action_small,parent,false)
        return MyActionSmallHolder(layout,callback)
    }
    class MyActionSmallHolder(itemView: View,val callback: AddReport) :BaseViewHolder<UserActionSmallResponse>(itemView){
       val format =SimpleDateFormat("dd/MM/yyyy")
        override fun onBindData(itemData: UserActionSmallResponse) {
            super.onBindData(itemData)
            configView(itemView,itemData)
            registerListener(itemView.buttonGoReport,itemData.userActionSmallId)
        }

        private fun registerListener(buttonGoReport: Button, userActionSmallId: Int) {
            buttonGoReport.setOnClickListener{
                callback.getUserActionSmallId(userActionSmallId)
            }
        }
        private fun configView(itemView: View, itemData: UserActionSmallResponse) {
            Glide.with(itemView.imageAvatarMember)
            Glide.with(itemView.imageAvatarMember)
                .load(itemData.avatar)
                .placeholder(R.drawable.bavarian)
                .into(itemView.imageAvatarMember)
            itemView.nameMember.text = itemData.fullName
            itemView.textNameAction.text = itemData.actionSmallName
            itemView.timeStart.text = format.format(itemData.timeStart)
            itemView.timeEnd.text = format.format(itemData.timeEnd)
        }
    }
    interface AddReport{
        fun getUserActionSmallId(id: Int)

    }

}
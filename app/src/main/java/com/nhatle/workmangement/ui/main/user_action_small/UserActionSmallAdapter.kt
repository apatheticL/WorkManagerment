package com.nhatle.workmangement.ui.main.user_action_small

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.UserActionSmallResponse
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import com.nhatle.workmangement.until.CommonData
import kotlinx.android.synthetic.main.item_user_action_small.view.*


class UserActionSmallAdapter(private val creatorId: Int, val call:SendActionSmall) :
    BaseRecyclerViewAdapter<UserActionSmallResponse, UserActionSmallAdapter.UserActionSmallHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserActionSmallHolder {
        val layout = LayoutInflater.from(parent.context).
        inflate(R.layout.item_user_action_small,parent,false)
        return UserActionSmallHolder(layout,creatorId)
    }

    class UserActionSmallHolder(itemView: View,val creatorId: Int) : BaseViewHolder<UserActionSmallResponse>(itemView){
        override fun onBindData(itemData: UserActionSmallResponse) {
            super.onBindData(itemData)
            addDataView(itemView,itemData)
        }

        private fun addDataView(itemView: View, itemData: UserActionSmallResponse) {
            if (creatorId==CommonData.getInstance().profile!!.profileId){
                itemView.buttonMore.visibility = View.VISIBLE
            }
            else{
                itemView.buttonMore.visibility = View.GONE
            }
            if (itemData.reportId==null){
                itemView.textStatusReport.visibility = View.GONE
            }
            if (itemData.reportId!=null){
                itemView.textStatusReport.visibility = View.VISIBLE
                itemView.textStatusReport.text= "Da bao cao"
            }
            Glide.with(itemView.imageAvatarMember)
                .load(itemData.avatar)
                .placeholder(R.drawable.bavarian)
                .into(itemView.imageAvatarMember)
            itemView.nameMember.text = itemData.fullName
            itemView.textNameAction.text = itemData.actionSmallName
            itemView.timeStart.text = itemData.timeStart
            itemView.timeEnd.text = itemData.timeEnd
        }
    }

    override fun onBindViewHolder(holder: UserActionSmallHolder, position: Int) {
        holder.onBindData(getData()[position])
        showMenu(holder.itemView.buttonMore,getData()[position])
    }

    private fun showMenu(
        buttonMore: ImageButton,
        userActionSmallResponse: UserActionSmallResponse
    ) {
        buttonMore.setOnClickListener{
            call.showMenu(buttonMore,userActionSmallResponse)
        }
    }
    interface SendActionSmall{
        fun showMenu(imageButton: ImageButton,userActionSmallResponse: UserActionSmallResponse)
    }

}
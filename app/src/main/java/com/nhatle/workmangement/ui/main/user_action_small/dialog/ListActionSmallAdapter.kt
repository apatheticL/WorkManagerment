package com.nhatle.workmangement.ui.main.user_action_small.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_action_small.view.*

class ListActionSmallAdapter(val call: SendData):
    BaseRecyclerViewAdapter<ActionSmall, ListActionSmallAdapter.ActionSamllHolder>() {
    class ActionSamllHolder(itemView: View, val call:SendData) :BaseViewHolder<ActionSmall>(itemView){
        override fun onBindData(itemData: ActionSmall) {
            super.onBindData(itemData)
            itemView.buttonDelete.visibility = View.GONE
            cofigView(itemData,itemView)
        }

         fun registerClick(itemView: View, itemData: ActionSmall) {
            itemView.setOnClickListener{
                call.sendUserTeam(itemData)
            }
        }

        private fun cofigView(itemData: ActionSmall, itemView: View) {
            itemView.nameActionSmall.text = itemData.description
        }
    }
    interface SendData{
        fun sendUserTeam(data:ActionSmall)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionSamllHolder {
        val la =
            LayoutInflater.from(parent.context).inflate(R.layout.item_action_small,parent,false)
        return ActionSamllHolder(la,call)
    }

    override fun onBindViewHolder(holder: ActionSamllHolder, position: Int) {
        holder.onBindData(getData()[position])
        holder.registerClick(holder.itemView,getData()[position])
    }
}
package com.nhatle.workmangement.ui.main.action.add.actionSmall

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_action_small.view.nameActionSmall

class ActionSmallAdapter(val call :DataActionSmall) :
    BaseRecyclerViewAdapter<ActionSmall, ActionSmallAdapter.ActionSmallHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionSmallHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_add_user_action_small, parent, false)
        return ActionSmallHolder(layout,call,{
            getData().removeAt(it)
            notifyItemRemoved(it)
            notifyItemRangeChanged(it,getData().size)
        })
    }

    class ActionSmallHolder(itemView: View,val call:DataActionSmall,val deleteItem:(Int)->Unit) : BaseViewHolder<ActionSmall>(itemView){
        override fun onBindData(itemData: ActionSmall) {
            super.onBindData(itemData)
            itemView.nameActionSmall.text = itemData.description

        }

        private fun registerListener(
            itemView: View,
            itemData: ActionSmall,
            itemPosition: Int
        ) {
           itemView.setOnClickListener{
               call.sendData(itemData)
               deleteItem(itemPosition)
           }
        }

        override fun onBindData(itemPosition: Int, itemData: ActionSmall) {
            super.onBindData(itemPosition, itemData)
            registerListener(itemView,itemData,itemPosition)
        }



    }
    interface DataActionSmall{
        fun sendData(actionSmall: ActionSmall)
    }
}
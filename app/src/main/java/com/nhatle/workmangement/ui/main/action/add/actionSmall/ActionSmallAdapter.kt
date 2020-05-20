package com.nhatle.workmangement.ui.main.action.add.actionSmall

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_action_small.view.*

class ActionSmallAdapter(val call :DataActionSmall) :
    BaseRecyclerViewAdapter<ActionSmall, ActionSmallAdapter.ActionSmallHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionSmallHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_action_small, parent, false)
        return ActionSmallHolder(layout)
    }

    class ActionSmallHolder(itemView: View) : BaseViewHolder<ActionSmall>(itemView){
        override fun onBindData(itemData: ActionSmall) {
            super.onBindData(itemData)
            itemView.buttonDelete.visibility = View.GONE
            itemView.nameActionSmall.text = itemData.description
        }


    }
    fun deletePosition(position: Int){
        getData().removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,getData().size)
    }

    interface DataActionSmall{
        fun sendData(actionSmall: ActionSmall,position: Int)
    }

    override fun onBindViewHolder(holder: ActionSmallHolder, position: Int) {
        holder.onBindData(getData()[position])
        registerListener(holder.itemView,getData()[position],position)
    }

    fun registerListener(
        itemView: View,
        itemData: ActionSmall,
        itemPosition: Int
    ) {
        itemView.setOnClickListener{
            call.sendData(itemData,itemPosition)
        }
    }
}
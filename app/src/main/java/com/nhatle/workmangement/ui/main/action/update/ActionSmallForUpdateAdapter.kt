package com.nhatle.workmangement.ui.main.action.update

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_action_small.view.*

class ActionSmallForUpdateAdapter(val callback: SendActionSmall) :
    BaseRecyclerViewAdapter<ActionSmall, ActionSmallForUpdateAdapter.ActionSmallHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionSmallHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_action_small, parent, false)
        return ActionSmallHolder(layout)
    }

    override fun onBindViewHolder(holder: ActionSmallHolder, position: Int) {
        holder.onBindData(getData()[position])
        registerItemClick(holder.itemView, getData()[position],position)
        registerButtonClick(holder.itemView.buttonDelete, getData()[position],position)
    }

    private fun registerButtonClick(
        buttonDelete: ImageButton,
        actionSmall: ActionSmall,
        position: Int
    ) {
        buttonDelete.setOnClickListener {
            callback.sendActionIsDelete(actionSmall,position)
        }
    }

    private fun registerItemClick(
        itemView: View,
        actionSmall: ActionSmall,
        position: Int
    ) {
        itemView.setOnClickListener {
            callback.sendActionSmall(actionSmall,position)
        }
    }

    class ActionSmallHolder(itemView: View) : BaseViewHolder<ActionSmall>(itemView) {
        override fun onBindData(itemData: ActionSmall) {
            super.onBindData(itemData)
            configView(itemView, itemData)
        }

        private fun configView(itemView: View, itemData: ActionSmall) {
            itemView.nameActionSmall.text = itemData.description
        }
    }

    fun deleteMember(position: Int){
        getData().removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,getData().size)
    }
    interface SendActionSmall {
        fun sendActionSmall(
            data: ActionSmall,
            position: Int
        )
        fun sendActionIsDelete(actionSmall: ActionSmall, position: Int)
    }
    fun replaceActionSmall(position: Int,actionSmall: ActionSmall){
        getData().set(position,actionSmall)
        notifyDataSetChanged()
    }

}
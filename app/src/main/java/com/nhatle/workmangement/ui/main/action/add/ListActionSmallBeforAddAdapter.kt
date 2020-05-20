package com.nhatle.workmangement.ui.main.action.add

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.until.ActionSmallBefor
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_action_small.view.*

class ListActionSmallBeforAddAdapter:
    BaseRecyclerViewAdapter<ActionSmallBefor, ListActionSmallBeforAddAdapter.ActionBeforeAddHolder>() {
    class ActionBeforeAddHolder(itemView: View, val call:DeleteItem) : BaseViewHolder<ActionSmallBefor>(itemView){
        override fun onBindData(itemData: ActionSmallBefor) {
            super.onBindData(itemData)
            itemView.nameActionSmall.text = itemData.actionSmallName
            itemView.buttonSave.visibility = View.GONE
        }

         fun registerOnclick(buttonDelete: ImageButton, itemPosition: Int) {
            buttonDelete.setOnClickListener{
                call.delete(position = itemPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionBeforeAddHolder {
        val layout = LayoutInflater.from(parent.context).
        inflate(R.layout.item_action_small,parent,false)
        return ActionBeforeAddHolder(layout,
        object :DeleteItem{
            override fun delete(position: Int) {
                getData().removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,getData().size)
            }
        })
    }
    interface DeleteItem{
        fun delete(position: Int)
    }

    override fun onBindViewHolder(holder: ActionBeforeAddHolder, position: Int) {
        holder.onBindData(getData()[position])
        holder.registerOnclick(holder.itemView.buttonDelete,position)
    }

}
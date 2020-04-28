package com.nhatle.workmangement.ui.main.action

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.ActionResponse
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_work.view.*
import java.text.SimpleDateFormat

class ActionAdapter(private val call: SendData) :
    BaseRecyclerViewAdapter<ActionResponse, ActionAdapter.ActionHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_work, parent, false)
        return ActionHolder(layout, call)
    }

    class ActionHolder(itemView: View,val call: SendData) :
        BaseViewHolder<ActionResponse>(itemView) {
        override fun onBindData(itemData: ActionResponse) {
            super.onBindData(itemData)
            initDataHolder(itemView, itemData)
            val current = itemData.numberFinish
            val all = itemData.numberActionMaked
            showSeekBar(itemView.seekBarStatus,current,all)
            listenerButton(itemView.buttonManager,itemData)
        }

        private fun listenerButton(
            buttonManager: ImageButton?,
            itemData: ActionResponse
        ) {
            call.showMenu(buttonManager,itemData)
        }

        private fun showSeekBar(
            current1: SeekBar,
            current: Int,
            all: Int
        ) {
            current1.progress = current
            current1.max = all
        }

        private fun initDataHolder(
            itemData1: View,
            itemData: ActionResponse
        ) {
            itemData1.nameAction.text = itemData.actionName
            itemData1.statusActionSmall.text =
                (itemData.numberFinish).toString() + "/" + itemData.numberActionMaked
            itemData1.timeStart.text = itemData.timeStart
            itemData1.timeEnd.text = itemData.timeEnd
            itemData1.statusDelay.text = itemData.numberDelay.toString()
            itemData1.nameGroup.text = itemData.groupName
            itemData1.nameCreator.text = itemData.nameCreator
        }

        override fun onBindData(itemPosition: Int, itemData: ActionResponse) {
            super.onBindData(itemPosition, itemData)
            call.sendData(itemData,position = itemPosition)

        }
    }

    interface SendData {
        fun sendData(actionResponse: ActionResponse, position: Int)
        fun showMenu(buttonManager: ImageButton?,data:ActionResponse)
    }

}




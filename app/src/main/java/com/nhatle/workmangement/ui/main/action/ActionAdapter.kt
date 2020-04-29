package com.nhatle.workmangement.ui.main.action

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.ActionResponse
import com.nhatle.workmangement.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_work.view.*

class ActionAdapter(private val call: SendData) :
    RecyclerView.Adapter<ActionAdapter.ActionHolder>() {
    private var list :ArrayList<ActionResponse>?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_work, parent, false)
        return ActionHolder(layout)
    }
    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: ActionHolder, position: Int) {
        holder.onBindData(list!!.get(position))
        listenerButton(holder.itemView.buttonManager, list!![position])
        itemViewClick(holder.itemView,list!![position],position)
    }

    private fun listenerButton(buttonManager: ImageButton, actionResponse: ActionResponse) {
        buttonManager.setOnClickListener {
            call.showMenu(buttonManager, actionResponse)
        }
    }

    fun itemViewClick(
        itemView: View,
        actionResponse: ActionResponse,
        position: Int
    ) {
        itemView.setOnClickListener {
            call.sendData(actionResponse,position = position)
        }
    }
    fun setData(list: ArrayList<ActionResponse>) {
        this.list = list
    }
    class ActionHolder(itemView: View) :
        BaseViewHolder<ActionResponse>(itemView) {
        override fun onBindData(itemData: ActionResponse) {
            super.onBindData(itemData)
            initDataHolder(itemView, itemData)
            val current = itemData.numberFinish
            val all = itemData.numberActionMaked
            showSeekBar(itemView.seekBarStatus, current, all)
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
    }

    interface SendData {
        fun sendData(actionResponse: ActionResponse, position: Int)
        fun showMenu(buttonManager: ImageButton,data:ActionResponse)
    }
}




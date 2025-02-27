package com.nhatle.workmangement.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    protected var itemData: T? = null
    protected var itemPosition: Int = -1

    open fun onBindData(itemData: T) {
        this.itemData = itemData
    }
   }
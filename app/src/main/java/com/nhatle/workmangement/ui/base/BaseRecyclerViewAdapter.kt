package com.nhatle.workmangement.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T, V : BaseViewHolder<T>>() :
    RecyclerView.Adapter<V>() {

    private var items = ArrayList<T>()

    fun setData(list: ArrayList<T>) {
        this.items = list
    }
    override fun getItemCount(): Int = items.size

    protected fun getData(): ArrayList<T> {
        return items
    }


}
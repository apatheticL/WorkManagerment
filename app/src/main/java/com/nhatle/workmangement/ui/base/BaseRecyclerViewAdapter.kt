package com.nhatle.workmangement.ui.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T, V : BaseViewHolder<T>> : RecyclerView.Adapter<V>() {

    private var items = ArrayList<T>()

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: V, position: Int) {
        getItem(position)?.let { viewHolder.onBindData(it) }
    }

    protected fun getItem(position: Int): T? =
        if (position in 0 until itemCount) items[position] else null
     fun setData(list: ArrayList<T>){
        this.items = list
    }
}
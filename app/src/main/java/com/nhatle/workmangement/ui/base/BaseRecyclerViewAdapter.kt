package com.nhatle.workmangement.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T, V : BaseViewHolder<T>> :
    RecyclerView.Adapter<V>() {

    private var items = ArrayList<T>()

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: V, position: Int) {
        getItem(position)?.let { viewHolder.onBindData(it) }
        setOnClick(viewHolder, viewHolder.itemView, viewHolder.adapterPosition)
    }
    private fun setOnClick(holder: V, itemView: View, position: Int) {
        itemView.setOnClickListener {
            getItem(position)?.let { holder.onBindData(position, it) }
        }
    }

    protected fun getItem(position: Int): T? =
        if (position in 0 until itemCount) items[position] else null

    fun setData(list: ArrayList<T>) {
        this.items = list
    }
    protected fun getData():ArrayList<T>{
        return items
    }
}
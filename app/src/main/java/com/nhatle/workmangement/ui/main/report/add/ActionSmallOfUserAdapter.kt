package com.nhatle.workmangement.ui.main.report.add

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.ActionSmall

class ActionSmallOfUserAdapter(context: Context, val list: List<ActionSmall>) :
    BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_action_small_of_user, parent, false)
            viewHolder = ItemHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ItemHolder
        }
        viewHolder.nameActionSmall!!.text = list[position].description
        return view
    }

    override fun getItem(position: Int): ActionSmall = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int =list.size

    class ItemHolder(row: View?) {
        val nameActionSmall: TextView? = row?.findViewById(R.id.nameActionSmallOfUser)
    }
}
package com.nhatle.workmangement.ui.main.image

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.until.ItemImage
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder


class ImageAdapter(val call:SendImage) : BaseRecyclerViewAdapter<ItemImage, ImageAdapter.ImageHolder>() {
    class ImageHolder(itemView: View,val call:SendImage) : BaseViewHolder<ItemImage>(itemView) {
        override fun onBindData(itemData: ItemImage) {
            super.onBindData(itemData)
            itemCick(itemView,itemData)
        }

        private fun itemCick(
            itemView: View,
            itemData: ItemImage
        ) {
            itemView.setOnClickListener {
                call.sendData(itemData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageHolder(layoutInflater,call)
    }
}
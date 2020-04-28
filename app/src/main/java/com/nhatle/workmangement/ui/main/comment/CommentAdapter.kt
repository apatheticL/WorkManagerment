package com.nhatle.workmangement.ui.main.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.CommentResponse
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import com.nhatle.workmangement.until.CommonData
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentAdapter(val call: SendComment) :
    BaseRecyclerViewAdapter<CommentResponse, CommentAdapter.CommentHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentHolder(layout, call)
    }

    class CommentHolder(itemView: View, val call: SendComment) :
        BaseViewHolder<CommentResponse>(itemView) {
        override fun onBindData(itemData: CommentResponse) {
            super.onBindData(itemData)
            if (itemData.profileId != CommonData.getInstance().profile!!.profileId){
                itemView.buttonDelete.visibility = View.GONE
            }
            configData(itemView, itemData)
        }

        private fun configData(itemView: View, itemData: CommentResponse) {
            Glide.with(itemView.avatarComment)
                .load(itemData.avatar)
                .placeholder(R.drawable.background)
                .into(itemView.avatarComment)
            itemView.nameComment.text = itemData.fullName

            if (itemData.typeContent == 1) {
                itemView.content.text = itemData.content
                itemView.imageContent.visibility = View.GONE
            }
            if (itemData.typeContent == 2) {
                itemView.content.visibility = View.GONE
                Glide.with(itemView.imageContent)
                    .load(itemData.avatar)
                    .placeholder(R.drawable.background)
                    .into(itemView.imageContent)
            }

        }

        override fun onBindData(itemPosition: Int, itemData: CommentResponse) {
            super.onBindData(itemPosition, itemData)
            registerItem(itemView.buttonDelete, itemData,itemPosition)
        }

        private fun registerItem(
            buttonDelete: ImageButton,
            itemData: CommentResponse,
            itemPosition: Int
        ) {
            buttonDelete.setOnClickListener{
                call.sendData(itemData, itemPosition)
            }
        }
    }
    fun deleteMember(position:Int){
        getData().removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,getData().size)
    }


}
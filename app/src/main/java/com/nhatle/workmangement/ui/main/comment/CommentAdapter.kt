package com.nhatle.workmangement.ui.main.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.CommentResponse
import com.nhatle.workmangement.ui.base.BaseRecyclerViewAdapter
import com.nhatle.workmangement.ui.base.BaseViewHolder
import com.nhatle.workmangement.until.CommonData
import de.hdodenhof.circleimageview.CircleImageView
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
                itemView.setBackgroundResource(R.color.colorWhile)
            }
            itemView.setBackgroundResource(R.color.colorItem)
            if (itemData.type == 1) {
                itemView.imageContent.visibility = View.GONE
                itemView.content.visibility = View.VISIBLE
                itemView.content.text = itemData.content

            }
            if (itemData.type == 2) {
                itemView.content.visibility = View.GONE
                itemView.imageContent.visibility = View.VISIBLE
                Glide.with(itemView.imageContent)
                    .load(itemData.content)
                    .placeholder(R.drawable.background)
                    .into(itemView.imageContent)
            }
            configData(itemView, itemData)
        }

        private fun configData(itemView: View, itemData: CommentResponse) {
            Glide.with(itemView.avatarComment)
                .load(itemData.avatar)
                .placeholder(R.drawable.background)
                .into(itemView.avatarComment)
            itemView.nameComment.text = itemData.fullName



        }
    }
    fun deleteMember(position:Int){
        getData().removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,getData().size)
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        holder.onBindData(getData()[position])
        registerItem(holder.itemView.buttonDelete,getData()[position],position)
    }

    private fun registerItem(
        buttonDelete: ImageButton,
        commentResponse: CommentResponse,
        position: Int
    ) {
        buttonDelete.setOnClickListener{
            call.sendData(commentResponse, position)
        }
    }
}
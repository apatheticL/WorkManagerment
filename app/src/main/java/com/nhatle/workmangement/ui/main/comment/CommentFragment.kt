package com.nhatle.workmangement.ui.main.comment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.Comment
import com.nhatle.workmangement.data.model.response.CommentResponse
import com.nhatle.workmangement.data.reponsitory.remote.CommentRemoteRepository
import com.nhatle.workmangement.data.source.remote.CommentRemoteDataSource
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.ActionFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonAction
import com.nhatle.workmangement.until.CommonData
import com.nhatle.workmangement.until.UploadFile
import kotlinx.android.synthetic.main.fragment_comment.*


class CommentFragment() : BaseFragment(), CommentContract.View {
    private val PICK_IMAGE_RESULT = 2
    private var imageUrl: Uri? = null
    private var checkInsertImage = false
    override val layoutResource: Int
        get() = R.layout.fragment_comment

    private lateinit var presenter: CommentPresenter
    private val adapter: CommentAdapter by lazy {
        CommentAdapter(object : SendComment {
            override fun sendData(data: CommentResponse, position: Int) {
                deleteComment(data, position)
            }
        })
    }

     override  fun initComponents() {
        initPresenter()
        presenter.getAllComment(CommonAction.getInstance().action!!.actionId)
    }

    override fun initData() {

        registerListener()
    }

    private fun registerListener() {
        buttonAddComment.setOnClickListener {
            if (checkInsertImage) {
                val name = "" + System.currentTimeMillis() + "." +
                        (activity as MainActivity).getFileException(imageUrl!!)
                uploadAndInsert(name)
            } else {
                if (contentComment.text.toString() != "") {
                    var content = contentComment.text.toString()
                    val comment = Comment(
                        0,
                        CommonData.getInstance().profile!!.profileId,
                        CommonAction.getInstance().action!!.actionId,
                        CommonAction.getInstance().action!!.groupId,
                        content,
                        1, null
                    )
                    presenter.addComment(
                        comment = comment
                    )
                }
            }
            Common.hideKeyBoard(activity!!)

        }
        buttonGetImage.setOnClickListener {
            openImageFragment()
            checkInsertImage = true
            Common.hideKeyBoard(activity!!)
        }
        buttonBack.setOnClickListener{
            (activity as MainActivity).hindNavigation(false)
            replaceFragment(R.id.frag_main, ActionFragment(),false)
            Common.hideKeyBoard(activity!!)
        }
    }

    private fun uploadAndInsert(name: String) {
        val upLoadFile = UploadFile(imageUrl!!, object : UploadFile.LoadData {
            override fun addOnSuccessListener() {

            }

            override fun addOnFailureListener(string: String) {

            }

            override fun addOnProgressListener(progress: Double) {

            }

            override fun onFailSelect() {

            }

            override fun sendUri(uri: Uri) {
                val content = uri.toString()
                val commonAction = Comment(
                    0,
                    CommonData.getInstance().profile!!.profileId,
                    CommonAction.getInstance().action!!.actionId,
                    CommonAction.getInstance().action!!.groupId,
                    content, 2, null
                )
                presenter.addComment(commonAction)
            }

            override fun sendFaile(string: String) {

            }
        })
        upLoadFile.upLoad(name)
    }

    private fun openImageFragment() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_RESULT)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_RESULT && resultCode == Activity.RESULT_OK &&
            data != null && data.data != null
        ) {
            imageSend.visibility = View.VISIBLE
            contentComment.visibility = View.GONE
            imageUrl = data.data
            Glide.with(imageSend).load(imageUrl)
                .placeholder(R.drawable.bavarian)
                .into(imageSend)
        }
    }


    private fun configRecyclerview() {
        recyclerComment.adapter = adapter
    }

    private fun deleteComment(data: CommentResponse, position: Int) {
        presenter.deleteComment(data.commentId, CommonData.getInstance().profile!!.profileId)
        adapter.deleteMember(position)
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = CommentRemoteDataSource.getInstance(userService)
        val repository = CommentRemoteRepository(dataSource)
        presenter = CommentPresenter(this, repository)
    }


    override fun getAllComment(list: List<CommentResponse>) {
        if (list.isEmpty()) {
            titleError.visibility = View.VISIBLE
            titleError.text = "không có dữ liệu"
        } else {
            titleError.visibility = View.GONE
            recyclerComment.visibility= View.VISIBLE
            adapter.setData(list = list as ArrayList<CommentResponse>)
            configRecyclerview()
        }
    }

    override fun deleteSuccess() {

    }

    override fun onFail(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    override fun insertSuccess() {
        presenter.getAllComment(CommonAction.getInstance().action!!.actionId)
        contentComment.setText("")
        imageSend.visibility = View.GONE
    }
}

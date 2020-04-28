package com.nhatle.workmangement.ui.main.comment

import android.widget.Toast
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.CommentResponse
import com.nhatle.workmangement.data.reponsitory.remote.CommentRemoteRepository
import com.nhatle.workmangement.data.source.remote.CommentRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.until.Common

class CommentFragment(val actionId: Int,val  groupId: Int) : BaseFragment(), CommentContract.View {
    override val layoutResource: Int
        get() = R.layout.fragment_comment
    private lateinit var presenter:CommentPresenter
    private lateinit var adapter: CommentAdapter
    override fun initData() {
      configRecyclerview()
    }

    private fun configRecyclerview() {
        adapter = CommentAdapter(object :SendComment{
            override fun sendData(data: CommentResponse, position: Int) {
                deleteComment(data,position)
            }
        })
    }

    private fun deleteComment(data: CommentResponse, position: Int) {

    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource  = CommentRemoteDataSource.getInstance(userService)
        val repository = CommentRemoteRepository(dataSource)
        presenter = CommentPresenter(this,repository)
    }

    override fun initComponents() {
        initPresenter()
        presenter.getAllComment(actionId)
    }

    override fun getAllComment(list: List<CommentResponse>) {
        adapter.setData(list = list as ArrayList<CommentResponse>)
    }

    override fun deleteSuccess() {

    }

    override fun onFail(string: String) {

    }

    override fun insertSuccess() {

    }

}

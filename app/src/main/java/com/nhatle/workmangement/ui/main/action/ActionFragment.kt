package com.nhatle.workmangement.ui.main.action

import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.ActionResponse
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.source.remote.ActionRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.add.AddActionFragment
import com.nhatle.workmangement.ui.main.action.detail.ActionDetailFragment
import com.nhatle.workmangement.ui.main.action.update.UpdateActionFragment
import com.nhatle.workmangement.ui.main.comment.CommentFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonData
import com.nhatle.workmangement.until.api.UserService
import kotlinx.android.synthetic.main.fragment_work.*

class ActionFragment : BaseFragment(), ActionContract.View, ActionAdapter.SendData {
    override val layoutResource: Int = R.layout.fragment_work
    private var presenter: ActionPresenter? = null
    private var adapter: ActionAdapter? = null
    override fun initData() {

        registerListener()
    }


    private fun handlerGetAllAction() {
        presenter!!.getAllActionIsMember(CommonData.getInstance().profile!!.profileId)
    }

    override fun initComponents() {
        initPresenter()
        handlerGetAllAction()
    }

    private fun registerListener() {
        buttonAddWork.setOnClickListener {
            addFragment(R.id.frag_main, AddActionFragment(), false)
        }
    }

    private fun initAdapter() {

        recyclerViewWork.adapter = adapter
    }

    private fun initPresenter() {
        val userService =Common.getUserService()
        val dataSource = ActionRemoteDataSource.getInstance(userService)
        val repository = ActionRemoteRepository(dataSource = dataSource)
        presenter = ActionPresenter(this, repository = repository)
    }

    override fun loadAllActionByUserMember(listAction: ArrayList<ActionResponse>) {
        textError.text = ""
        textError.visibility = View.GONE
        adapter = ActionAdapter(this)
        adapter?.setData(listAction)
        initAdapter()
    }

    override fun loadFailed(error: String) {
        textError.visibility = View.VISIBLE
        textError.text = error
    }

    override fun loadData() {

    }


    override fun sendData(actionResponse: ActionResponse, position: Int) {
        val fragment = ActionDetailFragment()
        addFragment(
            R.id.frag_main,
            ActionDetailFragment(), false
        )
        fragment.sendData(actionResponse)
    }

    override fun showMenu(buttonManager: ImageButton?, data: ActionResponse) {
        val popupMenu = PopupMenu(activity, buttonManager)
        popupMenu.inflate(R.menu.menu_mode)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.actionComment -> {
                    addFragment(R.id.frag_main, CommentFragment(data.actionId, data.groupId), false)
                }
                R.id.actionDelete -> {
                    if (CommonData.getInstance().profile!!.profileId == data.creatorId) {
                        presenter!!.deleteAction(data.actionId, data.creatorId)
                    }
                    Toast.makeText(
                        context,
                        "Bạn không thể thục hiện chức năng này",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.actionUpdate->{
                    if (CommonData.getInstance().profile!!.profileId == data.creatorId) {
                        val fragment = UpdateActionFragment()
                        fragment.sendData(data)
                        addFragment(R.id.frag_main,fragment,false)
                    }
                    Toast.makeText(
                        context,
                        "Bạn không thể thục hiện chức năng này",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            true
        }

    }


}
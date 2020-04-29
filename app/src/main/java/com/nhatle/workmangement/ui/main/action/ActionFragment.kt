package com.nhatle.workmangement.ui.main.action

import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.ActionResponse
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.source.remote.ActionRemoteDataSource
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.add.AddActionFragment
import com.nhatle.workmangement.ui.main.action.detail.ActionDetailFragment
import com.nhatle.workmangement.ui.main.action.update.UpdateActionFragment
import com.nhatle.workmangement.ui.main.comment.CommentFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonData
import kotlinx.android.synthetic.main.fragment_work.*

class ActionFragment : BaseFragment(), ActionContract.View {
    override val layoutResource: Int = R.layout.fragment_work
    private var presenter: ActionPresenter?=null
    private val adapter: ActionAdapter by lazy {
        ActionAdapter(object : ActionAdapter.SendData{
            override fun sendData(actionResponse: ActionResponse, position: Int) {
               sendDataType(actionResponse)

            }

            override fun showMenu(buttonManager: ImageButton, data: ActionResponse) {
               showMenuPopu(buttonManager,data)
            }

        })
    }
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
            replaceFragment(R.id.frag_main, AddActionFragment(), false)
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
        adapter.setData(listAction)
        initAdapter()
    }

    override fun loadFailed(error: String) {

    }

    override fun loadData() {

    }

    fun sendDataType(actionResponse:ActionResponse){
        (activity as MainActivity).hindNavigation(true)
        val fragment = ActionDetailFragment()
        fragment.sendData(actionResponse)

        replaceFragment(
            R.id.frag_image,
            fragment, false
        )
    }
     fun showMenuPopu(buttonManager: ImageButton?, data: ActionResponse) {
        val popupMenu = PopupMenu(activity, buttonManager)
        popupMenu.inflate(R.menu.menu_mode)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.actionComment -> {
                    (activity as MainActivity).hindNavigation(true)
                    replaceFragment(R.id.frag_image,
                        CommentFragment(data.actionId, data.groupId),true)
                }
                R.id.actionDeleteAction -> {
                    if (CommonData.getInstance().profile!!.profileId == data.creatorId) {
                        presenter!!.deleteAction(data.actionId, data.creatorId)
                    }
                    Toast.makeText(
                        context,
                        "Bạn không thể thục hiện chức năng này",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.actionUpdateAction->{
                    if (CommonData.getInstance().profile!!.profileId == data.creatorId) {
                        (activity as MainActivity).hindNavigation(true)
                        val fragment = UpdateActionFragment()
                        fragment.sendData(data)
                        replaceFragment(R.id.frag_image,fragment,true)
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
         popupMenu.show()
    }


}
package com.nhatle.workmangement.ui.main.action

import android.content.Context
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.ActionResponse
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.TeamRemoteRepository
import com.nhatle.workmangement.data.source.remote.ActionRemoteDataSource
import com.nhatle.workmangement.data.source.remote.TeamRemoteDataSource
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.add.AddActionFragment
import com.nhatle.workmangement.ui.main.action.detail.ActionDetailFragment
import com.nhatle.workmangement.ui.main.action.update.UpdateActionFragment
import com.nhatle.workmangement.ui.main.comment.CommentFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonAction
import com.nhatle.workmangement.until.CommonData
import kotlinx.android.synthetic.main.fragment_work.*
import kotlinx.android.synthetic.main.item_comment.*

class ActionFragment : BaseFragment(), ActionContract.View {
    override val layoutResource: Int = R.layout.fragment_work
    private var presenter: ActionPresenter? = null
    private var recyclerView: RecyclerView? = null
    private var textError: TextView? = null
    private val adapter: ActionAdapter by lazy {
        ActionAdapter(object : ActionAdapter.SendData {
            override fun sendData(actionResponse: ActionResponse, position: Int) {
                sendDataType(actionResponse)
                CommonAction.getInstance().action = actionResponse
            }

            override fun showMenu(buttonManager: ImageButton, data: ActionResponse) {
                showMenuPopu(buttonManager, data)
            }

            override fun getContext(): Context {
                return context!!
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
        recyclerView = recyclerViewWork
        textError = textErrorAction
        initPresenter()
        handlerGetAllAction()
    }

    private fun registerListener() {
        buttonAddWork.setOnClickListener {
            (activity as MainActivity).hindNavigation(true)
            replaceFragment(R.id.frag_main, AddActionFragment(), true)
        }
    }

    private fun initAdapter() {
        recyclerView!!.adapter = adapter
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = ActionRemoteDataSource.getInstance(userService)
        val teamDataSource = TeamRemoteDataSource.getInstance(userService)
        val teamRepository = TeamRemoteRepository(teamDataSource)
        val repository = ActionRemoteRepository(dataSource = dataSource)
        presenter = ActionPresenter(this, repository = repository, teamRepository = teamRepository)
    }

    override fun loadAllActionByUserMember(listAction: ArrayList<ActionResponse>) {
        if (listAction.size != 0) {
            adapter.setData(listAction)
            initAdapter()
            textError!!.visibility = View.GONE
        }
        if(listAction.size == 0) {
            textError!!.visibility = View.VISIBLE
            textError!!.text = "Hiện tại chưa có công việc"
        }
    }

    override fun loadFailed(error: String) {
        textError!!.text = error
        Toast.makeText(context!!, error, Toast.LENGTH_SHORT).show()
    }

    override fun deleteSuccess() {
        Toast.makeText(context!!, "Delete success", Toast.LENGTH_SHORT).show()
        presenter!!.getAllActionIsMember(CommonData.getInstance().profile!!.profileId)
    }

    override fun loadData() {
        Toast.makeText(context!!, "is success", Toast.LENGTH_SHORT).show()

    }

    fun sendDataType(actionResponse: ActionResponse) {
        (activity as MainActivity).hindNavigation(true)
        val fragment = ActionDetailFragment()
        CommonAction.getInstance().action = actionResponse
        replaceFragment(
            R.id.frag_main,
            fragment, true
        )
    }

    fun showMenuPopu(buttonManager: ImageButton?, data: ActionResponse) {
        val popupMenu = PopupMenu(activity, buttonManager)
        popupMenu.inflate(R.menu.menu_mode)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.actionComment -> {
                    CommonAction.getInstance().action = data

                    (activity as MainActivity).hindNavigation(true)
                    replaceFragment(
                        R.id.frag_main,
                        CommentFragment(), true
                    )
                }
                R.id.actionDeleteAction -> {
                    if (CommonData.getInstance().profile!!.profileId == data.creatorId) {
                        presenter!!.deleteGroup(data.groupId)
                        presenter!!.deleteAction(data.actionId, data.creatorId)

                        handlerGetAllAction()
                    } else {
                        Toast.makeText(
                            context,
                            "Bạn không thể thục hiện chức năng này",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
                R.id.actionUpdateAction -> {
                    if (CommonData.getInstance().profile!!.profileId == data.creatorId) {
                        (activity as MainActivity).hindNavigation(true)
                        val fragment = UpdateActionFragment()
                        CommonAction.getInstance().action = data
                        fragment.sendData(data)
                        replaceFragment(R.id.frag_main, fragment, false)
                    } else {
                        Toast.makeText(
                            context,
                            "Bạn không thể thục hiện chức năng này",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            true
        }
        popupMenu.show()
    }


}
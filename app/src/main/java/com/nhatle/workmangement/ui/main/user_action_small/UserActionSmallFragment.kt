package com.nhatle.workmangement.ui.main.user_action_small

import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.UserActionSmallResponse
import com.nhatle.workmangement.data.reponsitory.remote.UserActionSmallRepository
import com.nhatle.workmangement.data.source.remote.UserActionSmallRemoteDataSource
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.add.actionSmall.AddUserActionSmallFragment
import com.nhatle.workmangement.ui.main.action.detail.ActionDetailFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonAction
import com.nhatle.workmangement.until.CommonData
import com.nhatle.workmangement.until.Deferent
import kotlinx.android.synthetic.main.custom_add_user_action_small.*
import kotlinx.android.synthetic.main.fragment_user_action_small.*

class UserActionSmallFragment() : BaseFragment(),
    UserActionSmallContract.View {
    private var number = -1
    override val layoutResource: Int = R.layout.fragment_user_action_small
    private var presenter: UserActionSmallPresenter? = null
    private val userActionSmallAdapter: UserActionSmallAdapter by lazy {
        UserActionSmallAdapter(
            CommonAction.getInstance().action!!.creatorId
            , object : UserActionSmallAdapter.SendActionSmall {
                override fun showMenu(
                    imageButton: ImageButton,
                    userActionSmallResponse: UserActionSmallResponse
                ) {
                    showMenuDelete(imageButton, userActionSmallResponse)
                }

            }
        )
    }

    private fun showMenuDelete(
        imageButton: ImageButton,
        userActionSmallResponse: UserActionSmallResponse
    ) {
        val popupMenu = PopupMenu(context!!, imageButton)
        popupMenu.inflate(R.menu.menu_action_small)
        popupMenu.setOnMenuItemClickListener {
            if (it.itemId == R.id.actionDeleteActionSmall) {
                deleteUserActionSmall(userActionSmallResponse)
            }
            true
        }
        popupMenu.show()
    }

    private fun deleteUserActionSmall(userActionSmallResponse: UserActionSmallResponse) {
        presenter!!.deleteUserActionSmall(userActionSmallResponse.userActionSmallId)
    }

    private var check = false

    override fun initData() {
        configView()

    }

    private fun initShowListUserActionSmall() {
        presenter!!.getAllUserActionSmallByAction(actionId = CommonAction.getInstance().action!!.actionId)
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource =
            UserActionSmallRemoteDataSource.getInstance(userService = userService)
        val repository = UserActionSmallRepository(dataSource = dataSource)
        presenter = UserActionSmallPresenter(
            this, repository = repository
        )

    }

    override fun initComponents() {
        initPresenter()
        initShowListUserActionSmall()
    }

    private fun configView() {
        if (CommonAction.getInstance().action!!.creatorId == CommonData.getInstance().profile!!.profileId &&
            !check
        ) {
            addActionSmall.visibility = View.VISIBLE
            buttonGoAddUserActionSmall.setOnClickListener {
                (activity as MainActivity).hindNavigation(true)
                replaceFragment(
                    R.id.frag_main, AddUserActionSmallFragment(
                        CommonAction.getInstance().action!!.actionId,
                        CommonAction.getInstance().action!!.groupId,
                        CommonAction.getInstance().action!!.timeEnd,
                        CommonAction.getInstance().action!!.timeStart
                    ), true
                )
            }
        } else if (CommonAction.getInstance().action!!.creatorId == CommonData.getInstance().profile!!.profileId &&
            check && Deferent.getNumber() > number - 1
        ) {
            addActionSmall.visibility = View.VISIBLE
            recyclerListUserMakeActionSmall.visibility = View.VISIBLE
            buttonGoAddUserActionSmall.setOnClickListener {
                (activity as MainActivity).hindNavigation(true)
                replaceFragment(
                    R.id.frag_main, AddUserActionSmallFragment(
                        CommonAction.getInstance().action!!.actionId,
                        CommonAction.getInstance().action!!.groupId,
                        CommonAction.getInstance().action!!.timeEnd,
                        CommonAction.getInstance().action!!.timeStart
                    ), true
                )
            }
        } else {
            recyclerListUserMakeActionSmall.visibility = View.VISIBLE
            addActionSmall.visibility = View.GONE
        }
    }


    private fun recycleListUserAactionSmall() {
        recyclerListUserMakeActionSmall.adapter = userActionSmallAdapter
    }

    override fun loadAllActionSmall(listAction: List<UserActionSmallResponse>) {

        if (listAction.isEmpty()) {
            check = false
            textError.visibility = View.VISIBLE
            textError.text = "Không có dữ liệu"
        } else {
            check = true
            textError.visibility = View.GONE
            userActionSmallAdapter.setData(list = listAction as ArrayList<UserActionSmallResponse>)
            recycleListUserAactionSmall()
            number = listAction.size
        }
    }

    override fun loadFailed(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    override fun deleteSuccess() {
        initShowListUserActionSmall()
        Toast.makeText(context, "is succes", Toast.LENGTH_SHORT).show()
    }

    override fun deleteFail(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
        view!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event!!.action== KeyEvent.ACTION_UP&& keyCode== KeyEvent.KEYCODE_BACK){
                    replaceFragment(R.id.frag_main, ActionDetailFragment(),false)
                    (activity as MainActivity).hindNavigation(true)
                }
                return false
            }

        })
    }
}
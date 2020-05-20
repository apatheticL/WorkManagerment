package com.nhatle.workmangement.ui.main.friend.add

import android.view.View
import android.widget.Toast
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.InvitationFriend
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.reponsitory.remote.FriendRepository
import com.nhatle.workmangement.data.source.remote.FriendRemoteDataSource
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.friend.SendDataProfile
import com.nhatle.workmangement.ui.main.profile.UserProfileFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonData
import kotlinx.android.synthetic.main.fragment_add_friend.*

class ReceiverFragment : BaseFragment(), ReceiverContract.View {
    override val layoutResource: Int
        get() = R.layout.fragment_add_friend
    private var presenter: ReceiverPresenter? = null
    private val adapter: ListUserNotFriendAdapter by lazy {
        ListUserNotFriendAdapter(object : SendDataProfile.FriendAdd {
            override fun sendDataToAdd(data: UserProfile) {
                val invitationFriend =
                    InvitationFriend(
                        0, CommonData.getInstance().profile!!.profileId,
                        data.profileId, 0, null
                    )
                presenter!!.sendInvitationFriend(invitationFriend)
            }

            override fun sendDataToDeleteAdd(position: Int, data: UserProfile) {
                presenter!!.deleteReceiver(
                    CommonData.getInstance().profile!!.profileId,
                    data.profileId
                )
                removeDataOnRecyclerView(position +1)
            }

            override fun deleteItem(position: Int) {
                removeDataOnRecyclerView(position)
            }

            override fun showNotification(string: String) {
                Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
            }

            override fun sendData(id: Int) {
                val fragment = UserProfileFragment()
                fragment.setData(id)
                (activity as MainActivity).hindNavigation(false)
                replaceFragment(R.id.frag_main,fragment,false)
            }
        })
    }

    override fun initData() {
        registerListener()
    }

    private fun registerListener() {
        buttonSearch.setOnClickListener {
            Toast.makeText(context, "Search not support", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = FriendRemoteDataSource.getInStance(userService)
        val repository = FriendRepository(dataSource)
        presenter = ReceiverPresenter(this, repository)
    }

    override fun initComponents() {
        initPresenter()
        presenter!!.getAllUserNotFriend(CommonData.getInstance().profile!!.profileId)
    }

    private fun initRecyclerView() {
        recyclerAddFriend.adapter = adapter
    }

    override fun showAllUserNotFriend(listUser: List<UserProfile>) {
        if (listUser.isNotEmpty()) {
            adapter.setData(list = listUser as ArrayList<UserProfile>)
            initRecyclerView()
            textErrorSenderFriend.visibility = View.GONE
        }
        textErrorSenderFriend.visibility = View.VISIBLE
        textErrorSenderFriend.text = "Không có dữ liệu"
    }

    override fun onFailLoad(string: String) {
        textErrorSenderFriend.text =string
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    override fun sendInvitationSuccess(profileId: Int) {

    }

    override fun sendSuccess() {

    }

    private fun removeDataOnRecyclerView(position: Int) {
        adapter.deleteItem(position)
    }

}

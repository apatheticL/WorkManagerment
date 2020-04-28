package com.nhatle.workmangement.ui.main.friend.add

import android.widget.Toast
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.InvitationFriend
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.reponsitory.remote.FriendRepository
import com.nhatle.workmangement.data.source.remote.FriendRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.friend.SendDataProfile
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonData

class ReceiverFragment : BaseFragment(), ReceiverContract.View {
    override val layoutResource: Int
        get() = R.layout.fragment_add_friend
    private var presenter :ReceiverPresenter?=null
    private var adapter :ListUserNotFriendAdapter?=null
    override fun initData() {
       initPresenter()
        presenter!!.getAllUserNotFriend(CommonData.getInstance().profile!!.profileId)
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = FriendRemoteDataSource.getInStance(userService)
        val repository = FriendRepository(dataSource)
        presenter = ReceiverPresenter(this,repository)

    }

    override fun initComponents() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = ListUserNotFriendAdapter(object :SendDataProfile.FriendAdd{
            override fun sendDataToAdd(data: UserProfile) {
                val invitationFriend =
                    InvitationFriend(0,CommonData.getInstance().profile!!.profileId,
                        data.profileId,0,null)
                presenter!!.sendInvitationFriend(invitationFriend)
            }

            override fun sendDataToDeleteAdd(position: Int, data: UserProfile) {
                presenter!!.deleteReceiver(CommonData.getInstance().profile!!.profileId,
                    data.profileId)
                deleteItem(position)
            }

            override fun deleteItem(position: Int) {
                deleteItem(position)
            }

        })
    }

    override fun showAllUserNotFriend(listUser: List<UserProfile>) {
        adapter!!.setData(list = listUser as ArrayList<UserProfile>)
    }

    override fun onFailLoad(string: String) {
        Toast.makeText(context,string,Toast.LENGTH_SHORT).show()
    }

    override fun sendInvitationSuccess(profileId: Int) {

    }

    override fun sendSuccess() {

    }

    private fun removeDataOnRecyclerView(position: Int) {
        adapter!!.deleteItem(position)
    }

}

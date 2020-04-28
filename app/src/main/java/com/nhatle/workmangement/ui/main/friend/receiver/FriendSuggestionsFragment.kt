package com.nhatle.workmangement.ui.main.friend.receiver

import android.widget.Toast
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.InvitationFriend
import com.nhatle.workmangement.data.model.response.FriendResponse
import com.nhatle.workmangement.data.reponsitory.remote.FriendRepository
import com.nhatle.workmangement.data.source.remote.FriendRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.friend.SendDataProfile
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonData

class FriendSuggestionsFragment : BaseFragment(), FriendSuggestionsContract.View {
    override val layoutResource: Int
        get() = R.layout.fragment_friend_suggestions
    private var presenter:FriendSuggestionsPresenter?=null
    private var adapter :FriendSuggestionsAdapter?=null
    override fun initData() {
        initPresenter()
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = FriendRemoteDataSource.getInStance(userService)
        val repository= FriendRepository(dataSource)
        presenter = FriendSuggestionsPresenter(this,repository)
    }

    override fun initComponents() {
        configRecyclerView()
    }

    private fun configRecyclerView() {
        adapter = FriendSuggestionsAdapter(object :SendDataProfile.ReceiverFriend{
            override fun sendDataToAccept(position: Int, data: FriendResponse) {
                removeDataOnRecyclerView(position)
                insertAccept(data)
            }

            override fun sendDataToDeleteInvitation(position: Int, data: FriendResponse) {
                removeDataOnRecyclerView(position)
                deleteInvitation(data)
            }
        })
    }

    private fun deleteInvitation(data: FriendResponse) {
        presenter!!.deleteReceiver(data.friendId)
    }

    private fun insertAccept(data: FriendResponse) {
        val invitationFriend = InvitationFriend(0,data.friendId,
            CommonData.getInstance().profile!!.profileId,1,null)
        presenter!!.sendAcceptInvitationFriend(invitationFriend)
    }

    private fun removeDataOnRecyclerView(position: Int) {
        adapter!!.deleteItem(position)
    }

    override fun showListReceiver(listFriend: List<FriendResponse>) {
        adapter!!.setData(listFriend as ArrayList<FriendResponse>)
    }

    override fun acceptSuccess() {
        Toast.makeText(context,"is success",Toast.LENGTH_SHORT).show()
    }

    override fun deleteSuccess() {
        Toast.makeText(context,"is success",Toast.LENGTH_SHORT).show()
    }

    override fun onFail(string: String) {
        Toast.makeText(context,string,Toast.LENGTH_SHORT).show()
    }

}

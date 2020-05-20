package com.nhatle.workmangement.ui.main.friend.receiver

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.InvitationFriend
import com.nhatle.workmangement.data.model.response.FriendResponse
import com.nhatle.workmangement.data.reponsitory.remote.FriendRepository
import com.nhatle.workmangement.data.source.remote.FriendRemoteDataSource
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.friend.SendDataProfile
import com.nhatle.workmangement.ui.main.profile.UserProfileFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonData
import kotlinx.android.synthetic.main.fragment_friend_suggestions.*

class FriendSuggestionsFragment : BaseFragment(), FriendSuggestionsContract.View {
    override val layoutResource: Int
        get() = R.layout.fragment_friend_suggestions
    private var presenter: FriendSuggestionsPresenter? = null
    private var recyclerView: RecyclerView? = null
    private val adapter: FriendSuggestionsAdapter by lazy {
        FriendSuggestionsAdapter(object : SendDataProfile.ReceiverFriend {
            override fun sendData(id: Int) {
                val fragment = UserProfileFragment()
                fragment.setData(id)
                (activity as MainActivity).hindNavigation(false)
                replaceFragment(R.id.frag_main, fragment, false)
            }

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

    override fun initData() {

    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = FriendRemoteDataSource.getInStance(userService)
        val repository = FriendRepository(dataSource)
        presenter = FriendSuggestionsPresenter(this, repository)
    }

    override fun initComponents() {
        recyclerView = recyclerListSuggestion
        initPresenter()
        getAllData()
    }

    private fun getAllData() {
        presenter!!.getAllReceiver(CommonData.getInstance().profile!!.profileId)
    }

    private fun configRecyclerView() {
        recyclerView!!.adapter = adapter
    }

    private fun deleteInvitation(data: FriendResponse) {
        presenter!!.deleteReceiver(data.id)
    }

    private fun insertAccept(data: FriendResponse) {
        val invitationFriend = InvitationFriend(
            data.id, data.friendId,
            CommonData.getInstance().profile!!.profileId, 1, null
        )
        presenter!!.sendAcceptInvitationFriend(invitationFriend)
    }

    private fun removeDataOnRecyclerView(position: Int) {
        adapter.deleteItem(position)
    }

    override fun showListReceiver(listFriend: List<FriendResponse>) {
        if (listFriend.isNotEmpty()) {
            adapter.setData(listFriend as ArrayList<FriendResponse>)
            configRecyclerView()
            textErrorSuggestionFriend.visibility = View.GONE
        }
        if (listFriend.isEmpty()) {
            textErrorSuggestionFriend.visibility = View.VISIBLE
            textErrorSuggestionFriend.text = "Không có lời mời"
        }
    }

    override fun acceptSuccess() {
        Toast.makeText(context, "is success", Toast.LENGTH_SHORT).show()
    }

    override fun deleteSuccess() {
        Toast.makeText(context, "is success", Toast.LENGTH_SHORT).show()
    }

    override fun onFail(string: String) {
        textErrorSuggestionFriend.text = string
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

}

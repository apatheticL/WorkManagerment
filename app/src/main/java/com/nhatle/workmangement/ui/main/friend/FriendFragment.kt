package com.nhatle.workmangement.ui.main.friend

import android.widget.Toast
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.response.FriendResponse
import com.nhatle.workmangement.data.reponsitory.remote.FriendRepository
import com.nhatle.workmangement.data.source.remote.FriendRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.profile.UserProfileFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonData
import kotlinx.android.synthetic.main.fragment_friend.*

class FriendFragment : BaseFragment(), FriendContract.View {
    override val layoutResource: Int
        get() = R.layout.fragment_friend
    private var presenter:FriendPresenter?=null
    private var adapter:FriendAdapter? = null
    override fun initData() {
        initPresenter()
        getAllData()
    }

    override fun initComponents() {
        configRecyclerView()
    }

    private fun configRecyclerView() {
        adapter = FriendAdapter(object :SendDataProfile.Friend{
            override fun sendData(data: FriendResponse) {
                val fragment = UserProfileFragment()
                fragment.setData(data)
            }
        })
        recyclerListFriend.adapter = adapter
    }

    private fun initPresenter() {
        var userService = Common.getUserService()
        val dataSource = FriendRemoteDataSource.getInStance(userService)
        val repository = FriendRepository(dataSource)
        presenter = FriendPresenter(this,repository)
    }
    private fun getAllData(){
        presenter?.getAllFriend(CommonData.getInstance().profile!!.profileId)
    }

    override fun showAllFriendOfUser(listFriend: List<FriendResponse>) {
        adapter!!.setData(listFriend as ArrayList<FriendResponse>)
    }

    override fun loadFail(string: String) {
        Toast.makeText(context,string,Toast.LENGTH_SHORT).show()
    }

}

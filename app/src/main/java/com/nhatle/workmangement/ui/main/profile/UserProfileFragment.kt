package com.nhatle.workmangement.ui.main.profile

import com.nhatle.workmangement.data.model.response.FriendResponse
import com.nhatle.workmangement.ui.base.BaseFragment

class UserProfileFragment :BaseFragment(){
    override val layoutResource: Int
        get() = TODO("Not yet implemented")
    private var friendResponse:FriendResponse?=null
    override fun initData() {
        TODO("Not yet implemented")
    }

    override fun initComponents() {
        TODO("Not yet implemented")
    }

    fun setData(data: FriendResponse) {
        this.friendResponse=data
    }

}

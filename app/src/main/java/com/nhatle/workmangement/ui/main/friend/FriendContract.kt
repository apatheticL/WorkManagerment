package com.nhatle.workmangement.ui.main.friend

import com.nhatle.workmangement.data.model.response.FriendResponse

interface FriendContract {
    interface View {
        fun showAllFriendOfUser(listFriend: List<FriendResponse>)
        fun loadFail(string :String)
    }
    interface Presenter{
        fun getAllFriend(profileId:Int)
    }
}
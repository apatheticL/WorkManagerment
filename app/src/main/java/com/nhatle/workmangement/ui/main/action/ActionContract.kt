package com.nhatle.workmangement.ui.main.action

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.response.ActionResponse
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import java.lang.Exception

interface ActionContract {

    interface View{
        fun loadAllActionByUserMember(listAction:ArrayList<ActionResponse>)
        fun loadFailed(error:String)
    }
    interface Presenter{
        fun getAllActionIsMember(profileId: Int)

    }
}
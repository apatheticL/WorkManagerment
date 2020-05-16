package com.nhatle.workmangement.ui.main.action.add.actionSmall.dialog

import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.model.UserTeam
import com.nhatle.workmangement.data.model.response.UserTeamResponse

interface SendUserTeam {
    fun sendUserTeam(actionSmall :UserTeamResponse)
}
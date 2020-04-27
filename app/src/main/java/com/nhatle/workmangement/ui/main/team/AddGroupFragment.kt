package com.nhatle.workmangement.ui.main.team

import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.Team
import com.nhatle.workmangement.data.model.UserTeam
import com.nhatle.workmangement.data.reponsitory.remote.TeamRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserTeamRepository
import com.nhatle.workmangement.data.source.remote.TeamRemoteDataSource
import com.nhatle.workmangement.data.source.remote.UserTeamRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.until.Common

class AddGroupFragment:BaseFragment(), AddGroupContract.View {
    override val layoutResource: Int
        get() = R.layout.fragment_add_user_group
    private var presenter: AddGroupPresenter?=null

    override fun initData() {
        initPresenter()
    }

    private fun initPresenter() {
        val userService  = Common.getUserService()
        val teamDataSource = TeamRemoteDataSource.getInstance(userService)
        val userTeamDataSource = UserTeamRemoteDataSource.getInstance(userService)
        val groupRepository = TeamRemoteRepository(teamDataSource)
        val userGroupRepository = UserTeamRepository(userTeamDataSource)
        presenter = AddGroupPresenter(this,groupRepository,userGroupRepository)
    }

    override fun initComponents() {
    }

    override fun insertGroupSuccess(team: Team) {

    }

    override fun insertUserGroupSuccess(list: List<UserTeam>) {
        TODO("Not yet implemented")
    }

    override fun onFail(string: String) {
        TODO("Not yet implemented")
    }
}
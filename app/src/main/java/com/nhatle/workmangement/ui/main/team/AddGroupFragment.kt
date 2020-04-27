package com.nhatle.workmangement.ui.main.team

import android.view.View
import android.widget.Toast
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.Team
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.model.UserTeam
import com.nhatle.workmangement.data.reponsitory.remote.TeamRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserTeamRepository
import com.nhatle.workmangement.data.source.remote.TeamRemoteDataSource
import com.nhatle.workmangement.data.source.remote.UserTeamRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.add.AddActionFragment
import com.nhatle.workmangement.until.Common
import kotlinx.android.synthetic.main.fragment_add_user_group.*

class AddGroupFragment:BaseFragment(), AddGroupContract.View, View.OnClickListener {
    override val layoutResource: Int
        get() = R.layout.fragment_add_user_group
    private var presenter: AddGroupPresenter?=null
    private var group :Team?=null
    private var adater: AddUserTeamAdapter?=null
    private var listMember :MutableList<UserTeam>? = null
    override fun initData() {
        initPresenter()
        initRecycleView()
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
        registerListener()

    }

    private fun initRecycleView(){
        adater = AddUserTeamAdapter(object :AddUserTeamAdapter.SendFriend{
            override fun sendData(userProfile: UserProfile) {
                listMember?.add(UserTeam(group!!.groupId,userProfile.profileId))
            }
        })
    }
    private fun registerListener() {
        buttonAddGroup.setOnClickListener(this)
        buttonCancelGroup.setOnClickListener(this)
    }

    override fun insertGroupSuccess(team: Team) {
        this.group = team
    }

    override fun insertUserGroupSuccess(list: List<UserTeam>) {
        val fragment =AddActionFragment()
       addFragment(R.id.frag_main,fragment,false)
        fragment.sendGroupId(group!!.groupId)
    }

    override fun onFail(string: String) {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.buttonAddGroup->{
                checkAndInsertGroup()
            }
            R.id.buttonCancelGroup->{
                showDialog()
            }
        }
    }

    private fun showDialog() {

    }

    private fun checkAndInsertGroup() {
        if (editNameGroup.text.toString().isEmpty()){
            Toast.makeText(context,"Tên giroup không được để trống",Toast.LENGTH_SHORT).show()
        }
        val team = Team(0,editNameGroup.text.toString(),null)
        presenter?.insertGroup(team)
        listMember?.let { presenter?.insertUserGroup(it) }
    }
}
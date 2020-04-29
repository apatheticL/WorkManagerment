package com.nhatle.workmangement.ui.main.team

import android.view.View
import android.widget.Toast
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.Team
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.model.UserTeam
import com.nhatle.workmangement.data.model.response.FriendResponse
import com.nhatle.workmangement.data.reponsitory.remote.FriendRepository
import com.nhatle.workmangement.data.reponsitory.remote.TeamRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserTeamRepository
import com.nhatle.workmangement.data.source.remote.FriendRemoteDataSource
import com.nhatle.workmangement.data.source.remote.TeamRemoteDataSource
import com.nhatle.workmangement.data.source.remote.UserTeamRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.add.AddActionFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonData
import kotlinx.android.synthetic.main.fragment_add_user_group.*
import kotlin.random.Random

class AddGroupFragment:BaseFragment(), AddGroupContract.View, View.OnClickListener {
    override val layoutResource: Int
        get() = R.layout.fragment_add_user_group
    private var presenter: AddGroupPresenter?=null
    private var group :Team?=null
    private  val listMember :ArrayList<UserTeam> = ArrayList()
    private val adater: AddUserTeamAdapter by lazy {
         AddUserTeamAdapter(object :AddUserTeamAdapter.SendFriend{
            override fun sendData(userProfile: FriendResponse) {
                addMember(0,userProfile.friendId)
            }
        })
    }

    private fun addMember(groupId:Int,profileId:Int) {
        val userTeam = UserTeam(groupId,profileId)
        listMember.add(userTeam)
    }

    override fun initData() {
//        initRecycleView()
        registerListener()
    }

    private fun initPresenter() {
        val userService  = Common.getUserService()
        val teamDataSource = TeamRemoteDataSource.getInstance(userService)
        val userTeamDataSource = UserTeamRemoteDataSource.getInstance(userService)
        val friendDataSource = FriendRemoteDataSource.getInStance(userService)
        val groupRepository = TeamRemoteRepository(teamDataSource)
        val userGroupRepository = UserTeamRepository(userTeamDataSource)
        val friendRepository = FriendRepository(friendDataSource)
        presenter = AddGroupPresenter(this,groupRepository,userGroupRepository,friendRepository)
    }

    override fun initComponents() {
        initPresenter()
        presenter?.getAllUserIsFriend(CommonData.getInstance().profile!!.profileId)
    }
    private fun registerListener() {
        buttonAddGroup.setOnClickListener(this)
        buttonCancelGroup.setOnClickListener(this)
    }

    override fun showAllData(list: List<FriendResponse>) {
        adater.setData(list = list as ArrayList<FriendResponse>)
        recyclerChooseMember.adapter = adater
    }

    override fun insertGroupSuccess(team: Team) {
        val list :ArrayList<UserTeam> = ArrayList()
        this.group = team
        for (userTeam in listMember){
            userTeam.groupId = team.groupId
            list.add(userTeam)
        }
        presenter?.insertUserGroup(list)
    }

    override fun insertUserGroupSuccess() {
        val fragment =AddActionFragment()
        fragment.sendGroupId(group!!)
       replaceFragment(R.id.frag_main,fragment,false)

    }

    override fun onFail(string: String) {
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.buttonAddGroup->{
                activity?.let { Common.hideKeyBoard(it) }
                checkAndInsertGroup()
            }
            R.id.buttonCancelGroup->{
                activity?.let { Common.hideKeyBoard(it) }
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

    }
}
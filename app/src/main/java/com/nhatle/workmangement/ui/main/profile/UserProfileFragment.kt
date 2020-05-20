package com.nhatle.workmangement.ui.main.profile

import android.view.KeyEvent
import android.view.View
import android.widget.PopupMenu
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.reponsitory.remote.UserProfileRemoteRepository
import com.nhatle.workmangement.data.source.remote.UserProfileRemoteDataSource
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.friend.FriendManagerFragment
import com.nhatle.workmangement.ui.main.profile.update.UpdateProfileFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonData
import kotlinx.android.synthetic.main.fragment_profile.*

class UserProfileFragment : BaseFragment(), UserProfileContract.View, View.OnClickListener {
    override val layoutResource: Int
        get() = R.layout.fragment_profile
    private var idProfile: Int = -1
    private var presenter: UserProfilePresenter? = null
    override fun initData() {
        initPresenter()
        initView(CommonData.getInstance().profile)
        configView()


    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = UserProfileRemoteDataSource.getInstance(userService)
        val repository = UserProfileRemoteRepository(dataSource)
        presenter = UserProfilePresenter(this, repository)
    }

    private fun configView() {
        if (!checkUser(idProfile, CommonData.getInstance().profile!!.profileId)) {
            presenter!!.getAllDataUserProfile(idProfile)
        }
    }

    private fun initView(profile: UserProfile?) {
        Glide.with(imageAvatarPro)
            .load(profile!!.avatar)
            .placeholder(R.drawable.bavarian)
            .into(imageAvatarPro)
        fullnamepro.text = profile.fullName
        textAddressByUser.text = profile.address
        textemailByUser.text = profile.email
        textphoneByUser.text = profile.phoneNumber
    }

    private fun checkUser(id: Int, idUser: Int): Boolean {
        if (id == idUser) {
            return true
        }
        return false
    }

    override fun initComponents() {
        registerListener()
    }

    private fun registerListener() {
        buttonEnd.setOnClickListener(this)
    }

    fun setData(data: Int) {
        this.idProfile = data
    }

    override fun showDataProfile(userProfile: UserProfile) {
        initView(userProfile)
    }

    override fun loadFail(string: String) {
    }

    override fun onClick(v: View?) {
        val popu = PopupMenu(activity, buttonEnd)
        popu.menuInflater.inflate(R.menu.menu_logout, popu.menu)
        popu.setOnMenuItemClickListener {
            if (it.itemId == R.id.actionlogout) {
                (activity as MainActivity).logout()

            } else {
                val fragment = UpdateProfileFragment()
                (activity as MainActivity).hindNavigation(true)
                replaceFragment(R.id.frag_main, fragment, false)
            }
            true
        }
        popu.show()
    }

//    override fun onResume() {
//        super.onResume()
//        view!!.isFocusableInTouchMode = true
//        view!!.requestFocus()
//        view!!.setOnKeyListener(object : View.OnKeyListener {
//            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
//                if (event!!.action== KeyEvent.ACTION_UP&& keyCode== KeyEvent.KEYCODE_BACK){
//                   if (idProfile!=CommonData.getInstance().profile!!.profileId){
//                       (activity as MainActivity ).hindNavigation(false)
//                       replaceFragment(R.id.frag_main,FriendManagerFragment(),false)
//                   }
//                }
//                return false
//            }
//        })
//    }
}

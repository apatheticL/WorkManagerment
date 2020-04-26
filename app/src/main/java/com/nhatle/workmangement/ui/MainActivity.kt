package com.nhatle.workmangement.ui

import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nhatle.workmangement.R
import com.nhatle.workmangement.ui.base.BaseActivity
import com.nhatle.workmangement.ui.main.action.ActionFragment
import com.nhatle.workmangement.ui.main.friend.FriendManagerFragment
import com.nhatle.workmangement.ui.main.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override val layoutResource: Int = R.layout.activity_main
    override fun initComponent() {
        initNavigationButton()
        openFragmentAction()
    }

    override fun initData() {
        TODO("Not yet implemented")
    }

    private fun initNavigationButton() {
        val onNavigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId){
                R.id.actionWork -> {
                    fragment = ActionFragment()
                }
                R.id.actionFriend -> {
                    fragment = FriendManagerFragment()
                }
                R.id.actionProfile -> {
                    fragment = ProfileFragment()
                }
            }
            if (fragment!=null){
                replaceFragment(R.id.frag_main,fragment,false)
            }
            true
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationListener)
    }
    fun openFragmentAction(){
        replaceFragment(R.id.frag_main,ActionFragment(),false)
    }

}

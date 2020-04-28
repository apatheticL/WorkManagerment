package com.nhatle.workmangement.ui

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nhatle.workmangement.R
import com.nhatle.workmangement.ui.base.BaseActivity
import com.nhatle.workmangement.ui.main.action.ActionFragment
import com.nhatle.workmangement.ui.main.friend.FriendManagerFragment
import com.nhatle.workmangement.ui.main.profile.UserProfileFragment
import com.nhatle.workmangement.ui.start.StartActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override val layoutResource: Int = R.layout.activity_main
    override fun initComponent() {
        initNavigationButton()

    }

    override fun initData() {
        openFragmentAction()
    }

    private fun initNavigationButton() {
        val onNavigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.actionWork -> {
                    fragment = ActionFragment()
                }
                R.id.actionFriend -> {
                    fragment = FriendManagerFragment()
                }
                R.id.actionProfile -> {
                    fragment = UserProfileFragment()
                }
            }
            if (fragment != null) {
                replaceFragment(R.id.frag_main, fragment, false)
            }
            true
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationListener)
    }

    private fun openFragmentAction() {
        replaceFragment(R.id.frag_main, ActionFragment(), false)
    }

    fun logout() {
        var intent =
            Intent(this, StartActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
    fun hindNavigation(check:Boolean){
        if(check){
            frag_main.visibility = View.GONE
            bottomNavigationView.visibility = View.GONE
            frag_image.visibility = View.VISIBLE
        }
        else{
            frag_main.visibility = View.VISIBLE
            bottomNavigationView.visibility = View.VISIBLE
            frag_image.visibility = View.GONE
        }

    }

}

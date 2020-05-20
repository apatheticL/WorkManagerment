package com.nhatle.workmangement.ui.main.friend

import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.nhatle.workmangement.R
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.friend.add.ReceiverFragment
import com.nhatle.workmangement.ui.main.friend.receiver.FriendSuggestionsFragment
import kotlinx.android.synthetic.main.fragment_friend_manager.*

class FriendManagerFragment:BaseFragment() {
    override val layoutResource: Int
        get() = R.layout.fragment_friend_manager

    override fun initData() {
        configTab()
    }

    private fun configTab() {
        var tabLayout = tabFriend as TabLayout
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                var fragment: Fragment? = null
                when (p0!!.position) {
                    0 -> {
                        fragment = FriendFragment()
                    }
                    1 -> {
                        fragment =
                            FriendSuggestionsFragment()
                    }
                    2 -> {
                        fragment = ReceiverFragment()
                    }
                }
                if (fragment != null) {
                    replaceFragment(R.id.frag_friend, fragment,true)
                }
            }
        })
    }

    override fun initComponents() {
        addFragment(R.id.frag_friend,FriendFragment(),false)
    }
}
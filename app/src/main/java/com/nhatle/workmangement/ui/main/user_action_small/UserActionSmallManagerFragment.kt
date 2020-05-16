package com.nhatle.workmangement.ui.main.user_action_small

import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.nhatle.workmangement.R
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.ActionFragment
import com.nhatle.workmangement.ui.main.action.detail.ActionDetailFragment
import com.nhatle.workmangement.ui.main.user_action_small.user.MyActionSmallFragment
import kotlinx.android.synthetic.main.fragment_user_action_small_manager.*
import kotlin.properties.Delegates

class UserActionSmallManagerFragment:BaseFragment() {
    override val layoutResource: Int
        get() = R.layout.fragment_user_action_small_manager

    override fun initData() {
        configTab()
    }

    private fun configTab() {
        var tabLayout = tabUserActionSmall as TabLayout
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                var fragment: Fragment? = null
                when (p0!!.position) {
                    0 -> {
                        fragment = UserActionSmallFragment()
                    }
                    1 -> {
                        fragment =
                            MyActionSmallFragment()
                    }
                }
                if (fragment != null) {
                    replaceFragment(R.id.frag_user_action_small,fragment,false)
                }
            }
        })
    }

    override fun initComponents() {
        (activity as MainActivity).hindNavigation(true)
        addFragment(R.id.frag_user_action_small,UserActionSmallFragment(),false)
    }
    override fun onResume() {
        super.onResume()
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
        view!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event!!.action== KeyEvent.ACTION_UP&& keyCode== KeyEvent.KEYCODE_BACK){
                    (activity as MainActivity).hindNavigation(false)
                    replaceFragment(R.id.frag_main, ActionDetailFragment(),false)
                }
                return false
            }

        })
    }
}
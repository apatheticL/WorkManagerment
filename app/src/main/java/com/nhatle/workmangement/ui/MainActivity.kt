package com.nhatle.workmangement.ui

import android.content.Intent
import android.net.Uri
import android.view.View
import android.webkit.MimeTypeMap
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nhatle.workmangement.R
import com.nhatle.workmangement.ui.base.BaseActivity
import com.nhatle.workmangement.ui.main.action.ActionFragment
import com.nhatle.workmangement.ui.main.action.detail.ActionDetailFragment
import com.nhatle.workmangement.ui.main.friend.FriendManagerFragment
import com.nhatle.workmangement.ui.main.profile.UserProfileFragment
import com.nhatle.workmangement.ui.main.profile.update.UpdateProfileFragment
import com.nhatle.workmangement.ui.start.StartActivity
import com.nhatle.workmangement.until.CommonData
import com.nhatle.workmangement.until.ShareUntil
import com.nhatle.workmangement.until.SocketManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    override val layoutResource: Int = R.layout.activity_main
    override fun initComponent() {
        if(CommonData.getInstance().profile!=null){
            SocketManager.getInstance().connect()
        }
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
                replaceFragment(R.id.frag_main, fragment, true)
            }
            true
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationListener)
    }

    private fun openFragmentAction() {
        replaceFragment(R.id.frag_main, ActionFragment(), true)
    }

    fun logout() {
        ShareUntil.clearProfile(context = this)
        var intent =
            Intent(this, StartActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        SocketManager.getInstance().disconnect()
        this.finish()
        startActivity(intent)
    }
    fun hindNavigation(check:Boolean){
        if(check){
            bottomNavigationView.visibility = View.GONE
        }
        else{
            bottomNavigationView.visibility = View.VISIBLE
        }

    }
    fun getFileException(uri: Uri): String? {
        val content = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(content.getType(uri))
    }

    override fun onDestroy() {
        super.onDestroy()
        SocketManager.getInstance().disconnect()
    }

    fun openFragmentUserProfileFrament(updateProfileFragment: UpdateProfileFragment) {

        replaceFragment(R.id.frag_main,UserProfileFragment(),false)
        hindNavigation(false)
        bottomNavigationView.selectedItemId = R.id.actionProfile
//        remoteFragment(updateProfileFragment,false)
    }

    fun openFragmentActionDetail() {
        replaceFragment(R.id.frag_main,ActionDetailFragment(),false)
        hindNavigation(true)
    }


//    override fun onBackPressed() {
//        hindNavigation(false)
//        bottomNavigationView.selectedItemId = R.id.actionWork
//        replaceFragment(R.id.frag_main,ActionFragment(),false)
//    }


}

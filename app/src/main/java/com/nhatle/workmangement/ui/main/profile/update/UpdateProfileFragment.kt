package com.nhatle.workmangement.ui.main.profile.update

import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.reponsitory.remote.UserProfileRemoteRepository
import com.nhatle.workmangement.data.source.remote.UserProfileRemoteDataSource
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.image.ImageFragment
import com.nhatle.workmangement.ui.main.profile.UserProfileFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonData
import kotlinx.android.synthetic.main.fragment_update_profile.*

class UpdateProfileFragment : BaseFragment(), UpdateUserProfileContract.View, View.OnClickListener {
    override val layoutResource: Int
        get() = R.layout.fragment_update_profile
    private var presenter: UpdateUserProfilePresenter? = null

    override fun initData() {
        initView(CommonData.getInstance().profile!!)
        initPresenter()
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = UserProfileRemoteDataSource.getInstance(userService)
        val repository = UserProfileRemoteRepository(dataSource)
        presenter = UpdateUserProfilePresenter(this, repository)
    }

    private fun initView(profile: UserProfile) {
        Glide.with(circleImageView2)
            .load(profile.avatar)
            .into(circleImageView2)
        editAddressUp.setText(profile.address)
        editEmailUp.setText(profile.email)
        editFullnameUp.setText(profile.fullName)
        editPhoneUp.setText(profile.phoneNumber)
    }

    override fun initComponents() {
        registerListener()
    }

    private fun registerListener() {
        buttonUpAvatar.setOnClickListener(this)
        buttonUpdatePr.setOnClickListener(this)
        buttoncancle.setOnClickListener(this)
    }

    override fun updateSuccess(userProfile: UserProfile) {
        CommonData.getInstance().profile = userProfile
        replaceFragment(R.id.frag_main, UserProfileFragment(), false)
    }

    override fun loadFail(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonUpAvatar -> {
                (activity as MainActivity).hindNavigation(true)
                replaceFragment(R.id.frag_image,ImageFragment(),true)
            }
            R.id.buttonUpdatePr -> {
                updateProfile()
            }
            R.id.buttoncancle -> {
                replaceFragment(R.id.frag_main, UserProfileFragment(), false)
            }
        }
    }

    private fun updateProfile() {
        if (editAddressUp.text.toString().isEmpty() ||
            editEmailUp.text.toString().isEmpty() ||
            editFullnameUp.text.toString().isEmpty() ||
            editPhoneUp.text.toString().isEmpty()
        ){
            Toast.makeText(context,"It not null",Toast.LENGTH_SHORT).show()
        }

        val userProfile = UserProfile(CommonData.getInstance().profile!!.profileId,
        CommonData.getInstance().profile!!.username,null,
        editFullnameUp.text.toString(),editAddressUp.text.toString(),editPhoneUp.text.toString(),
        editEmailUp.text.toString(),null)
        presenter!!.update(userProfile)
    }
}
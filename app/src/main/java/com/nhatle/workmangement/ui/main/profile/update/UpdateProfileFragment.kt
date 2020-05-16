package com.nhatle.workmangement.ui.main.profile.update

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.reponsitory.remote.UserProfileRemoteRepository
import com.nhatle.workmangement.data.source.remote.UserProfileRemoteDataSource
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.profile.UserProfileFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonData
import com.nhatle.workmangement.until.StringFormatter
import com.nhatle.workmangement.until.UploadFile
import kotlinx.android.synthetic.main.fragment_update_profile.*

class UpdateProfileFragment : BaseFragment(), UpdateUserProfileContract.View, View.OnClickListener {
    private val PICK_IMAGE_REPUEST = 1
    override val layoutResource: Int
        get() = R.layout.fragment_update_profile
    private var mImageUri: Uri? = null
    private var checkUpdateAvatar = false
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
        (activity as MainActivity).hindNavigation(false)
        replaceFragment(R.id.frag_main, UserProfileFragment(), true)
    }

    override fun loadFail(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonUpAvatar -> {
                openFileChoose()
                checkUpdateAvatar = true
            }
            R.id.buttonUpdatePr -> {
                updateProfile()
            }
            R.id.buttoncancle -> {
                (activity as MainActivity).hindNavigation(false)
                replaceFragment(R.id.frag_main, UserProfileFragment(), true)
            }
        }
    }

    private fun openFileChoose() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, PICK_IMAGE_REPUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REPUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data!!
            Glide.with(circleImageView2).load(mImageUri).into(circleImageView2)
        }
    }

    private fun updateProfile() {
        if (editAddressUp.text.toString().isEmpty() ||
            editEmailUp.text.toString().isEmpty() ||
            editFullnameUp.text.toString().isEmpty() ||
            editPhoneUp.text.toString().isEmpty()
        ) {
            Toast.makeText(context, "It not null", Toast.LENGTH_SHORT).show()
        }
        if (checkUpdateAvatar) {
            val name = "" + System.currentTimeMillis() + "." +
                    (activity as MainActivity).getFileException(mImageUri!!)
            upLoadImage(name)
        } else {
            val fullName = editFullnameUp.text.toString()
            val address = editAddressUp.text.toString()
            val phone = editPhoneUp.text.toString()
            val email = editEmailUp.text.toString()
            if (StringFormatter.checkEmallFormat(email)) {
                val userProfile = UserProfile(
                    CommonData.getInstance().profile!!.profileId,
                    null,
                    null,
                    CommonData.getInstance().profile!!.avatar,
                    fullName, address, phone, email,
                    null
                )
                presenter!!.update(userProfile)
            } else {
                Toast.makeText(context, "email không đúng định dạng", Toast.LENGTH_SHORT).show()
                editEmailUp.setText("")
            }
        }
    }

    private fun upLoadImage(name: String) {

        val uploadFile = UploadFile(mImageUri!!, object : UploadFile.LoadData {
            override fun addOnSuccessListener() {

            }

            override fun addOnFailureListener(string: String) {
                Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
            }

            override fun addOnProgressListener(progress: Double) {
                // add to notifile
            }

            override fun onFailSelect() {
                Toast.makeText(context, "no item choose", Toast.LENGTH_SHORT).show()
            }

            override fun sendUri(uri: Uri) {
                val string = uri.toString()
                val email = editEmailUp.text.toString()
                if (StringFormatter.checkEmallFormat(email)) {
                    val userProfile = UserProfile(
                        CommonData.getInstance().profile!!.profileId,
                        null,
                        null,
                        string,
                        editFullnameUp.text.toString(),
                        editAddressUp.text.toString(),
                        editPhoneUp.text.toString(),
                        email,
                        null
                    )
                    presenter!!.update(userProfile)
                }
                else{
                    Toast.makeText(context, "email không đúng định dạng", Toast.LENGTH_SHORT).show()
                    editEmailUp.setText("")
                }
            }

            override fun sendFaile(string: String) {
                Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
            }

        })
        uploadFile.upLoad(name)
    }
    override fun onResume() {
        super.onResume()
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
        view!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event!!.action== KeyEvent.ACTION_UP&& keyCode== KeyEvent.KEYCODE_BACK){
                    (activity as MainActivity).hindNavigation(false)
                    replaceFragment(R.id.frag_main,UserProfileFragment(),false)
                }
                return false
            }

        })
    }
}
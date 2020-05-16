package com.nhatle.workmangement.ui.main.action.update

import android.app.DatePickerDialog
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.Action
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.model.response.ActionResponse
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.ActionSmallRemoteRepository
import com.nhatle.workmangement.data.source.remote.ActionRemoteDataSource
import com.nhatle.workmangement.data.source.remote.ActionSmallRemoteDataSource
import com.nhatle.workmangement.data.until.ActionSmallBefor
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.main.action.ActionFragment
import com.nhatle.workmangement.ui.main.action.add.ListActionSmallBeforAddAdapter
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonAction
import com.nhatle.workmangement.until.CommonData
import com.nhatle.workmangement.until.convertStringToDate
import kotlinx.android.synthetic.main.fragment_update_action_small.*
import kotlinx.android.synthetic.main.fragment_update_work.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UpdateActionFragment : BaseFragment(), UpdateActionContract.View, View.OnClickListener {
    override val layoutResource: Int = R.layout.fragment_update_work
    private var actionResponse: ActionResponse? = null
    private var checkItemClick = false
    private var presenter: UpdateActionPresenter? = null
    private var listActionSmallName: ArrayList<ActionSmallBefor> = ArrayList()
    private var listUpdate: ArrayList<ActionSmall> = ArrayList()
    private var lisDeleteActionSmall: ArrayList<ActionSmall> = ArrayList()
    private val adapter: ActionSmallForUpdateAdapter by lazy {
        ActionSmallForUpdateAdapter(object : ActionSmallForUpdateAdapter.SendActionSmall {
            override fun sendActionSmall(data: ActionSmall) {
                sendActionSmallForUpdate(data)
                checkItemClick = true
            }

            override fun sendActionIsDelete(actionSmall: ActionSmall, position: Int) {
                sendDataDeleteActionSmall(actionSmall, position)
            }

        })
    }
    private val adapterInsert: ListActionSmallBeforAddAdapter = ListActionSmallBeforAddAdapter()

    private fun sendDataDeleteActionSmall(
        actionSmall: ActionSmall,
        position: Int
    ) {
        lisDeleteActionSmall.add(actionSmall)
        adapter.deleteMember(position)
    }

    private fun sendActionSmallForUpdate(data: ActionSmall) {
        editActionSmallNameOnUpdate.setText(data.description)
        buttonSaveUpdate.setOnClickListener {
            listUpdate.add(data)
        }
    }

    override fun initData() {
        //loadData()
        regisListener()

    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = ActionRemoteDataSource.getInstance(userService)
        val smallDataSource = ActionSmallRemoteDataSource.getInstance(userService)
        val smallRepository = ActionSmallRemoteRepository(smallDataSource)
        val repository = ActionRemoteRepository(dataSource)
        presenter = UpdateActionPresenter(this, repository, smallRepository)
        presenter!!.getAllActionSmall(CommonAction.getInstance().action!!.actionId)
    }

    override fun initComponents() {
        initPresenter()
        loadData()
    }

    private fun regisListener() {
        buttonUpdateWork.setOnClickListener(this)
        buttonCancelUpdateWork.setOnClickListener(this)
        buttonAddDateEnd.setOnClickListener(this)
        buttonSaveUpdate.setOnClickListener(this)
    }

    private fun loadData() {
        Glide.with(image_avatar).load(CommonData.getInstance().profile!!.avatar)
            .into(image_avatar)
        nameCreatorAction.text = CommonData.getInstance().profile!!.fullName
        editDescription.setText(actionResponse!!.description)
        nameWork.setText(actionResponse!!.actionName)
        timeStartAction.text = actionResponse!!.timeStart
    }

    override fun getAllActionSmall(list: List<ActionSmall>) {
        adapter.setData(list as ArrayList<ActionSmall>)
        recyclerListActionSmallForUpdate.adapter = adapter
    }

    override fun updateSuccess() {
        (activity as MainActivity).hindNavigation(false)
        replaceFragment(R.id.frag_main, ActionFragment(), true)
    }

    override fun updateActionSmallSuccess() {
        openFragmentAction()
    }

    override fun insertActionSmallSuccess() {
        openFragmentAction()
    }

    override fun deleteActionSmallSuccess() {
        openFragmentAction()
    }

    override fun onFail(string: String) {
    }

    fun sendData(data: ActionResponse) {
        this.actionResponse = data
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonUpdateWork -> {
                updateWork()
            }
            R.id.buttonCancelUpdateWork -> {
                openFragmentAction()
            }
            R.id.buttonAddDateEnd -> {
                showDatetimeDialog(texttimEndUpdate)
            }
            R.id.buttonSaveUpdate -> {

                if (!checkItemClick) {
                    checkAndShowActionSmall()
                }
                editActionSmallNameOnUpdate.text = null
            }
        }
    }

    private fun showDatetimeDialog(textView: TextView) {
        val fmDateAndTime = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        val datePickerDialog =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH + 1, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                if (convertStringToDate(actionResponse!!.timeStart) < calendar.time) {
                    textView.text = fmDateAndTime.format(calendar.time)
                }
                if (convertStringToDate(actionResponse!!.timeStart) > calendar.time) {
                    textView.text =null
                        Toast.makeText(
                        context,
                        "Thoi gian ket thuc phai lon hon" + actionResponse!!.timeStart,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        activity?.let {
            DatePickerDialog(
                it, datePickerDialog,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }


    private fun updateWork() {
        if (editDescription.text.toString().isNotEmpty() || nameWork.text.toString().isNotEmpty()) {
            val action = Action(
                actionResponse!!.actionId
                ,
                nameWork.text.toString()
                ,
                actionResponse!!.creatorId,
                actionResponse!!.groupId,
                null,
                texttimEndUpdate.text.toString(),
                null,
                null,
                editDescription.text.toString()
            )
            presenter!!.updateAction(action)
            if (listUpdate.isNotEmpty()) {
                presenter!!.updateActionSmall(listUpdate)

            }
            if (lisDeleteActionSmall.isNotEmpty()) {
                for (actionSmallDelete in lisDeleteActionSmall) {
                    presenter!!.delateActionSmall(actionSmallDelete.actionSmallId)
                }
            }
            if (listActionSmallName.isNotEmpty()) {
                val list: MutableList<ActionSmall> = ArrayList()
                for (i in 0 until listActionSmallName.size step 1) {
                    val actionSmall =
                        ActionSmall(
                            0, actionResponse!!.actionId,
                            listActionSmallName.get(i).actionSmallName
                        )
                    list.add(actionSmall)
                }
                presenter!!.insertActionSmall(list)
            }
        }
    }

    private fun checkAndShowActionSmall() {
        if (editActionSmallNameOnUpdate.text.isEmpty()) {
            textErrorUpdate.visibility = View.VISIBLE
            textErrorUpdate.text = "Trường này chưa có thông tin"
        } else {
            textErrorUpdate.visibility = View.GONE
            if (editActionSmallNameOnUpdate.text.toString().isNotEmpty()) {
                listActionSmallName.add(ActionSmallBefor(editActionSmallNameOnUpdate.text.toString()))
                recyclerBefoInsert.visibility = View.VISIBLE
                adapterInsert.setData(listActionSmallName)
                recyclerBefoInsert.adapter = adapterInsert
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        openFragmentAction()
    }

    private fun openFragmentAction() {
        (activity as MainActivity).hindNavigation(false)
        replaceFragment(R.id.frag_main, ActionFragment(), false)
    }

    override fun onResume() {
        super.onResume()
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
        view!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event!!.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    (activity as MainActivity).hindNavigation(false)
                    replaceFragment(R.id.frag_main, ActionFragment(), false)
                }
                return false
            }

        })
    }
}
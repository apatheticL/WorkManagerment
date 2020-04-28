package com.nhatle.workmangement.ui.main.image

import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.until.ItemImage
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.until.ReadImage
import com.nhatle.workmangement.until.ReadImageAsyncTask
import kotlinx.android.synthetic.main.fragment_image.*

class ImageFragment:BaseFragment() {
    override val layoutResource: Int
        get() = R.layout.fragment_image
    private var adapter:ImageAdapter?=null
    private lateinit var readImage:ReadImage
    private val asyncTask:ReadImageAsyncTask by lazy {
        ReadImageAsyncTask(readImage = readImage){
            addAdapter(it)
        }
    }

    private fun addAdapter(it: List<ItemImage>) {
        adapter!!.setData(it as ArrayList<ItemImage>)
        adapter = ImageAdapter(object :SendImage{
            override fun sendData(date: ItemImage) {
                
            }
        })
        recyclerListImage.adapter= adapter
    }

    override fun initData() {
        readImage = context?.let { ReadImage(it) }!!
        asyncTask.execute()
    }

    override fun initComponents() {
        TODO("Not yet implemented")
    }
}
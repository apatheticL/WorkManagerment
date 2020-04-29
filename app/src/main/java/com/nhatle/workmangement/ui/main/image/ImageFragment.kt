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
    private val adapter:ImageAdapter by lazy {
          ImageAdapter(object :SendImage{
            override fun sendData(date: ItemImage) {

            }
        })
    }
    private lateinit var readImage:ReadImage
    private val asyncTask:ReadImageAsyncTask by lazy {
        ReadImageAsyncTask(readImage = readImage){
            addAdapter(it)
        }
    }

    private fun addAdapter(it: List<ItemImage>) {
        adapter.setData(it as ArrayList<ItemImage>)
    }

    override fun initData() {
        recyclerListImage.adapter= adapter
    }

    override fun initComponents() {
        readImage = context?.let { ReadImage(it) }!!
        asyncTask.execute()
    }
}
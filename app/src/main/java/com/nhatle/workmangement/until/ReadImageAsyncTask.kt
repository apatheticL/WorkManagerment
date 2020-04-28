package com.nhatle.workmangement.until

import android.os.AsyncTask
import com.nhatle.workmangement.data.until.ItemImage
import java.lang.Exception

class ReadImageAsyncTask (val readImage: ReadImage,
val success:(List<ItemImage>)->Unit)
    : AsyncTask<Unit, String, List<ItemImage>>() {
    override fun doInBackground(vararg params: Unit?): List<ItemImage>? {
        return try {
            readImage.readImage()
        }catch (e: Exception){
            null
        }
    }

    override fun onPostExecute(result: List<ItemImage>?) {
        super.onPostExecute(result)
        result?.let { success(it) }
    }

}
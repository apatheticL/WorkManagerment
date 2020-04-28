package com.nhatle.workmangement.until

import android.content.Context
import android.provider.MediaStore
import com.nhatle.workmangement.data.until.ItemImage

class ReadImage (val context: Context){
    fun readImage():List<ItemImage>{
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null, null, null, null
        )
        cursor!!.columnNames
        val getDatapath = "_data"
        val getDataName = MediaStore.MediaColumns.DISPLAY_NAME
        val indexPath = cursor.getColumnIndex(getDatapath)
        val indexName = cursor.getColumnIndex(getDataName)
        cursor.moveToFirst()
        return ArrayList<ItemImage>().apply {
            while (!cursor.isAfterLast) {
                val path: String = cursor.getString(indexPath)
                val name = cursor.getString(indexName)
                add(ItemImage(path,name))
                cursor.moveToNext()
            }
            cursor.close()
        }
    }

}
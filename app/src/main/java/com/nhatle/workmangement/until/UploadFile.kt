package com.nhatle.workmangement.until

import android.net.Uri
import android.os.Handler
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.nhatle.workmangement.data.until.ItemImage

class UploadFile(val mImageUri: Uri?, val call: LoadData) {

    private fun getStorageRef(): StorageReference {
        return FirebaseStorage.getInstance().getReference(LOCATION_URI)
    }

    private fun getDatabaseRef(): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference(PART_URI)
    }

    fun upLoad(name: String) {
        if (mImageUri != null) {
            val fileReference =
                getStorageRef().child(name)
            fileReference.putFile(mImageUri)
                .addOnSuccessListener {
                    val handler = Handler()
                    handler.postDelayed({
                        call.addOnSuccessListener()
                        downLoad(name)
                    }, 5000)
                    val image = ItemImage("image", it.uploadSessionUri.toString())
                    val uploadId = getDatabaseRef().push().key
                    getDatabaseRef().child(uploadId!!).setValue(image)
                }
                .addOnFailureListener {
                    call.addOnFailureListener(it.message.toString())
                }
                .addOnProgressListener {
                    val propress = 100.0 * it.bytesTransferred / it.totalByteCount
                    call.addOnProgressListener(propress)
                }
        } else {
            call.onFailSelect()
        }
    }

    private fun downLoad(name: String) {

        val store = FirebaseStorage.getInstance().reference
        store.child("$PART_URI/$name").downloadUrl
            .addOnSuccessListener { p0 -> call.sendUri(p0!!) }
            .addOnFailureListener { p0 -> call.sendFaile(p0.message.toString()) }
    }

    interface LoadData {
        fun addOnSuccessListener()
        fun addOnFailureListener(string: String)
        fun addOnProgressListener(progress: Double)
        fun onFailSelect()
        fun sendUri(uri: Uri)
        fun sendFaile(string: String)
    }
}
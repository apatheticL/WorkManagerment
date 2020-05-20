package com.nhatle.workmangement.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.nhatle.workmangement.R
import com.nhatle.workmangement.ui.start.StartActivity
import com.nhatle.workmangement.until.CommonData
import com.nhatle.workmangement.until.ShareUntil
import kotlinx.android.synthetic.main.activity_sflat.*

class SflatActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sflat)
        val handler = Handler()
        handler.postDelayed(
            {
                try {
                    val userProfile = ShareUntil.getUserProfile(this@SflatActivity)
                    if (userProfile!=null){
                        CommonData.getInstance().profile = userProfile
                        val intent = Intent(applicationContext,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        val intent = Intent(applicationContext,StartActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    progressBar.progress = 0
                }catch (e:InterruptedException){
                    e.printStackTrace()
                }
            },2000)
        progressBar.progress = 2000
    }
}
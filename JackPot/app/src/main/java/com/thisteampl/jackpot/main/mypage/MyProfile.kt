package com.thisteampl.jackpot.main.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thisteampl.jackpot.R
import kotlinx.android.synthetic.main.activity_my_profile.*

class MyProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        val intent_editprofile = Intent(this,EditProfile::class.java)

        btn_editprofile.setOnClickListener {
            startActivity(intent_editprofile)
        }


    }
}
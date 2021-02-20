package com.thisteampl.jackpot.main.userpage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thisteampl.jackpot.R
import kotlinx.android.synthetic.main.activity_profile_edit_change_state.*

//https://m.blog.naver.com/l5547/221845481754, OnActivityResult

class ProfileEditChangeStateActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit_change_state)

        setupView()
    }

    private fun setupView(){
        profile_edit_change_state_back_button.setOnClickListener { onBackPressed() }


    }

}
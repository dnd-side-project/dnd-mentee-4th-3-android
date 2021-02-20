package com.thisteampl.jackpot.main.userpage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thisteampl.jackpot.R
import kotlinx.android.synthetic.main.activity_profile_change_password.*

class ProfileChangePasswordActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_change_password)

        setupView()
    }

    private fun setupView(){
        profile_changePW_back_button.setOnClickListener { onBackPressed() }


    }

}
package com.thisteampl.jackpot.main.userpage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.userController.userAPI

class ProfileEditActivity: AppCompatActivity() {

    private val userApi = userAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        setupView()
    }

    private fun setupView(){

    }
}
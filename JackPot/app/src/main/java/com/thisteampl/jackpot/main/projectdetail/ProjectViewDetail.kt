package com.thisteampl.jackpot.main.projectdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.thisteampl.jackpot.R

class ProjectViewDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_view_detail)


        Log.d("tag","id : ${intent.getLongExtra("id",0)}")
    }

    private fun setUpView(){

    }
}
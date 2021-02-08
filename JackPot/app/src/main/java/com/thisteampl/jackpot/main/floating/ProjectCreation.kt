package com.thisteampl.jackpot.main.floating

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thisteampl.jackpot.R
import kotlinx.android.synthetic.main.activity_project_creation.*

class ProjectCreation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_creation)

        createproject_minusbutton_imageview.setOnClickListener{
            finish()
        }
    }
}
package com.thisteampl.jackpot.main.mainview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thisteampl.jackpot.R
import kotlinx.android.synthetic.main.activity_find_project.*

class FindProject : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_project)


        val intent  = Intent(this, ExampleProject::class.java)
        project_btn.setOnClickListener{startActivity(intent)}

    }
}
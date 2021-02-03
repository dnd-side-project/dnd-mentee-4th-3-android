package com.thisteampl.jackpot.main.mainhome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thisteampl.jackpot.R
import kotlinx.android.synthetic.main.activity_project_view_more.*
import java.util.ArrayList

class ProjectViewMore : AppCompatActivity() {
    var recentlyregister: ArrayList<RecentlyRegisterList>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_view_more)

        recentlyregister = intent.getParcelableArrayListExtra<RecentlyRegisterList>("InputRecently")


        val Adapter = ProjectViewMoreAdapter(this,recentlyregister)
        detail_print_listview.adapter = Adapter

    }
}
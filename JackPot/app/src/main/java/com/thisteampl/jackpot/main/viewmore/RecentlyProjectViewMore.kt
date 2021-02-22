package com.thisteampl.jackpot.main.viewmore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.mainhome.RecentlyRegisterList
import kotlinx.android.synthetic.main.activity_project_view_more.*

class RecentlyProjectViewMore : AppCompatActivity() {
    var recentlyregister: ArrayList<RecentlyRegisterList>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recently_project_view_more)




        val adapter =
            RecentlyProjectViewMoreAdapter(
                this,
                recentlyregister
            )
        detail_recentlyprint_listview.adapter = adapter

    }
}
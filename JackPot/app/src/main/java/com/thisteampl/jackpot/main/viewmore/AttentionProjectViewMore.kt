package com.thisteampl.jackpot.main.viewmore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.mainhome.AttentionProject
import com.thisteampl.jackpot.main.mainhome.AttentionProjectList
import kotlinx.android.synthetic.main.activity_attention_project_view_more.*
import java.util.ArrayList

class AttentionProjectViewMore : AppCompatActivity() {
    var attentionprojectlist: ArrayList<AttentionProjectList>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attention_project_view_more)


        // AttentionProject 전역변수 attention를 attentionprojectlist에 대입
        attentionprojectlist = AttentionProject()!!.attention


        val adapter = AttentionProjectViewMoreAdapter(this,attentionprojectlist)

        detail_projectprint_listview.adapter = adapter

    }
}
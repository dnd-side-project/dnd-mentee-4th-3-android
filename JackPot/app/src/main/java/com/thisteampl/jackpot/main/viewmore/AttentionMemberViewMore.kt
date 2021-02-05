package com.thisteampl.jackpot.main.viewmore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.mainhome.AttentionMember
import com.thisteampl.jackpot.main.mainhome.AttentionMemberList
import com.thisteampl.jackpot.main.mainhome.RecentlyRegisterList
import com.thisteampl.jackpot.main.mainhome.RecentlyRegisterProject
import kotlinx.android.synthetic.main.activity_attention_member_view_more.*
import java.util.ArrayList

class AttentionMemberViewMore : AppCompatActivity() {
    var attentionmemberlist: ArrayList<AttentionMemberList>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attention_member_view_more)
        
        // AttentionMember 전역변수 attention를 attentionmemberlist에 대입
        attentionmemberlist = AttentionMember()!!.attention

        val adapter = AttentionMemberViewMoreAdapter(this,attentionmemberlist)

        detail_memberprint_listview.adapter = adapter

    }
}
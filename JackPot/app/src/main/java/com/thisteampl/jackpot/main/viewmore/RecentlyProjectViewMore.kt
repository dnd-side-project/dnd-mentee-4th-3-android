package com.thisteampl.jackpot.main.viewmore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.mainhome.RecentlyRegisterList
import kotlinx.android.synthetic.main.activity_project_view_more.*
import java.util.ArrayList

class RecentlyProjectViewMore : AppCompatActivity() {
    var recentlyregister: MutableList<RecentlyRegisterList> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_view_more)

        // MainActivity에서 데이터 전달된 값 recentlyregister에 저장 (getParcelableArrayListExtra => ArrayList전달 받음)

        
        // 백엔드 관련 코드 작성 부분



        // ListView 어댑터
        val adapter =
            RecentlyProjectViewMoreAdapter(
                this,
                recentlyregister
            )
        detail_recentlyprint_listview.adapter = adapter

    }
}
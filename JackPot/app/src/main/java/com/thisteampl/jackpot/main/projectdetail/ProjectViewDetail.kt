package com.thisteampl.jackpot.main.projectdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.MainActivity
import com.thisteampl.jackpot.main.filtering.FilteringSearch
import com.thisteampl.jackpot.main.userpage.MyPage
import kotlinx.android.synthetic.main.activity_project_view_detail.*

class ProjectViewDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_view_detail)

        // 상세 page text, intent로 정보 전달한다.
        var project = intent.getStringExtra("project")
        var position = intent.getStringExtra("position")

        projectview_projectname_textview.text = project

        // 이름, 지역 입력해야한다.

        // db에서 가져올 내용 text
        projectview_participate_textview.text = position

        
        // 수정할 부분
        projectview_favorite_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) projectview_favoritecount_textview.text = "1"
            else projectview_favoritecount_textview.text = "0"
        }

        val intent = Intent(this, MyPage::class.java)
        val mainintent = Intent(this, MainActivity::class.java)
        val searchintent = Intent(this, FilteringSearch::class.java)

        // 검색
//        projectviewdetail_search_imagebutton.setOnClickListener {
//            startActivity(searchintent)
//        }

        // 나의 페이지
        projectviewdetail_mypage_imagebutton.setOnClickListener{
            startActivity(intent)
        }

        // MainActivity 실행
        projectviewdetail_appname_imageview.setOnClickListener{
            startActivity(mainintent)
        }

    }
}
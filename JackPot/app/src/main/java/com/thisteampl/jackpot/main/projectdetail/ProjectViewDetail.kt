package com.thisteampl.jackpot.main.projectdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.MainActivity
import com.thisteampl.jackpot.main.mainhome.SearchViewPage
import com.thisteampl.jackpot.main.mypage.MyPage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.main_appname_textview
import kotlinx.android.synthetic.main.activity_project_view_detail.*

class ProjectViewDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_view_detail)

        // 상세 page text, intent로 정보 전달한다.
        var project = intent.getStringExtra("project")
        var position = intent.getStringExtra("position")
        var update = intent.getStringExtra("update")
        var stack1 = intent.getStringExtra("stack1")
        var stack2 = intent.getStringExtra("stack2")
        var stack3 = intent.getStringExtra("stack3")

        projectview_projectname_textview.text = project

        // 이름, 지역 입력해야한다.

        // db에서 가져올 내용 text
        projectview_inputupdate_textview.text = update
        projectview_stack1_textview.text = stack1
        projectview_stack2_textview.text = stack2
        projectview_stack3_textview.text = stack3
        projectview_participate_textview.text = position

        
        // 수정할 부분
        projectview_favorite_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) projectview_favoritecount_textview.text = "1"
            else projectview_favoritecount_textview.text = "0"
        }

        val intent = Intent(this, MyPage::class.java)
        val mainintent = Intent(this, MainActivity::class.java)
        val searchintent = Intent(this, SearchViewPage::class.java)

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
package com.thisteampl.jackpot.main.projectdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log

import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.common.GlobalApplication
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_project_view_detail.*

class ProjectViewDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_view_detail)



        Log.d("tag","id : ${intent.getLongExtra("id",0)}")

        setSupportActionBar(project_detail_toolbar) // 기본액션바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 제목 없애기
        setUpView()
    }

    private fun setUpView() {
        if (GlobalApplication.prefs.getString("token", "NO_TOKEN") == "NO_TOKEN") {
            project_detail_comment_edittext.hint = "로그인한 유저만 댓글을 달 수 있습니다."
            project_detail_comment_input_button.isEnabled = false
            project_detail_comment_edittext.isEnabled = false
            project_detail_project_scrap_button.isEnabled = false
            project_detail_project_register_button.isEnabled = false
        }

        project_detail_back_button.setOnClickListener { super.onBackPressed() }
    }

    //내 프로젝트일 경우 메뉴바 생성
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //if, 내프로젝트 아닐경우 return false
        val inflater = menuInflater
        inflater.inflate(R.menu.project_menu, menu)
        return true
    }

    // 메뉴바 선택시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //if, 내프로젝트 아닐경우 return false
        when(item.itemId) {
        }
        return super.onOptionsItemSelected(item)
    }
}
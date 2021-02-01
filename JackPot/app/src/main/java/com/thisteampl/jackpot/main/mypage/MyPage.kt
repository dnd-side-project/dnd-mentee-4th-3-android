package com.thisteampl.jackpot.main.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.LoginActivity
import kotlinx.android.synthetic.main.activity_my_page.*

class MyPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)
        setupView()
    }
        // 마이페이지 뷰 셋팅하는 메서드. 로그인 돼 있을 경우와 로그아웃 돼 있을 경우를 분리한다.
        private fun setupView(){
            //토큰이 있는 경우 : 로그아웃이 보이게, 토큰이 없는 경우 : 로그인이 보이게

            mypage_login_button.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }

            mypage_logout_button.setOnClickListener {
                mypage_profile_layout.visibility = View.GONE
                mypage_login_button.visibility = View.VISIBLE
                mypage_logout_button.visibility = View.GONE
           }
        }
}
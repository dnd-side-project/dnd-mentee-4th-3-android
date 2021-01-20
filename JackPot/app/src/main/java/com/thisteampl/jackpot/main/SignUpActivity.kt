package com.thisteampl.jackpot.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.user.UserApiClient
import com.thisteampl.jackpot.R
import kotlinx.android.synthetic.main.activity_signup.*

/* 회원가입을 위한 화면.*/

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setupView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        finish()
    }

    //만일 메인화면에서 넘어온 상태로, 회원가입이 된 상태라면 메인화면으로 간다.(기능구현예정)

    // 화면이 구성되고 View를 만들어 준다.
    private fun setupView(){

        /* 유저 정보에 저장해 둘 3개 SNS의 idx들*/
        var signUpType: Int = intent.getIntExtra("signuptype", 0)
        // 회원가입 타입, 0 : 일반회원가입, 1 : 카카오 로그인, 2 : 네이버 로그인, 3 : 구글 로그인
        var userIdx: Long? = 0

        if(signUpType == 1) {
            Toast.makeText(this, "카카오 로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            //카카오 로그인을 했을 시 카카오idx와 이름을 불러온다.
            UserApiClient.instance.me { user, error ->
                userIdx = user?.id
                signup_id_text.setText(userIdx.toString())
                signup_name_text.setText("${user?.kakaoAccount?.profile?.nickname}")
            }
        } else if(signUpType == 2) {
            Toast.makeText(this, "네이버 로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            //네이버 로그인을 했을 시 네이버idx와 이름을 불러온다.
            var id: String? = intent.getStringExtra("id")
            var name: String? = intent.getStringExtra("name")
            userIdx = id?.toLong()
            signup_id_text.setText(userIdx.toString())
            signup_name_text.setText(name)
        } else if(signUpType == 3) {
            //구글 로그인을 했을 시 구글idx와 이름을 불러온다.
        }

        signup_cancel_button.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }

        signup_finish_button.setOnClickListener{
            /*
            * 서버에 유저정보를 insert하는 코드
            * */
            Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }
    }
}
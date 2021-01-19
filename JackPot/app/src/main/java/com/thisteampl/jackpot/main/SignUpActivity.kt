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

    //만일 메인화면에서 넘어온 상태로, SNS 로그인이 돼 있다면 메인화면으로 간다.(기능구현예정)

    // 화면이 구성되고 View를 만들어 준다.
    private fun setupView(){

        /* 유저 정보에 저장해 둘 3개 SNS의 idx들*/
        var kakaoidx:Long? = 0
        var googleidx:Long? = 0
        var naveridx:Long? = 0

        //카카오 로그인을 했을 시 카카오idx와 이름을 불러온다.
        UserApiClient.instance.me { user, error ->
            if (error == null) {
                kakaoidx = user?.id
                signup_name_text.setText("${user?.kakaoAccount?.profile?.nickname}")
            }
        }

        signup_cancel_button.setOnClickListener {

            // 취소 버튼 눌렀을 시  KAKAO 로그아웃.
            UserApiClient.instance.logout { error ->
                if (error == null) {
                    Toast.makeText(this, "카카오 계정이 로그아웃 됐습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

        signup_finish_button.setOnClickListener{
            Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }
    }
}
package com.thisteampl.jackpot.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.user.UserApiClient
import com.thisteampl.jackpot.R
import kotlinx.android.synthetic.main.activity_signup.*

/* 회원가입을 위한 화면.
* 지역 스피너 : https://black-jin0427.tistory.com/222 참고했음.
* */

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

        signup_developer_radio_button.isChecked = true // 개발자로 체크를 해 둔다.

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

        var regions = arrayOf("서울","부산","대구","인천","광주",
            "대전","울산","세종","경기도","강원도","충청북도","충청남도","전라북도","전라남도","경상북도","경상남도","제주도")
        val regionAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, regions)
        // 지역 선택을 해 줄 배열과 액티비티의 스피너와 연결해줄 어댑터.

        signup_region_spinner.adapter = regionAdapter // 스피너와 어댑터를 연결

       signup_region_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Toast.makeText(baseContext, regions[position], Toast.LENGTH_SHORT).show()

                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when(position) {
                    0   ->  {

                    }
                    1   ->  {

                    }
                    //...
                    else -> {

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


    }
}
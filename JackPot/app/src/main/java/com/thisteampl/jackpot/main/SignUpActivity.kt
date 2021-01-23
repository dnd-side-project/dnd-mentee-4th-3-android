package com.thisteampl.jackpot.main

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.user.UserApiClient
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.common.AppDatabase
import com.thisteampl.jackpot.main.user.User
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.regex.Pattern

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
        var thirdPartyID: String
        var regionIdx = 0

        if(signUpType == 1) {
            Toast.makeText(this, "카카오 로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            //카카오 로그인을 했을 시 카카오idx와 이름을 불러온다.
            UserApiClient.instance.me { user, error ->
                thirdPartyID = user?.id.toString()
                signup_id_text.setText(thirdPartyID)
                signup_name_text.setText("${user?.kakaoAccount?.profile?.nickname}")
            }
        } else if(signUpType == 2) {
            Toast.makeText(this, "네이버 로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            //네이버 로그인을 했을 시 네이버idx와 이름을 불러온다.
            var id: String? = intent.getStringExtra("id")
            var name: String? = intent.getStringExtra("name")
            thirdPartyID = id.toString()
            signup_id_text.setText(thirdPartyID)
            signup_name_text.setText(name)
        } else if(signUpType == 3) {
            Toast.makeText(this, "구글 로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            //구글 로그인을 했을 시 구글idx와 이름을 불러온다.
            var id: String? = intent.getStringExtra("id")
            var name: String? = intent.getStringExtra("name")
            thirdPartyID = id.toString()
            signup_id_text.setText(thirdPartyID)
            signup_name_text.setText(name)
        }

        var regions = arrayOf("서울","부산","대구","인천","광주",
            "대전","울산","세종","경기도","강원도","충청북도","충청남도","전라북도","전라남도","경상북도","경상남도","제주도")
        val regionAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, regions)
        // 지역 선택을 해 줄 배열과 액티비티의 스피너와 연결해줄 어댑터.

        signup_region_spinner.adapter = regionAdapter // 스피너와 어댑터를 연결

        signup_region_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                regionIdx = position
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

        signup_prev_button.setOnClickListener {
            signup_must_information_layout.visibility = View.VISIBLE
            signup_next_button.visibility = View.VISIBLE
            signup_cancel_button.visibility = View.VISIBLE

            signup_add_information_layout.visibility = View.GONE
            signup_finish_button.visibility = View.GONE
            signup_prev_button.visibility = View.GONE
        }

        signup_cancel_button.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }

        signup_next_button.setOnClickListener {
            if(signup_id_text.text.trim().length < 6 || signup_id_text.text.trim().length > 12) {
                Toast.makeText(this, "아이디는 최소 6글자 최대 12글자 입니다.", Toast.LENGTH_SHORT).show()
            } else if(signup_password_text.text.length < 6 || signup_password_text.text.length > 15) {
                Toast.makeText(this, "비밀번호는 최소 6글자 최대 15글자 입니다.", Toast.LENGTH_SHORT).show()
            } else if(signup_password_check_text.text.toString() != signup_password_text.text.toString()) {
                Toast.makeText(this, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
            else if(signup_name_text.text.trim().length < 3 || signup_name_text.text.trim().length > 6) {
                Toast.makeText(this, "이름은 최소 3글자 최대 6글자 입니다.", Toast.LENGTH_SHORT).show()
            } else {
                // 조건이 다 맞다면, 추가입력 화면으로 넘어가게 하기.
                signup_add_information_layout.visibility = View.VISIBLE
                signup_finish_button.visibility = View.VISIBLE
                signup_prev_button.visibility = View.VISIBLE

                signup_must_information_layout.visibility = View.GONE
                signup_next_button.visibility = View.GONE
                signup_cancel_button.visibility = View.GONE
            }
        }

        signup_finish_button.setOnClickListener{
            /*
            * 서버에 유저정보를 insert하는 코드
            * */
            var job = 0
            if(signup_developer_radio_button.isChecked) { job = 0}
            else { job = 1 }

            AppDatabase.instance.userDao()
                .insert(
                    User(0, signup_id_text.text.toString(), "0", signup_name_text.text.toString(),
                        regions[regionIdx],
                     job, 0, signup_introduce_text.text.toString())
                )

            Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }

        signup_designer_radio_button.setOnClickListener{
            signup_developer_stack_layout.visibility = View.GONE
            signup_designer_stack_layout.visibility = View.VISIBLE
        }

        signup_developer_radio_button.setOnClickListener {
            signup_developer_stack_layout.visibility = View.VISIBLE
            signup_designer_stack_layout.visibility = View.GONE
        }


        // 아이디 정규식 영문, 숫자 최대 10글자
        // https://jo-coder.tistory.com/19 참고.
        signup_id_text.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
            val ps: Pattern =
                Pattern.compile("^[a-zA-Z0-9\\u318D\\u119E\\u11A2\\u2022\\u2025a\\u00B7\\uFE55]+$")
            if (source == "" || ps.matcher(source).matches()) {
                return@InputFilter source
            }
            Toast.makeText( this, "영문, 숫자만 입력 가능합니다.", Toast.LENGTH_SHORT).show()
            ""
        }, InputFilter.LengthFilter(10))



    }
}
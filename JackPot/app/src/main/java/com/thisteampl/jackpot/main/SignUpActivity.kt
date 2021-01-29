package com.thisteampl.jackpot.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.kakao.sdk.user.UserApiClient
import com.thisteampl.jackpot.R
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.regex.Pattern

/* 회원가입을 위한 화면.
* 지역 스피너 : https://black-jin0427.tistory.com/222 참고했음.
* */

class SignUpActivity : AppCompatActivity() {

    //이메일 정규식 확인, https://blog.codejun.space/49
    val EMAIL_ADDRESS_PATTERN : Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
    )

    private fun checkEmail(email: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

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
        var emailCheck: Boolean = false

        /* 유저 정보에 저장해 둘 3개 SNS의 idx들*/
        var signUpType: Int = intent.getIntExtra("signuptype", 0)
        // 회원가입 타입, 0 : 일반회원가입, 1 : 카카오 로그인, 2 : 네이버 로그인, 3 : 구글 로그인
        var token: String = intent.getStringExtra("token").toString()
        var regionIdx = 0

        if(signUpType == 1) {
            Toast.makeText(this, "카카오 로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            //카카오 로그인을 했을 시 카카오idx와 이름을 불러온다.
            UserApiClient.instance.me { user, error ->
                signup_name_text.setText(token)
                //signup_name_text.setText("${user?.kakaoAccount?.profile?.nickname}")
            }

        } else if(signUpType == 2) {
            Toast.makeText(this, "네이버 로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            //네이버 로그인을 했을 시 네이버idx와 이름을 불러온다.
            signup_name_text.setText(token)

        } else if(signUpType == 3) {
            Toast.makeText(this, "구글 로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            //구글 로그인을 했을 시 구글idx와 이름을 불러온다.
            signup_name_text.setText(token)

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
            if(!emailCheck){
                Toast.makeText(this, "이메일 중복 확인을 해주세요.", Toast.LENGTH_SHORT).show()
            }else if(signup_password_text.text.length < 6 || signup_password_text.text.length > 15) {
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
            var job = ""
            if(signup_developer_radio_button.isChecked) { job = "개발자"}
            else if(signup_designer_radio_button.isChecked) { job = "디자이너" }
            else { job = "기획자" }

            /*
            * 서버에 유저정보를 insert하는 코드
            * */

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

        signup_director_radio_button.setOnClickListener {
            signup_stack_layout.visibility = View.GONE
        }

        // 이메일 체크를 위한 메서드, 중복확인하고 이메일을 바꿀 수 있으므로 변경 감지.
        // https://minwook-shin.github.io/android-kotlin-text-watcher/
        signup_id_text.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                //입력이 끝날때 작동
                emailCheck = false
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //입력 전에 작동
                emailCheck = false
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //타이핑 되는 텍스트에 변화가 있을 경우.
                emailCheck = false
            }
        })

        signup_id_check_button.setOnClickListener {
            //아이디 중복확인, 추후에
            if (!checkEmail(signup_id_text.text.toString())) {
                Toast.makeText(this, "올바른 이메일 패턴을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }else {
                emailCheck = true
            }
        }
    }
}
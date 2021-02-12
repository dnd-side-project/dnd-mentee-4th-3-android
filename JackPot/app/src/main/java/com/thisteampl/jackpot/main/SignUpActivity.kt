package com.thisteampl.jackpot.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kakao.sdk.user.UserApiClient
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.userController.CheckResponse
import com.thisteampl.jackpot.main.userController.SignUp
import com.thisteampl.jackpot.main.userController.userAPI
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.regex.Pattern
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

/* 회원가입을 위한 화면.
* 지역 스피너 : https://black-jin0427.tistory.com/222 참고했음.
* */

class SignUpActivity : AppCompatActivity() {

    private var positionBtn = arrayOfNulls<Button>(3)
    private var stateBtn = arrayOfNulls<Button>(3)
    private var sGradeBtn = arrayOfNulls<Button>(4)
    private var regionBtn = arrayOfNulls<Button>(18)
    private val stackTool = mutableListOf<String>()

    // 화면전환 애니메이션, fillAfter : 옮긴 후 원상복구, duration : 지속시간
    private val anim: Animation = AlphaAnimation(0f, 1f).apply {
        fillAfter = true
        duration = 350
    }
    private var emailCheck: Boolean = false
    private var nameCheck: Boolean = false
    var authCode = ""
    private val userApi = userAPI.create()
    private var page: Int = 0
    /*
    * page 변수에 따라 보여지는 layout을 visible 해준다.
    * SNS로 시작할 경우 -> 1페이지부터 시작
    * 0 : 이메일, 비밀번호 적는 페이지(이메일로 시작하기)
    * 1 : 닉네임 적는 페이지 / 2 : 지역 / 3 : 직군 / 4 : 상태 / 5 : 스택 / 6 : 자기소개 / 7 : 프로필 공개여부
    * */

    /* 유저 정보에 저장해 둘 3개 SNS의 idx들*/
    private var region = "지역" // 지역 저장용
    private var position = "직군" // 직군 : 기획자, 개발자, 디자이너
    private var state = "상태" // 상태 : 학생, 취업 준비생, 주니어
    private var signUpType = ""
    // 회원가입 상태, normal : 일반 로그인, kakao, naver, google : 서드파티 로그인

    //이메일 정규식 확인, https://blog.codejun.space/49
    private val EMAIL_ADDRESS_PATTERN : Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
    )

    //닉네임 정규식 확인, https://jizard.tistory.com/233
    fun isValidNickname(nickname: String?): Boolean {
        val trimmedNickname = nickname?.trim().toString()
        val exp = Regex("^[가-힣ㄱ-ㅎa-zA-Z0-9._-]{3,}\$")
        return !trimmedNickname.isNullOrEmpty() && exp.matches(trimmedNickname)
    }


    private fun checkEmail(email: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setupView()
    }

    override fun onBackPressed() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        finish()
    }

    //만일 메인화면에서 넘어온 상태로, 회원가입이 된 상태라면 메인화면으로 간다.(기능구현예정)

    // 화면이 구성되고 View를 만들어 준다.
    private fun setupView(){
        signUpType = intent.getStringExtra("signuptype").toString()

        when (signUpType) {

            "normal" -> {
                signup_email_signup_layout.visibility = View.VISIBLE
            }
            "kakao" -> {
                //카카오 로그인을 했을 시 카카오idx와 이름을 불러온다.
                signup_nickname_layout.visibility = View.VISIBLE
                page = 1
                UserApiClient.instance.me { user, error ->
                    signup_id_text.setText(user?.id.toString())
                }

            }
            "naver" -> {
                //네이버 로그인을 했을 시 네이버idx와 이름을 불러온다.
                signup_nickname_layout.visibility = View.VISIBLE
                signup_id_text.setText(intent.getStringExtra("SNSID").toString())
                page = 1
            }
            "google" -> {
                //구글 로그인을 했을 시 구글idx와 이름을 불러온다.
                signup_nickname_layout.visibility = View.VISIBLE
                signup_id_text.setText(intent.getStringExtra("SNSID").toString())
                page = 1
            }
        }

        signup_page_viewer.text = "$page / 6"

        //뷰들에 애니메이션을 적용해준다
        for (i in 0 until signup_screen.childCount) {
            val child: View = signup_screen.getChildAt(i)
            if(child is Button) {
                child.animation = anim
            }
        }


        //닉네임 중복 확인 버튼
        signup_name_check_button.setOnClickListener {
            if(!isValidNickname(signup_name_text.text.toString())) {
                Toast.makeText(this, "유효한 닉네임을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else if(signup_name_text.text.trim().length < 3 || signup_name_text.text.trim().length > 8) {
                Toast.makeText(this, "닉네임은 최소 3글자 최대 8글자 입니다.", Toast.LENGTH_SHORT).show()
            } else {
                userApi?.getCheckName(signup_name_text.text.toString())
                    ?.enqueue(object : Callback<CheckResponse> {
                        override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                            // userAPI에서 타입이나 이름 안맞췄을때
                            Log.e("tag ", "onFailure" + t.localizedMessage)
                        }

                        override fun onResponse(
                            call: Call<CheckResponse>,
                            response: Response<CheckResponse>
                        ) {
                            if (response.code().toString() == "404") {
                                Toast.makeText(
                                    baseContext,
                                    "사용 가능한 닉네임입니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                nameCheck = true
                            } else {
                                Toast.makeText(
                                    baseContext,
                                    "사용 불가능한 닉네임입니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
            }
        }

        // 회원가입 다음 버튼
        signup_confirm_button.setOnClickListener {
            when (page) {
                0 -> {
                    if(!emailCheck){
                        Toast.makeText(this, "이메일 중복 확인을 해주세요.", Toast.LENGTH_SHORT).show()
                    }else if(signup_password_text.text.length < 6 || signup_password_text.text.length > 15) {
                        Toast.makeText(this, "비밀번호는 최소 6글자 최대 15글자 입니다.", Toast.LENGTH_SHORT).show()
                    } else if(signup_password_check_text.text.toString() != signup_password_text.text.toString()) {
                        Toast.makeText(this, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        page = 1
                        signup_email_signup_layout.visibility = View.GONE
                        signup_nickname_layout.visibility = View.VISIBLE
                        signup_previous_button.visibility = View.VISIBLE
                    }
                    signup_page_viewer.text = "$page / 6"
                }
                1 -> {
                    if(!nameCheck) {
                        Toast.makeText(this, "닉네임 중복 확인을 해주세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        page = 2
                        signup_nickname_layout.visibility = View.GONE
                        signup_previous_button.visibility = View.VISIBLE
                        signup_region_layout.visibility = View.VISIBLE
                        signup_call_name_position_text.text =
                            signup_name_text.text.toString() + "님, 어떤\n포지션에 해당하시나요?"
                    }
                    signup_page_viewer.text = "$page / 6"
                }
                2 -> {
                    if(region == "지역") {
                        Toast.makeText(this, "지역을 선택해주세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        page = 3
                        signup_region_layout.visibility = View.GONE
                        signup_position_layout.visibility = View.VISIBLE
                    }
                    signup_page_viewer.text = "$page / 6"
                }
                3 -> {
                    if(position == "직군") {
                        Toast.makeText(this, "직군을 선택해주세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        page = 4
                        signup_position_layout.visibility = View.GONE
                        signup_state_layout.visibility = View.VISIBLE
                    }
                    if(state[0] == '학' && state[1] == '생') {
                        signup_state_grade_layout.visibility = View.VISIBLE // 학생일때 보이기 (이전갔다가 돌아올 때)
                    }
                    signup_page_viewer.text = "$page / 6"
                }
                4 -> {
                    when (state) {
                        "상태" -> {
                            Toast.makeText(this, "현재 상태를 선택해주세요.", Toast.LENGTH_SHORT).show()
                        }
                        "학생" -> {
                            Toast.makeText(this, "학년을 선택해주세요.", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            page = 5
                            signup_state_layout.visibility = View.GONE
                            if(state[0] == '학' && state[1] == '생') {
                                signup_state_grade_layout.visibility = View.GONE // 학생일때 가리기
                            }
                            when (position) {
                                "개발자" -> {
                                    signup_developer_stack_layout.visibility = View.VISIBLE
                                }
                                "디자이너" -> {
                                    signup_designer_tool_layout.visibility = View.VISIBLE
                                }
                                else -> {
                                    page = 6
                                    signup_introduce_layout.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
                    signup_page_viewer.text = "$page / 6"
                }
                5 -> {
                    page = 6
                    if(position == "개발자") {
                        signup_developer_stack_layout.visibility = View.GONE
                    } else {
                        signup_designer_tool_layout.visibility = View.GONE
                    }
                    signup_introduce_layout.visibility = View.VISIBLE
                    signup_page_viewer.text = "$page / 6"
                }
                6 -> {
                    page = 7
                    signup_page_viewer.visibility = View.GONE
                    signup_introduce_layout.visibility = View.GONE
                    signup_exit_button.visibility = View.GONE
                    signup_open_layout.visibility = View.VISIBLE

                    signup_confirm_button.text = "공개할래요"
                    signup_previous_button.text = "공개하고싶지 않아요"
                }
                7 -> { // 프로필 공개 여부
                    signUp("yes")
                }
            }
        }

        //이메일 체크 버튼
        signup_id_check_button.setOnClickListener {

            if (!checkEmail(signup_id_text.text.toString())) {
                Toast.makeText(this, "올바른 이메일 패턴을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else {
                userApi?.getCheckEmail(signup_id_text.text.toString())
                    ?.enqueue(object : Callback<CheckResponse> {
                        override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                            // userAPI에서 타입이나 이름 안맞췄을때
                            Log.e("tag ", "onFailure" + t.localizedMessage)
                        }

                        override fun onResponse(
                            call: Call<CheckResponse>,
                            response: Response<CheckResponse>
                        ) {
                            authCode = makeCode()
                            if (response.code().toString() == "404") {
                                sendEmail(signup_id_text.text.toString(), authCode)
                                Toast.makeText(baseContext, "입력하신 이메일로 인증을 보냈습니다.\n인증코드를 입력해 주세요.", Toast.LENGTH_SHORT)
                                    .show()
                                signup_auth_layout.visibility = View.VISIBLE
                            } else {
                                Toast.makeText(
                                    baseContext,
                                    "이미 사용중이거나\n사용 불가능한 이메일입니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
            }
        }

        //인증번호 입력 버튼
        signup_auth_check_button.setOnClickListener {
            if(signup_auth_text.text.toString() == authCode) {
                Toast.makeText(baseContext, "인증되었습니다.", Toast.LENGTH_SHORT)
                    .show()
                signup_auth_text.isEnabled = false
                signup_id_text.isEnabled = false
                signup_auth_check_button.isEnabled = false
                signup_id_check_button.isEnabled = false
                emailCheck = true
            } else {
                Toast.makeText(baseContext, "인증코드를 확인해 주세요.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        //이전 버튼
        signup_previous_button.setOnClickListener {
            when (page) {
                1 -> {
                    page = 0
                    signup_previous_button.visibility = View.GONE
                    signup_email_signup_layout.visibility = View.VISIBLE
                    signup_nickname_layout.visibility = View.GONE
                    signup_page_viewer.text = "$page / 6"
                }
                2 -> {
                    if(signUpType != "normal") {
                        signup_previous_button.visibility = View.GONE
                    }
                    page = 1
                    signup_nickname_layout.visibility = View.VISIBLE
                    signup_region_layout.visibility = View.GONE
                    signup_page_viewer.text = "$page / 6"
                }
                3 -> {
                    page = 2
                    signup_region_layout.visibility = View.VISIBLE
                    signup_position_layout.visibility = View.GONE
                    signup_page_viewer.text = "$page / 6"
                }
                4 -> {
                    page = 3
                    signup_position_layout.visibility = View.VISIBLE
                    signup_state_layout.visibility = View.GONE
                    if(state[0] == '학' && state[1] == '생') {
                        signup_state_grade_layout.visibility = View.GONE // 학생일때 가리기
                    }
                    signup_page_viewer.text = "$page / 6"
                }
                5 -> {
                    page = 4
                    signup_state_layout.visibility = View.VISIBLE
                    if(position == "개발자") {
                        signup_developer_stack_layout.visibility = View.GONE
                    } else {
                        signup_designer_tool_layout.visibility = View.GONE
                    }
                    if(state[0] == '학' && state[1] == '생') {
                        signup_state_grade_layout.visibility = View.VISIBLE // 학생일때 보이기
                    }
                    signup_page_viewer.text = "$page / 6"
                }
                6 -> {
                    page = 5
                    when (position) {
                        "개발자" -> {
                            signup_developer_stack_layout.visibility = View.VISIBLE
                        }
                        "디자이너" -> {
                            signup_designer_tool_layout.visibility = View.VISIBLE
                        }
                        else -> {
                            page = 4
                            signup_state_layout.visibility = View.VISIBLE
                            if(state[0] == '학' && state[1] == '생') {
                                signup_state_grade_layout.visibility = View.VISIBLE // 학생일때 보이기
                            }
                        }
                    }
                    signup_introduce_layout.visibility = View.GONE
                    signup_page_viewer.text = "$page / 6"
                }
                7 -> { // 프로필 공개 여부.
                    signUp("no")
                }
            }
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

        //닉네임 체크를 위한 메서드, 위와 마찬가지로 변경 감지
        signup_name_text.addTextChangedListener (object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                //입력이 끝날때 작동
                nameCheck = false
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //입력 전에 작동
                nameCheck = false
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //타이핑 되는 텍스트에 변화가 있을 경우.
                nameCheck = false
            }
        })

        //나가기 버튼
        signup_exit_button.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }

        makeBtnFunc()
    }

    //버튼 선택 리스너. 선택 시 테두리 색과 텍스트 색상이 바뀌게 했다.
    private fun btnOnClick(v : View) {
        var id = v.id

        // 직군 선택 버튼의 리스너 설정
        if(id == R.id.signup_designer_button || id == R.id.signup_developer_button
            || id == R.id.signup_director_button) {
            var pos = Integer.parseInt(v.contentDescription.toString())
            for(i in 0..2) {
                if(i == pos) {
                    setStackToolBtn()
                    positionBtn[i]?.background = ContextCompat.getDrawable(this@SignUpActivity,R.drawable.radius_background_transparent_select)
                    positionBtn[i]?.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorButtonSelect))
                    position = positionBtn[i]?.text.toString()
                }
                else {
                    positionBtn[i]?.background = ContextCompat.getDrawable(this@SignUpActivity, R.drawable.radius_button_effect)
                    positionBtn[i]?.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorBlack))
                }
            }
        }
        // 상태 선택 버튼의 리스너 설정
        else if(id == R.id.signup_state_student || id == R.id.signup_state_jobfinder
            || id == R.id.signup_state_junior) {
            var pos = Integer.parseInt(v.contentDescription.toString())
            for(i in 0..2) {
                if(i == pos) {
                    stateBtn[i]?.background = ContextCompat.getDrawable(this@SignUpActivity,R.drawable.radius_background_transparent_select)
                    stateBtn[i]?.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorButtonSelect))
                    state = stateBtn[i]?.text.toString()
                    if(i == 0) {
                        signup_state_grade_layout.visibility = View.VISIBLE
                    } else {
                        signup_state_grade_layout.visibility = View.GONE
                        for(j in 0..3) {
                            sGradeBtn[j]?.background = ContextCompat.getDrawable(this@SignUpActivity, R.drawable.radius_button_effect)
                            sGradeBtn[j]?.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorBlack))
                        }
                    }
                }
                else {
                    stateBtn[i]?.background = ContextCompat.getDrawable(this@SignUpActivity, R.drawable.radius_button_effect)
                    stateBtn[i]?.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorBlack))
                }
            }
        }
        // 학년 선택 버튼의 리스너 설정
        else if(id == R.id.signup_student_grade_one || id == R.id.signup_student_grade_two
            || id == R.id.signup_student_grade_three || id == R.id.signup_student_grade_four) {
            var pos = Integer.parseInt(v.contentDescription.toString())
            for(i in 0..3) {
                if(i == pos) {
                    sGradeBtn[i]?.background = ContextCompat.getDrawable(this@SignUpActivity,R.drawable.radius_background_transparent_select)
                    sGradeBtn[i]?.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorButtonSelect))
                    state = "학생 " + sGradeBtn[i]?.text.toString()
                }
                else {
                    sGradeBtn[i]?.background = ContextCompat.getDrawable(this@SignUpActivity, R.drawable.radius_button_effect)
                    sGradeBtn[i]?.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorBlack))
                }
            }
        }
        // 지역 선택 버튼 리스너 설정
        else {
            var pos = Integer.parseInt(v.contentDescription.toString())
            for(i in 0..17) {
                if(i == pos) {
                    regionBtn[i]?.background = ContextCompat.getDrawable(this@SignUpActivity,R.drawable.radius_background_transparent_select)
                    regionBtn[i]?.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorButtonSelect))
                    region = regionBtn[i]?.text.toString()
                }
                else {
                    regionBtn[i]?.background = ContextCompat.getDrawable(this@SignUpActivity, R.drawable.radius_button_effect)
                    regionBtn[i]?.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorBlack))
                }
            }
        }
    }

    private fun makeBtnFunc() {
        //직군 선택 리스터
        positionBtn[0] = signup_director_button
        positionBtn[1] = signup_developer_button
        positionBtn[2] = signup_designer_button
        for(i in 0..2) {
            positionBtn[i]?.setOnClickListener { positionBtn[i]?.let{it1 -> this.btnOnClick(it1)} }
        }

        //상태 선택 리스너
        stateBtn[0] = signup_state_student
        stateBtn[1] = signup_state_jobfinder
        stateBtn[2] = signup_state_junior
        for(i in 0..2) {
            stateBtn[i]?.setOnClickListener { stateBtn[i]?.let{it1 -> this.btnOnClick(it1)} }
        }

        //학생 학년 선택 리스너
        sGradeBtn[0] = signup_student_grade_one
        sGradeBtn[1] = signup_student_grade_two
        sGradeBtn[2] = signup_student_grade_three
        sGradeBtn[3] = signup_student_grade_four
        for(i in 0..3) {
            sGradeBtn[i]?.setOnClickListener { sGradeBtn[i]?.let{it1 -> this.btnOnClick(it1)} }
        }

        regionBtn[0] = signup_region_seoul
        regionBtn[1] = signup_region_gyeonggi
        regionBtn[2] = signup_region_incheon
        regionBtn[3] = signup_region_daejeon
        regionBtn[4] = signup_region_gwangju
        regionBtn[5] = signup_region_ulsan
        regionBtn[6] = signup_region_sejong
        regionBtn[7] = signup_region_daegu
        regionBtn[8] = signup_region_busan
        regionBtn[9] = signup_region_gangwondo
        regionBtn[10] = signup_region_chungbuk
        regionBtn[11] = signup_region_chungnam
        regionBtn[12] = signup_region_jeollabuk
        regionBtn[13] = signup_region_jeollanam
        regionBtn[14] = signup_region_gyeongnam
        regionBtn[15] = signup_region_gyeongbuk
        regionBtn[16] = signup_region_jejudo
        regionBtn[17] = signup_region_overseas
        for(i in 0..17) {
            regionBtn[i]?.setOnClickListener { regionBtn[i]?.let{it1 -> this.btnOnClick(it1)} }
        }
    }

    // 스택, 툴 버튼의 온클릭 리스너
    private fun stackToolBtnOnClick(button: Button) {
        if(!stackTool.contains(button.text.toString())) {
            button.background = ContextCompat.getDrawable(this@SignUpActivity, R.drawable.radius_background_transparent_select)
            button.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorButtonSelect))
            stackTool.add(button.text.toString())
        } else {
            button.background = ContextCompat.getDrawable(this@SignUpActivity, R.drawable.radius_button_effect)
            button.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorBlack))
            stackTool.remove(button.text.toString())
        }
    }

    /*
    * 스택, 툴 버튼을 설정해 주는 메서드, 그리고 스택, 툴을 저장해주는 리스트를 초기화해준다.
    * */
    private fun setStackToolBtn(){
        stackTool.clear()

        for (i in 0 until signup_developer_stack_layout.childCount) {
            val child: View = signup_developer_stack_layout.getChildAt(i)
            if(child is Button) {
                child.background = ContextCompat.getDrawable(this@SignUpActivity, R.drawable.radius_button_effect)
                child.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorBlack))
                child.setOnClickListener { this.stackToolBtnOnClick(child) }
            }
        }

        for (i in 0 until signup_designer_tool_layout.childCount) {
            val child: View = signup_designer_tool_layout.getChildAt(i)
            if(child is Button) {
                child.background = ContextCompat.getDrawable(this@SignUpActivity, R.drawable.radius_button_effect)
                child.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorBlack))
                child.setOnClickListener { this.stackToolBtnOnClick(child) }
            }
        }
    }

    //이메일 인증을 위한 랜덤 코드
    fun makeCode(): String {
        val str = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9")

        var code = ""
        for(i in 1..8) {
            val random = Random()
            val num = random.nextInt(str.size)
            code += str[num]
        }
        return code
    }

    //이메일 인증 메일 보내기 https://heegyukim.medium.com/
    fun sendEmail(
        dest: String,       // 받는 메일 주소
        code: String       // 인증 코드
    )
    {

        var title = "잭팟 인증번호 메일입니다."      // 메일 제목
        var body = "인증번호는 $code 입니다."       // 메일 내용

        // 보내는 메일 주소와 비밀번호
        val username = "dndjackpot3@gmail.com"
        val password = ""

        val props = Properties();
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.smtp.host"] = "smtp.gmail.com"
        props["mail.smtp.port"] = "587"

        // 비밀번호 인증으로 세션 생성
        val session = Session.getInstance(props,
            object: javax.mail.Authenticator() {
                override  fun getPasswordAuthentication(): javax.mail.PasswordAuthentication {
                    return javax.mail.PasswordAuthentication(username, password)
                }
            })

        // 메시지 객체 만들기
        val message = MimeMessage(session)
        message.setFrom(InternetAddress(username))
        // 수신자 설정, 여러명으로도 가능
        message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse(dest))
        message.subject = title
        message.setText(body)

        // 전송
        Thread {
            Transport.send(message)
        }.start()
    }

    //회원가입 완료 메서드. 매개변수로 프로필 공개 여부를 넣어준다.
    private fun signUp(profileOpen: String) {
        var signUp = SignUp("ROLE_USER", state, signup_id_text.text.toString(),
            position, signUpType, signup_name_text.text.toString(), signup_password_text.text.toString(),
            region, stackTool)

        userApi?.getUserSignUp(signUp)
            ?.enqueue(object : Callback<CheckResponse>{
                override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                    // userAPI에서 타입이나 이름 안맞췄을때
                    Log.e("tag ", "onFailure" + t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<CheckResponse>,
                    response: Response<CheckResponse>
                ) {
                    if(response.code().toString() == "200") {
                        Toast.makeText(baseContext, "\uD83C\uDF89회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(baseContext, LoginActivity::class.java)
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        finish()
                    } else {
                        Toast.makeText(baseContext, "회원가입에 실패했습니다.\n에러 코드 : " + response.code() + "\n" + response.body().toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            })
    }
}
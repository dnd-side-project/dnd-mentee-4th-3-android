package com.thisteampl.jackpot.main.userpage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.userController.*
import kotlinx.android.synthetic.main.activity_profile_edit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//https://m.blog.naver.com/l5547/221845481754, OnActivityResult

class ProfileEditActivity: AppCompatActivity() {

    private val userApi = userAPI.create()
    lateinit var userprofile : MyProfile
    lateinit var updateprofile : MyProfileEdit
    private var stackTool = mutableListOf<String>()
    private var nameCheck: Boolean = false

    private val EMOJI_REQUEST_CODE = 100
    private val STATE_REQUEST_CODE = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        setupView()
    }

    private fun setupView(){
        profile_edit_back_button.setOnClickListener { onBackPressed() }
        getProfile()
        setStackToolBtn()

        profile_edit_name_check_button.setOnClickListener {
            if(!isValidNickname(profile_edit_name_edittext.text.toString())) {
                Toast.makeText(this, "유효한 닉네임을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else if(profile_edit_name_edittext.text.trim().length < 3 || profile_edit_name_edittext.text.trim().length > 8) {
                Toast.makeText(this, "닉네임은 최소 3글자 최대 8글자 입니다.", Toast.LENGTH_SHORT).show()
            } else {
                userApi?.getCheckName(profile_edit_name_edittext.text.toString())
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
                                    "이미 사용중이거나\n사용 불가능한 닉네임입니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
            }
        }

        //닉네임 체크를 위한 메서드, 변경 감지
        profile_edit_name_edittext.addTextChangedListener (object : TextWatcher {
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

        //이모지 수정버튼
        profile_edit_job_change_icon_layout_button.setOnClickListener {
            val intent = Intent(baseContext, ProfileEditChangeEmojiActivity::class.java)
            startActivityForResult(intent, EMOJI_REQUEST_CODE)
        }

        //상태 수정버튼
        profile_edit_change_state_layout_button.setOnClickListener {
            val intent = Intent(baseContext, ProfileEditChangeStateActivity::class.java)
            startActivityForResult(intent, STATE_REQUEST_CODE)
        }

        //회원정보 수정 확인버튼
        profile_edit_confirm_button.setOnClickListener {
            if(!nameCheck && profile_edit_name_edittext.text.toString() != userprofile.name) {
                Toast.makeText(this, "닉네임 중복 확인을 해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                updateprofile.introduction = profile_edit_introduce_text.text.toString()
                updateprofile.portfolioLink1 = profile_edit_link_first_edittext.text.toString()
                updateprofile.portfolioLink2 = profile_edit_link_second_edittext.text.toString()
                updateprofile.name = profile_edit_name_edittext.text.toString()
                updateprofile.stacks = stackTool
                setProfile(updateprofile)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != Activity.RESULT_OK) {
            return
        }
        if(requestCode == EMOJI_REQUEST_CODE) {
            updateprofile.emoticon = data?.extras?.getString("emoji").toString()
            profile_edit_job_icon_text.text = updateprofile.emoticon
        } else if(requestCode == STATE_REQUEST_CODE) {
            updateprofile.career = data?.extras?.getString("state").toString()
            profile_edit_job_text2.text = updateprofile.position + " ・ " + updateprofile.career
        }
    }


    //닉네임 정규식 확인, https://jizard.tistory.com/233
    fun isValidNickname(nickname: String?): Boolean {
        val trimmedNickname = nickname?.trim().toString()
        val exp = Regex("^[가-힣ㄱ-ㅎa-zA-Z0-9._-]{3,}\$")
        return !trimmedNickname.isNullOrEmpty() && exp.matches(trimmedNickname)
    }

    // 프로필을 가져오는 메서드.
    private fun getProfile(){
        userApi?.getProfile()?.enqueue(
            object : Callback<CheckProfile> {
                override fun onFailure(call: Call<CheckProfile>, t: Throwable) {
                    // userAPI에서 타입이나 이름 안맞췄을때
                    Log.e("tag ", "onFailure, " + t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<CheckProfile>,
                    response: Response<CheckProfile>
                ) {
                    when {
                        response.code().toString() == "200" -> {

                            userprofile = response.body()!!.result
                            stackTool = response.body()!!.result.stacks.toMutableList()
                            initialize()

                            profile_edit_job_text.text = response.body()!!.result.position + " ・ " + response.body()!!.result.career
                            profile_edit_job_text2.text = response.body()!!.result.position + " ・ " + response.body()!!.result.career
                            profile_edit_job_icon_text.text = response.body()!!.result.emoticon

                            profile_edit_link_first_edittext.setText(response.body()!!.result.portfolioLink1)
                            profile_edit_link_second_edittext.setText(response.body()!!.result.portfolioLink2)
                            profile_edit_introduce_text.setText(response.body()!!.result.introduction)

                            profile_edit_name_text.text = response.body()!!.result.name
                            profile_edit_name_edittext.setText(response.body()!!.result.name)
                            if(response.body()!!.result.privacy) {
                                profile_edit_profile_close_image.visibility = View.GONE
                            }

                            when (response.body()!!.result.position) {
                                "개발자" -> {
                                    profile_edit_developer_stack_layout.visibility = View.VISIBLE
                                    // 기술스택 버튼 눌리게하기.
                                    for (i in response.body()!!.result.stacks) {
                                        for(j in 0 until profile_edit_developer_stack_layout.childCount) {
                                            val child: View = profile_edit_developer_stack_layout.getChildAt(j)
                                            if(child is Button && child.text == i) {
                                                child.background = ContextCompat.getDrawable(this@ProfileEditActivity, R.drawable.radius_background_transparent_select)
                                                child.setTextColor(ContextCompat.getColor(this@ProfileEditActivity, R.color.colorButtonSelect))
                                                break
                                            }
                                        }
                                    }
                                    profile_edit_link_first_edittext.hint = " 깃허브 링크를 입력해주세요."
                                    var drawble = ContextCompat.getDrawable(baseContext,R.drawable.android_signup_github)
                                    drawble?.setBounds(0,0, 120, 120)
                                    profile_edit_link_first_edittext.setCompoundDrawables(drawble,
                                        null, null, null)
                                    profile_edit_job_background_image.setImageResource(R.drawable.background_developer)
                                }
                                "디자이너" -> {
                                    profile_edit_designer_tool_layout.visibility = View.VISIBLE
                                    // 툴 버튼 눌리게하기.
                                    for (i in response.body()!!.result.stacks) {
                                        for(j in 0 until profile_edit_designer_tool_layout.childCount) {
                                            val child: View = profile_edit_designer_tool_layout.getChildAt(j)
                                            if(child is Button && child.text == i) {
                                                child.background = ContextCompat.getDrawable(this@ProfileEditActivity, R.drawable.radius_background_transparent_select)
                                                child.setTextColor(ContextCompat.getColor(this@ProfileEditActivity, R.color.colorButtonSelect))
                                                break
                                            }
                                        }
                                    }
                                    profile_edit_link_first_edittext.hint = " 비핸스 링크를 입력해주세요."
                                    var drawble = ContextCompat.getDrawable(baseContext,R.drawable.android_signup_behance)
                                    drawble?.setBounds(0,0, 120, 120)
                                    profile_edit_link_first_edittext.setCompoundDrawables(drawble,
                                        null, null, null)
                                    profile_edit_job_background_image.setImageResource(R.drawable.background_designer)
                                }
                                else -> {
                                    profile_edit_job_background_image.setImageResource(R.drawable.background_director)

                                    profile_edit_link_first_edittext.hint = " 개인 웹사이트가 있다면 입력해주세요."
                                    var drawble = ContextCompat.getDrawable(baseContext,R.drawable.android_signup_global)
                                    drawble?.setBounds(0,0, 120, 120)
                                    profile_edit_link_first_edittext.setCompoundDrawables(drawble,
                                        null, null, null)
                                    profile_edit_link_second_edittext.visibility = View.GONE
                                }
                            }
                            //포트폴리오, 자기소개 추가해야 함.
                        }
                        else -> {
                            Toast.makeText(
                                baseContext, "에러가 발생했습니다. 에러코드 : " + response.code()
                                , Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
    }

    // 프로필을 수정하는 메서드. 여기서는 프로필 공개 여부만 변경한다.
    private fun setProfile(updateProfile: MyProfileEdit){
        userApi?.getUpdateProfile(updateprofile)?.enqueue(
            object : Callback<CheckResponse> {
                override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                    // userAPI에서 타입이나 이름 안맞췄을때
                    Log.e("tag ", "onFailure, " + t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<CheckResponse>,
                    response: Response<CheckResponse>
                ) {
                    when {
                        response.code().toString() == "200" -> {
                            finish()
                            val intent1 = Intent(baseContext, MyPageActivity::class.java)
                            startActivity(intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                            val intent2 = Intent(baseContext, ProfileActivity::class.java).putExtra("title", "내 프로필")
                            startActivity(intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                            Toast.makeText(
                                baseContext, "회원님의 정보가 수정되었습니다."
                                , Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {
                            Toast.makeText(
                                baseContext, "에러가 발생했습니다. 에러코드 : " + response.code()
                                , Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
    }

    // 스택, 툴 버튼의 온클릭 리스너
    private fun stackToolBtnOnClick(button: Button) {
        if(!stackTool.contains(button.text.toString())) {
            button.background = ContextCompat.getDrawable(this@ProfileEditActivity, R.drawable.radius_background_transparent_select)
            button.setTextColor(ContextCompat.getColor(this@ProfileEditActivity, R.color.colorButtonSelect))
            stackTool.add(button.text.toString())
        } else {
            button.background = ContextCompat.getDrawable(this@ProfileEditActivity, R.drawable.radius_button_effect)
            button.setTextColor(ContextCompat.getColor(this@ProfileEditActivity, R.color.colorBlack))
            stackTool.remove(button.text.toString())
        }
    }

    /*
    * 스택, 툴 버튼을 설정해 주는 메서드
    * */
    private fun setStackToolBtn(){
        for (i in 0 until profile_edit_developer_stack_layout.childCount) {
            val child: View = profile_edit_developer_stack_layout.getChildAt(i)
            if(child is Button) {
                child.background = ContextCompat.getDrawable(this@ProfileEditActivity, R.drawable.radius_button_effect)
                child.setTextColor(ContextCompat.getColor(this@ProfileEditActivity, R.color.colorBlack))
                child.setOnClickListener { this.stackToolBtnOnClick(child) }
            }
        }

        for (i in 0 until profile_edit_designer_tool_layout.childCount) {
            val child: View = profile_edit_designer_tool_layout.getChildAt(i)
            if(child is Button) {
                child.background = ContextCompat.getDrawable(this@ProfileEditActivity, R.drawable.radius_button_effect)
                child.setTextColor(ContextCompat.getColor(this@ProfileEditActivity, R.color.colorBlack))
                child.setOnClickListener { this.stackToolBtnOnClick(child) }
            }
        }
    }

    private fun initialize(){
        updateprofile = MyProfileEdit(userprofile.career, userprofile.emoticon, userprofile.introduction,
            userprofile.name, userprofile.portfolioLink1, userprofile.portfolioLink2, userprofile.position,
            userprofile.privacy, userprofile.region, userprofile.stacks)
    }
}
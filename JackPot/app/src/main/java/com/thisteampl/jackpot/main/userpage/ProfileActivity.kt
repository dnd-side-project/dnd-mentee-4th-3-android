package com.thisteampl.jackpot.main.userpage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.common.GlobalApplication
import com.thisteampl.jackpot.main.LoginActivity
import com.thisteampl.jackpot.main.userController.*
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileActivity: AppCompatActivity() {

    private val userApi = userAPI.create()
    lateinit var userprofile : MyProfile
    lateinit var updateprofile : MyProfileEdit
    private var mMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setSupportActionBar(profile_toolbar) // 기본액션바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 제목 없애기
        setupView()
        invalidateOptionsMenu()
    }

    private fun setupView(){
        profile_title_text.text = intent.getStringExtra("title").toString()
        //프로필 텍스트를 Extra로 받음

        if(profile_title_text.text == "내 프로필") {
            getProfile()
            profile_memberscrap_button.visibility = View.GONE
        } else {

        }

        profile_back_button.setOnClickListener { super.onBackPressed() }

        profile_portfolio_github_button.setOnClickListener {
            if(URLUtil.isValidUrl(userprofile.portfolioLink1)) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(userprofile.portfolioLink1))
                startActivity(intent)
            } else {
                Toast.makeText(baseContext, userprofile.portfolioLink1 + "\n해당 링크는 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        profile_portfolio_behance_button.setOnClickListener {
            if(URLUtil.isValidUrl(userprofile.portfolioLink1)) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(userprofile.portfolioLink1))
                startActivity(intent)
            } else {
                Toast.makeText(baseContext, userprofile.portfolioLink1 + "\n해당 링크는 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        profile_portfolio_global_button.setOnClickListener {
            val intent: Intent
            if(userprofile.position == "기획자") {
                if(URLUtil.isValidUrl(userprofile.portfolioLink1)) {
                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(userprofile.portfolioLink1))
                    startActivity(intent)
                } else {
                    Toast.makeText(baseContext, userprofile.portfolioLink1 + "\n해당 링크는 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                if(URLUtil.isValidUrl(userprofile.portfolioLink2)) {
                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(userprofile.portfolioLink2))
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                } else {
                    Toast.makeText(baseContext, userprofile.portfolioLink2 + "\n해당 링크는 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //내 프로필일 경우 메뉴바 생성
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(profile_title_text.text != "내 프로필") {
            return false
        }
        mMenu = menu
        val inflater = menuInflater
        inflater.inflate(R.menu.profile_menu, menu)
        return true
    }

    // 메뉴바 선택시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(profile_title_text.text != "내 프로필") {
            return false
        }
        when(item.itemId) {
            R.id.profile_menu -> {
                if(userprofile.privacy) {
                    mMenu?.findItem(R.id.profile_privacy_open_menu)?.isVisible = false
                    mMenu?.findItem(R.id.profile_privacy_close_menu)?.isVisible = true
                }
                if(userprofile.loginType != "normal") {
                    mMenu?.findItem(R.id.profile_edit_password_menu)?.isVisible = false
                }
            }
            R.id.profile_edit_menu -> {
                val intent = Intent(baseContext, ProfileEditActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
            R.id.profile_privacy_open_menu -> {
                setProfile(true)
            }
            R.id.profile_privacy_close_menu -> {
                setProfile(false)
            }
            R.id.profile_edit_password_menu -> {
                val intent = Intent(baseContext, ProfileChangePasswordActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
            R.id.profile_edit_logout_menu -> {
                GlobalApplication.prefs.setString("token", "NO_TOKEN")
                Toast.makeText(baseContext, "정상적으로 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
            R.id.profile_edit_withdraw_menu -> {
                userApi?.getWithDraw()?.enqueue(
                object : Callback<CheckResponse> {
                    override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                        // userAPI에서 타입이나 이름 안맞췄을때
                        Log.e("tag ", "onFailure" + t.localizedMessage)
                    }

                    override fun onResponse(
                        call: Call<CheckResponse>,
                        response: Response<CheckResponse>
                    ) {
                        if (response.code().toString() == "200") {
                            Toast.makeText(
                                baseContext,
                                "회원탈퇴가 완료되었습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            GlobalApplication.prefs.setString("token", "NO_TOKEN")
                            finish()
                        } else {
                            Toast.makeText(
                                baseContext,
                                "회원탈퇴에 실패했습니다.\n에러 코드 : " + response.code() + "\n" + response.body()
                                    .toString(),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                })
            }
        }
        return super.onOptionsItemSelected(item)
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
                            initialize()
                            Log.e("ProfileActivity_getProfile : ", "User : " + response.body()!!.result.toString())
                            profile_job_text.text = response.body()!!.result.position + " ・ " + response.body()!!.result.career
                            profile_name_text.text = response.body()!!.result.name
                            profile_job_icon_text.text = response.body()!!.result.emoticon
                            profile_introduce_text.text = response.body()!!.result.introduction

                            if(response.body()!!.result.privacy) {
                                profile_profile_close_image.visibility = View.GONE
                            }

                            when (response.body()!!.result.position) {
                                "개발자" -> {
                                    profile_portfolio_behance_button.visibility = View.GONE
                                    if(response.body()!!.result.portfolioLink1 == "") {
                                        profile_portfolio_github_button.visibility = View.GONE
                                    }
                                    if(response.body()!!.result.portfolioLink2 == "") {
                                        profile_portfolio_global_button.visibility = View.GONE
                                    }

                                    profile_availablestackTool_text.text = "기술 스택"
                                    profile_job_background_image.setImageResource(R.drawable.background_developer)
                                }
                                "디자이너" -> {
                                    profile_portfolio_github_button.visibility = View.GONE
                                    if(response.body()!!.result.portfolioLink1 == "") {
                                        profile_portfolio_behance_button.visibility = View.GONE
                                    }
                                    if(response.body()!!.result.portfolioLink2 == "") {
                                        profile_portfolio_global_button.visibility = View.GONE
                                    }

                                    profile_availablestackTool_text.text = "사용 가능 툴"
                                    profile_job_background_image.setImageResource(R.drawable.background_designer)
                                }
                                else -> {
                                    profile_portfolio_behance_button.visibility = View.GONE
                                    profile_portfolio_github_button.visibility = View.GONE
                                    if(response.body()!!.result.portfolioLink1 == "") {
                                        profile_portfolio_global_button.visibility = View.GONE
                                    }

                                    profile_availablestackTool_layout.visibility = View.GONE
                                    profile_job_background_image.setImageResource(R.drawable.background_director)
                                }
                            }
                                // 기술스택 동적 추가
                            for (i in response.body()!!.result.stacks) {
                                var layoutParams = LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                )
                                layoutParams.setMargins(0, 0, 20, 0)
                                val textView = TextView(baseContext)
                                textView.text = i
                                textView.setPadding(40, 10, 40, 10)
                                textView.layoutParams = layoutParams

                                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14F)
                                textView.setTextColor(ContextCompat.getColor(baseContext, R.color.colorBlack))
                                textView.background =
                                    ContextCompat.getDrawable(baseContext, R.drawable.radius_background_transparent)
                                textView.isSingleLine = true

                                profile_stackTool_layout.addView(textView)
                            }
                            //포트폴리오, 자기소개 추가해야 함.
                        }
                        response.code().toString() == "401" -> {
                            Toast.makeText(
                                baseContext,
                                "토큰 유효기간이 만료됐습니다.\n 로그인 페이지로 이동합니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            GlobalApplication.prefs.setString("token", "NO_TOKEN")
                            val intent = Intent(baseContext, LoginActivity::class.java)
                            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                            finish()
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
    private fun setProfile(privacy_ : Boolean){
        updateprofile.privacy = privacy_
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

    private fun initialize(){
        updateprofile = MyProfileEdit(userprofile.career, userprofile.emoticon, userprofile.introduction,
            userprofile.name, userprofile.portfolioLink1, userprofile.portfolioLink2, userprofile.position,
            userprofile.privacy, userprofile.region, userprofile.stacks)
    }
}
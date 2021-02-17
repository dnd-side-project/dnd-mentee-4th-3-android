package com.thisteampl.jackpot.main.userpage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.common.GlobalApplication
import com.thisteampl.jackpot.main.LoginActivity
import com.thisteampl.jackpot.main.userController.CheckProfile
import com.thisteampl.jackpot.main.userController.userAPI
import kotlinx.android.synthetic.main.activity_my_page.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.holder_mypage_anotherproject.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity: AppCompatActivity() {

    private val userApi = userAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupView()
    }

    private fun setupView(){
        profile_title_text.text = intent.getStringExtra("title").toString()

        if(profile_title_text.text == "내 프로필") {
            getProfile()
        } else {

        }

        profile_back_button.setOnClickListener { super.onBackPressed() }

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
                            Log.e("getProfile ", "User : " + response.body()!!.result.toString())
                            profile_job_text.text = response.body()!!.result.job + " ・ " + response.body()!!.result.career
                            profile_name_text.text = response.body()!!.result.name
                            if(response.body()!!.result.privacy) {
                                profile_profile_close_image.visibility = View.GONE
                            }

                            when (response.body()!!.result.job) {
                                "개발자" -> {
                                    profile_availablestackTool_text.text = "기술 스택"
                                }
                                "디자이너" -> {
                                    profile_availablestackTool_text.text = "사용 가능 툴"
                                }
                                else -> {
                                    profile_availablestackTool_text.visibility = View.GONE
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
}
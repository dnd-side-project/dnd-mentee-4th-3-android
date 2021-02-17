package com.thisteampl.jackpot.main.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.common.GlobalApplication.Companion.prefs
import com.thisteampl.jackpot.main.LoginActivity
import com.thisteampl.jackpot.main.userController.CheckProfile
import com.thisteampl.jackpot.main.userController.CheckResponse
import com.thisteampl.jackpot.main.userController.userAPI
import kotlinx.android.synthetic.main.activity_my_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPage : AppCompatActivity() {

    private val userApi = userAPI.create()
    lateinit var myProjectAdapter: MyProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        getProfile()
        setUpRecyclerView()
        setupView()
    }
        // 마이페이지 뷰 셋팅하는 메서드. 로그인 돼 있을 경우와 로그아웃 돼 있을 경우를 분리한다.
        private fun setupView(){
            var p1 = MyProject("프로젝트 1", listOf("1", "2"))
            var p2 = MyProject("프로젝트 2", listOf("1", "2"))
            var p3 = MyProject("프로젝트 3", listOf("1"))
            myProjectAdapter.items.add(p1)
            myProjectAdapter.items.add(p2)
            myProjectAdapter.items.add(p3)

            mypage_project_num_text.text = myProjectAdapter.items.size.toString() + "개"

            mypage_back_button.setOnClickListener { super.onBackPressed() }

            mypage_logout_button.setOnClickListener {
                prefs.setString("token", "NO_TOKEN")
                Toast.makeText(baseContext, "정상적으로 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }

            mypage_withdraw_button.setOnClickListener {
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
                                prefs.setString("token", "NO_TOKEN")
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

            mypage_mycomment_button.setOnClickListener {
                mypage_mycomment_button.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
                mypage_select_mycomment_bottombar.visibility = View.VISIBLE
                mypage_no_scrapcomment_text.text = "아직 댓글을 단 프로젝트가 없어요"

                mypage_select_myscrap_bottombar.visibility = View.GONE
                mypage_myscrap_button.setTextColor(ContextCompat.getColor(this, R.color.colorLightGray))
            }

            mypage_myscrap_button.setOnClickListener {
                mypage_no_scrapcomment_text.text = "아직 스크랩한 프로젝트나 멤버가 없어요"
                mypage_select_myscrap_bottombar.visibility = View.VISIBLE
                mypage_myscrap_button.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))

                mypage_mycomment_button.setTextColor(ContextCompat.getColor(this, R.color.colorLightGray))
                mypage_select_mycomment_bottombar.visibility = View.GONE
            }
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
                            mypage_job_text.text = response.body()!!.result.job + " ・ " + response.body()!!.result.career
                            mypage_name_text.text = response.body()!!.result.name
                            if(response.body()!!.result.privacy) {
                                mypage_profile_open_image.setImageResource(R.drawable.profile_open_true)
                            } else {
                                mypage_profile_open_image.setImageResource(R.drawable.profile_open_false)
                            }
                        }
                        response.code().toString() == "401" -> {
                            Toast.makeText(
                                baseContext,
                                "토큰 유효기간이 만료됐습니다.\n 로그인 페이지로 이동합니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            prefs.setString("token", "NO_TOKEN")
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

    private fun setUpRecyclerView(){
        myProjectAdapter = MyProjectAdapter()
        mypage_myproject_recyclerview.adapter = myProjectAdapter
        mypage_myproject_recyclerview.layoutManager = LinearLayoutManager(this)
    }
}
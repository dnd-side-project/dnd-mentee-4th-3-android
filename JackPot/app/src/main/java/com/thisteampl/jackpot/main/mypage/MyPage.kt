package com.thisteampl.jackpot.main.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.common.GlobalApplication
import com.thisteampl.jackpot.common.GlobalApplication.Companion.prefs
import com.thisteampl.jackpot.main.LoginActivity
import com.thisteampl.jackpot.main.userController.CheckResponse
import com.thisteampl.jackpot.main.userController.userAPI
import kotlinx.android.synthetic.main.activity_my_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPage : AppCompatActivity() {

    private val userApi = userAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)
        setupView()
    }
        // 마이페이지 뷰 셋팅하는 메서드. 로그인 돼 있을 경우와 로그아웃 돼 있을 경우를 분리한다.
        private fun setupView(){
            //토큰이 있는 경우 : 로그아웃이 보이게, 토큰이 없는 경우 : 로그인이 보이게

            mypage_login_button.setOnClickListener {
                if(prefs.getString("token", "NO_TOKEN") == "NO_TOKEN") {
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    Toast.makeText(baseContext, "이미 로그인 돼어있음", Toast.LENGTH_SHORT).show()
                }
            }

            mypage_logout_button.setOnClickListener {
                if(prefs.getString("token", "NO_TOKEN") == "NO_TOKEN") {
                    Toast.makeText(baseContext, "로그아웃할 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    prefs.setString("token", "NO_TOKEN")
                    Toast.makeText(baseContext, "정상적으로 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                }
           }

            mypage_withdraw_button.setOnClickListener {
                if (prefs.getString("token", "NO_TOKEN") == "NO_TOKEN") {
                    Toast.makeText(baseContext, "회원탈퇴할 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                } else {
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
                                    val intent = Intent(baseContext, LoginActivity::class.java)
                                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
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
        }
}
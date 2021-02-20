package com.thisteampl.jackpot.main.userpage

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.userController.CheckResponse
import com.thisteampl.jackpot.main.userController.userAPI
import kotlinx.android.synthetic.main.activity_profile_change_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileChangePasswordActivity: AppCompatActivity() {

    private val userApi = userAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_change_password)

        setupView()
    }

    private fun setupView() {
        profile_changePW_back_button.setOnClickListener { onBackPressed() }
        profile_changePW_confirm_button.setOnClickListener {
            if (profile_changePW_newPW_text.text.length < 6 || profile_changePW_newPW_text.text.length > 15) {
                Toast.makeText(this, "비밀번호는 최소 6글자 최대 15글자 입니다.", Toast.LENGTH_SHORT).show()
            } else if (profile_changePW_newPW_text.text.toString() != profile_changePW_newPW_check_text.text.toString()) {
                Toast.makeText(this, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            } else {
                userApi?.getPasswordChange(
                    profile_changePW_newPW_text.text.toString(),
                    profile_changePW_curPW_text.text.toString()
                )
                    ?.enqueue(object : Callback<CheckResponse> {
                        override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                            // userAPI에서 타입이나 이름 안맞췄을때
                            Log.e("tag ", "onFailure" + t.localizedMessage)
                        }

                        override fun onResponse(
                            call: Call<CheckResponse>,
                            response: Response<CheckResponse>
                        ) {
                            when {
                                response.code().toString() == "200" -> {
                                    Toast.makeText(
                                        baseContext, "비밀번호가 변경되었습니다."
                                        , Toast.LENGTH_SHORT
                                    ).show()
                                    finish()
                                }
                                response.code().toString() == "404" -> {
                                    Toast.makeText(
                                        baseContext, "현재 비밀번호가 다릅니다."
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
        }
    }
}
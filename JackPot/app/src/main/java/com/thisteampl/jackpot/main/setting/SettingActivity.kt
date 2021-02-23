package com.thisteampl.jackpot.main.setting

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.common.GlobalApplication
import com.thisteampl.jackpot.main.userController.CheckMyProfile
import com.thisteampl.jackpot.main.userController.CheckResponse
import com.thisteampl.jackpot.main.userController.userAPI
import com.thisteampl.jackpot.main.userpage.ProfileChangePasswordActivity
import kotlinx.android.synthetic.main.activity_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingActivity : AppCompatActivity(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {


    class MainSettingFragment : Fragment() {

        private var userApi = userAPI.create()

        private var loginType : String? = "NO_TYPE_NEED_INITIALIZE"
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            //프로필을 가져와서 로그인 타입을 가져온다.
            //SNS 회원 비밀번호 변경을 막기 위함.
            userApi?.getProfile()?.enqueue(
                object : Callback<CheckMyProfile> {
                    override fun onFailure(call: Call<CheckMyProfile>, t: Throwable) {
                        // userAPI에서 타입이나 이름 안맞췄을때
                        Log.e("tag ", "onFailure, " + t.localizedMessage)
                    }

                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onResponse(
                        call: Call<CheckMyProfile>,
                        response: Response<CheckMyProfile>
                    ) {
                        when {
                            response.code().toString() == "200" -> {
                                loginType = response.body()?.result?.loginType
                            }
                            else -> {
                                Toast.makeText(
                                    context, "에러가 발생했습니다. 에러코드 : " + response.code()
                                    , Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                })

        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            var root = inflater.inflate(R.layout.fragment_setting_main, container, false)

            //비밀번호 변경 버튼, 로그인타입 값을 받아온다.
            root.findViewById<View>(R.id.setting_changePW).setOnClickListener {
                if(loginType == "normal") {
                    val intent = Intent(context, ProfileChangePasswordActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                } else {
                    Toast.makeText(
                        context, "SNS 로그인 회원은\n비밀번호 변경을 할 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            //로그아웃 버튼
            //비밀번호 변경 버튼, 로그인타입 값을 받아온다.
            root.findViewById<View>(R.id.setting_logout).setOnClickListener {
                GlobalApplication.prefs.setString("token", "NO_TOKEN")
                Toast.makeText(context, "정상적으로 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                activity?.finish()
            }

            //회원탈퇴 버튼
            root.findViewById<View>(R.id.setting_withdraw).setOnClickListener {
                val dialog = AlertDialog.Builder(context)
                dialog.setTitle("회원 탈퇴")
                dialog.setMessage("정말 회원탈퇴 하시겠습니까?\n회원님의 정보는 복구가 불가능합니다.")

                var dialog_listener =
                    DialogInterface.OnClickListener { _, which ->
                        when (which) {
                            DialogInterface.BUTTON_POSITIVE -> {
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
                                                    context,
                                                    "회원탈퇴가 완료되었습니다.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                GlobalApplication.prefs.setString("token", "NO_TOKEN")
                                                activity?.finish()
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "회원탈퇴에 실패했습니다.\n에러 코드 : " + response.code() + "\n" + response.body()
                                                        .toString(),
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()
                                            }
                                        }
                                    })
                            }
                            DialogInterface.BUTTON_NEGATIVE -> {

                            }
                        }
                    }
                dialog.setPositiveButton("확인", dialog_listener)
                dialog.setNegativeButton("취소", dialog_listener)
                dialog.show()
            }

            return root
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setting_back_button.setOnClickListener { super.onBackPressed() }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.setting_container, MainSettingFragment())
            .commit()
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat?,
        pref: Preference?
    ): Boolean {
        TODO("Not yet implemented")
    }


}
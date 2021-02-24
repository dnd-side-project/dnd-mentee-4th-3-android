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
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.common.GlobalApplication
import com.thisteampl.jackpot.main.userController.*
import com.thisteampl.jackpot.main.userpage.ProfileChangePasswordActivity
import kotlinx.android.synthetic.main.activity_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//액티비티 하나와 프래그먼트로 구성된 설정화면.

class SettingActivity : AppCompatActivity() {

    private var userApi = userAPI.create()
    lateinit var userprofile : MyProfile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setting_back_button.setOnClickListener { onBackPressed() }

        getMyProfile(true)
    }

    override fun onBackPressed() {
        if(setting_toolbar_title.text == "설정") {
            finish()
        } else {
            setting_toolbar_title.text = "설정"
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.setting_container, MainSettingFragment())
                .commit()
        }
    }

    //내 프로필을 가져온다. 매개변수 first로 처음 불러올 땐
    //메인 프래그먼트를 가져오고 아니라면 프래그먼트를 가져오지 않는다.
    private fun getMyProfile(first: Boolean) {
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
                            userprofile = response.body()?.result!!
                            if(first) {
                                supportFragmentManager
                                    .beginTransaction()
                                    .replace(R.id.setting_container, MainSettingFragment())
                                    .commit()
                            }
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

    //설정의 메인 프래그먼트
    class MainSettingFragment : Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            var root = inflater.inflate(R.layout.fragment_setting_main, container, false)

            //비밀번호 변경 버튼, 로그인타입 값을 받아온다.
            root.findViewById<View>(R.id.setting_changePW).setOnClickListener {
                if((activity as SettingActivity).userprofile.loginType == "normal") {
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
                                (activity as SettingActivity).userApi?.getWithDraw()?.enqueue(
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

            //알람설정 버튼
            root.findViewById<View>(R.id.setting_notification).setOnClickListener {
                (activity as SettingActivity).setting_toolbar_title.text = "알림"
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.setting_container, NotifyFragment())?.commit()
            }
            return root
        }
    }

    //알림 설정 프래그먼트
    class NotifyFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            var root = inflater.inflate(R.layout.fragment_setting_notify, container, false)

            //유저 프로필을 서버에서 받아서 댓글, 프로젝트 참여, 참여 신청 알림이 켜져있다면 스위치를 체크해둠.
            if((activity as SettingActivity).userprofile.commentPush) {
                (root.findViewById<View>(R.id.setting_notification_comment_switch) as SwitchCompat).isChecked = true
            }
            if((activity as SettingActivity).userprofile.requestAcceptPush) {
                (root.findViewById<View>(R.id.setting_notification_request_accept_switch) as SwitchCompat).isChecked = true
            }
            if((activity as SettingActivity).userprofile.requestPush) {
                (root.findViewById<View>(R.id.setting_notification_participant_request_switch) as SwitchCompat).isChecked = true
            }

            // 각 버튼을 누를 때 서버에 정보를 보낸다.
            (root.findViewById<View>(R.id.setting_notification_comment_switch) as SwitchCompat).setOnClickListener {
                changeNotify((root.findViewById<View>(R.id.setting_notification_comment_switch) as SwitchCompat).isChecked,
                    (root.findViewById<View>(R.id.setting_notification_request_accept_switch) as SwitchCompat).isChecked,
                    (root.findViewById<View>(R.id.setting_notification_participant_request_switch) as SwitchCompat).isChecked)
            }
            (root.findViewById<View>(R.id.setting_notification_request_accept_switch) as SwitchCompat).setOnClickListener {
                changeNotify((root.findViewById<View>(R.id.setting_notification_comment_switch) as SwitchCompat).isChecked,
                    (root.findViewById<View>(R.id.setting_notification_request_accept_switch) as SwitchCompat).isChecked,
                    (root.findViewById<View>(R.id.setting_notification_participant_request_switch) as SwitchCompat).isChecked)
            }
            (root.findViewById<View>(R.id.setting_notification_participant_request_switch) as SwitchCompat).setOnClickListener {
                changeNotify((root.findViewById<View>(R.id.setting_notification_comment_switch) as SwitchCompat).isChecked,
                    (root.findViewById<View>(R.id.setting_notification_request_accept_switch) as SwitchCompat).isChecked,
                    (root.findViewById<View>(R.id.setting_notification_participant_request_switch) as SwitchCompat).isChecked)
            }
            return root
        }

        private fun changeNotify(comment : Boolean, accept : Boolean, request : Boolean) {
            (activity as SettingActivity).userApi?.getUpdatePush(Push(comment, accept, request))?.enqueue(
                object : Callback<CheckResponse> {
                    override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                        // userAPI에서 타입이나 이름 안맞췄을때
                        Log.e("tag ", "onFailure" + t.localizedMessage)
                    }

                    override fun onResponse(
                        call: Call<CheckResponse>,
                        response: Response<CheckResponse>
                    ) {
                        if (response.code().toString() != "200") {
                            Toast.makeText(
                                context,
                                "알림 수정에 실패했습니다.\n에러 코드 : " + response.code() + "\n" + response.body()
                                    .toString(),
                                Toast.LENGTH_SHORT
                            )
                                .show()

                        } else { // 200번이라면 수정 성공.
                            (activity as SettingActivity).getMyProfile(false) // 매개변수 false 프로필만 다시 불러온다.
                        }
                    }
                })
        }
    }
}
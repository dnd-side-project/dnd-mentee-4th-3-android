package com.thisteampl.jackpot.main

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.common.GlobalApplication.Companion.prefs
import com.thisteampl.jackpot.main.userController.CheckResponse
import com.thisteampl.jackpot.main.userController.SNSSignIn
import com.thisteampl.jackpot.main.userController.SignIn
import com.thisteampl.jackpot.main.userController.userAPI
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


/*
* 카카오 로그인 기능 구현 : https://studyforcoding.tistory.com/6?category=800442 참조.
* kakaosdk v2 사용
*
* 구글 로그인 기능 구현 : https://galid1.tistory.com/109
* https://philosopher-chan.tistory.com/341
* https://chjune0205.tistory.com/136 세 곳을 참조.
*
* 맨 처음 앱이 시작될 때 나오는 화면. 로그인이 돼 있다면 바로 다음 메인화면으로 간다.
* */

class LoginActivity : AppCompatActivity() {

    lateinit var mOAuthLoginInstance : OAuthLogin // 네이버 로그인 모듈
    lateinit var googleSignInClient: GoogleSignInClient // 구글 로그인 모듈
    
    private val userApi = userAPI.create()
    private var sendAuth = false
    private var authCode = makeCode()

    // 화면전환 애니메이션, fillAfter : 옮긴 후 원상복구, duration : 지속시간
    private val anim: Animation = AlphaAnimation(0f, 1f).apply {
        fillAfter = true
        duration = 350
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupView()
    }

    override fun onBackPressed() {
        finish()
    }

    // 카카오 로그인을 위한 callback 메서드.
    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        /* error 가 null이 아니라면 로그인 불가.*/
        if (error != null) {
            Toast.makeText(this, "카카오 로그인에 실패했습니다.\n $error", Toast.LENGTH_SHORT).show()
        }
        /*token이 null이 아니라면 카카오 API로 값을 불러와서 회원의 정보를 가져온다.
        * 그리고 회원가입 페이지로 이동한다.*/
        else if (token != null) {
            checkThirdPartyToken(token.accessToken, "kakao", "id")
        }
    }

    // 네이버 로그인을 위해 토큰을 받아옴. 안드로이드에서는 스레드를 사용해서 웹의 서비스에 접근해야 한다.
    //추가로 id와 name을 받아와서 다음 activity에 extra로 넘겨준다.
    private val naverOAuthLoginHandler: OAuthLoginHandler = object : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
                var accessToken = mOAuthLoginInstance.getAccessToken(baseContext)
                var id: String
                Thread {
                    val data: String = mOAuthLoginInstance.requestApi(baseContext, accessToken, "https://openapi.naver.com/v1/nid/me")
                    try {
                        id = JSONObject(data).getJSONObject("response").getString("id")
                        checkThirdPartyToken(accessToken, "naver", id)
                    }
                    catch (e: JSONException) { }
                }.start()
                mOAuthLoginInstance.logout(baseContext)
            } else {
                Toast.makeText(baseContext, "네이버 로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 화면이 구성되고 View를 만들어 준다.
    private fun setupView(){
        var page = 0 // 이메일 관련 페이지, 0은 이메일 로그인 1은 비밀번호 찾기(이메일 인증), 2는 비밀번호 변경
        
        //뷰들에 애니메이션을 적용해준다
        for (i in 0 until login_total_layout.childCount) {
            val child: View = login_total_layout.getChildAt(i)
            if(child is Button) {
                child.animation = anim
            }
        }
        
        mOAuthLoginInstance = OAuthLogin.getInstance()
        mOAuthLoginInstance.init( // 네이버 로그인 모듈 초기화
            this,
            getString(R.string.naver_client_id),
            getString(R.string.naver_client_secret) ,
            getString(R.string.app_name)
        )
        var gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // 구글 로그인을 위한 GSO 객체
        
        //이메일로 로그인하기 버튼
        login_email_login_button.setOnClickListener {

            login_email_login_layout.visibility = View.VISIBLE
            login_confirm_button.visibility = View.VISIBLE

            login_first_layout.visibility = View.GONE
        }

        login_signUp_button.setOnClickListener {
            val intent = Intent(baseContext, SignUpActivity::class.java)
                .putExtra("signuptype", "normal")
            startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }

        // 로그인 확인 버튼, page에 따라 작동하는 기능이 다름. 0 : 이메일로그인, 1 : 비밀번호찾기 인증, 2 : 새 비밀번호 설정
        login_confirm_button.setOnClickListener {
            if(page == 0) {
                var signIn = SignIn(
                    login_id_text.text.toString(), "normal",
                    login_password_text.text.toString(), makeFCMToken()
                )
                userApi?.getUserLogin(signIn)
                    ?.enqueue(object : Callback<CheckResponse> {
                        override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                        override fun onResponse(
                            call: Call<CheckResponse>,
                            response: Response<CheckResponse>
                        ) {
                            if (response.code().toString() == "200") {
                                response.body()?.token?.let { prefs.setString("token", it) }
                                prefs.setString("loginType", "normal")
                                Toast.makeText(baseContext, "로그인에 성공했습니다.", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(baseContext, MainActivity::class.java)
                                startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
                                finish()
                            } else {
                                Toast.makeText(
                                    baseContext, "로그인에 실패했습니다.\n아이디와 비밀번호를 확인해 주세요."
                                    , Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
            } else if(page == 1 && !sendAuth) {
                userApi?.getCheckEmail(login_findPW_email_text.text.toString())
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
                                Toast.makeText(baseContext, "가입하지 않은 회원입니다.", Toast.LENGTH_SHORT)
                                    .show()
                            } else if (response.code().toString() == "200"){
                                Toast.makeText(
                                    baseContext, "인증번호를 보냈습니다.\n인증번호를 입력해 주세요."
                                    , Toast.LENGTH_SHORT
                                ).show()
                                sendAuth = true
                                login_findPW_email_text.isEnabled = false
                                sendEmail(login_findPW_email_text.text.toString(), authCode)
                                login_findPW_emailcheck_title_text.text = "메일로 보내드린\n인증번호를 입력해주세요."
                                login_findPW_auth_layout.visibility = View.VISIBLE
                                login_confirm_button.text = "확인"
                            }
                        }
                    })

                
            } else if(page == 1 && sendAuth) {
                if(login_findPW_auth_text.text.toString() == authCode) {
                    page = 2
                    login_findPW_emailcheck_layout.visibility = View.GONE
                    login_findPW_PWchange_layout.visibility = View.VISIBLE
                    login_confirm_button.text = "비밀번호 재설정"
                } else {
                    Toast.makeText(
                        this, "인증번호를 확인해 주세요."
                        , Toast.LENGTH_SHORT
                    ).show()
                }
            } else if(page == 2) {
                if(login_findPW_PWchange_text.text.length < 6 || login_findPW_PWchange_text.text.length > 15) {
                    Toast.makeText(this, "비밀번호는 최소 6글자 최대 15글자 입니다.", Toast.LENGTH_SHORT).show()
                } else if(login_findPW_PWchange_text.text.toString() != login_findPW_PWchange_check_text.text.toString()) {
                    Toast.makeText(this, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    userApi?.getFindPW(login_findPW_email_text.text.toString(), login_findPW_PWchange_text.text.toString())
                        ?.enqueue(object : Callback<CheckResponse> {
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
                                        baseContext, "\uD83D\uDD11재설정한 비밀번호로 로그인 해주세요."
                                        , Toast.LENGTH_SHORT
                                    ).show()
                                    val intent = Intent(baseContext, LoginActivity::class.java)
                                    startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
                                    finish()
                                } else {
                                    Toast.makeText(
                                        baseContext, "에러가 발생했습니다. 에러코드 : " + response.code()
                                        , Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        })
                }
            }
        }

        login_findPW_button.setOnClickListener {
            page = 1
            login_email_login_layout.visibility = View.GONE
            login_findPW_emailcheck_layout.visibility = View.VISIBLE
            login_confirm_button.text = "인증번호 받기"
        }

        //카카오로 로그인 버튼의 기능. callback 함수를 호출해서 회원가입 페이지로 이동하게 한다.
        login_kakao_login_button.setOnClickListener {
            if(LoginClient.instance.isKakaoTalkLoginAvailable(this)){
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            }else{
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

        login_naver_login_button.setOnClickListener{
            mOAuthLoginInstance.startOauthLoginActivity(this, naverOAuthLoginHandler)
        }

        login_google_login_button.setOnClickListener{
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, 9001)
        }

        login_exit_button.setOnClickListener {
            finish()
        }
    }

    private fun makeFCMToken(): String {
        //파이어베이스 알림을 위한 토큰 받아오기.
        var fcmToken = "NO_TOKEN"
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(
                        "log_FCM",
                        "Fetching FCM registration token failed",
                        task.exception
                    )
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                fcmToken = task.result!!

                // Log and toast
                val msg = "토큰 : $fcmToken"
                Log.d("log_FCM", msg)
            })
        return fcmToken
    }

    // 구글 로그인을 위한 오버라이딩. requestcode == 9001은 구글 로그인을 의미한다.
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 9001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    //구글 로그인 됐을 때 회원가입으로 이동.
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)
            val token = account?.idToken //<- 서버에 넘겨줄 구글 토큰 값.
            checkThirdPartyToken(token.toString(), "google", account?.id.toString())
            googleSignInClient.signOut()
        } catch (e: ApiException) {
            Toast.makeText(baseContext, "구글 로그인에 실패했습니다.\n $e", Toast.LENGTH_SHORT).show()
        }
    }

    //이메일 인증을 위한 랜덤 코드
    private fun makeCode(): String {
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
    private fun sendEmail(
        dest: String,       // 받는 메일 주소
        code: String       // 인증 코드
    )
    {

        var title = "잭팟 인증번호 메일입니다."      // 메일 제목
        var body = "인증번호는 $code 입니다."       // 메일 내용

        // 보내는 메일 주소와 비밀번호
        val username = "dndjackpot3@gmail.com"
        val password = ""

        val props = Properties()
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

    // 서드파티에서 받아온 토큰을 확인
    private fun checkThirdPartyToken(token: String, type: String, id: String) {
        var signIn = SNSSignIn(makeFCMToken(), token)
        when (type) {
            "kakao" -> {
                userApi?.getCheckKakaoToken(signIn)?.enqueue(object : Callback<CheckResponse>{
                    override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                        // userAPI에서 타입이나 이름 안맞췄을때
                        Log.e("tag ", "onFailure" + t.localizedMessage)
                    }

                    override fun onResponse(
                        call: Call<CheckResponse>,
                        response: Response<CheckResponse>
                    ) {
                        when {
                            // 가입하지 않은 회원. 회원가입 필요.
                            response.code().toString() == "404" -> {
                                val intent = Intent(
                                    baseContext,
                                    SignUpActivity::class.java
                                ).putExtra("signuptype", type)
                                Toast.makeText(baseContext, "카카오로 회원가입을 진행합니다.", Toast.LENGTH_SHORT)
                                    .show()
                                startActivity(intent)
                                finish()
                            }
                            //가입된 회원. 토큰을 받아온다. 후에 서버에서 주는 토큰을 sharedPreferences에 저장
                            response.code().toString() == "200" -> {
                                response.body()?.token?.let { prefs.setString("token", it) }
                                prefs.setString("loginType", "kakao")
                                Toast.makeText(baseContext, "카카오 로그인에 성공하였습니다.", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(baseContext, MainActivity::class.java)
                                startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
                                finish()
                            }
                            else -> {
                                Toast.makeText(baseContext, "로그인에 실패했습니다.\n에러 코드 : " + response.code() + "\n" + response.message(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                })

            }
            "naver" -> {
                userApi?.getCheckNaverToken(signIn)?.enqueue(object : Callback<CheckResponse>{
                    override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                        // userAPI에서 타입이나 이름 안맞췄을때
                        Log.e("tag ", "onFailure" + t.localizedMessage)
                    }

                    override fun onResponse(
                        call: Call<CheckResponse>,
                        response: Response<CheckResponse>
                    ) {
                        when {
                            // 가입하지 않은 회원. 회원가입 필요.
                            response.code().toString() == "404" -> {
                                val intent = Intent(
                                    baseContext,
                                    SignUpActivity::class.java
                                ).putExtra("signuptype", type).putExtra("SNSID", id)
                                Toast.makeText(
                                    baseContext,
                                    "네이버로 회원가입을 진행합니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(intent)
                                finish()
                            }
                            //가입된 회원. 토큰을 받아온다. 후에 서버에서 주는 토큰을 sharedPreferences에 저장
                            response.code().toString() == "200" -> {
                                response.body()?.token?.let { prefs.setString("token", it) }
                                prefs.setString("loginType", "naver")
                                Toast.makeText(
                                    baseContext,
                                    "네이버 로그인에 성공하였습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(baseContext, MainActivity::class.java)
                                startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
                                finish()
                            }
                            else -> {
                                Toast.makeText(baseContext, "로그인에 실패했습니다.\n에러 코드 : " + response.code() + "\n" + response.message(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }

                })
            }
            "google" -> {
                userApi?.getCheckGoogleToken(signIn)?.enqueue(object : Callback<CheckResponse>{
                    override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                        // userAPI에서 타입이나 이름 안맞췄을때
                        Log.e("tag ", "onFailure" + t.localizedMessage)
                    }

                    override fun onResponse(
                        call: Call<CheckResponse>,
                        response: Response<CheckResponse>
                    ) {
                        when {
                            // 가입하지 않은 회원. 회원가입 필요.
                            response.code().toString() == "404" -> {
                                val intent = Intent(
                                    baseContext,
                                    SignUpActivity::class.java
                                ).putExtra("signuptype", type).putExtra("SNSID", id)
                                Toast.makeText(baseContext, "구글로 회원가입을 진행합니다.", Toast.LENGTH_SHORT)
                                    .show()
                                startActivity(intent)
                                finish()
                            }
                            //가입된 회원. 토큰을 받아온다. 후에 서버에서 주는 토큰을 sharedPreferences에 저장
                            response.code().toString() == "200" -> {
                                response.body()?.token?.let { prefs.setString("token", it) }
                                prefs.setString("loginType", "google")
                                Toast.makeText(baseContext, "구글 로그인에 성공하였습니다.", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(baseContext, MainActivity::class.java)
                                startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
                                finish()
                            }
                            else -> {
                                Toast.makeText(baseContext, "로그인에 실패했습니다.\n에러 코드 : " + response.code() + "\n" + response.message(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }

                })
            }
        }
    }
}

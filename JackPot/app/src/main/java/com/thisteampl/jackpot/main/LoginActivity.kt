package com.thisteampl.jackpot.main

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.thisteampl.jackpot.R
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException


/*
* 카카오 로그인 기능 구현 : https://studyforcoding.tistory.com/6?category=800442 참조.
* kakaosdk v2 사용
*
* 구글 로그인 기능 구현 : https://galid1.tistory.com/109
* https://philosopher-chan.tistory.com/341 두 곳을 참조.
*
* 맨 처음 앱이 시작될 때 나오는 화면. 로그인이 돼 있다면 바로 다음 메인화면으로 간다.
* */

class LoginActivity : AppCompatActivity() {

    lateinit var mOAuthLoginInstance : OAuthLogin // 네이버 로그인 모듈
    lateinit var googleSignInClient: GoogleSignInClient // 구글 로그인 모듈

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupView()
    }

    // 카카오 로그인을 위한 callback 메서드.
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        /* error 가 null이 아니라면 로그인 불가.*/
        if (error != null) {
            Toast.makeText(this, "카카오 로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
        /*token이 null이 아니라면 카카오 API로 값을 불러와서 회원의 정보를 가져온다.
        * 그리고 회원가입 페이지로 이동한다.*/
        else if (token != null) {
            val intent = Intent(this, SignUpActivity::class.java).putExtra("signuptype", 1)
                .putExtra("token", token.accessToken)
            startActivity(intent)
            finish()
        }
    }

    // 네이버 로그인을 위해 토큰을 받아옴. 안드로이드에서는 스레드를 사용해서 웹의 서비스에 접근해야 한다.
    //추가로 id와 name을 받아와서 다음 activity에 extra로 넘겨준다.
    private val naverOAuthLoginHandler: OAuthLoginHandler = object : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
                    val accessToken: String = mOAuthLoginInstance.getAccessToken(baseContext) // <- 서버에 넘겨줄 토큰값
                    try {

                    }
                    catch (e: JSONException) { }
                    val intent = Intent(baseContext, SignUpActivity::class.java)
                        .putExtra("signuptype", 2).putExtra("token", accessToken)
                    startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
                    finish()
            } else {
                Toast.makeText(baseContext, "네이버 로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 화면이 구성되고 View를 만들어 준다.
    private fun setupView(){

        mOAuthLoginInstance = OAuthLogin.getInstance()
        mOAuthLoginInstance.init( // 네이버 로그인 모듈 초기화
            this,
            "RQcA9ncoO8aUd2YwNz1o",
            "v0NYcGwVmY" ,
            "잭팟"
        )

        var gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // 구글 로그인을 위한 GSO 객체

        //카카오로 로그인 버튼의 기능. callback 함수를 호출해서 회원가입 페이지로 이동하게 한다.
        login_kakao_login_button.setOnClickListener {
            if(LoginClient.instance.isKakaoTalkLoginAvailable(this)){
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            }else{
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

        login_signup_button.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java).putExtra("signuptype", 0)
            startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }

        login_naver_login_button.setOnClickListener{
            mOAuthLoginInstance.startOauthLoginActivity(this, naverOAuthLoginHandler)
        }

        login_login_button.setOnClickListener {
            //서버와 연동해서 아이디와 비밀번호가 일치하는게 있는지 확인하는 코드

            if(login_id_text.text.toString() != "admin" && login_password_text.text.toString() != "admin") {
                Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                //추후에 서버에 저장돼있는 아이디가 저장돼 있는지 확인하는 코드로 바꾸기.
            } else {
                //추후에 서버에 저장돼있는 토큰을 내부 DB에 저장하는 역할을 한다.
                // GlobalApplication.tokenPrefs.token = 서버에서 받아온 토큰
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

        login_google_login_button.setOnClickListener{
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, 9001)
        }

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
            val intent = Intent(baseContext, SignUpActivity::class.java)
                .putExtra("signuptype", 3).putExtra("token", token)
            startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        } catch (e: ApiException) {
            Toast.makeText(baseContext, "구글 로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}

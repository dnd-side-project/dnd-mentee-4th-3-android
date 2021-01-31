package com.thisteampl.jackpot.main.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.thisteampl.jackpot.R

class MyPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)
    }


    // 이전 소스 (MyPage, 이전 Fragment 소스)


//
//    class MyPage{
//
//        companion object {
//
//            fun newInstance(): MyPage {
//                return MyPage()
//            }
//        }
//
//        // 메모리에 올라갔을 때
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//
//        }
//
//        // 프래그먼트를 액티비티에 붙었을 때
//        override fun onAttach(context: Context) {
//            super.onAttach(context)
//        }
//
//        // 뷰가 생성되었을 때
//        // 프래그먼트와 레이아웃을 연결시켜주는 부분이다.
//        override fun onCreateView(
//            inflater: LayoutInflater,
//            container: ViewGroup?,
//            savedInstanceState: Bundle?
//        ): View? {
//
//            val view = inflater.inflate(R.layout.fragment_my_page,container,false)
//            setupView(view)
//
//
//            return view
//        }
//
//        // 마이페이지 뷰 셋팅하는 메서드. 로그인 돼 있을 경우와 로그아웃 돼 있을 경우를 분리한다.
//        private fun setupView(view: View){
//            //토큰이 있는 경우 : 로그아웃이 보이게, 토큰이 없는 경우 : 로그인이 보이게
//
//            view.mypage_login_button.setOnClickListener {
//                context?.startActivity(Intent(context, LoginActivity::class.java))
//            }
//
//            view.mypage_logout_button.setOnClickListener {
//                view.mypage_profile_layout.visibility = View.GONE
//                view.mypage_login_button.visibility = View.VISIBLE
//                view.mypage_logout_button.visibility = View.GONE
//            }
//        }
//
//
//
//    }

}
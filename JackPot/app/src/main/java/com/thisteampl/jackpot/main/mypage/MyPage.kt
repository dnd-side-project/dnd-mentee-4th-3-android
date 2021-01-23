package com.thisteampl.jackpot.main.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.common.AppDatabase
import com.thisteampl.jackpot.main.LoginActivity
import kotlinx.android.synthetic.main.fragment_my_page.view.*


class MyPage : Fragment() {

    companion object {

        fun newInstance(): MyPage {
            return MyPage()
        }
    }

    // 메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    // 프래그먼트를 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }






    // 뷰가 생성되었을 때
    // 프래그먼트와 레이아웃을 연결시켜주는 부분이다.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_my_page,container,false)
        setupView(view)


        return view
    }

    // 마이페이지 뷰 셋팅하는 메서드. 로그인 돼 있을 경우와 로그아웃 돼 있을 경우를 분리한다.
    private fun setupView(view: View){
        val user = AppDatabase.instance.userDao().getAll()

        if(user.isNotEmpty()) {
            view.mypage_login_button.visibility = View.GONE
            view.mypage_logout_button.visibility = View.VISIBLE
            view.mypage_id_text.text = "아이디 : " + user[0].id
            view.mypage_name_text.text = "이름 : " + user[0].name
            view.mypage_job_text.text = "직업 : " +  user[0].job.toString()
            view.mypage_region_text.text = "지역 : " +  user[0].region
        } else {
            view.mypage_profile_layout.visibility = View.GONE
            view.mypage_login_button.visibility = View.VISIBLE
            view.mypage_logout_button.visibility = View.GONE
        }

        view.mypage_login_button.setOnClickListener {
            context?.startActivity(Intent(context, LoginActivity::class.java))
        }

        view.mypage_logout_button.setOnClickListener {
            AppDatabase.instance.userDao().delete(user[0])
            view.mypage_profile_layout.visibility = View.GONE
            view.mypage_login_button.visibility = View.VISIBLE
            view.mypage_logout_button.visibility = View.GONE
        }
    }



}


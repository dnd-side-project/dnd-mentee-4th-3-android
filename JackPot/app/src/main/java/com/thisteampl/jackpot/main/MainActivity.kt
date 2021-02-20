package com.thisteampl.jackpot.main


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.common.GlobalApplication.Companion.prefs
import com.thisteampl.jackpot.main.filtering.FilteringSearch
import com.thisteampl.jackpot.main.floating.ProjectCreation
import com.thisteampl.jackpot.main.mainhome.AttentionMember
import com.thisteampl.jackpot.main.mainhome.AttentionProject
import com.thisteampl.jackpot.main.mainhome.RecentlyRegisterProject

import com.thisteampl.jackpot.main.userpage.MyPage
import com.thisteampl.jackpot.main.viewmore.RecentlyProjectViewMore
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    private lateinit var recentlyregister: RecentlyRegisterProject

    // projectAPI retrofit

    private val selectAllItems = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mypageIntent = Intent(this, MyPage::class.java)
        val mainintent = Intent(this, MainActivity::class.java)




        // 검색
        main_search_imageview.setOnClickListener {

            val searchintentpage = Intent(this, FilteringSearch::class.java)
            startActivity(searchintentpage)
        }

        //

        // 나의 페이지
        main_mypage_imageview.setOnClickListener {
            // 후에 401 에러 시 만료 에러 + 다시 로그인
            if (prefs.getString("token", "NO_TOKEN") == "NO_TOKEN") {
                Toast.makeText(
                    this, "로그인 정보가 없습니다." +
                            "\n로그인 화면으로 이동합니다.", Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            } else {
                startActivity(mypageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
        }

        //

        // MainActivity 실행
        main_appname_textview.setOnClickListener {
            val intentmainacitivty = Intent(this, MainActivity::class.java)
            intentmainacitivty.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intentmainacitivty)
        }


        var attentionlocation : Int = 0
        setFrag(attentionlocation)
        // 주목받는 프로젝트, 액티비티 프래그먼트 연결
        // attentionlocation => 더보기 버튼 옵션 넣기 위해 사용

        main_projectattention_button.setOnClickListener {
            attentionlocation = 0
            main_projectattention_button.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.colorbrightly))
            main_memberattention_button.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.colordarkly))
            main_attentionprojectline_textview.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.page_line_background_select
            )
            main_attentionmemberline_textview.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.page_line_background_white
            )
            setFrag(attentionlocation)
        }

         //주목받는 멤버, 액티비티 프래그먼트 연결
         //attentionlocation => 더보기 버튼 옵션 넣기 위해 사용
        main_memberattention_button.setOnClickListener {
            attentionlocation += 1
            main_memberattention_button.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.colorbrightly))
            main_projectattention_button.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.colordarkly))

            main_attentionprojectline_textview.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.page_line_background_white
            )
            main_attentionmemberline_textview.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.page_line_background_select
            )
            setFrag(attentionlocation)
        }


        // 최근에 등록된 프로젝트, 액티비티 프래그먼트 연결
        recentlyregister = RecentlyRegisterProject.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.main_recentlyregisterproject_framelayout,recentlyregister).commit()



        // 참고 자료 : https://medium.com/@logishudson0218/intent-flag%EC%97%90-%EB%8C%80%ED%95%9C-%EC%9D%B4%ED%95%B4-d8c91ddd3bfc
        // addFlags() : 새로운 flag를 기존 flag에 붙임
        // 최근에 등록된 프로젝트 더보기 버튼 (더보기 page에서 백엔드 연결할 예정)
        main_recentlyviewmore_textview.setOnClickListener {
            val intentrecentlyviewmore = Intent(this, RecentlyProjectViewMore::class.java)
            intentrecentlyviewmore.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intentrecentlyviewmore)
        }



        // 플로팅 버튼 눌렸을 때
        main_optionmenu_floatingactionbutton.setOnClickListener {
            var drawproject:Intent = Intent(this, ProjectCreation::class.java)
            drawproject.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(drawproject)

        }


    }


    // 0번 : 주목받는 프로젝트, 1번 : 주목받는 멤버
    private fun setFrag(fragNum : Int){
        val ft = supportFragmentManager.beginTransaction()

        when(fragNum){
            0 -> {
                ft.replace(R.id.main_projectview_framelayout, AttentionProject()).commit()
            }

            1-> {
                ft.replace(R.id.main_projectview_framelayout, AttentionMember()).commit()
            }
        }

    }

}





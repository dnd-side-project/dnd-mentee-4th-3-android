package com.thisteampl.jackpot.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.mainview.MainMenu
import com.thisteampl.jackpot.main.mypage.MyPage
import com.thisteampl.jackpot.main.projectsearch.Project
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_my_page.*


class MainActivity : AppCompatActivity(){

    // lateinit : 나중에 값을 넣기 위해 사용
    private lateinit var pj : Project
    private lateinit var mainmenu : MainMenu
    private lateinit var mypage : MyPage



    companion object{

        const val TAG: String = "로그"

    }


    // 메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG,"MainActivity - onCreate() called")


        navi_bottom.setOnNavigationItemSelectedListener(onBottomNaviItemSelectedListener)

        // 첫 번째 fragment 추가 (add 사용)
        mainmenu = MainMenu.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.framelayout_frame, mainmenu).commit()

    }


    // 바텀네비게이션 아이템 클릭 리스너 설정
    private val onBottomNaviItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.mypage -> {
                mypage = MyPage.newInstance()
                // supportFragmentMananger : Fragment 관리
                // replace : 다른 fragment로 교체
                // R.id.frameLayout_frame 자리(page)에 mypage로 대체
                supportFragmentManager.beginTransaction().replace(R.id.framelayout_frame, mypage)
                    .commit()
            }
            R.id.mainview -> {
                mainmenu = MainMenu.newInstance()
                // supportFragmentMananger : Fragment 관리
                // replace : 다른 fragment로 교체
                // R.id.frameLayout_frame 자리(page)에 mainmenu로 대체
                supportFragmentManager.beginTransaction().replace(R.id.framelayout_frame, mainmenu)
                    .commit()
            }
            R.id.project -> {
                pj = Project.newInstance()
                // supportFragmentMananger : Fragment 관리
                // replace : 다른 fragment로 교체
                // R.id.frameLayout_frame 자리(page)에 project로 대체
                supportFragmentManager.beginTransaction().replace(R.id.framelayout_frame, pj)
                    .commit()
            }
        }

        true
    }
}
package com.thisteampl.jackpot.main


import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.common.GlobalApplication.Companion.prefs
import com.thisteampl.jackpot.main.floating.MyAppeal
import com.thisteampl.jackpot.main.floating.ProjectCreation
import com.thisteampl.jackpot.main.mainhome.*
import com.thisteampl.jackpot.main.mypage.MyPage
import com.thisteampl.jackpot.main.viewmore.RecentlyProjectViewMore
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){

    private lateinit var recentlyregister : RecentlyRegisterProject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mypageIntent = Intent(this, MyPage::class.java)
        val mainintent = Intent(this,MainActivity::class.java)
        val searchintent = Intent(this,SearchViewPage::class.java)

        // 검색
        main_search_imageview.setOnClickListener {
            startActivity(searchintent)
        }

        // 나의 페이지
        main_mypage_imageview.setOnClickListener{
            if(prefs.getString("token", "NO_TOKEN") == "NO_TOKEN") {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            } else {
                startActivity(mypageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
        }

        // MainActivity 실행
        main_appname_textview.setOnClickListener{
            startActivity(mainintent)
        }


        var attentionlocation : Int = 0
        setFrag(attentionlocation)

        // 주목받는 프로젝트, 액티비티 프래그먼트 연결
        // attentionlocation => 더보기 버튼 옵션 넣기 위해 사용
//        main_projectattention_textview.setOnClickListener {
//            attentionlocation = 0
//            main_projectattention_textview.setTextColor(Color.BLACK)
//            main_memberattention_textview.setTextColor(Color.GRAY)
//            setFrag(attentionlocation)
//        }

        // 주목받는 멤버, 액티비티 프래그먼트 연결
        // attentionlocation => 더보기 버튼 옵션 넣기 위해 사용
//        main_memberattention_textview.setOnClickListener {
//            attentionlocation += 1
//            main_memberattention_textview.setTextColor(Color.BLACK)
//            main_projectattention_textview.setTextColor(Color.GRAY)
//            setFrag(attentionlocation)
//        }


        // 최근에 등록된 프로젝트, 액티비티 프래그먼트 연결
//        recentlyregister = RecentlyRegisterProject.newInstance()
//        supportFragmentManager.beginTransaction().add(R.id.main_recentlyproject_framelayout,recentlyregister).commit()

        val intentrecentlyviewmore = Intent(this, RecentlyProjectViewMore::class.java)


        // 최근에 등록된 프로젝트 더보기 버튼
//        main_recentlyviewmore_textview.setOnClickListener {
//            intentrecentlyviewmore.putExtra("InputRecently",recentlyregister.recentlyregister)
//            startActivity(intentrecentlyviewmore)
//        }

        // 참고자료 : https://jinsangjin.tistory.com/12
        // xml값을 로딩하기위해 AnimationUtils.loadAnimation 사용
        val open_fab = AnimationUtils.loadAnimation(this,R.anim.fab_open)
        val close_fab = AnimationUtils.loadAnimation(this,R.anim.fab_close)
        val anticlockwise_rotate = AnimationUtils.loadAnimation(this,R.anim.rotate_anticlockwise)
        val rclockwise_rotate = AnimationUtils.loadAnimation(this,R.anim.rotate_clockwise)
        var isOpen = false

        val appeal = Intent(this,MyAppeal::class.java)
        var drawproject = Intent(this,ProjectCreation::class.java)


        // 플로팅 버튼 눌렸을 때
        // open = false 열림, close = true
        main_optionmenu_floatingactionbutton.setOnClickListener{
            if(isOpen){
                appeal_fab.startAnimation(close_fab)
                drawproject_fab.startAnimation(close_fab)
                main_optionmenu_floatingactionbutton.startAnimation(rclockwise_rotate)


                // 자기어필, 프로젝트 상세히 버튼 닫히게
                appeal_fab.isClickable = false
                drawproject_fab.isClickable = false


                isOpen = false
            }
            else{
                appeal_fab.startAnimation(open_fab)
                drawproject_fab.startAnimation(open_fab)
                main_optionmenu_floatingactionbutton.startAnimation(anticlockwise_rotate)

                // 자기어필, 프로젝트 상세히 버튼 열리게
                appeal_fab.isClickable = true
                drawproject_fab.isClickable = true

                appeal_fab.setOnClickListener {
                    startActivity(appeal)
                }
                drawproject_fab.setOnClickListener {
                    startActivity(drawproject)
                }

                isOpen = true

            }
        }


    }



    // 0번 : 주목받는 프로젝트, 1번 : 주목받는 멤버
    private fun setFrag(fragNum : Int){
        val ft = supportFragmentManager.beginTransaction()

        when(fragNum){
//            0 -> {
//                ft.replace(R.id.main_projectview_framelayout,AttentionProject()).commit()
//            }
//
//            1-> {
//                ft.replace(R.id.main_projectview_framelayout,AttentionMember()).commit()
//            }
        }

    }

}

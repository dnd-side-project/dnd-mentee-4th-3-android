package com.thisteampl.jackpot.main


import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.common.GlobalApplication.Companion.prefs
import com.thisteampl.jackpot.main.filtering.FilteringSearch
import com.thisteampl.jackpot.main.floating.MyAppeal
import com.thisteampl.jackpot.main.floating.ProjectCreation
import com.thisteampl.jackpot.main.mainhome.*
import com.thisteampl.jackpot.main.mypage.MyPage
import com.thisteampl.jackpot.main.projectController.ProjectElement
import com.thisteampl.jackpot.main.viewmore.RecentlyProjectViewMore
import kotlinx.android.synthetic.main.activity_main.*
import com.thisteampl.jackpot.main.projectController.projectAPI
import kotlinx.android.synthetic.main.activity_project_creation.*
import kotlinx.android.synthetic.main.activity_project_creation.view.*
import okhttp3.Callback
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity(){

    private lateinit var recentlyregister : RecentlyRegisterProject

    // projectAPI retrofit
    private var projectapi = projectAPI.projectRetrofitService()
    private val selectAllItems = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mypageIntent = Intent(this, MyPage::class.java)
        val mainintent = Intent(this,MainActivity::class.java)
        val searchintent = Intent(this,FilteringSearch::class.java)

        // 검색
        main_search_imageview.setOnClickListener {
            startActivity(searchintent)
        }

        // 나의 페이지
        main_mypage_imageview.setOnClickListener{
            // 후에 401 에러 시 만료 에러 + 다시 로그인
            if(prefs.getString("token", "NO_TOKEN") == "NO_TOKEN") {
                Toast.makeText(this, "로그인 정보가 없습니다." +
                        "\n로그인 화면으로 이동합니다.",Toast.LENGTH_SHORT).show()
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
        setFrag(0)
        setFrag(1)
        // 주목받는 프로젝트, 액티비티 프래그먼트 연결
        // attentionlocation => 더보기 버튼 옵션 넣기 위해 사용
//        main_projectattention_textview.setOnClickListener {
//            attentionlocation = 0
//            main_projectattention_textview.setTextColor(Color.BLACK)
//            main_memberattention_textview.setTextColor(Color.GRAY)
//
//        }

//         주목받는 멤버, 액티비티 프래그먼트 연결
//         attentionlocation => 더보기 버튼 옵션 넣기 위해 사용
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


        // 확인라인
        btn.setOnClickListener {

            for(i in 1..10){
                projectapi?.getprojectsID(i)
                    ?.enqueue(object : retrofit2.Callback<ProjectElement>{
                        override fun onFailure(call: Call<ProjectElement>, t: Throwable) {
                            Log.d("tag : ","Not found id")
                        }
                        override fun onResponse(
                            call: Call<ProjectElement>,
                            response: Response<ProjectElement>
                        ) {
                            Log.d("tag num : ","${i}")
                            Log.d("tag, id : ","${response.code()}")
                            Log.d("tag, 기간 : ","${response.body()?.getduration()}")
                            Log.d("tag, 관심 : ","${response.body()?.getinterest()?.duration}")
                            Log.d("tag, 지역 : ","${response.body()?.getinterest()?.region}")
                            Log.d("tag, 제목 : ","${response.body()?.getinterest()?.title}")
                            Log.d("tag, 포지션 : ","${response.body()?.getinterest()?.position}")
                            Log.d("tag, 스택 : ","${response.body()?.getinterest()?.stacks}")



                        }

                    })
            }


            projectapi?.getprojectsID(1)
                ?.enqueue(object : retrofit2.Callback<ProjectElement>{
                    override fun onFailure(call: Call<ProjectElement>, t: Throwable) {
                        Log.d("tag : ","Not found id")
                    }
                    override fun onResponse(
                        call: Call<ProjectElement>,
                        response: Response<ProjectElement>
                    ) {

                        Log.d("tag num : ","${1}")
                        Log.d("tag, id : ","${response.code()}")
                        Log.d("tag, 기간 : ","${response.body()?.getduration()}")
                        Log.d("tag, 관심 : ","${response.body()?.getinterest()?.duration}")
                        Log.d("tag, 지역 : ","${response.body()?.getinterest()?.region}")
                        Log.d("tag, 제목 : ","${response.body()?.getinterest()?.title}")
                        Log.d("tag, 포지션 : ","${response.body()?.getinterest()?.position}")

                    }

                })

            projectapi?.getprojectsID(6)
                ?.enqueue(object : retrofit2.Callback<ProjectElement>{
                    override fun onFailure(call: Call<ProjectElement>, t: Throwable) {
                        Log.d("tag : ","Not found id")
                    }
                    override fun onResponse(
                        call: Call<ProjectElement>,
                        response: Response<ProjectElement>
                    ) {

                        Log.d("tag num : ","${6}")
                        Log.d("tag, id : ","${response.code()}")
                        Log.d("tag, 기간 : ","${response.body()?.getduration()}")
                        Log.d("tag, 관심 : ","${response.body()?.getinterest()?.duration}")
                        Log.d("tag, 지역 : ","${response.body()?.getinterest()?.region}")
                        Log.d("tag, 제목 : ","${response.body()?.getinterest()?.title}")
                        Log.d("tag, 포지션 : ","${response.body()?.getinterest()?.position}")

                    }

                })
        }


    }


    // 0번 : 주목받는 프로젝트, 1번 : 주목받는 멤버
    private fun setFrag(fragNum : Int){
        val ft = supportFragmentManager.beginTransaction()

//        when(fragNum){
//            0 -> {
//                ft.replace(R.id.main_projectview_framelayout,AttentionProject()).commit()
//            }
//
//            1-> {
//                ft.replace(R.id.main_projectview_framelayout2,AttentionMember()).commit()
//            }
//        }

    }




}

package com.thisteampl.jackpot.main


import android.content.Intent
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
import com.thisteampl.jackpot.main.mainhome.FieldMore
import com.thisteampl.jackpot.main.projectController.ProjectGetElement
import com.thisteampl.jackpot.main.projectController.ProjectPostLatest
import com.thisteampl.jackpot.main.projectController.projectAPI
import com.thisteampl.jackpot.main.userController.CheckMyProfile
import com.thisteampl.jackpot.main.userController.userAPI

import com.thisteampl.jackpot.main.userpage.MyPageActivity
import com.thisteampl.jackpot.main.viewmore.RecentlyProjectViewMore
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//  RESTAPI , Main pageNumber 0번

class MainActivity : AppCompatActivity() {


    private var len  = 0
    private lateinit var attentionproject_backend: AttentionProject
    private lateinit var recentlyregister: RecentlyRegisterProject
    private val userApi = userAPI.create()

    // projectAPI retrofit
    var projectapi = projectAPI.projectRetrofitService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // 어댑터에 api작성시 : Fragment, 어댑터 실행 후 api 소스 실행 됨
        // 그럼에 main에서 RESETAPI 소스 호출하여 실행한 후, onResponse 안에서 Fragment, 어댑터 호출

        // 주목받는 프로젝트, 주목받는 멤버, 최근 등록된 프로젝트 관련 메소드
        adapters_fragments_in_main()



        val mypageIntent = Intent(this, MyPageActivity::class.java)
        getProfile()





        // 검색
        main_search_imageview.setOnClickListener {

            if (prefs.getString("token", "NO_TOKEN") == "NO_TOKEN") {
                Toast.makeText(
                    this, "로그인 정보가 없습니다." +
                            "\n로그인 화면으로 이동합니다.", Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }else{
                val searchintentpage = Intent(this, FilteringSearch::class.java)
                userApi?.getProfile()?.enqueue(
                    object : Callback<CheckMyProfile> {
                        override fun onFailure(call: Call<CheckMyProfile>, t: Throwable) {
                            // userAPI에서 타입이나 이름 안맞췄을때
                            Log.e("tag ", "onFailure, " + t.localizedMessage)
                        }

                        override fun onResponse(
                            call: Call<CheckMyProfile>,
                            response: Response<CheckMyProfile>
                        ) {
                            if(response.isSuccessful){
                                searchintentpage.putExtra("position",response.body()!!.result.position)
                                searchintentpage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                startActivity(searchintentpage)
                            }
                        }
                    }
                )
            }

        }

        // 분야 선택
        fieldSelect()


        // 알림
        main_bell_imageview.setOnClickListener {
            if (prefs.getString("token", "NO_TOKEN") == "NO_TOKEN") {
                Toast.makeText(
                    this, "로그인 정보가 없습니다." +
                            "\n로그인 화면으로 이동합니다.", Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            } else {
//                startActivity(mypageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }

        }


        // 나의 페이지
        main_mypage_imageview.setOnClickListener {
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


        // MainActivity 실행
        main_appname_textview.setOnClickListener {
            val mainintent = Intent(this, MainActivity::class.java)
            startActivity(mainintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }


        var attentionlocation : Int = 0
        setFrag(attentionlocation)
        // 주목받는 프로젝트, 액티비티 프래그먼트 연결
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




        // 참고 자료 : https://medium.com/@logishudson0218/intent-flag%EC%97%90-%EB%8C%80%ED%95%9C-%EC%9D%B4%ED%95%B4-d8c91ddd3bfc
        // addFlags() : 새로운 flag를 기존 flag에 붙임
        // 최근에 등록된 프로젝트 더보기 버튼 (더보기 page에서 백엔드 연결할 예정)
        main_recentlyviewmore_textview.setOnClickListener {
            if (prefs.getString("token", "NO_TOKEN") == "NO_TOKEN") {
                Toast.makeText(
                    this, "로그인 정보가 없습니다." +
                            "\n로그인 화면으로 이동합니다.", Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }else{
                val intentrecentlyviewmore = Intent(this, RecentlyProjectViewMore::class.java)
                intentrecentlyviewmore.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intentrecentlyviewmore)
            }
        }



        // 플로팅 버튼 눌렸을 때
        main_optionmenu_floatingactionbutton.setOnClickListener {
            if (prefs.getString("token", "NO_TOKEN") == "NO_TOKEN") {
                Toast.makeText(
                    this, "로그인 정보가 없습니다." +
                            "\n로그인 화면으로 이동합니다.", Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            } else {
                var drawproject: Intent = Intent(this, ProjectCreation::class.java)
                drawproject.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(drawproject)
            }
        }


    }

    // 분야 선택
    private fun fieldSelect() {
        // ConstraintLayout을 전체적임에 반복문 x, 8개 버튼 각각 구현
        main_selftdevelopment_imgbtn.setOnClickListener {
            field_selection("자기계발")
        }
        main_hobby_imgbtn.setOnClickListener {
            field_selection("취미")
        }
        main_economy_imgbtn.setOnClickListener {
            field_selection("경제")
        }
        main_cook_imgbtn.setOnClickListener {
            field_selection("요리")
        }
        main_it_imgbtn.setOnClickListener {
            field_selection("IT")
        }
        main_art_imgbtn.setOnClickListener {
            field_selection("예술_창작")
        }
        main_health_imgbtn.setOnClickListener {
            field_selection("건강")
        }
        main_repose_imgbtn.setOnClickListener {
            field_selection("휴식")
        }

    }

    private fun field_selection(field : String) {
        if (prefs.getString("token", "NO_TOKEN") == "NO_TOKEN") {
            Toast.makeText(
                this, "로그인 정보가 없습니다." +
                        "\n로그인 화면으로 이동합니다.", Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        } else {
            var drawproject: Intent = Intent(this, FieldMore::class.java)
            drawproject.putExtra("field",field)
            drawproject.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(drawproject)
        }
    }


    // 주목 받는 프로젝트, 주목 받는 멤버 5개, 최근 등록된 파일에서는 10개 아래로
    // RESTAPI , Main pageNumber 0번, pageSize 10개
    // Main에서는 duration, interestFilter, stackFilter : 빈칸 처리,
    //pageNumber 보여줄 page,
    // pageSize: 페이지 크기
    //regionFilter : 지역필터(빈칸)
    private fun adapters_fragments_in_main() {
        var file_empty2 = mutableListOf<String>()
        var file_empty = String()
        Log.d("tag","Main에서 fragment 호출")
        file_empty2.add("")
        file_empty = ""

        // 3. 최근에 등록된 프로젝트
        var recruitmentproject = ProjectPostLatest(
            file_empty2,file_empty2,0,5,file_empty,"최신순",file_empty2
        )

        projectapi?.getProjectContents(recruitmentproject)
            ?.enqueue(object : Callback<ProjectGetElement> {
                override fun onFailure(call: Call<ProjectGetElement>, t: Throwable) {
                    Log.e("tag ", "onFailure, " + t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ProjectGetElement>,
                    response: Response<ProjectGetElement>
                ) {

                    if (response.isSuccessful) {
                        val recentlylist = response.body()?.contents
                        // 최근에 등록된 프로젝트, 액티비티 프래그먼트 연결
                        recentlyregister = RecentlyRegisterProject.newInstance()
                        if (recentlylist != null) {
                            recentlyregister.connectprojectbackend(recentlylist)
                        }
                        Log.d("tag","Main에서 recentlyregister 호출")
                        supportFragmentManager.beginTransaction().add(R.id.main_recentlyregisterproject_framelayout,recentlyregister)
                            .commitAllowingStateLoss()


                    }else{
                        Log.e("tag","Main에서 ${response.message()}")
                        Log.e("tag","Main에서 ${response.code().toString()}")
                    }
                }
            })
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
    }


    // 0번 : 주목받는 프로젝트, 1번 : 주목받는 멤버
    private fun setFrag(fragNum : Int){
        val ft = supportFragmentManager.beginTransaction()

        var file_empty2 = mutableListOf<String>()
        var file_empty = String()
        Log.d("tag","Main에서 fragment 호출")
        file_empty2.add("")
        file_empty = ""


        when(fragNum){
            0 -> {
                var attentionproject = ProjectPostLatest(
                    file_empty2,file_empty2,0,5,file_empty,"인기순",file_empty2
                )
                projectapi?.getProjectContents(attentionproject)
                    ?.enqueue(object : Callback<ProjectGetElement> {
                        override fun onFailure(call: Call<ProjectGetElement>, t: Throwable) {
                            Log.e("tag ", "onFailure, " + t.localizedMessage)
                        }

                        override fun onResponse(
                            call: Call<ProjectGetElement>,
                            response: Response<ProjectGetElement>
                        ) {

                            if (response.isSuccessful) {
                                val attentionlist = response.body()?.contents
                                // 최근에 등록된 프로젝트, 액티비티 프래그먼트 연결
                                attentionproject_backend = AttentionProject.newInstance()
                                if (attentionlist != null) {
                                    attentionproject_backend.connectprojectbackend(attentionlist)
                                }
                                Log.d("tag","Main에서 attentionproject 호출")
                                ft.replace(R.id.main_projectview_framelayout, attentionproject_backend).commit()
                            }else{
                                Log.e("tag","Main에서 attentionproject 결과 : ${response.code().toString()}")
                            }
                        }
                    })

            }

            1-> {

                // 아직안됨
                var attentionproject = ProjectPostLatest(
                    file_empty2,file_empty2,0,5,file_empty,"멤버순",file_empty2
                )
                projectapi?.getProjectContents(attentionproject)
                    ?.enqueue(object : Callback<ProjectGetElement> {
                        override fun onFailure(call: Call<ProjectGetElement>, t: Throwable) {
                            Log.e("tag ", "onFailure, " + t.localizedMessage)
                        }

                        override fun onResponse(
                            call: Call<ProjectGetElement>,
                            response: Response<ProjectGetElement>
                        ) {

                            if (response.isSuccessful) {
                                val attentionlist = response.body()?.contents
                                // 최근에 등록된 프로젝트, 액티비티 프래그먼트 연결
                                attentionproject_backend = AttentionProject.newInstance()
                                if (attentionlist != null) {
                                    attentionproject_backend.connectprojectbackend(attentionlist)
                                }
                                Log.d("tag","Main에서 attentionproject 호출")
                                ft.replace(R.id.main_projectview_framelayout, attentionproject_backend).commit()
                            }else{
                                Log.e("tag","Main에서 attentionproject 결과 : ${response.code().toString()}")
                            }
                        }
                    })

                ft.replace(R.id.main_projectview_framelayout, AttentionMember()).commit()
            }
        }

    }


    // onResume
    override fun onResume() {
        super.onResume()
    }

    // 유효기간 만료 체크
    private fun getProfile(){
        userApi?.getProfile()?.enqueue(
            object : Callback<CheckMyProfile> {
                override fun onFailure(call: Call<CheckMyProfile>, t: Throwable) {
                    // userAPI에서 타입이나 이름 안맞췄을때
                    Log.e("tag ", "onFailure, " + t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<CheckMyProfile>,
                    response: Response<CheckMyProfile>
                ) {
                    when {
                        response.code().toString() == "401" -> {
                            prefs.setString("token", "NO_TOKEN")
                        }
                    }
                }
            })
    }

}





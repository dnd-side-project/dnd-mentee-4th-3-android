package com.thisteampl.jackpot.main.filtering

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectController.ProjectGetElement
import com.thisteampl.jackpot.main.projectController.ProjectPostLatest
import com.thisteampl.jackpot.main.projectController.projectAPI
import com.thisteampl.jackpot.main.userController.*
import com.thisteampl.jackpot.main.viewmore.FilteringFragment
import kotlinx.android.synthetic.main.activity_filtered_search_results.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilteringSearchResults : AppCompatActivity() {

    var projectapi = projectAPI.projectRetrofitService()
    var userapi = userAPI.create()


    // mutableListOf => getStringArrayListExtra
    // String => getStringExtra
    // 포지션, 스택, 오프라인 온라인, 지역, 기간, 관심분야
    var position = mutableListOf<String>()
    var stackTool = mutableListOf<String>()
    var onoff = ""
    var region = ""
    var duration = mutableListOf<String>()
    var interestfilter = mutableListOf<String>()

    // page
    var page = ""
    var selection_result = "최신순"

    private lateinit var filtering: FilteringFragment
    private lateinit var filteringID: FilteringUserFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtered_search_results)

        page = intent.getStringExtra("page")!!
        position = intent.getStringArrayListExtra("position")!!
        stackTool = intent.getStringArrayListExtra("stackTool")!!
        onoff = intent.getStringExtra("onoff")!!
        region = intent.getStringExtra("region")!!
        duration = intent.getStringArrayListExtra("duration")!!
        interestfilter = intent.getStringArrayListExtra("interest")!!


//        Log.d("tag","page 결과 : ${page}")
//        Log.d("tag","positon 결과 : ${position}")
//        Log.d("tag","stackTool 결과 : ${stackTool}")
//        Log.d("tag","onoff 결과 : ${onoff}")
//        Log.d("tag","region 결과 : ${region}")
//        Log.d("tag","duration 결과 : ${duration}")
//        Log.d("tag","interest 결과 : ${interest}")
//        if(page.equals("1")){
//            Log.d("tag","1pgage입니다.")
//        }
//        if(position.isEmpty()){
//            Log.d("tag","position 비어있다.")
//        }
//        Log.d("tag","--------------끝")


        filterresult_backbutton_button.setOnClickListener {




            finish()
        }


        var rankinglist = listOf("최신순", "인기순")
        filterresult_powerspinner.setItems(rankinglist)
        filterresult_powerspinner.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            selection_result = newItem

            if (page.equals("1")) {
                setprojectListView()
            } else if (page.equals("2")) {
                setuserListView()
            }

        }



        if (page.equals("1")) {
            filterresult_filteringsearch_textview.text = "프로젝트 찾기"
            setprojectListView()
        } else if (page.equals("2")) {
            filterresult_filteringsearch_textview.text = "멤버찾기"
            setuserListView()
        }


    }

    private fun setuserListView() {

        var filteringuser = UserRelatedFilteringPost(
            0, 30, position, region, "", stackTool
        )

        Log.d("tag","스택 : ${stackTool}")
        if (selection_result.equals("최신순")) {
            filteringuser.sortType = "최신순"
        } else if (selection_result.equals("인기순")) {
            filteringuser.sortType = "인기순"
        }
//        Log.d("tag", "최신순, 인기순 : ${filteringuser.sortType}")
//
//        Log.d("tag", "pageNumber : ${filteringuser.pageNumber}")
//        Log.d("tag", "pageSize: ${filteringuser.pageSize}")
//        Log.d("tag", "포지션 : ${filteringuser.position}")
//        Log.d("tag", "지역 : ${filteringuser.regionFilter}")
//        Log.d("tag", "정렬 : ${filteringuser.sortType}")
//        Log.d("tag", "스택 : ${filteringuser.stackFilter}")


        userapi?.getUserPosition(filteringuser)
            ?.enqueue(object : Callback<UserRelatedFilteringGet> {
                override fun onFailure(call: Call<UserRelatedFilteringGet>, t: Throwable) {
                    Log.d("tag : ", "onFailure" + t.localizedMessage)

                }

                override fun onResponse(
                    call: Call<UserRelatedFilteringGet>,
                    response: Response<UserRelatedFilteringGet>
                ) {

                    if (response.isSuccessful) {
//                        ToastmakeTextPrint("멤버 찾기 검색 되었습니다.")
                        filtersearch_projectcount_textview.visibility = View.GONE
                        filtersearch_projectcount2_textview.visibility = View.VISIBLE

                        if(response.body()!!.contents.isNotEmpty()){
                            filtersearch_projectcount2_textview.text = "${response.body()!!.contents.size}명의 멤버"
                        }else{
                            filtersearch_projectcount2_textview.visibility = View.GONE
                        }


                        var usersearch = response.body()?.contents
                        // 최근에 등록된 프로젝트, 액티비티 프래그먼트 연결
                        filteringID = FilteringUserFragment.newInstance()
                        if (usersearch != null) {
                            filteringID.connectprojectbackend(usersearch)
                        }
                        supportFragmentManager.beginTransaction().add(R.id.filterresult_fragment,filteringID)
                            .commitAllowingStateLoss()

                    } else {
                        ToastmakeTextPrint("멤버 찾기 검색 되지 않았습니다.")
                        Log.d("tag", "${response.code().toString()}")
                    }
                }
            })


    }


    val arrid = arrayListOf<Long>(100)
    var idx = 0

    private fun setprojectListView() {


        var filteringproject = ProjectPostLatest(
            duration, interestfilter, 0, 30, region, "", stackTool
        )


        if (selection_result.equals("최신순")) {
            filteringproject.sortType = "최신순"
        } else if (selection_result.equals("인기순")) {
            filteringproject.sortType = "인기순"
        }


        projectapi?.getProjectContents(filteringproject)
            ?.enqueue(object : Callback<ProjectGetElement> {
                override fun onFailure(call: Call<ProjectGetElement>, t: Throwable) {
                    Log.d("tag : ", "onFailure" + t.localizedMessage)

                }

                override fun onResponse(
                    call: Call<ProjectGetElement>,
                    response: Response<ProjectGetElement>
                ) {

                    if (response.isSuccessful) {

                        response.body()

//                        ToastmakeTextPrint("프로젝트 검색 완료")
                        filtersearch_projectcount_textview.visibility = View.VISIBLE
                        filtersearch_projectcount2_textview.visibility = View.GONE


                        if(response.body()!!.contents.isNotEmpty()){
                            filtersearch_projectcount_textview.text = "${response.body()!!.contents.size}개의 프로젝트"
                        }else{
                            filtersearch_projectcount_textview.visibility = View.GONE
                        }

//                        val len = response.body()!!.contents.size
//
//                        Log.d("tag","결과 : ${len}")
//
//                        for(n in 0 until len){
//                            getuserid(response.body()!!.contents[n].id)
//                        }


                        var filteringsearch = response.body()?.contents
                        // 최근에 등록된 프로젝트, 액티비티 프래그먼트 연결
                        filtering = FilteringFragment.newInstance()
                        if (filteringsearch != null) {
                            filtering.connectprojectbackend(filteringsearch)
                        }
                        supportFragmentManager.beginTransaction().add(R.id.filterresult_fragment,filtering)
                            .commitAllowingStateLoss()

                    } else {
                        ToastmakeTextPrint("프로젝트 검색 완료되지 않았습니다.")
                        Log.d("tag", "${response.code().toString()}")
                    }
                }
            })
    }

    private fun getuserid(projectid : Long){
        userapi?.getProfile()?.enqueue(
            object : Callback<CheckMyProfile> {
                override fun onFailure(call: Call<CheckMyProfile>, t: Throwable) {
                    // userAPI에서 타입이나 이름 안맞췄을때
                    Log.e("tag ", "onFailure, " + t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<CheckMyProfile>,
                    response: Response<CheckMyProfile>
                ) {
                   if(response.isSuccessful) {
                        for (i in response.body()?.result!!.scrapProjects) {
                            if (i.id == projectid) {
                                // 체크되게
                                Log.d("tag","해당 id, ${projectid}")
                                arrid[idx++] = projectid
                            }
                        }

                    } else{
                        ToastmakeTextPrint("스크랩이 적용되지 않았습니다.")
                        Log.d("tag", "${response.code().toString()}")
                    }
                }
            })

    }

    private fun ToastmakeTextPrint(word: String) {
        Toast.makeText(this, word, Toast.LENGTH_SHORT).show()
    }
}
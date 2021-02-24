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
import com.thisteampl.jackpot.main.userController.UserRelatedFilteringGet
import com.thisteampl.jackpot.main.userController.UserRelatedFilteringPost
import com.thisteampl.jackpot.main.userController.userAPI
import kotlinx.android.synthetic.main.activity_filtered_search_results.*
import kotlinx.android.synthetic.main.activity_filtering_search.*
import kotlinx.android.synthetic.main.activity_project_view_more.*
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
            filterresult_filteringsearch_textview.visibility = View.VISIBLE
            filterresult_filteringsearch2_textview.visibility = View.GONE
            setprojectListView()
        } else if (page.equals("2")) {
            filterresult_filteringsearch_textview.visibility = View.GONE
            filterresult_filteringsearch2_textview.visibility = View.VISIBLE
            setuserListView()
        }


    }

    private fun setuserListView() {

        var filteringuser = UserRelatedFilteringPost(
            0, 30, position, region, "", stackTool
        )

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

                    // 데이터 전달하지 못했다면
                    if (response.isSuccessful) {
                        ToastmakeTextPrint("멤버 찾기 검색 되었습니다.")
                        filtersearch_projectcount_textview.visibility = View.GONE
                        filtersearch_projectcount2_textview.visibility = View.VISIBLE

                        if(response.body()!!.contents.isNotEmpty()){
                            filtersearch_projectcount2_textview.text = "${response.body()!!.contents.size}명의 멤버"
                        }else{
                            filtersearch_projectcount2_textview.visibility = View.GONE
                        }
                        

                    val adapter = Filteringuseradapter(
                            getApplicationContext(), response.body()!!.contents
                        )
                        filterresult_listview.adapter = adapter

                    } else {
                        ToastmakeTextPrint("멤버 찾기 검색 되지 않았습니다.")
                        Log.d("tag", "${response.code().toString()}")
                    }
                }
            })


    }

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
                    // 데이터 전달하지 못했다면
                    if (response.isSuccessful) {
                        ToastmakeTextPrint("프로젝트 검색 완료 되었습니다.")
                        filtersearch_projectcount_textview.visibility = View.VISIBLE
                        filtersearch_projectcount2_textview.visibility = View.GONE


                        if(response.body()!!.contents.isNotEmpty()){
                            filtersearch_projectcount_textview.text = "${response.body()!!.contents.size}개의 프로젝트"
                        }else{
                            filtersearch_projectcount_textview.visibility = View.GONE
                        }



                        val adapter = FilteringProjectAdapter(
                            getApplicationContext(), response.body()!!.contents
                        )
                        filterresult_listview.adapter = adapter

                        Log.d("tag","크기 : ${adapter.getCount()}")
                    } else {
                        ToastmakeTextPrint("프로젝트 검색 완료되지 않았습니다.")
                        Log.d("tag", "${response.code().toString()}")
                    }
                }
            })
    }


    private fun ToastmakeTextPrint(word: String) {
        Toast.makeText(this, word, Toast.LENGTH_SHORT).show()
    }
}
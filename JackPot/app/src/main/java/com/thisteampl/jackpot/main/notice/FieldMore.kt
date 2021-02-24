//package com.thisteampl.jackpot.main.notice
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Toast
//import com.thisteampl.jackpot.R
//import com.thisteampl.jackpot.main.filtering.Filteringuseradapter
//import com.thisteampl.jackpot.main.mainhome.RecentlyRegisterProject
//import com.thisteampl.jackpot.main.projectController.ProjectGetElement
//import com.thisteampl.jackpot.main.projectController.ProjectPostLatest
//import com.thisteampl.jackpot.main.projectController.projectAPI
//import com.thisteampl.jackpot.main.userController.UserRelatedFilteringGet
//import com.thisteampl.jackpot.main.userController.UserRelatedFilteringPost
//import com.thisteampl.jackpot.main.userController.userAPI
//import kotlinx.android.synthetic.main.activity_field_more.*
//import kotlinx.android.synthetic.main.activity_filtered_search_results.*
//import okhttp3.Call
//import okhttp3.Callback
//import okhttp3.Response
//
//class FieldMore : AppCompatActivity() {
//
//
//    var projectapi = projectAPI.projectRetrofitService()
//    var userapi = userAPI.create()
//    var field = ""
//    var selection_result = ""
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_field_more)
//
//        field = intent.getStringExtra("field")!!
//
//
//        recentlyregitstered_backbutton_button.setOnClickListener {
//            finish()
//        }
//
//        var rankinglist = listOf("최신순", "인기순")
//        recentlyregitstered_powerspinner.setItems(rankinglist)
//        recentlyregitstered_powerspinner.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
//            selection_result = newItem
//
//        }
//
//        setprojectListView()
//    }
//
//    private fun setprojectListView() {
//
//        val mut = mutableListOf<String>()
//        mut.add("")
//        var fieldlist = mutableListOf<String>()
//        fieldlist.add(field)
//        var filteringproject = ProjectPostLatest(
//            mut, fieldlist, 0, 30, "", "", mut
//        )
//
//
//        if (selection_result.equals("최신순")) {
//            filteringproject.sortType = "최신순"
//        } else if (selection_result.equals("인기순")) {
//            filteringproject.sortType = "인기순"
//        }
//
//
//        projectapi?.getProjectContents(filteringproject)
//            ?.enqueue(object : Callback<ProjectGetElement>)
//        projectapi?.getProjectContents(filteringproject)
//            ?.enqueue(object : retrofit2.Callback<ProjectGetElement> {
//                override fun onFailure(call: Call<ProjectGetElement>, t: Throwable) {
//                    Log.d("tag : ", "onFailure" + t.localizedMessage)
//
//                }
//
//                override fun onResponse(
//                    call: Call<ProjectGetElement>,
//                    response: Response<ProjectGetElement>
//                ) {
//                    // 데이터 전달하지 못했다면
//                    if (response.isSuccessful) {
////                        ToastmakeTextPrint("프로젝트 검색 완료 되었습니다.")
////                        recentlyregitstered_projectcount_textview.visibility = View.VISIBLE
////                        recentlyregitstered_projectcount2_textview.visibility = View.GONE
////
////
////                        if (response.body()!!.contents.isNotEmpty()) {
////                            recentlyregitstered_projectcount_textview.text =
////                                "${response.body()!!.contents.size}개의 프로젝트"
////                        } else {
////                            recentlyregitstered_projectcount_textview.visibility = View.GONE
////                        }
////
////
////                        val adapter = FilteringProjectAdapter(
////                            getApplicationContext(), response.body()!!.contents
////                        )
////                        recentlyregitstered_listview.adapter = adapter
////
////                        Log.d("tag", "크기 : ${adapter.getCount()}")
////                    } else {
////                        ToastmakeTextPrint("프로젝트 검색 완료되지 않았습니다.")
////                        Log.d("tag", "${response.code().toString()}")
////                    }
////                }
//                    })
//
//
//
//                    projectapi?.getProjectContents(filteringproject)
//                        ?.enqueue(object : retrofit2.Callback<ProjectGetElement> {
//                            override fun onFailure(
//                                call: retrofit2.Call<ProjectGetElement>,
//                                t: Throwable
//                            ) {
//                                Log.d("tag : ", "onFailure" + t.localizedMessage)
//                            }
//
//                            override fun onResponse(
//                                call: retrofit2.Call<ProjectGetElement>,
//                                response: retrofit2.Response<ProjectGetElement>
//                            ) {
//                                if (response.isSuccessful) {
//                                    ToastmakeTextPrint("프로젝트 검색 완료 되었습니다.")
//
//
//                                    val adapter = FieldMoreAdapter(
//                                        getApplicationContext(), response.body()!!.contents
//                                    )
//                                    recentlyregitstered_listview.adapter = adapter
//
//                                    Log.d("tag", "크기 : ${adapter.getCount()}")
//                                } else {
//                                    ToastmakeTextPrint("프로젝트 검색 완료되지 않았습니다.")
//                                    Log.d("tag", "${response.code().toString()}")
//                                }
//
//                            }
//
//                        })
//
//
//                }
//
//
//                private fun ToastmakeTextPrint(word: String) {
//                    Toast.makeText(baseContext, word, Toast.LENGTH_SHORT).show()
//                }
//
//
//            }

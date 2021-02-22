package com.thisteampl.jackpot.main.filtering

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectController.ProjectGetElement
import com.thisteampl.jackpot.main.projectController.ProjectPostLatest
import com.thisteampl.jackpot.main.projectController.projectAPI
import kotlinx.android.synthetic.main.activity_filtered_search_results.*
import kotlinx.android.synthetic.main.activity_filtering_search.*
import kotlinx.android.synthetic.main.activity_project_view_more.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilteringSearchResults : AppCompatActivity() {

    var projectapi = projectAPI.projectRetrofitService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtered_search_results)

        var page = intent.getIntExtra("page",0)

        var selection_result = String()
        var rankinglist = listOf("최신순","인기순")
        filterresult_powerspinner.setItems(rankinglist)
        filterresult_powerspinner.setOnSpinnerItemSelectedListener<String>{ oldIndex, oldItem, newIndex, newItem ->
            selection_result = newItem
        }


        Log.d("tag","page 번호 여기!: ${page}")
        if (page == 1){
            // 여기 에러
//            filtersearch_filteringfiltersearch_textview.text = "프로젝트 찾기"
            Log.d("tag","에러")
            setprojectListView(selection_result)
        }else if(page == 2){
//            filtersearch_filteringfiltersearch_textview.text = "멤버 찾기"
            setuserListView(selection_result)
        }


    }

    private fun setuserListView(select:String) {
        var userlist = ArrayList<Filteringuserdata>()





    }

    private fun setprojectListView(select:String) {
        var projectlist = mutableListOf<FilteringSearch>()



        if(select.equals("최신순")){
            var liststr = mutableListOf<String>()
            liststr.add("")
            var filteringproject = ProjectPostLatest(
                liststr, liststr, 0, 100, "", "최신순",liststr
            )

            projectapi?.getprojectcontents(filteringproject)
                ?.enqueue(object : Callback<ProjectGetElement> {
                    override fun onFailure(call: Call<ProjectGetElement>, t: Throwable) {
                        Log.d("tag : ", "onFailure"+t.localizedMessage)

                    }
                    override fun onResponse(
                        call: Call<ProjectGetElement>,
                        response: Response<ProjectGetElement>
                    ) {

                        // 데이터 전달하지 못했다면
                        if(response.isSuccessful){
                            ToastmakeTextPrint("프로젝트 모집글 작성 완료 되었습니다.")

                            val adapter = Filteringprojectadapter(
                                getApplicationContext(), response.body()!!.contents
                            )
                            filterresult_listview.adapter = adapter

                        }else{
                            ToastmakeTextPrint("프로젝트 모집글 작성 완료되지 않았습니다.")
                            Log.d("tag","${response.code().toString()}")
                            Log.e("tag","onFailure" + response.message())
                        }
                    }
                })

        }else if(select.equals("인기순")){
            var liststr = mutableListOf<String>()
            liststr.add("")
            var filteringuser = ProjectPostLatest(
                liststr, liststr, 0, 100, "", "인기순",liststr
            )

            projectapi?.getprojectcontents(filteringuser)
                ?.enqueue(object : Callback<ProjectGetElement> {
                    override fun onFailure(call: Call<ProjectGetElement>, t: Throwable) {
                        Log.d("tag : ", "onFailure"+t.localizedMessage)

                    }

                    override fun onResponse(
                        call: Call<ProjectGetElement>,
                        response: Response<ProjectGetElement>
                    ) {
                        if(response.isSuccessful){
                            ToastmakeTextPrint("프로젝트 모집글 작성 완료 되었습니다.")
                        }else{
                            ToastmakeTextPrint("프로젝트 모집글 작성 완료되지 않았습니다.")
                            Log.d("tag","${response.code().toString()}")
                            Log.e("tag","onFailure" + response.message())
                        }
                    }
                })

        }





    }

    private fun ToastmakeTextPrint(word: String) {
        Toast.makeText(this, word, Toast.LENGTH_SHORT).show()
    }
}
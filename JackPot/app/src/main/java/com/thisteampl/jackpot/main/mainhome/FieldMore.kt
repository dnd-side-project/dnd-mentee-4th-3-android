package com.thisteampl.jackpot.main.mainhome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectController.ProjectGetElement
import com.thisteampl.jackpot.main.projectController.ProjectPostLatest
import com.thisteampl.jackpot.main.projectController.projectAPI
import com.thisteampl.jackpot.main.userController.userAPI
import com.thisteampl.jackpot.main.viewmore.FieldMoreFragment
import com.thisteampl.jackpot.main.viewmore.RecentlyProjectViewFragment
import kotlinx.android.synthetic.main.activity_field_more.*


class FieldMore : AppCompatActivity() {

    var projectapi = projectAPI.projectRetrofitService()
    var userapi = userAPI.create()
    var field = ""
    var selection_result = ""
    private lateinit var fieldmore: FieldMoreFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field_more)

        field = intent.getStringExtra("field")!!


        fieldmore_backbutton_button.setOnClickListener {
            finish()
        }

        var rankinglist = listOf("주목받는 순")
        fieldmore_powerspinner.setItems(rankinglist)
        fieldmore_powerspinner.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            selection_result = newItem

            setprojectListView()
        }

        setprojectListView()
    }

    // ListView 출력
    private fun setprojectListView() {
        val mut = mutableListOf<String>()
        mut.add("")
        var fieldlist = mutableListOf<String>()
        fieldlist.add(field)
        var filteringproject = ProjectPostLatest(
            mut, fieldlist, 0, 30, "", "최신순", mut
        )


        if (selection_result.equals("주목받는 순")) {
            filteringproject.sortType = "최신순"
        }


        // 제목
        if(field.equals("예술_창작")) fieldmore_filteringsearch_textview.text = "예술/창작"
        else fieldmore_filteringsearch_textview.text = field
        
        projectapi?.getProjectContents(filteringproject)
            ?.enqueue(object : retrofit2.Callback<ProjectGetElement> {
                override fun onFailure(
                    call: retrofit2.Call<ProjectGetElement>,
                    t: Throwable
                ) {
                    Log.d("tag : ", "onFailure" + t.localizedMessage)
                }

                override fun onResponse(
                    call: retrofit2.Call<ProjectGetElement>,
                    response: retrofit2.Response<ProjectGetElement>
                ) {
                    if (response.isSuccessful) {
//                        ToastmakeTextPrint("분야 검색 완료")

                        var field = response.body()?.contents
                        // 최근에 등록된 프로젝트, 액티비티 프래그먼트 연결
                        fieldmore = FieldMoreFragment.newInstance()
                        if (field != null) {
                            fieldmore.connectprojectbackend(field)
                        }
                        supportFragmentManager.beginTransaction().add(R.id.fieldmore_listview,fieldmore)
                            .commitAllowingStateLoss()


                    } else {
                        ToastmakeTextPrint("분야 검색 완료되지 않았습니다.")
                        Log.d("tag", "${response.code().toString()}")
                        Log.d("tag","분야 : ${field}")
                        Log.d("tag","분야 : ")
                    }

                }

            })


    }


    private fun ToastmakeTextPrint(word: String) {
        Toast.makeText(baseContext, word, Toast.LENGTH_SHORT).show()
    }


}

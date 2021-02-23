package com.thisteampl.jackpot.main.projectdetail

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectController.CheckProject
import com.thisteampl.jackpot.main.projectController.projectAPI
import com.thisteampl.jackpot.main.userpage.AnotherProject
import kotlinx.android.synthetic.main.activity_project_request.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectRequestActivity : AppCompatActivity() {

    private val projectApi = projectAPI.create()
    private var projectID = 0
    lateinit var mPrjRequestAdapter: ProjectRequestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_request)

        projectID = intent.getLongExtra("id", 0).toInt()

        //신청자 리사이클러뷰 어댑터 설정
        mPrjRequestAdapter = ProjectRequestAdapter()
        project_request_recyclerview.adapter = mPrjRequestAdapter
        project_request_recyclerview.layoutManager = LinearLayoutManager(this)

        getProject(projectID)
        setupView()
    }

    private fun setupView(){
        project_request_back_button.setOnClickListener { super.onBackPressed() }
    }

    //프로젝트 정보를 가져온다.
    private fun getProject(id: Int) {
        projectApi?.getprojectsID(id)?.enqueue(object : Callback<CheckProject> {
            override fun onFailure(call: Call<CheckProject>, t: Throwable) {
                // userAPI에서 타입이나 이름 안맞췄을때
                Log.e("tag ", "onFailure" + t.localizedMessage)
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<CheckProject>,
                response: Response<CheckProject>
            ) {
                if(response.code().toString() == "200") {
                    for(i in response.body()?.result!!.requests) {
                        mPrjRequestAdapter.items.add(ProjectRequestMember(
                            projectID.toLong(), i.userIndex, i.position, i.name, i.emoticon))
                    }
                    mPrjRequestAdapter.notifyDataSetChanged()
                } else {
                    finish()
                }
            }
        })
    }
}
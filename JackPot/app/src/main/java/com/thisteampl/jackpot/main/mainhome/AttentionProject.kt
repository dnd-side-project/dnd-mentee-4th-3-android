package com.thisteampl.jackpot.main.mainhome

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.MainActivity
import com.thisteampl.jackpot.main.projectController.ProjectElement
import com.thisteampl.jackpot.main.projectController.projectAPI
import kotlinx.android.synthetic.main.fragment_attention_project.*
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

// 참고 자료 : https://youtu.be/BT206iXW9bk
// 주목받는 프로젝트
class AttentionProject : Fragment() {
    var attention: MutableList<AttentionProjectList> = mutableListOf()
    private var projectapi = projectAPI.projectRetrofitService()

    // init 초기화할 때, list를 삽입한다.
    init{

        // 백엔드 호출
        for (i in 1..10) {
            projectapi?.getprojectsID(i)
                ?.enqueue(object : retrofit2.Callback<ProjectElement> {
                    override fun onFailure(call: Call<ProjectElement>, t: Throwable) {
                        Log.d("tag : ", "Not found id")
                    }

                    override fun onResponse(
                        call: Call<ProjectElement>,
                        response: Response<ProjectElement>
                    ) {
//                        Log.d("tag, 관심 : ", "${response.body()?.getinterest()?.duration}")
//                        Log.d("tag, 지역 : ", "${response.body()?.getinterest()?.region}")
//                        Log.d("tag, 제목 : ", "${response.body()?.getinterest()?.title}")
//                        Log.d("tag, 포지션 : ", "${response.body()?.getinterest()?.position}")
//                        Log.d("tag, 스택 : ", "${response.body()?.getinterest()?.stacks}")


                    }

                })
        }



        attention = arrayListOf(
            AttentionProjectList(R.drawable.field_art,"프로젝트 체크","개발자"),
            AttentionProjectList(R.drawable.field_cook,"프로젝트 체크","개발자"),
            AttentionProjectList(R.drawable.field_health,"프로젝트 체크","개발자"),
            AttentionProjectList(R.drawable.field_economy,"프로젝트 체크","개발자"),
            AttentionProjectList(R.drawable.field_it,"프로젝트 체크","개발자")
        )
    }

    // View가 만들어진 후, onViewCreated() 콜백된다.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        main_attentionprojectlist_recyclerview.layoutManager = LinearLayoutManager((activity as MainActivity),LinearLayoutManager.HORIZONTAL,false)
        main_attentionprojectlist_recyclerview.setHasFixedSize(true) // RecyclerView 크기 유지 (변경 x)
        main_attentionprojectlist_recyclerview.adapter = AttentionProjectListAdapter(attention)
    }

    // 액티비티 프래그먼트 연결될 때 onAttach
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    // onCreate 후에 화면을 구성할 때 호출되는 부분
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_attention_project,container,false)
        return view
    }
}
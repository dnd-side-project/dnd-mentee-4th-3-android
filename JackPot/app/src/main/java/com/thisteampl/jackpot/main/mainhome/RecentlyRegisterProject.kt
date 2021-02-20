package com.thisteampl.jackpot.main.mainhome

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
// 최근 등록된 프로젝트
class RecentlyRegisterProject : Fragment() {
    var recentlyregister: MutableList<RecentlyRegisterList> = mutableListOf()



    private var projectapi = projectAPI.projectRetrofitService()

    // 최근등록된 내용을 DB에서 받아서 화면에 출력하기

    // init 초기화할 때, list를 삽입한다.
    init {

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
                        Log.d("tag num : ", "${i}")
                        Log.d("tag, id : ", "${response.code()}")
                        Log.d("tag, 기간 : ", "${response.body()?.getduration()}")
//                        Log.d("tag, 관심 : ", "${response.body()?.getinterest()?.duration}")
//                        Log.d("tag, 지역 : ", "${response.body()?.getinterest()?.region}")
//                        Log.d("tag, 제목 : ", "${response.body()?.getinterest()?.title}")
//                        Log.d("tag, 포지션 : ", "${response.body()?.getinterest()?.position}")
//                        Log.d("tag, 스택 : ", "${response.body()?.getinterest()?.stacks}")


                    }

                })
        }



        // 기술 스택 arrayListOf
        // ListOf 처리

        val datetime = "30분"
        val stacks = arrayListOf<String>()
        stacks.add("Java")
        stacks.add("Django")
        stacks.add("Figma")
        stacks.add("Cplus")
        stacks.add("Js")
        stacks.add("Kotlin")
        stacks.add("오예~")
        recentlyregister = arrayListOf(
            RecentlyRegisterList(R.drawable.field_art,"프로젝트 체크","디자이너",datetime,stacks),
            RecentlyRegisterList(0,"프로젝트 체크2","개발자",datetime,stacks),
            RecentlyRegisterList(R.drawable.field_art,"프로젝트 체크3","개발자",datetime,stacks),
            RecentlyRegisterList(0,"프로젝트 체크4","개발자","10분전",stacks),
            RecentlyRegisterList(0,"프로젝트 체크5","개발자","10분전",stacks)
        )


    }
    companion object {
        fun newInstance(): RecentlyRegisterProject{
            return RecentlyRegisterProject()
        }

    }

    // View가 만들어진 후, onViewCreated() 콜백된다.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        main_attentionprojectlist_recyclerview.layoutManager = LinearLayoutManager((activity as MainActivity),
            LinearLayoutManager.VERTICAL,false)
        main_attentionprojectlist_recyclerview.setHasFixedSize(true)
        main_attentionprojectlist_recyclerview.adapter = RecentlyRegisterListAdapter(recentlyregister)
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
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
import kotlinx.android.synthetic.main.fragment_recently_register_project.*
import java.util.ArrayList

// 참고 자료 : https://youtu.be/BT206iXW9bk
// 최근 등록된 프로젝트
class RecentlyRegisterProject : Fragment() {
    var recentlyregister: ArrayList<RecentlyRegisterList>? = null

    // 최근등록된 내용을 DB에서 받아서 화면에 출력하기

    // init 초기화할 때, list를 삽입한다.
    init {
        recentlyregister = arrayListOf(
            RecentlyRegisterList(R.drawable.android_appeal,"프로젝트 체크","디자이너","2020.01.31","c++","c#","c"),
            RecentlyRegisterList(0,"프로젝트 체크","디자이너","2020.01.31","java","eclipse","ki"),
            RecentlyRegisterList(R.drawable.android_plus_sign,"프로젝트 체크","디자이너","2020.01.31","Kotlin","window","mac")
          )


    }
    companion object {
        fun newInstance(): RecentlyRegisterProject{
            return RecentlyRegisterProject()
        }

    }

    // View가 만들어진 후, onViewCreated() 콜백된다.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        main_recentlyprojectlist_recyclerview.layoutManager = LinearLayoutManager((activity as MainActivity),
            LinearLayoutManager.HORIZONTAL,false)
        main_recentlyprojectlist_recyclerview.setHasFixedSize(true)
        main_recentlyprojectlist_recyclerview.adapter = RecentlyRegisterListAdapter(recentlyregister)
    }

    // 액티비티 프래그먼트 연결될 때 onAttach
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    // onCreate 후에 화면을 구성할 때 호출되는 부분
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recently_register_project,container,false)
        return view
    }
}
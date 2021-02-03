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

class RecentlyRegisterProject : Fragment() {
    var recentlyregister: ArrayList<RecentlyRegisterList>? = null

    // 최근등록된 내용을 DB에서 받아서 화면에 출력하기

    init {
        recentlyregister = arrayListOf(
            RecentlyRegisterList(R.drawable.android_appeal,"프로젝트 체크","디자이너","2020.01.31","c++","c#","c"),
            RecentlyRegisterList(R.drawable.android_group,"프로젝트 체크","디자이너","2020.01.31","java","eclipse","ki"),
            RecentlyRegisterList(R.drawable.android_plus_sign,"프로젝트 체크","디자이너","2020.01.31","Kotlin","window","mac"),
            RecentlyRegisterList(R.drawable.android_group,"프로젝트 체크","디자이너","2020.01.31","Kotlin","window","mac"),
            RecentlyRegisterList(R.drawable.android_group,"프로젝트 체크","디자이너","2020.01.31","Kotlin","window","mac"),
            RecentlyRegisterList(R.drawable.android_appeal,"프로젝트 체크","디자이너","2020.01.31","c++","c#","c"),
            RecentlyRegisterList(R.drawable.android_group,"프로젝트 체크","디자이너","2020.01.31","java","eclipse","ki"),
            RecentlyRegisterList(R.drawable.android_plus_sign,"프로젝트 체크","디자이너","2020.01.31","Kotlin","window","mac"),
            RecentlyRegisterList(R.drawable.android_group,"프로젝트 체크","디자이너","2020.01.31","Kotlin","window","mac"),
            RecentlyRegisterList(R.drawable.android_group,"프로젝트 체크","디자이너","2020.01.31","Kotlin","window","mac"),
            RecentlyRegisterList(R.drawable.android_appeal,"프로젝트 체크","디자이너","2020.01.31","c++","c#","c"),
            RecentlyRegisterList(R.drawable.android_group,"프로젝트 체크","디자이너","2020.01.31","java","eclipse","ki"),
            RecentlyRegisterList(R.drawable.android_plus_sign,"프로젝트 체크","디자이너","2020.01.31","Kotlin","window","mac"),
            RecentlyRegisterList(R.drawable.android_group,"프로젝트 체크","디자이너","2020.01.31","Kotlin","window","mac"),
            RecentlyRegisterList(R.drawable.android_group,"프로젝트 체크","디자이너","2020.01.31","Kotlin","window","mac")




        )
    }
    companion object {
        fun newInstance(): RecentlyRegisterProject{
            return RecentlyRegisterProject()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        main_recentlyprojectlist_recyclerview.layoutManager = LinearLayoutManager((activity as MainActivity),
            LinearLayoutManager.VERTICAL,false)
        main_recentlyprojectlist_recyclerview.setHasFixedSize(true)
        main_recentlyprojectlist_recyclerview.adapter = RecentlyRegisterListAdapter(recentlyregister)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recently_register_project,container,false)
        return view
    }
}
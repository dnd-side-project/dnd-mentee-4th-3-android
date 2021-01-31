package com.thisteampl.jackpot.main.mainhome

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.MainActivity
import kotlinx.android.synthetic.main.fragment_recently_register_project.*

class RecentlyRegisterProject : Fragment() {

    companion object {
        fun newInstance(): RecentlyRegisterProject{
            return RecentlyRegisterProject()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recentlyregister = arrayListOf(
            RecentlyRegisterList("프로젝트 체크","디자이너","2020.01.31","c++","c#","c"),
            RecentlyRegisterList("프로젝트 체크","디자이너","2020.01.31","java","eclipse","ki"),
            RecentlyRegisterList("프로젝트 체크","디자이너","2020.01.31","Kotlin","window","mac"),
            RecentlyRegisterList("프로젝트 체크","디자이너","2020.01.31","Kotlin","window","mac"),
            RecentlyRegisterList("프로젝트 체크","디자이너","2020.01.31","Kotlin","window","mac")


        )

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
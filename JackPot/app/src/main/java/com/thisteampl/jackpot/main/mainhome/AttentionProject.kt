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
import com.thisteampl.jackpot.main.projectController.ProjectComponent
import kotlinx.android.synthetic.main.fragment_attention_project.*

// 참고 자료 : https://youtu.be/BT206iXW9bk
// 주목받는 프로젝트
class AttentionProject : Fragment() {
    var attentionprojectlist : List<ProjectComponent> = listOf()

    companion object {
        fun newInstance(): AttentionProject{
            return AttentionProject()
        }
    }

    fun connectprojectbackend(list : List<ProjectComponent>){
        attentionprojectlist=list
        for(num in 0..attentionprojectlist.size-1){
            Log.d("tag","recentlylist size : ${attentionprojectlist.get(num).title}")
        }
    }

    // View가 만들어진 후, onViewCreated() 콜백된다.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        main_attentionprojectlist_recyclerview.layoutManager = LinearLayoutManager((activity as MainActivity),LinearLayoutManager.HORIZONTAL,false)
        main_attentionprojectlist_recyclerview.setHasFixedSize(true) // RecyclerView 크기 유지 (변경 x)
        main_attentionprojectlist_recyclerview.adapter = AttentionProjectListAdapter(attentionprojectlist)
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
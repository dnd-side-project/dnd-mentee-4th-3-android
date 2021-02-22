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
import com.thisteampl.jackpot.main.projectController.projectAPI
import kotlinx.android.synthetic.main.fragment_attention_project.*
import retrofit2.Call
import retrofit2.Response

// 참고 자료 : https://youtu.be/BT206iXW9bk
// 주목 받는 멤버 class
class AttentionMember : Fragment() {

    var attention : MutableList<AttentionMemberList> = mutableListOf()
    private var projectapi = projectAPI.projectRetrofitService()
    // init 초기화할 때, list를 삽입한다.


    // View가 만들어진 후, onViewCreated() 콜백된다.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        main_attentionprojectlist_recyclerview.layoutManager = LinearLayoutManager((activity as MainActivity),
            LinearLayoutManager.HORIZONTAL,false)
        main_attentionprojectlist_recyclerview.setHasFixedSize(true)  // RecyclerView 크기 유지 (변경 x)
        main_attentionprojectlist_recyclerview.adapter = AttentionMemberListAdapter(attention)
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
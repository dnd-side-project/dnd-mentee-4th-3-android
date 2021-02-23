package com.thisteampl.jackpot.main.mainhome

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kakao.sdk.common.KakaoSdk.init
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.MainActivity
import com.thisteampl.jackpot.main.projectController.*
import kotlinx.android.synthetic.main.fragment_attention_project.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 참고 자료 : https://youtu.be/BT206iXW9bk
// 최근 등록된 프로젝트
class RecentlyRegisterProject : Fragment() {

    // 백엔드에서 List<클래스> 선언하였기에 사용
    var recentlylist : List<ProjectElementMaterial> = listOf()

    companion object {
        fun newInstance(): RecentlyRegisterProject{
            return RecentlyRegisterProject()
        }

    }

    fun connectprojectbackend(list : List<ProjectElementMaterial>){
        recentlylist=list
        for(num in 0..recentlylist.size-1){
            Log.d("tag","recentlylist size : ${recentlylist.get(num).title}")
        }
    }


    // View가 만들어진 후, onViewCreated() 콜백된다.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.d("tag","RecentlyRegisterListAdapter")

        main_attentionprojectlist_recyclerview.layoutManager = LinearLayoutManager((activity as MainActivity),
            LinearLayoutManager.VERTICAL,false)
        main_attentionprojectlist_recyclerview.setHasFixedSize(true)
        main_attentionprojectlist_recyclerview.adapter = RecentlyRegisterListAdapter(recentlylist)
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
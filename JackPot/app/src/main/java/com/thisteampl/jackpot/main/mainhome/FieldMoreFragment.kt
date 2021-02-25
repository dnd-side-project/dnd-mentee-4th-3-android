package com.thisteampl.jackpot.main.viewmore

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
import com.thisteampl.jackpot.main.mainhome.FieldMore
import com.thisteampl.jackpot.main.projectController.ProjectElementMaterial
import kotlinx.android.synthetic.main.fragment_attention_project.*


class FieldMoreFragment : Fragment() {

    var fieldlist : List<ProjectElementMaterial> = listOf()

    companion object {
        fun newInstance(): FieldMoreFragment {
            return FieldMoreFragment()
        }

    }

    fun connectprojectbackend(list : List<ProjectElementMaterial>){
        fieldlist=list
        for(num in 0..fieldlist.size-1){
            Log.d("tag","fieldlist size : ${fieldlist.get(num).title}")
        }
    }


    // View가 만들어진 후, onViewCreated() 콜백된다.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.d("tag","RecentlyRegisterListAdapter")

        main_attentionprojectlist_recyclerview.layoutManager = LinearLayoutManager((activity as FieldMore),
            LinearLayoutManager.VERTICAL,false)
        main_attentionprojectlist_recyclerview.setHasFixedSize(true)
        main_attentionprojectlist_recyclerview.adapter = FieldMoreAdapter(fieldlist)
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
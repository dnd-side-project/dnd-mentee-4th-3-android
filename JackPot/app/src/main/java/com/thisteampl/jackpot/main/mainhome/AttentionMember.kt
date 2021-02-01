package com.thisteampl.jackpot.main.mainhome

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.MainActivity
import kotlinx.android.synthetic.main.fragment_attention_member.*
import kotlinx.android.synthetic.main.fragment_attention_project.*

class AttentionMember : Fragment() {

    companion object {
        fun newInstance(): AttentionMember {
            return AttentionMember()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val attention = arrayListOf(
            AttentionMemberList("멤버 체크","개발자","2020.01.29","c++","c#","c"),
            AttentionMemberList("멤버 체크","개발자","2020.01.29","java","javascript","jsp"),
            AttentionMemberList("멤버 체크","개발자","2020.01.29","linux","window","mac"),
            AttentionMemberList("멤버 체크","개발자","2020.01.29","linux","window","mac"),
            AttentionMemberList("멤버 체크","개발자","2020.01.29","linux","window","mac"),
            AttentionMemberList("멤버 체크","개발자","2020.01.29","linux","window","mac"),
            AttentionMemberList("멤버 체크","개발자","2020.01.29","linux","window","mac")

        )

        main_attentionmemberlist_recyclerview.layoutManager = LinearLayoutManager((activity as MainActivity),
            LinearLayoutManager.HORIZONTAL,false)
        main_attentionmemberlist_recyclerview.setHasFixedSize(true)
        main_attentionmemberlist_recyclerview.adapter = AttentionMemberListAdapter(attention)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_attention_member,container,false)
        return view
    }


}
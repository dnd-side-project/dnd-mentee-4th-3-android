package com.thisteampl.jackpot.main.viewmore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.mainhome.AttentionMemberList

class AttentionMemberViewMoreAdapter(val context: Context, val MemberList: ArrayList<AttentionMemberList>? = null
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.main_projectviewdetail_list, null)

        val profile = view.findViewById<ImageView>(R.id.imageViewdetail)
        val member_name = view.findViewById<TextView>(R.id.main_projectviewdetail_textview)
        val inputmemberviewdetail_position =
            view.findViewById<TextView>(R.id.main_inputprojectviewdetail_position_textview)
        val inputmemberviewdetail_update =
            view.findViewById<TextView>(R.id.main_inputprojectviewdetail_update_textview)
        val memberviewdetailstack =
            view.findViewById<TextView>(R.id.main_projectviewdetailstack_textview)
        val memberviewdetailstack2 =
            view.findViewById<TextView>(R.id.main_projectviewdetailstack2_textview)
        val memberviewdetailstack3 =
            view.findViewById<TextView>(R.id.main_projectviewdetailstack3_textview)

        var list = MemberList!![position]
        profile.setImageResource(list.memberiamge)
        member_name.text = list.attention_member_name
        inputmemberviewdetail_position.text = list.attentionmember_recruitment_position
        inputmemberviewdetail_update.text = list.update_date
        memberviewdetailstack.text = list.stack1
        memberviewdetailstack2.text = list.stack2
        memberviewdetailstack3.text = list.stack3

        return view
    }

    override fun getItem(position: Int): Any {
        return MemberList!![position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return MemberList!!.size
    }
}

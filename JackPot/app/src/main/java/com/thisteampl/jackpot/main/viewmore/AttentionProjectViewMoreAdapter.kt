package com.thisteampl.jackpot.main.viewmore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.mainhome.AttentionProjectList


class AttentionProjectViewMoreAdapter (val context: Context, val ProjectList: ArrayList<AttentionProjectList>? = null
) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.main_projectviewdetail_list, null)

        val profile = view.findViewById<ImageView>(R.id.imageViewdetail)
        val project_name = view.findViewById<TextView>(R.id.main_projectviewdetail_textview)
        val inputprojectviewdetail_position =
            view.findViewById<TextView>(R.id.main_inputprojectviewdetail_position_textview)
        val inputprojectviewdetail_update =
            view.findViewById<TextView>(R.id.main_inputprojectviewdetail_update_textview)
        val projectviewdetailstack =
            view.findViewById<TextView>(R.id.main_projectviewdetailstack_textview)
        val projectviewdetailstack2 =
            view.findViewById<TextView>(R.id.main_projectviewdetailstack2_textview)
        val projectviewdetailstack3 =
            view.findViewById<TextView>(R.id.main_projectviewdetailstack3_textview)

        var list = ProjectList!![position]
        profile.setImageResource(list.project_image)
        project_name.text = list.attention_project_name
        inputprojectviewdetail_position.text = list.attention_recruitment_position
        inputprojectviewdetail_update.text = list.update_date
        projectviewdetailstack.text = list.stack1
        projectviewdetailstack2.text = list.stack2
        projectviewdetailstack3.text = list.stack3

        return view
    }

    override fun getItem(position: Int): Any {
        return ProjectList!![position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return ProjectList!!.size
    }
}
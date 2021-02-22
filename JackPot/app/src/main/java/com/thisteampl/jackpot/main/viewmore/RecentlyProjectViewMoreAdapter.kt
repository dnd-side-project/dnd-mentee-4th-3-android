package com.thisteampl.jackpot.main.viewmore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.mainhome.RecentlyRegisterList

class RecentlyProjectViewMoreAdapter(val context: Context, val ProjectList: ArrayList<RecentlyRegisterList>?= null): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.main_projectviewmore_list,null)

        val profile = view.findViewById<ImageView>(R.id.imageViewdetail)
        val project_name = view.findViewById<TextView>(R.id.main_projectviewdetail_textview)
        val inputprojectviewdetail_position = view.findViewById<TextView>(R.id.main_inputprojectviewdetail_position_textview)
        val inputprojectviewdetail_update = view.findViewById<TextView>(R.id.main_inputprojectviewdetail_update_textview)

        var list = ProjectList!![position]
        project_name.text = list.recentlyregister_project_name
        inputprojectviewdetail_position.text = list.recentlyregister_recruitment_position?.get(0)
        inputprojectviewdetail_update.text = list.update_date

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
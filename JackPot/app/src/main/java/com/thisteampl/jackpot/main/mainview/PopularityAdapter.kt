package com.thisteampl.jackpot.main.mainview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectsearch.Project


class PopularityAdapter(val popularityList: ArrayList<Popularity>): RecyclerView.Adapter<PopularityAdapter.ProjectView>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularityAdapter.ProjectView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)

        return ProjectView(view).apply {
            itemView.setOnClickListener{
                val cur = adapterPosition
                val popular : Popularity = popularityList.get(cur)
                Log.d("apply부분","")
                Toast.makeText(parent.context,"프로젝트 : ${popular.project_name}",Toast.LENGTH_SHORT ).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return popularityList.size
    }

    @Override
    override fun onBindViewHolder(holder: PopularityAdapter.ProjectView, position: Int) {
        //val popularity : Popularity = popularityList.get(position)

        holder.project_name.text = popularityList.get(position).project_name
//        holder.heart_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
//            if(isChecked){
//                Log.d("버튼 체크","")
//            }
//        }
    }

    class ProjectView(itemView: View):RecyclerView.ViewHolder(itemView) {
        val project_name = itemView.findViewById<TextView>(R.id.projectname_tv)
//        val heart_checkbox = itemView.findViewById<CheckBox>(R.id.heart_mainhome_checkbox)

    }
}
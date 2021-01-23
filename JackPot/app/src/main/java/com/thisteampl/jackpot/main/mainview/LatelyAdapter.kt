package com.thisteampl.jackpot.main.mainview


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R

class LatelyAdapter(val latelyList: ArrayList<Lately>? = null): RecyclerView.Adapter<LatelyAdapter.ProjectView>() {

    class ProjectView(itemView: View):RecyclerView.ViewHolder(itemView) {
        val project_name = itemView.findViewById<TextView>(R.id.projectname_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatelyAdapter.ProjectView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        Log.d("OnCreateView부분","")

        return ProjectView(view).apply {
            itemView.setOnClickListener {
                val current = adapterPosition
                val current_lately : Lately = latelyList!!.get(current)

                Toast.makeText(parent.context,"프로젝트 : ${current_lately.lately_project_name}",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        if(latelyList != null ){
            return latelyList.size
        }else{
            return 0
        }
    }


    @Override
    override fun onBindViewHolder(holder: LatelyAdapter.ProjectView, position:Int) {
        holder.project_name.text = latelyList!!.get(index = position).lately_project_name

        // 다음 page
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,ProjectViewDetail::class.java)
            ContextCompat.startActivity(holder.itemView.context,intent,null)

        }
    }

}
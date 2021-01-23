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

class PopularityAdapter(val popularityList: ArrayList<Popularity>? = null): RecyclerView.Adapter<PopularityAdapter.ProjectView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularityAdapter.ProjectView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)

        return ProjectView(view).apply {
            itemView.setOnClickListener{
                val cur = adapterPosition
                val popular : Popularity = popularityList!!.get(cur)
                Log.d("apply부분","")
                Toast.makeText(parent.context,"프로젝트 : ${popular.project_name}",Toast.LENGTH_SHORT ).show()
            }
        }
    }

    override fun getItemCount(): Int {
        if (popularityList != null) {
            return popularityList.size
        }else{
            return 0
        }
    }

    override fun onBindViewHolder(holder: PopularityAdapter.ProjectView, position: Int) {

        holder.project_name.text = popularityList!!.get(index = position).project_name

        // 다음 page
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,ProjectViewDetail::class.java)
            ContextCompat.startActivity(holder.itemView.context,intent,null)

        }
    }

    class ProjectView(itemView: View):RecyclerView.ViewHolder(itemView) {
        val project_name = itemView.findViewById<TextView>(R.id.projectname_tv)

    }
}
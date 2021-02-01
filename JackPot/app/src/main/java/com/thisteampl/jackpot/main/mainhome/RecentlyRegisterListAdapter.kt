package com.thisteampl.jackpot.main.mainhome

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R
import java.util.*

class RecentlyRegisterListAdapter (val recentlyregisterlist: ArrayList<RecentlyRegisterList>? = null): RecyclerView.Adapter<RecentlyRegisterListAdapter.ProjectView>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentlyRegisterListAdapter.ProjectView {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_recentlyproject_list, parent, false)

        return ProjectView(view)
    }

    override fun getItemCount(): Int {
        if (recentlyregisterlist != null) {
            return recentlyregisterlist.size
        } else {
            return 0
        }
    }

    override fun onBindViewHolder(holder: RecentlyRegisterListAdapter.ProjectView, position: Int) {

        holder.project_name.text =
            recentlyregisterlist!!.get(index = position).recentlyregister_project_name
        holder.recruitment_position.text =
            recentlyregisterlist!!.get(index = position).recentlyregister_recruitment_position
        holder.update_date.text = recentlyregisterlist!!.get(index = position).update_date
        holder.stack1.text = recentlyregisterlist!!.get(index = position).stack1
        holder.stack2.text = recentlyregisterlist!!.get(index = position).stack2
        holder.stack3.text = recentlyregisterlist!!.get(index = position).stack3


        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProjectViewDetail::class.java)
            ContextCompat.startActivity(holder.itemView.context, intent, null)

        }
    }

    class ProjectView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val project_name = itemView.findViewById<TextView>(R.id.main_recentlyproject_textview)
        val recruitment_position =
            itemView.findViewById<TextView>(R.id.main_inputrecentlyproject_position_textview)
        val update_date =
            itemView.findViewById<TextView>(R.id.main_inputrecentlyproject_update_textview)
        val stack1 = itemView.findViewById<TextView>(R.id.main_recentlyprojectstack_textview)
        val stack2 = itemView.findViewById<TextView>(R.id.main_recentlyprojectstack2_textview)
        val stack3 = itemView.findViewById<TextView>(R.id.main_recentlyprojectstack3_textview)

    }


}
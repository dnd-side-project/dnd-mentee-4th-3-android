package com.thisteampl.jackpot.main.mainhome

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectdetail.ProjectViewDetail
import java.util.*


// 최근 등록된 프로젝트 어댑터(연결 구간)
class RecentlyRegisterListAdapter (val recentlyregisterlist: ArrayList<RecentlyRegisterList>? = null): RecyclerView.Adapter<RecentlyRegisterListAdapter.ProjectView>() {


    // onCreateViewHolder : ViewHolder와 Layout 파일을 연결해주는 역할
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentlyRegisterListAdapter.ProjectView {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_recentlyregisterproject_list, parent, false)

        return ProjectView(view)
    }

    override fun getItemCount(): Int {
        if (recentlyregisterlist != null) {
            return recentlyregisterlist.size
        } else {
            return 0
        }
    }


    // onBindViewHolder : 생성된 ViewHolder에 바인딩 해주는 함수
    override fun onBindViewHolder(holder: RecentlyRegisterListAdapter.ProjectView, position: Int) {

//        holder.imageview.setImageResource(recentlyregisterlist!!.get(position).recentlyiamge)
//        holder.project_name.text =
//            recentlyregisterlist.get(index = position).recentlyregister_project_name
//        holder.recruitment_position.text =
//            recentlyregisterlist.get(index = position).recentlyregister_recruitment_position
//        holder.update_date.text = recentlyregisterlist.get(index = position).update_date
//        holder.stack1.text = recentlyregisterlist.get(index = position).stack1
//        holder.stack2.text = recentlyregisterlist.get(index = position).stack2
//        holder.stack3.text = recentlyregisterlist.get(index = position).stack3


        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProjectViewDetail::class.java)
            intent.putExtra("project", holder.project_name.text as String?)
            intent.putExtra("position",holder.recruitment_position.text as String?)
            intent.putExtra("update",holder.update_date.text as String?)
            intent.putExtra("stack1",holder.stack1.text as String?)
            intent.putExtra("stack2",holder.stack2.text as String?)
            intent.putExtra("stack3",holder.stack3.text as String?)
            holder.itemView.context.startActivity(intent)

        }
    }

    class ProjectView(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imageview = itemView.findViewById<ImageView>(R.id.main_recentlyregisterprojectimage_imageview)
        val project_name = itemView.findViewById<TextView>(R.id.main_recentlyregisterprojectname_textview)
        val recruitment_position =
            itemView.findViewById<TextView>(R.id.main_inputposition_textview)
        val update_date =
            itemView.findViewById<TextView>(R.id.main_inputupdatedate_textview)
        val stack1 = itemView.findViewById<TextView>(R.id.main_recentlyregisterprojectstack_textview)
        val stack2 = itemView.findViewById<TextView>(R.id.main_recentlyregisterprojectstack2_textview)
        val stack3 = itemView.findViewById<TextView>(R.id.main_recentlyregisterprojectstack3_textview)

    }


}
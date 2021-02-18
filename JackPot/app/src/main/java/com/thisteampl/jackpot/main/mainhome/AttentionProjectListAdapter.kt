package com.thisteampl.jackpot.main.mainhome

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectdetail.ProjectViewDetail
import java.util.*

// 주목받는 프로젝트 어댑터(연결 구간)
class AttentionProjectListAdapter (val attentionlist: ArrayList<AttentionProjectList>? = null): RecyclerView.Adapter<AttentionProjectListAdapter.ProjectView>(){

    // onCreateViewHolder : ViewHolder와 Layout 파일을 연결해주는 역할
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttentionProjectListAdapter.ProjectView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_attentionproject_list,parent,false)

        return ProjectView(view)
    }

    override fun getItemCount(): Int {
        if (attentionlist != null) {
            return attentionlist.size
        }else{
            return 0
        }
    }


    // onBindViewHolder : 생성된 ViewHolder에 바인딩 해주는 함수
    override fun onBindViewHolder(holder: AttentionProjectListAdapter.ProjectView, position: Int) {

        holder.imageview.setImageResource(attentionlist!!.get(position).project_image)
        holder.project_name.text = attentionlist.get(index = position).attention_project_name
        holder.recruitment_position.text = attentionlist.get(index = position).attention_recruitment_position


        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProjectViewDetail::class.java)
            intent.putExtra("project", holder.project_name.text.toString())
            intent.putExtra("position",holder.recruitment_position.text.toString())
            holder.itemView.context.startActivity(intent)
        }
    }

    class ProjectView(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageview = itemView.findViewById<ImageView>(R.id.main_attention_imageview)
        val project_name = itemView.findViewById<TextView>(R.id.main_attentionproject_textview)
        val recruitment_position = itemView.findViewById<TextView>(R.id.main_inputattentionproject_position_textview)


    }


}
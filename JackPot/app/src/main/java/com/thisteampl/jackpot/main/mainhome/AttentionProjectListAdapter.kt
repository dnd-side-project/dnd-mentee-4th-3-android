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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_attention_list,parent,false)

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
        holder.project_name.text = attentionlist!!.get(index = position).attention_project_name
        holder.recruitment_position.text = attentionlist!!.get(index = position).attention_recruitment_position
        holder.update_date.text = attentionlist!!.get(index = position).update_date
        holder.stack1.text = attentionlist!!.get(index = position).stack1
        holder.stack2.text = attentionlist!!.get(index = position).stack2
        holder.stack3.text = attentionlist!!.get(index = position).stack3


        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProjectViewDetail::class.java)
            ContextCompat.startActivity(holder.itemView.context,intent,null)

        }
    }

    class ProjectView(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageview = itemView.findViewById<ImageView>(R.id.main_projectimage_imageview)
        val project_name = itemView.findViewById<TextView>(R.id.main_projectname_textview)
        val recruitment_position = itemView.findViewById<TextView>(R.id.main_inputposition_textview)
        val update_date = itemView.findViewById<TextView>(R.id.main_inputupdatedate_textview)
        val stack1 = itemView.findViewById<TextView>(R.id.main_projectstack_textview)
        val stack2 = itemView.findViewById<TextView>(R.id.main_projectstack2_textview)
        val stack3 = itemView.findViewById<TextView>(R.id.main_projectstack3_textview)

    }


}
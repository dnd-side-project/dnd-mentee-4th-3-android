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


// 주목받는 멤버 어댑터(연결 구간)
class AttentionMemberListAdapter(val attentionmemberlist: ArrayList<AttentionMemberList> ?= null): RecyclerView.Adapter<AttentionMemberListAdapter.ProjectView>() {

    class ProjectView(itemview: View):RecyclerView.ViewHolder(itemview){
        val imageview = itemView.findViewById<ImageView>(R.id.main_memberimage_imageview)
        val member_name = itemView.findViewById<TextView>(R.id.main_membername_textview)
        val position = itemView.findViewById<TextView>(R.id.main_inputmemberposition_textview)
        val update_date = itemView.findViewById<TextView>(R.id.main_inputmemberupdatedate_textview)
        val stack1 = itemView.findViewById<TextView>(R.id.main_projectmemberstack_textview)
        val stack2 = itemView.findViewById<TextView>(R.id.main_projectmemberstack2_textview)
        val stack3 = itemView.findViewById<TextView>(R.id.main_projectmemberstack3_textview)

    }

    // onCreateViewHolder : ViewHolder와 Layout 파일을 연결해주는 역할
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_attentionmember_list,parent,false)

        return ProjectView(view)
    }

    override fun getItemCount(): Int {
        if (attentionmemberlist != null) {
            return attentionmemberlist.size
        }else{
            return 0
        }
    }

    // onBindViewHolder : 생성된 ViewHolder에 바인딩 해주는 함수
    override fun onBindViewHolder(holder: ProjectView, position: Int) {
        holder.imageview.setImageResource(attentionmemberlist!!.get(position).memberiamge)
        holder.member_name.text = attentionmemberlist!!.get(index = position).attention_member_name
        holder.position.text = attentionmemberlist!!.get(index = position).attentionmember_recruitment_position
        holder.update_date.text = attentionmemberlist!!.get(index = position).update_date
        holder.stack1.text = attentionmemberlist!!.get(index = position).stack1
        holder.stack2.text = attentionmemberlist!!.get(index = position).stack2
        holder.stack3.text = attentionmemberlist!!.get(index = position).stack3


        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProjectViewDetail::class.java)
            ContextCompat.startActivity(holder.itemView.context,intent,null)

        }

    }
}
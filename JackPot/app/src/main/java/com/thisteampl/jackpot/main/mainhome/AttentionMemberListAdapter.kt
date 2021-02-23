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
import com.thisteampl.jackpot.main.projectController.ProjectElementMaterial
import com.thisteampl.jackpot.main.projectdetail.ProjectViewDetail
import com.thisteampl.jackpot.main.userController.UserRelatedFilteringcontents
import kotlinx.android.synthetic.main.main_attentionmember_list.view.*
import java.util.*


// 주목받는 멤버 어댑터(연결 구간)
class AttentionMemberListAdapter(val attentionmemberlist: List<UserRelatedFilteringcontents> = mutableListOf()): RecyclerView.Adapter<AttentionMemberListAdapter.AttentionMemberListRecyclerViewHolder>() {

    class AttentionMemberListRecyclerViewHolder(view:View):RecyclerView.ViewHolder(view)

    // onCreateViewHolder : ViewHolder와 Layout 파일을 연결해주는 역할
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttentionMemberListRecyclerViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_attentionmember_list,parent,false)

        return AttentionMemberListRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (attentionmemberlist != null) {
            return attentionmemberlist.size
        }else{
            return 0
        }
    }

    // onBindViewHolder : 생성된 ViewHolder에 바인딩 해주는 함수
    override fun onBindViewHolder(holder: AttentionMemberListRecyclerViewHolder, position: Int) {


        val item = attentionmemberlist[position]

        with(holder.itemView) {
//            main_attentionmemberitem_image.setImageResource(item.emoticon)
            main_attentionmember_title_textview.text = item.name


            main_attentionproject_content_textview.text = "${item.position} ・${item.career}"


        }


        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProjectViewDetail::class.java)
            // 유저쪽 멤버라 id 없음
            intent.putExtra("id",item.name)
            holder.itemView.context.startActivity(intent)

        }

    }
}
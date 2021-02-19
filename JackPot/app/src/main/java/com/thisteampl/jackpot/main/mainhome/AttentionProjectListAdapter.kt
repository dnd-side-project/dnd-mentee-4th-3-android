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
import kotlinx.android.synthetic.main.main_attentionproject_list.view.*
import java.util.*

// 주목받는 프로젝트 어댑터(연결 구간)
class AttentionProjectListAdapter(val attentionlist: MutableList<AttentionProjectList> = mutableListOf()) :
    RecyclerView.Adapter<AttentionProjectListAdapter.AttentionProjectListAdapterRecyclerViewHolder>() {


    class AttentionProjectListAdapterRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view)

    // onCreateViewHolder : ViewHolder와 Layout 파일을 연결해주는 역할
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AttentionProjectListAdapterRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_attentionproject_list, parent, false)

        return AttentionProjectListAdapterRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (attentionlist != null) {
            return attentionlist.size
        } else {
            return 0
        }
    }


    // onBindViewHolder : 생성된 ViewHolder에 바인딩 해주는 함수
    override fun onBindViewHolder(
        holder: AttentionProjectListAdapterRecyclerViewHolder,
        position: Int
    ) {


        val item = attentionlist[position]

        with(holder.itemView) {
            main_attentionitem_image.setImageResource(item.project_image)
            main_attentionproject_textview.text = item.attention_project_name
            main_inputattentionproject_position_textview.text = item.attention_recruitment_position


            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, ProjectViewDetail::class.java)
                holder.itemView.context.startActivity(intent)
            }
        }


    }
}
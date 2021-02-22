package com.thisteampl.jackpot.main.projectdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R
import kotlinx.android.synthetic.main.holder_project_detail_comment.view.*


class ProjectCommentAdapter(var items: MutableList<ProjectDetailComment> = mutableListOf()
) : RecyclerView.Adapter<ProjectCommentAdapter.ProjectCommentRecyclerViewHolder>() {

    class ProjectCommentRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProjectCommentRecyclerViewHolder {
        return ProjectCommentRecyclerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.holder_project_detail_comment, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ProjectCommentRecyclerViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            when (item.position) {
                "개발자" -> {
                    holder_project_detail_comment_position_background.setImageResource(R.drawable.background_developer)
                }
                "디자이너" -> {
                    holder_project_detail_comment_position_background.setImageResource(R.drawable.background_designer)
                }
                else -> {
                    holder_project_detail_comment_position_background.setImageResource(R.drawable.background_director)
                }
            }
            holder_project_detail_comment_date_text.text = item.date
            holder_project_detail_comment_name_text.text = item.name
            holder_project_detail_comment_position_text.text = item.position
            holder_project_detail_comment_text.text = item.comment
            //holder_project_detail_comment_icon_text.text = item.emoticon
        }
    }
}
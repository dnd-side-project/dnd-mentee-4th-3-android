package com.thisteampl.jackpot.main.projectdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R


class ProjectCommentAdapter(var items: MutableList<ProjectComment> = mutableListOf()
) : RecyclerView.Adapter<ProjectCommentAdapter.ProjectCommentRecyclerViewHolder>() {

    class ProjectCommentRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProjectCommentRecyclerViewHolder {
        return ProjectCommentRecyclerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.holder_mypage_anotherproject, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ProjectCommentRecyclerViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
        }
    }
}
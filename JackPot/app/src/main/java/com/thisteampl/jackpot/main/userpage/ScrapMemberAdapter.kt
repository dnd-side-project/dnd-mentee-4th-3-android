package com.thisteampl.jackpot.main.userpage

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectController.projectAPI
import kotlinx.android.synthetic.main.holder_project_member.view.*

class ScrapMemberAdapter(var items: MutableList<ScrapMember> = mutableListOf()
) : RecyclerView.Adapter<ScrapMemberAdapter.ProjectRequestRecyclerViewHolder>() {

    class ProjectRequestRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProjectRequestRecyclerViewHolder {
        return ProjectRequestRecyclerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.holder_project_member, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ProjectRequestRecyclerViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            holder_project_request_icon_text.text = item.emoticon

            when (item.position) {
                "개발자" -> {
                    holder_project_request_position_background
                        .setImageResource(R.drawable.background_developer)
                }
                "디자이너" -> {
                    holder_project_request_position_background
                        .setImageResource(R.drawable.background_designer)
                }
                else -> {
                    holder_project_request_position_background
                        .setImageResource(R.drawable.background_director)
                }
            }

            holder_project_request_position_name_text.text = item.position + "・" + item.name

            //프로필 보기 버튼
            holder_project_request_watch_profile_button.setOnClickListener {
                val intent = Intent(context, ProfileActivity::class.java)
                    .putExtra("title", "멤버 프로필").putExtra("id", item.id)
                context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }

            holder_project_request_accept_button.visibility = View.GONE
            holder_project_horizontal_bar.visibility = View.GONE
        }
    }
}
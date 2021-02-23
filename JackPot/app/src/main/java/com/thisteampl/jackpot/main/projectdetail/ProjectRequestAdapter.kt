package com.thisteampl.jackpot.main.projectdetail

import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectController.ParticipantAccept
import com.thisteampl.jackpot.main.projectController.projectAPI
import com.thisteampl.jackpot.main.userController.CheckResponse
import com.thisteampl.jackpot.main.userpage.ProfileActivity
import kotlinx.android.synthetic.main.holder_project_member.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectRequestAdapter(var items: MutableList<ProjectRequestMember> = mutableListOf()
) : RecyclerView.Adapter<ProjectRequestAdapter.ProjectRequestRecyclerViewHolder>() {

    class ProjectRequestRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view)
    private val projectApi = projectAPI.create()
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

            // 멤버 수락 버튼
            holder_project_request_accept_button.setOnClickListener {
                projectApi?.getProjectAccept(ParticipantAccept(item.projectId, item.id))?.enqueue(object : Callback<CheckResponse> {
                    override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                        // userAPI에서 타입이나 이름 안맞췄을때
                        Log.e("tag ", "onFailure" + t.localizedMessage)
                    }

                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onResponse(
                        call: Call<CheckResponse>,
                        response: Response<CheckResponse>
                    ) {
                        if(response.code().toString() == "200") {
                            Toast.makeText(
                                context,
                                item.name + "님이 프로젝트에 추가됐습니다!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            val intent1 = Intent(context, ProjectViewDetail::class.java)
                                .putExtra("id", item.projectId)
                            context.startActivity(intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                            val intent2 = Intent(context, ProjectRequestActivity::class.java)
                                .putExtra("id", item.projectId)
                            context.startActivity(intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        } else {
                            Toast.makeText(
                                context,
                                "멤버 프로젝트 수락에 실패했습니다.\n에러 코드 : " + response.code() + "\n" + response.body()?.message,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                })
            }
        }
    }
}
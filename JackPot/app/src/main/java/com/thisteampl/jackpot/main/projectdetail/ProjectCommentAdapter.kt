package com.thisteampl.jackpot.main.projectdetail

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.common.GlobalApplication
import com.thisteampl.jackpot.main.projectController.projectAPI
import com.thisteampl.jackpot.main.userController.CheckMyProfile
import com.thisteampl.jackpot.main.userController.CheckResponse
import com.thisteampl.jackpot.main.userController.userAPI
import kotlinx.android.synthetic.main.holder_project_detail_comment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProjectCommentAdapter(var items: MutableList<ProjectDetailComment> = mutableListOf()
) : RecyclerView.Adapter<ProjectCommentAdapter.ProjectCommentRecyclerViewHolder>() {

    class ProjectCommentRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view)
    private val userApi = userAPI.create()
    private val projectApi = projectAPI.create()
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
        getName()
        val item = items[position]

        with(holder.itemView) {
            //댓글이 비공개일 경우
            Log.e("tag ", getName() + ", " + item.name + ", " + item.projectOwnerName)
            if(!item.privacy && getName() != item.name && getName() != item.projectOwnerName) {
                holder_project_detail_comment_position_background.visibility = View.GONE
                holder_project_detail_comment_date_text.visibility = View.GONE
                holder_project_detail_comment_name_text.visibility = View.GONE
                holder_project_detail_comment_position_text.visibility = View.GONE
                holder_project_detail_comment_text.visibility = View.GONE
                holder_project_detail_comment_icon_text.visibility = View.GONE

                holder_project_detail_comment_hide_text.visibility = View.VISIBLE
            }

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
            holder_project_detail_comment_icon_text.text = item.emoticon

            if(getName() == item.name) {
                holder_project_detail_comment_delete_button.visibility = View.VISIBLE
            }

            //댓글 삭제 버튼
            holder_project_detail_comment_delete_button.setOnClickListener {
                projectApi?.deleteComment(item.id)?.enqueue(object : Callback<CheckResponse> {
                    override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                        // userAPI에서 타입이나 이름 안맞췄을때
                        Log.e("tag ", "onFailure" + t.localizedMessage)
                    }

                    override fun onResponse(
                        call: Call<CheckResponse>,
                        response: Response<CheckResponse>
                    ) {
                        if(response.code().toString() == "200") {
                            Toast.makeText(context, "댓글이 삭제되었습니다.", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(context, ProjectViewDetail::class.java)
                            context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        } else {
                            Toast.makeText(context, "댓글 삭제에 실패했습니다.\n에러 코드 : " + response.code() + "\n" + response.body().toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                })
            }

        }
    }

    //유저 이름 가져오기
    private fun getName(): String {
        var userName = "USER_NAME_NEED_INITIALIZE"
        userApi?.getProfile()?.enqueue(
            object : Callback<CheckMyProfile> {
                override fun onFailure(call: Call<CheckMyProfile>, t: Throwable) {
                    // userAPI에서 타입이나 이름 안맞췄을때
                    Log.e("tag ", "onFailure, " + t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<CheckMyProfile>,
                    response: Response<CheckMyProfile>
                ) {
                    when {
                        response.code().toString() == "200" -> {
                            userName = response.body()?.result!!.name
                        }
                        response.code().toString() == "401" -> {
                            GlobalApplication.prefs.setString("token", "NO_TOKEN")
                        }
                    }
                }
            })
        return userName
    }

}
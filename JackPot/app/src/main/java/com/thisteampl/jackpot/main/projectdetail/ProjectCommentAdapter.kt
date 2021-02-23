package com.thisteampl.jackpot.main.projectdetail

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
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
        val item = items[position]

        with(holder.itemView) {
            //댓글이 비공개일 경우 - 보는사람이 글쓴이가 아니고, 보는 사람 이름과 쓴 사람 이름이 같지 않다면 비공개
            if(!item.privacy && !item.isOwner && item.name != item.watcherName) {
                holder_project_detail_comment_position_background.setImageResource(R.drawable.background_hide)
                holder_project_detail_comment_icon_text.text = "🔒"
                holder_project_detail_comment_date_text.text = item.date
                holder_project_detail_comment_name_text.text = "비공개 댓글입니다"
                holder_project_detail_comment_position_text.visibility = View.GONE
                holder_project_detail_comment_text.text = ""
            } else {
                when (item.position) {
                    "개발자" -> {
                        holder_project_detail_comment_position_background.setImageResource(R.drawable.background_developer)
                        holder_project_detail_comment_position_text.setTextColor(getColor(context, R.color.colorDeveloper))
                    }
                    "디자이너" -> {
                        holder_project_detail_comment_position_background.setImageResource(R.drawable.background_designer)
                        holder_project_detail_comment_position_text.setTextColor(getColor(context, R.color.colorDesigner))
                    }
                    else -> {
                        holder_project_detail_comment_position_background.setImageResource(R.drawable.background_director)
                        holder_project_detail_comment_position_text.setTextColor(getColor(context, R.color.colorDirector))
                    }
                }
                holder_project_detail_comment_date_text.text = item.date
                if (!item.privacy) {
                    holder_project_detail_comment_name_text.text = item.name + "🔒"
                } else {
                    holder_project_detail_comment_name_text.text = item.name
                }
                holder_project_detail_comment_position_text.text = item.position
                holder_project_detail_comment_text.text = item.comment
                holder_project_detail_comment_icon_text.text = item.emoticon

                if (item.watcherName == item.name) {
                    holder_project_detail_comment_delete_button.visibility = View.VISIBLE
                }
            }

            //댓글 삭제 버튼
            holder_project_detail_comment_delete_button.setOnClickListener {

                val dialog = AlertDialog.Builder(context)
                dialog.setTitle("댓글 삭제")
                dialog.setMessage("댓글을 삭제하시겠습니까?")

                var dialog_listener =
                    DialogInterface.OnClickListener { _, which ->
                        when (which) {
                            DialogInterface.BUTTON_POSITIVE -> {
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
                                            val intent = Intent(context, ProjectViewDetail::class.java).putExtra("id", item.projectID)
                                            context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        } else {
                                            Toast.makeText(context, "댓글 삭제에 실패했습니다.\n에러 코드 : " + response.code() + "\n" + response.body().toString(), Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                    }
                                })
                            }
                            DialogInterface.BUTTON_NEGATIVE -> {

                            }
                        }
                    }
                dialog.setPositiveButton("확인", dialog_listener)
                dialog.setNegativeButton("취소", dialog_listener)
                dialog.show()
            }


        }
    }
}
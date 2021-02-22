package com.thisteampl.jackpot.main.userpage

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectdetail.ProjectViewDetail
import kotlinx.android.synthetic.main.holder_mypage_myproject.view.*

class MyProjectAdapter(var items: MutableList<MyProject> = mutableListOf()
) : RecyclerView.Adapter<MyProjectAdapter.MyProjectRecyclerViewHolder>() {

    class MyProjectRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProjectRecyclerViewHolder {
        return MyProjectRecyclerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.holder_mypage_myproject, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyProjectRecyclerViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProjectViewDetail::class.java)
            intent.putExtra("id",item.id)
            holder.itemView.context.startActivity(intent)
        }

        with(holder.itemView) {
            holder_mypage_myprj_name.text = item.name
            holder_mypage_myprj_joinnum_text.text = item.users.size.toString()

            with(holder.itemView) {
                holder_mypage_myprj_name.text = item.name
                holder_mypage_myprj_joinnum_text.text = item.users.size.toString() + "명"
                
                //분야에 따른 대표이미지 설정
                when (item.interest) {
                    "IT" -> {holder_mypage_myprj_icon_image.setImageResource(R.drawable.field_it)}
                    "예술_창작" -> {holder_mypage_myprj_icon_image.setImageResource(R.drawable.field_art)}
                    "건강" -> {holder_mypage_myprj_icon_image.setImageResource(R.drawable.field_health)}
                    "요리" -> {holder_mypage_myprj_icon_image.setImageResource(R.drawable.field_cook)}
                    "취미" -> {holder_mypage_myprj_icon_image.setImageResource(R.drawable.field_hobby)}
                    "휴식" -> {holder_mypage_myprj_icon_image.setImageResource(R.drawable.field_repose)}
                    "자기계발" -> {holder_mypage_myprj_icon_image.setImageResource(R.drawable.field_selfdeveloper)}
                    else -> {holder_mypage_myprj_icon_image.setImageResource(R.drawable.field_economy)} // 경제
                }
                
                //유저 동적 추가 직군에 따라 들어가는 그림 다르게하기
                for(i in item.users) {
                    var layoutParams = LinearLayout.LayoutParams(98, 98)
                    if(i == item.users[0]) {
                        layoutParams.setMargins(0, 0, 0, 0)
                    } else {
                        layoutParams.setMargins(-30, 0, 0, 0)
                    }
                    val image = ImageView(context)
                    image.layoutParams = layoutParams

                    when (i) {
                        "개발자" -> {
                            image.setImageResource(R.drawable.circle_developer)
                        }
                        "디자이너" -> {
                            image.setImageResource(R.drawable.circle_designer)
                        }
                        else -> {
                            image.setImageResource(R.drawable.circle_director)
                        }
                    }

                    holder_mypage_myprj_joinmember_layout.addView(image)
                }
            }
        }
    }
}
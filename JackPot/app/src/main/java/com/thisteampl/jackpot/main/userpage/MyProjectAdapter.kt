package com.thisteampl.jackpot.main.userpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R
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

        with(holder.itemView) {
            holder_mypage_myprj_name.text = item.name
            holder_mypage_myprj_joinnum_text.text = item.users.size.toString()

            with(holder.itemView) {
                holder_mypage_myprj_name.text = item.name
                holder_mypage_myprj_joinnum_text.text = item.users.size.toString() + "명"

                //유저 동적 추가 직군에 따라 들어가는 그림 다르게하기
                for(i in item.users) {
                    var layoutParams = LinearLayout.LayoutParams(120, 120)
                    if(i == item.users[0]) {
                        layoutParams.setMargins(0, 0, 0, 0)
                    } else {
                        layoutParams.setMargins(-30, 0, 0, 0)
                    }
                    val image = ImageView(context)
                    image.layoutParams = layoutParams

                    image.setImageResource(R.drawable.circle_developer)

                    holder_mypage_myprj_joinmember_layout.addView(image)
                }
            }
        }
    }
}
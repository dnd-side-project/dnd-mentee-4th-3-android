package com.thisteampl.jackpot.main.mypage

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
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
            holder_mypage_myprj_joinnum_text.text = item.users.size.toString() + "명"

                //유저 동적 추가
                for(i in item.users) {
                var layoutParams = LinearLayout.LayoutParams(
                    120,
                    120
                )
                    layoutParams.setMargins(0,0,10,0)
                    val button = Button(context)
                    button.layoutParams = layoutParams

                    button.gravity = Gravity.CENTER
                    button.background = ContextCompat.getDrawable(context,R.drawable.radius_button_effect)

                holder_mypage_myprj_joinmember_layout.addView(button)
                }
        }
    }
}
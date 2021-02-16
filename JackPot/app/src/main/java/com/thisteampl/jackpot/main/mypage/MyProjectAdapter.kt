package com.thisteampl.jackpot.main.mypage

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R
import kotlinx.android.synthetic.main.holder_mypage_project_big_ver.view.*

class MyProjectAdapter(var items: MutableList<ProjectBigVer> = mutableListOf()
) : RecyclerView.Adapter<MyProjectAdapter.MyProjectRecyclerViewHolder>() {

    class MyProjectRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProjectRecyclerViewHolder {
        return MyProjectRecyclerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.holder_mypage_project_big_ver, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyProjectRecyclerViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            holder_mypage_bigver_projectname.text = item.name
            holder_mypage_bigver_progress.text = item.progress
            for(i in item.stackTool) {
                var layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(0,0,5,0)
                val textView = TextView(context)
                textView.text = i
                textView.setPadding(30,5,30,5)
                textView.layoutParams = layoutParams

                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10F)
                textView.setTextColor(ContextCompat.getColor(context, R.color.colorBlack))
                textView.background = ContextCompat.getDrawable(context,R.drawable.radius_background_transparent)
                textView.isSingleLine = true

                holder_mypage_bigver_stackTool_layout.addView(textView)
            }
        }
    }
}
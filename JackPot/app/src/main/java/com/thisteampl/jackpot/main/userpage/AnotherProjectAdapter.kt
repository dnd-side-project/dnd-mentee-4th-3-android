package com.thisteampl.jackpot.main.userpage

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R
import kotlinx.android.synthetic.main.holder_mypage_anotherproject.view.*

class AnotherProjectAdapter(var items: MutableList<AnotherProject> = mutableListOf()
) : RecyclerView.Adapter<AnotherProjectAdapter.AnotherProjectRecyclerViewHolder>() {

    class AnotherProjectRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnotherProjectRecyclerViewHolder {
        return AnotherProjectRecyclerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.holder_mypage_anotherproject, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AnotherProjectRecyclerViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            holder_mypage_anotherprj_name.text = item.name
            holder_mypage_anotherprj_position.text = item.position

            // 기술스택 동적 추가
            for (i in item.stack) {
                var layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(0, 0, 20, 0)
                val textView = TextView(context)
                textView.text = i
                textView.setPadding(30, 5, 30, 5)
                textView.layoutParams = layoutParams

                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10F)
                textView.setTextColor(ContextCompat.getColor(context, R.color.colorBlack))
                textView.background =
                    ContextCompat.getDrawable(context, R.drawable.radius_background_transparent)
                textView.isSingleLine = true

                holder_mypage_anotherprj_stackTool_layout.addView(textView)
            }
        }
    }
}
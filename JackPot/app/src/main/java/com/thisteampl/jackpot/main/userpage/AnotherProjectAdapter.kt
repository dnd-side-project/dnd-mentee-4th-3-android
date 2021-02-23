package com.thisteampl.jackpot.main.userpage

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectdetail.ProjectViewDetail
import kotlinx.android.synthetic.main.holder_mypage_anotherproject.view.*
import kotlinx.android.synthetic.main.holder_mypage_myproject.view.*

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AnotherProjectRecyclerViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProjectViewDetail::class.java)
            intent.putExtra("id",item.id)
            holder.itemView.context.startActivity(intent)
        }

        with(holder.itemView) {
            holder_mypage_anotherprj_name.text = item.name

            //분야에 따른 대표이미지 설정
            when (item.interest) {
                "IT" -> {holder_mypage_anotherprj_icon_image.setImageResource(R.drawable.field_it)}
                "예술_창작" -> {holder_mypage_anotherprj_icon_image.setImageResource(R.drawable.field_art)}
                "건강" -> {holder_mypage_anotherprj_icon_image.setImageResource(R.drawable.field_health)}
                "요리" -> {holder_mypage_anotherprj_icon_image.setImageResource(R.drawable.field_cook)}
                "취미" -> {holder_mypage_anotherprj_icon_image.setImageResource(R.drawable.field_hobby)}
                "휴식" -> {holder_mypage_anotherprj_icon_image.setImageResource(R.drawable.field_repose)}
                "자기계발" -> {holder_mypage_anotherprj_icon_image.setImageResource(R.drawable.field_selfdeveloper)}
                else -> {holder_mypage_anotherprj_icon_image.setImageResource(R.drawable.field_economy)} // 경제
            }
            //포지션 동적 추가
            for (i in item.position) {
                var layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(0, 0, 5, 0)
                val textView = TextView(context)
                textView.text = i
                textView.typeface = resources.getFont(R.font.roboto_font)
                textView.setPadding(5, 5, 5, 5)
                textView.layoutParams = layoutParams

                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12F)
                textView.setTextColor(ContextCompat.getColor(context, R.color.colorBlack))
                textView.isSingleLine = true

                holder_mypage_anotherprj_position_layout.addView(textView)
            }

            // 기술스택 동적 추가
            for (i in item.stack) {
                var layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(0, 0, 20, 0)
                val textView = TextView(context)
                textView.text = i
                textView.typeface = resources.getFont(R.font.roboto_font)
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
package com.thisteampl.jackpot.main.mainhome

import android.content.Intent
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectController.ProjectComponent
import com.thisteampl.jackpot.main.projectdetail.ProjectViewDetail
import kotlinx.android.synthetic.main.main_attentionproject_list.view.*
import kotlinx.android.synthetic.main.main_recentlyregisterproject_list.view.*
import kotlinx.coroutines.flow.combine
import java.util.*

// 주목받는 프로젝트 어댑터(연결 구간)
class AttentionProjectListAdapter(val attentionlist: List<ProjectComponent> = mutableListOf()) :
    RecyclerView.Adapter<AttentionProjectListAdapter.AttentionProjectListAdapterRecyclerViewHolder>() {

    class AttentionProjectListAdapterRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view)

    // onCreateViewHolder : ViewHolder와 Layout 파일을 연결해주는 역할
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AttentionProjectListAdapterRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_attentionproject_list, parent, false)

        return AttentionProjectListAdapterRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (attentionlist != null) {
            return attentionlist.size
        } else {
            return 0
        }
    }

    // onBindViewHolder : 생성된 ViewHolder에 바인딩 해주는 함수
    override fun onBindViewHolder(
        holder: AttentionProjectListAdapterRecyclerViewHolder,
        position: Int
    ) {


        val item = attentionlist[position]

        with(holder.itemView) {

            if(item.interest.equals("자기계발")) {
                main_attentionitem_image.setImageResource(R.drawable.field_selfdeveloper)
            }else if(item.interest.equals("취미")){
                main_attentionitem_image.setImageResource(R.drawable.field_hobby)
            }else if(item.interest.equals("경제")){
                main_attentionitem_image.setImageResource(R.drawable.field_economy)
            }else if(item.interest.equals("요리")){
                main_attentionitem_image.setImageResource(R.drawable.field_cook)
            }else if(item.interest.equals("IT")){
                main_attentionitem_image.setImageResource(R.drawable.field_it)
            }else if(item.interest.equals("예술/장착")){
                main_attentionitem_image.setImageResource(R.drawable.field_art)
            }else if(item.interest.equals("건강")){
                main_attentionitem_image.setImageResource(R.drawable.field_health)
            }else if(item.interest.equals("휴식")){
                main_attentionitem_image.setImageResource(R.drawable.field_repose)
            }


            var combinestr :String = ""
            for(num in 0..item.position.size-1){
                combinestr += item.position[num]
                combinestr += " "
            }

            main_attentionproject_textview.text = item.title
            main_inputattentionproject_position_textview.text =  combinestr



            var stacklen =item.stacks.size-1
            if(stacklen >3) stacklen = 3
            for(i in 0..stacklen) {
                val stackcontent = item.stacks[i]

                var layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT ,
                    LinearLayout.LayoutParams.MATCH_PARENT )
                layoutParams.setMargins(0, 0, 10, 0) // 오른쪽 자리

                val stacktext = TextView(context)
                stacktext.text = stackcontent
                stacktext.setPadding(30,5,30,5)
                stacktext.layoutParams = layoutParams

                stacktext.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10F)
                stacktext.setTextColor(ContextCompat.getColor(context,R.color.visibletext))
                stacktext.background= ContextCompat.getDrawable(context, R.drawable.radius_background_transparent)

                stacktext.isSingleLine = true

                main_attention_scrollview_linearlayout.addView(stacktext)
            }

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, ProjectViewDetail::class.java)
                holder.itemView.context.startActivity(intent)
            }
        }


    }
}
package com.thisteampl.jackpot.main.mainhome

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_recentlyregisterproject_list.view.*
import java.util.*


// 최근 등록된 프로젝트 어댑터(연결 구간)
class RecentlyRegisterListAdapter(
    val recentlyregisterlist: List<ProjectComponent> = mutableListOf()): RecyclerView.Adapter<RecentlyRegisterListAdapter.RecentlyRegisterListRecyclerViewHolder>() {

    class RecentlyRegisterListRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view)



    // onCreateViewHolder : ViewHolder와 Layout 파일을 연결해주는 역할
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyRegisterListRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_recentlyregisterproject_list, parent, false)

        return RecentlyRegisterListRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (recentlyregisterlist != null) {
            return recentlyregisterlist.size
        } else {
            return 0
        }
    }


    // onBindViewHolder : 생성된 ViewHolder에 바인딩 해주는 함수
    override fun onBindViewHolder(holder: RecentlyRegisterListRecyclerViewHolder, position: Int) {

        val item = recentlyregisterlist[position]

        with(holder.itemView) {

            val randomindex = Random().nextInt(3)
            
            // 배경색 변경 방법 알아보기
//            if(randomindex==0){
//                main_recentlyproject_image.setBackgroundColor(R.color.buttoncolorpink)
//            }else if(randomindex == 1){
//                main_recentlyproject_image.setBackgroundColor(R.color.buttoncolorgreen)
//            }else{
//                main_recentlyproject_image.setBackgroundColor(R.color.buttoncolorblue)
//            }
//            main_recentlyproject_image.setBackground(R.drawable.attentionimagebluenview)
//            main_recentlyproject_image.background = ContextCompat.getDrawable(
//                this@RecentlyRegisterListAdapter,
//                R.drawable.page_line_background_white
//            )

            if(item.interest.equals("자기계발")) {
                main_recentlyregister_field_image.setImageResource(R.drawable.field_selfdeveloper)
            }else if(item.interest.equals("취미")){
                main_recentlyregister_field_image.setImageResource(R.drawable.field_hobby)
            }else if(item.interest.equals("경제")){
                main_recentlyregister_field_image.setImageResource(R.drawable.field_economy)
            }else if(item.interest.equals("요리")){
                main_recentlyregister_field_image.setImageResource(R.drawable.field_cook)
            }else if(item.interest.equals("IT")){
                main_recentlyregister_field_image.setImageResource(R.drawable.field_it)
            }else if(item.interest.equals("예술/장착")){
                main_recentlyregister_field_image.setImageResource(R.drawable.field_art)
            }else if(item.interest.equals("건강")){
                main_recentlyregister_field_image.setImageResource(R.drawable.field_health)
            }else if(item.interest.equals("휴식")){
                main_recentlyregister_field_image.setImageResource(R.drawable.field_repose)
            }

            var combinestr :String = ""
            for(num in 0..item.position.size-1){
                combinestr += item.position[num]
                combinestr += " "
            }

            main_recentlytitle_textview.text = item.title
            main_inputrecentlyproject_position_textview.text = combinestr

            main_recentlytime_textview.text = "30분"


            for(stackcontent in item.stacks) {

                var layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT ,LinearLayout.LayoutParams.MATCH_PARENT )
                layoutParams.setMargins(0, 0, 10, 0) // 오른쪽 자리

                val stacktext = TextView(context)
                stacktext.text = stackcontent
                stacktext.setPadding(30,5,30,5)
                stacktext.layoutParams = layoutParams

                stacktext.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10F)
                stacktext.setTextColor(ContextCompat.getColor(context,R.color.visibletext))
                stacktext.background=ContextCompat.getDrawable(context, R.drawable.radius_background_transparent)

                stacktext.isSingleLine = true

                main_recentlyregister_scrollview_textview.addView(stacktext)
            }

        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProjectViewDetail::class.java)
            intent.putExtra("id",item.id)
            holder.itemView.context.startActivity(intent)

        }
    }


}
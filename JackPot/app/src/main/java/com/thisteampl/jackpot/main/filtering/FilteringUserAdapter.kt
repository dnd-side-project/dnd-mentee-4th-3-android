package com.thisteampl.jackpot.main.filtering

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectdetail.ProjectViewDetail
import com.thisteampl.jackpot.main.userController.UserRelatedFilteringcontents
import kotlinx.android.synthetic.main.holder_filtered_search_memberrelated_list.view.*
import java.util.*


// 최근 등록된 프로젝트 어댑터(연결 구간)
class FilteringUserAdapter(
    val filteringuseradapter: List<UserRelatedFilteringcontents> = mutableListOf()): RecyclerView.Adapter<FilteringUserAdapter.filteringUserViewHolder>() {

    class filteringUserViewHolder(view: View) : RecyclerView.ViewHolder(view)


    // onCreateViewHolder : ViewHolder와 Layout 파일을 연결해주는 역할
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): filteringUserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.holder_filtered_search_memberrelated_list, parent, false)

        return filteringUserViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (filteringuseradapter != null) {
            return filteringuseradapter.size
        } else {
            return 0
        }
    }


    // onBindViewHolder : 생성된 ViewHolder에 바인딩 해주는 함수
    override fun onBindViewHolder(holder: filteringUserViewHolder, position: Int) {

        val item = filteringuseradapter[position]

        with(holder.itemView) {

            val randomindex = Random().nextInt(3)

            // 배경색 변경
            if(randomindex==0){
                holder_memberrelated_image.background = ContextCompat.getDrawable(context, R.drawable.attentionimagebluenview)
            }else if(randomindex ==1){
                holder_memberrelated_image.background = ContextCompat.getDrawable(context, R.drawable.attentionimagegreenview)
            }else{
                holder_memberrelated_image.background = ContextCompat.getDrawable(context, R.drawable.attentionimagepinkview)
            }

//            // 별표 표시 부분 (별표 id 선택)
//            starcheck.setOnClickListener {
//
//
//                // 체크 되었을 때 background
//                starcheck.background =ContextCompat.getDrawable(
//                    context,
//                    R.drawable.star_select
//                )
//
//
//                // 체크 되지 않았을 때 background
//                starcheck.background =ContextCompat.getDrawable(
//                    context,
//                    R.drawable.star
//                )
//
//
//            }



            holderfiltered_textview.text = item.emoticon
            holder_memberrelated_position_textview.text = item.position
            holder_memberrelated_username_textview.text = item.name
//            holder_filter_checkstar_text.text = "1"
//            holder_starviewuser_button.background =  ContextCompat.getDrawable(
//                context,
//                R.drawable.page_line_background_select
//            )


        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProjectViewDetail::class.java)
            intent.putExtra("id",item.name)
            holder.itemView.context.startActivity(intent)

        }
    }


}
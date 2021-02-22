package com.thisteampl.jackpot.main.mainhome

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectController.ProjectComponent
import com.thisteampl.jackpot.main.projectdetail.ProjectViewDetail
import kotlinx.android.synthetic.main.main_recentlyregisterproject_list.view.*


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

            main_recentlytitle_textview.text = item.title
            main_inputrecentlyproject_position_textview.text = item.position.toString()

            main_recentlytime_textview.text = "30분"


            val stack_size = item.stacks.size
            for(i in 0..stack_size!!) {


                var layoutParams = LinearLayout.LayoutParams(100,LinearLayout.LayoutParams.MATCH_PARENT )
                layoutParams.setMargins(0, 0, 10, 0) // 오른쪽 자리

                var image = ImageView(context)
                image.layoutParams = layoutParams


                // 글자 어떻게 넣는건지 알아야 함 일단 imageview는 만들어짐
//                    var text = TextView(context)
//                    examtext.text = item.technology_stack!![0]


                image.setImageResource(R.drawable.radius_background_transparent)

                main_recentlyregister_scrollview_textview.addView(image)
            }

        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProjectViewDetail::class.java)
            intent.putExtra("id",item.id)
            holder.itemView.context.startActivity(intent)

        }
    }




}
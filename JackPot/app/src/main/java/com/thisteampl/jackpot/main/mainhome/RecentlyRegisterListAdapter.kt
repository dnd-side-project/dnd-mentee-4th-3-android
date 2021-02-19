package com.thisteampl.jackpot.main.mainhome

import android.content.Intent
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectdetail.ProjectViewDetail
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.holder_mypage_myproject.view.*
import kotlinx.android.synthetic.main.main_recentlyregisterproject_list.view.*



// 최근 등록된 프로젝트 어댑터(연결 구간)
class RecentlyRegisterListAdapter (val recentlyregisterlist: MutableList<RecentlyRegisterList> = mutableListOf()
): RecyclerView.Adapter<RecentlyRegisterListAdapter.RecentlyRegisterListRecyclerViewHolder>() {

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
            main_recentlyregister_health_image.setImageResource(item.recentlyiamge)
            main_recentlytitle_textview.text = item.recentlyregister_project_name
            main_inputrecentlyproject_position_textview.text = item.recentlyregister_recruitment_position




            with(holder.itemView) {
                main_recentlyregister_health_image.setImageResource(item.recentlyiamge)
                main_recentlytitle_textview.text = item.recentlyregister_project_name
                main_inputrecentlyproject_position_textview.text = item.recentlyregister_recruitment_position
                main_recentlytime_textview.text = item.update_date
                val stack_size = item.technology_stack?.size
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
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProjectViewDetail::class.java)
            holder.itemView.context.startActivity(intent)

        }
    }




}
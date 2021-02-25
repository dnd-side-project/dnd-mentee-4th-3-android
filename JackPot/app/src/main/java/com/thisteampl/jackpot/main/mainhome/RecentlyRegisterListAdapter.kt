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
import com.thisteampl.jackpot.main.projectController.ProjectElementMaterial
import com.thisteampl.jackpot.main.projectdetail.ProjectViewDetail
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_recentlyregisterproject_list.view.*
import okio.utf8Size
import java.util.*


// 최근 등록된 프로젝트 어댑터(연결 구간)
class RecentlyRegisterListAdapter(
    val recentlyregisterlist: List<ProjectElementMaterial> = mutableListOf()): RecyclerView.Adapter<RecentlyRegisterListAdapter.RecentlyRegisterListRecyclerViewHolder>() {

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
            
            // 배경색 변경
            if(randomindex==0){
                main_recentlyproject_image.background = ContextCompat.getDrawable(context, R.drawable.attentionimagebluenview)
            }else if(randomindex ==1){
                main_recentlyproject_image.background = ContextCompat.getDrawable(context, R.drawable.attentionimagegreenview)
            }else{
                main_recentlyproject_image.background = ContextCompat.getDrawable(context, R.drawable.attentionimagepinkview)
            }


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
            }else if(item.interest.equals("예술_창작")){
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

            var resulttime = ""
            // 분 처리
            if(item.duration.isNotEmpty()){
                var time = item.duration.substring(item.duration.length-1,item.duration.length)
                if(time.equals("H")){
                    var hour = item.duration.substring(0,item.duration.length-1)
                    resulttime = hour +"시간전"
                }else if(time.equals("n")){
                    resulttime = (item.duration.substring(0,item.duration.length-3)) + "분전"
                }else{
                    resulttime = (item.duration.substring(0,item.duration.length-1)) + "초전"
                }
            }



            main_recentlytime_textview.text = resulttime

            for(stackcontent in item.stacks) {

                var layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT ,LinearLayout.LayoutParams.MATCH_PARENT )
                layoutParams.setMargins(0, 0, 10, 0) // 오른쪽 자리

                val stacktext = TextView(context)
                stacktext.text = stackcontent


                if(stacktext.text.equals("Html_CSS"))stacktext.text = "Html/CSS"
                if(stacktext.text.equals("React_js"))stacktext.text = "React.JS"
                if(stacktext.text.equals("After_Effects"))stacktext.text = "After Effects"
                if(stacktext.text.equals("Cplus"))stacktext.text = "C++"
                if(stacktext.text.equals("Flask"))stacktext.text = "FLASK"
                if(stacktext.text.equals("Photoshop")) stacktext.text  = "PhotoShop"



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
package com.thisteampl.jackpot.main.viewmore

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
import com.thisteampl.jackpot.main.mainhome.RecentlyRegisterListAdapter
import com.thisteampl.jackpot.main.projectController.ProjectComponent
import com.thisteampl.jackpot.main.projectController.ProjectElementMaterial
import com.thisteampl.jackpot.main.projectdetail.ProjectViewDetail
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.holder_filtered_project_list.view.*
import kotlinx.android.synthetic.main.main_recentlyregisterproject_list.view.*
import okio.utf8Size
import java.util.*


// 최근 등록된 프로젝트 어댑터(연결 구간)
class FieldMoreAdapter(
    val fieldmoreviewlist: List<ProjectElementMaterial> = mutableListOf()): RecyclerView.Adapter<FieldMoreAdapter.FieldMoreListViewHolder>() {

    class FieldMoreListViewHolder(view: View) : RecyclerView.ViewHolder(view)



    // onCreateViewHolder : ViewHolder와 Layout 파일을 연결해주는 역할
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldMoreListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.holder_filtered_project_list, parent, false)

        return FieldMoreListViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (fieldmoreviewlist != null) {
            return fieldmoreviewlist.size
        } else {
            return 0
        }
    }


    // onBindViewHolder : 생성된 ViewHolder에 바인딩 해주는 함수
    override fun onBindViewHolder(holder: FieldMoreListViewHolder, position: Int) {

        val item = fieldmoreviewlist[position]

        with(holder.itemView) {

            val randomindex = Random().nextInt(3)

            // 배경색 변경
            if(randomindex==0){
                holder_filter_project_image.background = ContextCompat.getDrawable(context, R.drawable.attentionimagebluenview)
            }else if(randomindex ==1){
                holder_filter_project_image.background = ContextCompat.getDrawable(context, R.drawable.attentionimagegreenview)
            }else{
                holder_filter_project_image.background = ContextCompat.getDrawable(context, R.drawable.attentionimagepinkview)
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


            if(item.interest.equals("자기계발")) {
                holder_filter_field_image.setImageResource(R.drawable.field_selfdeveloper)
            }else if(item.interest.equals("취미")){
                holder_filter_field_image.setImageResource(R.drawable.field_hobby)
            }else if(item.interest.equals("경제")){
                holder_filter_field_image.setImageResource(R.drawable.field_economy)
            }else if(item.interest.equals("요리")){
                holder_filter_field_image.setImageResource(R.drawable.field_cook)
            }else if(item.interest.equals("IT")){
                holder_filter_field_image.setImageResource(R.drawable.field_it)
            }else if(item.interest.equals("예술_창작")){
                holder_filter_field_image.setImageResource(R.drawable.field_art)
            }else if(item.interest.equals("건강")){
                holder_filter_field_image.setImageResource(R.drawable.field_health)
            }else if(item.interest.equals("휴식")){
                holder_filter_field_image.setImageResource(R.drawable.field_repose)
            }

            var combinestr :String = ""
            for(num in 0..item.position.size-1){
                combinestr += item.position[num]
                combinestr += " "
            }

            holder_title_textview.text = item.title
            holder_filter_inputposition_textview.text = combinestr


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

                holder_filtering_project_linearlayout.addView(stacktext)
            }

        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProjectViewDetail::class.java)
            intent.putExtra("id",item.id)
            holder.itemView.context.startActivity(intent)

        }
    }


}
package com.thisteampl.jackpot.main.filtering

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectController.ProjectComponent
import com.thisteampl.jackpot.main.projectController.ProjectElementMaterial
import com.thisteampl.jackpot.main.projectController.projectAPI
import com.thisteampl.jackpot.main.userController.CheckResponse
import kotlinx.android.synthetic.main.main_recentlyregisterproject_list.view.*
import okhttp3.Callback
import retrofit2.Call
import retrofit2.Response
import java.util.*

class FilteringProjectAdapter(val context: Context, val ProjectList: List<ProjectElementMaterial>): BaseAdapter() {

    val projectapi = projectAPI.projectRetrofitService()
    var check = booleanArrayOf()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.holder_filtered_project_list,null)

        val item = ProjectList!![position]

        val profile = view.findViewById<ImageView>(R.id.holder_filter_field_image)
        val project_name = view.findViewById<TextView>(R.id.holder_title_textview)
        val project_position = view.findViewById<TextView>(R.id.holder_filter_inputposition_textview)
        val stacks = view.findViewById<LinearLayout>(R.id.holder_filtering_project_linearlayout)
        val starcheck = view.findViewById<Button>(R.id.holder_starview_button)
        var backcolor = view.findViewById<ImageView>(R.id.holder_filter_project_image)

        val randomindex = Random().nextInt(3)

        // 배경색 변경
        if(randomindex==0){
            backcolor.background = ContextCompat.getDrawable(context, R.drawable.attentionimagebluenview)
        }else if(randomindex ==1){
            backcolor.background = ContextCompat.getDrawable(context, R.drawable.attentionimagegreenview)
        }else{
            backcolor.background = ContextCompat.getDrawable(context, R.drawable.attentionimagepinkview)
        }


        //select_star
//        starcheck.setOnClickListener {
//            if (check[position] ==false){
//                starcheck.background =ContextCompat.getDrawable(
//                    context,
//                    R.drawable.star_select
//                )
//                Log.d("tag","check ${position}번째 선택")
//                check[position] = true


//                projectapi?.getProjectScrap(ProjectList!![position].id)?.enqueue(
//                    object : Callback<CheckResponse> {
//                        override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
//                            // userAPI에서 타입이나 이름 안맞췄을때
//                            Log.e("tag ", "onFailure, " + t.localizedMessage)
//                        }
//
//                        override fun onResponse(
//                            call: Call<CheckResponse>,
//                            response: Response<CheckResponse>
//                        ) {
//                        }
//                    })

                // 별표 체크 문의 드리기

//
//            }else{
//                starcheck.background =ContextCompat.getDrawable(
//                    context,
//                    R.drawable.star
//                )
//                Log.d("tag","check ${position}번째 취소")
//
//                check[position] = false
//            }

            Log.d("tag","버튼 추가하였습니다.22")

//        }

        var list = ProjectList!![position]

        if(list.interest.equals("자기계발")){
            profile.setImageResource(R.drawable.field_selfdeveloper)
        }else if(list.interest.equals("취미")){
            profile.setImageResource(R.drawable.field_hobby)
        }else if(list.interest.equals("경제")){
            profile.setImageResource(R.drawable.field_economy)
        }else if(list.interest.equals("요리")){
            profile.setImageResource(R.drawable.field_cook)
        }else if(list.interest.equals("IT")){
            profile.setImageResource(R.drawable.field_it)
        }else if(list.interest.equals("예술_창작")){
            profile.setImageResource(R.drawable.field_art)
        }else if(list.interest.equals("건강")){
            profile.setImageResource(R.drawable.field_health)
        }else if(list.interest.equals("휴식")){
            profile.setImageResource(R.drawable.field_repose)
        }


        var combinestr :String = ""
        for(num in 0..item.position.size-1){
            combinestr += item!!.position[num]
            combinestr += " "
        }

        project_name.text = list.title
        project_position.text = combinestr



        for(stackcontent in item.stacks) {

            var layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT ,LinearLayout.LayoutParams.MATCH_PARENT )
            layoutParams.setMargins(0, 0, 10, 0) // 오른쪽 자리

            val stacktext = TextView(context)
            stacktext.text = stackcontent
            stacktext.setPadding(30,5,30,5)
            stacktext.layoutParams = layoutParams

            stacktext.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10F)
            stacktext.setTextColor(ContextCompat.getColor(context,R.color.visibletext))
            stacktext.background= ContextCompat.getDrawable(context, R.drawable.radius_background_transparent)

            stacktext.isSingleLine = true

            // 기술스택에 넣기
            stacks.addView(stacktext)
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return ProjectList!![position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return ProjectList!!.size
    }
}

private fun <T> Call<T>?.enqueue(any: Any) {

}

package com.thisteampl.jackpot.main.filtering

import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.common.GlobalApplication
import com.thisteampl.jackpot.main.projectController.ProjectElementMaterial
import com.thisteampl.jackpot.main.projectController.projectAPI
import com.thisteampl.jackpot.main.projectdetail.ProjectViewDetail
import com.thisteampl.jackpot.main.userController.CheckMyProfile
import com.thisteampl.jackpot.main.userController.CheckResponse
import com.thisteampl.jackpot.main.userController.userAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FilteringProjectAdapter(val context: Context, val ProjectList: List<ProjectElementMaterial>): BaseAdapter() {

    private val projectapi = projectAPI.projectRetrofitService()
    private val userApi = userAPI.create()
    private var check = BooleanArray(30)
    private var checkscrapstar = false
    private var projectID = 0L // 프로젝트 게시물의 id

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.holder_filtered_project_list,null)

        projectID = ProjectList!![position].id



        val item = ProjectList!![position]

        val profile = view.findViewById<ImageView>(R.id.holder_filter_field_image)
        val project_name = view.findViewById<TextView>(R.id.holder_title_textview)
        val project_position = view.findViewById<TextView>(R.id.holder_filter_inputposition_textview)
        val stacks = view.findViewById<LinearLayout>(R.id.holder_filtering_project_linearlayout)
        val starcheck = view.findViewById<Button>(R.id.holder_starview_button)
        var backview = view.findViewById<ImageView>(R.id.holder_filter_project_image)


        val randomindex = Random().nextInt(3)

        // 배경색 변경
        if(randomindex==0){
            backview.background = ContextCompat.getDrawable(context, R.drawable.attentionimagebluenview)
        }else if(randomindex ==1){
            backview.background = ContextCompat.getDrawable(context, R.drawable.attentionimagegreenview)
        }else{
            backview.background = ContextCompat.getDrawable(context, R.drawable.attentionimagepinkview)
        }




        // 별표 표시 부분 (별표 id 선택)
        starcheck.setOnClickListener {


            // 체크 되었을 때 background
            starcheck.background =ContextCompat.getDrawable(
                context,
                R.drawable.star_select
            )



            // 체크 되지 않았을 때 background
            starcheck.background =ContextCompat.getDrawable(
                context,
                R.drawable.star
            )



        }







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

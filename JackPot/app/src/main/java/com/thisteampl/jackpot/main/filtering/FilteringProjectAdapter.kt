package com.thisteampl.jackpot.main.filtering

import android.content.ContentValues.TAG
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
import kotlin.collections.ArrayList

// 문제있는 곳

class FilteringProjectAdapter(val context: Context, val ProjectList: List<ProjectElementMaterial>): BaseAdapter() {

    private val projectapi = projectAPI.projectRetrofitService()
    private val userApi = userAPI.create()
    private var check = BooleanArray(1000)
    private var CheckScrapStar = false
    private var index = 0L
    private var firstcheck = BooleanArray(30)
    private var starCheckcheck = arrayOfNulls<Button>(30)
    private var scrap = IntArray(30)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.holder_filtered_project_list,null)



        val item = ProjectList!![position]

        val profile = view.findViewById<ImageView>(R.id.holder_filter_field_image)
        val project_name = view.findViewById<TextView>(R.id.holder_title_textview)
        val project_position = view.findViewById<TextView>(R.id.holder_filter_inputposition_textview)
        val stacks = view.findViewById<LinearLayout>(R.id.holder_filtering_project_linearlayout)
        val starcheck = view.findViewById<Button>(R.id.holder_starview_button)
        var backview = view.findViewById<ImageView>(R.id.holder_filter_project_image)
        var starcount = view.findViewById<TextView>(R.id.holder_filter_starcount)


        val randomindex = Random().nextInt(3)

        // 배경색 변경
        if(randomindex==0){
            backview.background = ContextCompat.getDrawable(context, R.drawable.attentionimagebluenview)
        }else if(randomindex ==1){
            backview.background = ContextCompat.getDrawable(context, R.drawable.attentionimagegreenview)
        }else{
            backview.background = ContextCompat.getDrawable(context, R.drawable.attentionimagepinkview)
        }







        var list = ProjectList!![position]

        // 첫 번째 실행 되었을 때
        //
//        if(!firstcheck[position]){
//            starCheckcheck[position] = starcheck
//            Log.d("tag","starcheckcheck: ${starCheckcheck[position]},starcheck : ${starcheck}")
//            ScrapCheckBackEnd(list.id,position)
//            firstcheck[position] = true
//            Log.d("tag","position: ${position}")
//            Log.d("tag","firstcheck: ${firstcheck[position]}" )
//            scrap[position] = list.scrapped
//        }



        // 두 번째 선택 (해당 별표를 눌렸을 때)
        starcheck.setOnClickListener {

            if(!check[position]){
                // 체크 되었을 때 background
//                projectapi?.getProjectScrap(item.id.toLong())
//                    ?.enqueue(object : Callback<CheckResponse> {
//                        override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
//                            // userAPI에서 타입이나 이름 안맞췄을때
//                            Log.e("tag ", "onFailure" + t.localizedMessage)
//                        }
//
//                        override fun onResponse(
//                            call: Call<CheckResponse>,
//                            response: Response<CheckResponse>
//                        ) {
//                            Log.d("tag","ScrapCheckConfirm 결과")
//                            if (response.code().toString() == "200") {
////                                Toast.makeText(context, "스크랩 처리가 완료되었습니다.", Toast.LENGTH_SHORT).show()
//
//                                // 체크 되었을 때 background
//                                starcheck.background =ContextCompat.getDrawable(context, R.drawable.star_select)
//                                Log.d("tag","스크랩 적용")
//
//                            } else {
////                                Toast.makeText(context, "스크랩에 실패했습니다.\n에러 코드 : " + response.code() + "\n" + response.body()?.message, Toast.LENGTH_SHORT)
////                                    .show()
//                            }
//                        }
//                    })
                starcheck.background =ContextCompat.getDrawable(context, R.drawable.star_select)
                check[position] = true
//                scrap[position]++
            }else{
//                projectapi?.deleteProjectScrap(item.id.toLong())
//                    ?.enqueue(object : Callback<CheckResponse> {
//                        override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
//                            // userAPI에서 타입이나 이름 안맞췄을때
//                            Log.e("tag ", "onFailure" + t.localizedMessage)
//                        }
//
//                        override fun onResponse(
//                            call: Call<CheckResponse>,
//                            response: Response<CheckResponse>
//                        ) {
//                            if (response.code().toString() == "200") {
////                                Toast.makeText(context, "프로젝트 스크랩이 취소됐습니다.", Toast.LENGTH_SHORT).show()
//                                // 체크 되지 않았을 때 background
//                                starcheck.background =ContextCompat.getDrawable(context, R.drawable.star)
//                                Log.d("tag","스크랩 취소")
//
//                            } else {
////                                Toast.makeText(context, "스크랩 취소에 실패했습니다.\n에러 코드 : " + response.code() + "\n" + response.body()?.message, Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    })
                starcheck.background =ContextCompat.getDrawable(context, R.drawable.star)
                check[position] = false
//                scrap[position]--
                Log.d("tag","${position}취소")
            }

//            Log.d("tag","scrap: ${scrap}")
        }





        if (list.interest.equals("자기계발")) {
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
//        starcount.text = scrap[position].toString()




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
            stacktext.background= ContextCompat.getDrawable(context, R.drawable.radius_background_transparent)

            stacktext.isSingleLine = true

            // 기술스택에 넣기
            stacks.addView(stacktext)
        }

        return view
    }

    private fun ToastmakeTextPrint(str : String){
        Toast.makeText(context,"$str",Toast.LENGTH_SHORT).show()
    }
//
//    // 스크랩 적용되어있는지 확인
//    private fun ScrapCheckBackEnd(id : Long, post:Int) {
//

//
//    }


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
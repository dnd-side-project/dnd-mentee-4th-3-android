package com.thisteampl.jackpot.main.viewmore

import android.content.Context
import android.media.Image
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectController.ProjectComponent
import com.thisteampl.jackpot.main.projectController.ProjectElementMaterial
import java.util.*


class RecentlyProjectViewMoreAdapter(val context: Context, val ProjectList: List<ProjectElementMaterial>?= null): BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.main_recentlyregisterproject_list,null)

        val profile = view.findViewById<ImageView>(R.id.main_recentlyregister_field_image)
        val project_name = view.findViewById<TextView>(R.id.main_recentlytitle_textview)
        val project_position = view.findViewById<TextView>(R.id.main_inputrecentlyproject_position_textview)
        val project_update = view.findViewById<TextView>(R.id.main_recentlytime_textview)
        val project_stacks = view.findViewById<LinearLayout>(R.id.main_recentlyregister_scrollview_textview)
        val projectbackimage = view.findViewById<ImageView>(R.id.main_recentlyproject_image)


        var itemlist = ProjectList!![position]


        val randomindex = Random().nextInt(3)

        // 배경색 itemlist변경
        if(randomindex==0){
            projectbackimage.background = ContextCompat.getDrawable(context, R.drawable.attentionimagebluenview)
        }else if(randomindex ==1){
            projectbackimage.background = ContextCompat.getDrawable(context, R.drawable.attentionimagegreenview)
        }else{
            projectbackimage.background = ContextCompat.getDrawable(context, R.drawable.attentionimagepinkview)
        }


        if(itemlist.interest.equals("자기계발")){
            profile.setImageResource(R.drawable.field_selfdeveloper)
        }else if(itemlist.interest.equals("취미")){
            profile.setImageResource(R.drawable.field_hobby)
        }else if(itemlist.interest.equals("경제")){
            profile.setImageResource(R.drawable.field_economy)
        }else if(itemlist.interest.equals("요리")){
            profile.setImageResource(R.drawable.field_cook)
        }else if(itemlist.interest.equals("IT")){
            profile.setImageResource(R.drawable.field_it)
        }else if(itemlist.interest.equals("예술_창작")){
            profile.setImageResource(R.drawable.field_art)
        }else if(itemlist.interest.equals("건강")){
            profile.setImageResource(R.drawable.field_health)
        }else if(itemlist.interest.equals("휴식")){
            profile.setImageResource(R.drawable.field_repose)
        }


        var combinestr :String = ""
        for(num in 0..itemlist.position.size-1){
            combinestr += itemlist!!.position[num]
            combinestr += " "
        }

        project_name.text = itemlist.title
        project_position.text = combinestr



        for(stackcontent in itemlist.stacks) {

            var layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT ,
                LinearLayout.LayoutParams.MATCH_PARENT )
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
            project_stacks.addView(stacktext)
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
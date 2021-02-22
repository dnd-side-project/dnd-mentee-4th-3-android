package com.thisteampl.jackpot.main.filtering

import android.content.Context
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

class Filteringprojectadapter(val context: Context, val ProjectList: List<ProjectComponent>?= null): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.holder_filtered_project_list,null)

        val item = ProjectList!![position]

        val profile = view.findViewById<ImageView>(R.id.holder_filter_field_image)
        val project_name = view.findViewById<TextView>(R.id.holder_title_textview)
        val project_position = view.findViewById<TextView>(R.id.holder_filter_inputposition_textview)
        val stacks = view.findViewById<LinearLayout>(R.id.holder_filtering_project_linearlayout)


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
//            holder_filtering_project_linearlayout
//            main_recentlyregister_scrollview_textview.addView(stacktext)
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
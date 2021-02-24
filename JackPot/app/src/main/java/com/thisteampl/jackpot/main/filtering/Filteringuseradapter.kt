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
import com.thisteampl.jackpot.main.userController.UserRelatedFilteringcontents
import java.util.*

class Filteringuseradapter(val context: Context, val UserList: List<UserRelatedFilteringcontents>?= null): BaseAdapter() {

    var check2 = BooleanArray(30)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.holder_filtered_search_memberrelated_list,null)

        // 수정
        val user_name = view.findViewById<TextView>(R.id.holder_memberrelated_username_textview)
        val user_position = view.findViewById<TextView>(R.id.holder_memberrelated_position_textview)
        val user_star = view.findViewById<Button>(R.id.holder_staruserview_button)
        val user_startext = view.findViewById<TextView>(R.id.holder_filter_checkstar_text)
        val user_textview = view.findViewById<TextView>(R.id.holderfiltered_textview)

        val user_backview = view.findViewById<ImageView>(R.id.holder_memberrelated_image)


        val randomindex = Random().nextInt(3)

        // 배경색 변경
        if(randomindex==0){
            user_backview.background = ContextCompat.getDrawable(context, R.drawable.attentionimagebluenview)
        }else if(randomindex ==1){
            user_backview.background = ContextCompat.getDrawable(context, R.drawable.attentionimagegreenview)
        }else{
            user_backview.background = ContextCompat.getDrawable(context, R.drawable.attentionimagepinkview)
        }





        // 별표 표시 부분 (별표 id 선택)
        user_star.setOnClickListener {

            // 체크 되었을 때 background
            user_star.background =ContextCompat.getDrawable(
                context,
                R.drawable.star_select
            )

            // 체크 되지 않았을 때 background
            user_star.background =ContextCompat.getDrawable(
                context,
                R.drawable.star
            )
        }










        var list = UserList!![position]
        user_textview.text = list.emoticon
        user_name.text = list.name
        user_position.text = list.position

        return view
    }

    override fun getItem(position: Int): Any {
        return UserList!![position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return UserList!!.size
    }
}
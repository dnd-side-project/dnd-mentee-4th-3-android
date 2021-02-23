package com.thisteampl.jackpot.main.filtering


import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.userController.UserRelatedFilteringcontents

class Filteringuseradapter(val context: Context, val UserList: List<UserRelatedFilteringcontents>?= null): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.holder_filtered_search_memberrelated_list,null)


        // 수정
        val profile = view.findViewById<ImageView>(R.id.holder_memberrelated_image)
        val user_name = view.findViewById<TextView>(R.id.holder_memberrelated_username_textview)
        val user_position = view.findViewById<TextView>(R.id.holder_memberrelated_position_textview)
        val user_star = view.findViewById<Button>(R.id.holder_staruserview_button)
        val user_startext = view.findViewById<TextView>(R.id.holder_filter_checkstar_text)


        var list = UserList!![position]
        user_name.text = list.name
        user_position.text = list.position


//        var combinestr :String = ""
//        for(num in 0..list.position.-1){
//            combinestr += list!!.position[num]
//            combinestr += " "
//        }



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
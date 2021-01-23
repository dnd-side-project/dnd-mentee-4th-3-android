package com.thisteampl.jackpot.main.mainview


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import com.thisteampl.jackpot.R
import java.lang.NumberFormatException

class ExampleProject(val latelyList: ArrayList<Lately>? = null): RecyclerView.Adapter<ExampleProject.LatelyProjectView>() {

    class LatelyProjectView(itemView: View):RecyclerView.ViewHolder(itemView) {
        val lately_name = itemView.findViewById<TextView>(R.id.lately_rv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleProject.LatelyProjectView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        Log.d("OnCreateView부분","")

        return LatelyProjectView(view).apply {
            itemView.setOnClickListener {
                val current = adapterPosition
                val current_lately : Lately = latelyList!!.get(current)
                Log.d("최근부분","")
                Toast.makeText(parent.context,"최근 프로젝트 : ${current_lately.lately_project_name}",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        if(latelyList != null ){
            return latelyList.size
        }else{
            return 0
        }
    }


    @Override
    override fun onBindViewHolder(holder: ExampleProject.LatelyProjectView, position:Int) {
        try {
            holder.lately_name.text = latelyList!!.get(index = position).lately_project_name

        }catch (e: NumberFormatException){
            Log.d("오류발생 : ","오류")
        }
    }

}
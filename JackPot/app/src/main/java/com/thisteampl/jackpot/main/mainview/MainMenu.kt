package com.thisteampl.jackpot.main.mainview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.mypage.MyProfile
import kotlinx.android.synthetic.main.fragment_main_menu.*

/* Menu */
class MainMenu : Fragment() {
    companion object {

        const val TAG : String = "페이지"
        fun newInstance(): MainMenu {
            return MainMenu()
        }

    }

    var popularList = ArrayList<Popularity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val popular = arrayListOf(
            Popularity("팀플"),
            Popularity("팀플2"),
            Popularity("팀플3")
        )

        /////////////////////// 문제점

        Log.d(TAG,"MainActivity - this.popularList.size 전 : ${this.popularList.size}")

        for (i in 1..10){
            var popular = Popularity(project_name = "창")
            this.popularList.add(popular)
        }

        Log.d(TAG,"MainActivity - this.popularList.size 후 : ${this.popularList.size}")

//        Log.d("popular ~ ","${popular[0]}")
//        popular_rv.layoutManager = LinearLayoutManager(this@MainMenu,LinearLayoutManager.HORIZONTAL,false)
//
//
//        popular_rv.setHasFixedSize(true)
//
//        popular_rv.adapter = PopularityAdapter(popular)

    }




    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 프로젝트 찾기
        mainmenu_findproject_tv.setOnClickListener{
            activity?.let{
                val intent_myprofile = Intent(context, FindProject::class.java)
                startActivity(intent_myprofile)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main_menu,container,false)
        return view

    }
}
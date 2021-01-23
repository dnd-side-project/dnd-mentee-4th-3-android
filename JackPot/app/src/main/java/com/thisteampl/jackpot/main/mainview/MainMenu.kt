package com.thisteampl.jackpot.main.mainview


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.MainActivity
import com.thisteampl.jackpot.main.mypage.MyProfile
import kotlinx.android.synthetic.main.fragment_main_menu.*

/* Menu */
class MainMenu : Fragment() {
    companion object {

        fun newInstance(): MainMenu {
            return MainMenu()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val popularlist = arrayOf("팀플10","팀플11","팀플12")
        val popular = arrayListOf(
            Popularity("text1"),
            Popularity("text2"),
            Popularity("text3")
        )

        val cu = arrayListOf(
            Lately("a")
        )

//        val popular = arrayListOf<Popularity>()[6]

//
        for(num in 1..2)
            popular.add(Popularity(popularlist[num]))


        popular_rv.layoutManager = LinearLayoutManager((activity as MainActivity),
            LinearLayoutManager.HORIZONTAL,false)


        popular_rv.setHasFixedSize(true)
        popular_rv.adapter = PopularityAdapter(popular)


        var view:View

        lately_rv.layoutManager = LinearLayoutManager((activity as MainActivity),
            LinearLayoutManager.HORIZONTAL,false)
        lately_rv.setHasFixedSize(true)
//        lately_rv.adapter = ExampleProject(cu)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 프로젝트 찾기
        mainmenu_findproject_tv.setOnClickListener{
//            activity?.let{
//                val intent_myprofile = Intent(context, FindProject::class.java)
//                startActivity(intent_myprofile)
//            }
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
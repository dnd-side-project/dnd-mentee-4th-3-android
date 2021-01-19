package com.thisteampl.jackpot.main.mypage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.myproject.MyProfile
import kotlinx.android.synthetic.main.activity_my_page.*
import android.content.Intent as Intent


class MyPage : Fragment() {

    companion object {

        fun newInstance(): MyPage {
            return MyPage()
        }
    }

    // 메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // 프래그먼트를 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_myprofile.setOnClickListener{
            activity?.let{
                val intent_myprofile = Intent(context, MyProfile::class.java)
                startActivity(intent_myprofile)
            }
        }
//
//        btn_project.setOnClickListener{
//            activity?.let{
//                val intent_myproject = Intent(context, SaveProject::class.java)
//                startActivity(intent_myproject)
//            }
//
//        }
//
//        btn_setting.setOnClickListener{
//            activity?.let{
//                val intent_myprofile = Intent(context, MyProfile::class.java)
//                startActivity(intent_myprofile)
//            }
//        }

    }

    // 뷰가 생성되었을 때
    // 프래그먼트와 레이아웃을 연결시켜주는 부분이다.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_my_page,container,false)
        return view
    }



}
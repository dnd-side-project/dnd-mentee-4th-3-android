package com.thisteampl.jackpot.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thisteampl.jackpot.R


class MyPage : Fragment() {

    companion object {
        //(동반자 객체)
        // 클래스 인스턴스 없이 어떤 클래스 내부에 접근
        // 클래스 내부에 객체를 선언할 때 companion 식별자를 붙인
        // object를 선언하면 된다.

        const val TAG : String = "페이지"

        fun newInstance(): MyPage {
            return MyPage()

            // Main에서 HomeFragement.newInstance() 호출로 현재 function 호출
            // MyPage 메모리에 올라간 것을 가져오게 된다.
        }
    }

    // 메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // 프래그먼트를 안고 있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    // 뷰가 생성되었을 때
    // 프래그먼트와 레이아웃을 연결시켜주는 부분이다.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_my_page,container,false)

        return super.onCreateView(inflater, container, savedInstanceState)

    }
}
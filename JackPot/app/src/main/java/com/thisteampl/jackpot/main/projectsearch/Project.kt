package com.thisteampl.jackpot.main.projectsearch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thisteampl.jackpot.R


class Project : Fragment() {

    companion object {
        const val TAG : String = "페이지"

        fun newInstance(): Project {
            return Project()

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

    // 뷰가 생성되었을 때
    // 프래그먼트와 레이아웃을 연결시켜주는 부분이다.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_project,container,false)
        return view
    }
}

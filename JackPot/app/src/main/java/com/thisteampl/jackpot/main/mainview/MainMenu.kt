package com.thisteampl.jackpot.main.mainview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thisteampl.jackpot.R


/* Menu */
class MainMenu : Fragment() {

    companion object {

        const val TAG : String = "페이지"

        fun newInstance(): MainMenu {
            return MainMenu()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_main_menu,container,false)
        return view

    }
}

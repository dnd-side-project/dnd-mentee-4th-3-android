package com.thisteampl.jackpot.main


import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.floating.MyAppeal
import com.thisteampl.jackpot.main.floating.ProjectCreation
import com.thisteampl.jackpot.main.mainhome.*
import com.thisteampl.jackpot.main.mypage.MyPage
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){

    private lateinit var recentlyregister : RecentlyRegisterProject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, MyPage::class.java)
        val mainintent = Intent(this,MainActivity::class.java)
        val searchintent = Intent(this,SearchViewPage::class.java)

        main_search_imagebutton.setOnClickListener {
            startActivity(searchintent)
        }

        main_mypage_imagebutton.setOnClickListener{
            startActivity(intent)
        }

        main_appname_textview.setOnClickListener{
            startActivity(mainintent)
        }

        setFrag(0)

        main_projectattention_textview.setOnClickListener {
            main_projectattention_textview.setTextColor(Color.BLACK)
            main_memberattention_textview.setTextColor(Color.GRAY)
            setFrag(0)
        }

        main_memberattention_textview.setOnClickListener {
            main_memberattention_textview.setTextColor(Color.BLACK)
            main_projectattention_textview.setTextColor(Color.GRAY)
            setFrag(1)
        }


        recentlyregister = RecentlyRegisterProject.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.main_recentlyregisterproject_framelayout,recentlyregister).commit()

        val intentviewmore = Intent(this, ProjectViewMore::class.java)

        main_viewmore_textview.setOnClickListener {
            intentviewmore.putExtra("InputRecently",recentlyregister.recentlyregister)
            startActivity(intentviewmore)
        }

        // 참고자료 : https://jinsangjin.tistory.com/12
        val open_fab = AnimationUtils.loadAnimation(this,R.anim.fab_open)
        val close_fab = AnimationUtils.loadAnimation(this,R.anim.fab_close)
        val anticlockwise_rotate = AnimationUtils.loadAnimation(this,R.anim.rotate_anticlockwise)
        val rclockwise_rotate = AnimationUtils.loadAnimation(this,R.anim.rotate_clockwise)
        var isOpen = false

        val appeal = Intent(this,MyAppeal::class.java)
        var drawproject = Intent(this,ProjectCreation::class.java)

        main_optionmenu_floatingactionbutton.setOnClickListener{
            if(isOpen){
                appeal_fab.startAnimation(close_fab)
                drawproject_fab.startAnimation(close_fab)
                main_optionmenu_floatingactionbutton.startAnimation(rclockwise_rotate)


                appeal_fab.isClickable = false
                drawproject_fab.isClickable = false


                isOpen = false
            }
            else{
                appeal_fab.startAnimation(open_fab)
                drawproject_fab.startAnimation(open_fab)
                main_optionmenu_floatingactionbutton.startAnimation(anticlockwise_rotate)


                appeal_fab.isClickable = true
                drawproject_fab.isClickable = true

                appeal_fab.setOnClickListener {
                    startActivity(appeal)
                }
                drawproject_fab.setOnClickListener {
                    startActivity(drawproject)
                }

                isOpen = true

            }
        }


    }

    private fun setFrag(fragNum : Int){
        val ft = supportFragmentManager.beginTransaction()

        when(fragNum){
            0 -> {
                ft.replace(R.id.main_projectview_framelayout,AttentionProject()).commit()
            }

            1-> {
                ft.replace(R.id.main_projectview_framelayout,AttentionMember()).commit()
            }
        }

    }




}

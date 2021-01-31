package com.thisteampl.jackpot.main


import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.mainhome.AttentionProject
import com.thisteampl.jackpot.main.mainhome.RecentlyRegisterProject
import com.thisteampl.jackpot.main.mypage.MyPage
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){

    private lateinit var attention : AttentionProject
    private lateinit var recentlyregister : RecentlyRegisterProject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, MyPage::class.java)

        main_mypage_imagebutton.setOnClickListener{
            startActivity(intent)
        }

        attention = AttentionProject.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.main_projectview_framelayout,attention).commit()

        recentlyregister = RecentlyRegisterProject.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.main_recentlyregisterproject_framelayout,recentlyregister).commit()



        main_projectattention_textview.setOnClickListener {
            attention = AttentionProject.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.main_projectview_framelayout,attention).commit()
        }


//        main_memberattention_textview.setOnClickListener {
//            main_memberattention_textview.setTextColor(Color.BLACK)
//            main_memberattention_textview.TextStyle()
//        }




        // 참고자료 : https://jinsangjin.tistory.com/12
        val open_fab = AnimationUtils.loadAnimation(this,R.anim.fab_open)
        val close_fab = AnimationUtils.loadAnimation(this,R.anim.fab_close)
        val anticlockwise_rotate = AnimationUtils.loadAnimation(this,R.anim.rotate_anticlockwise)
        val rclockwise_rotate = AnimationUtils.loadAnimation(this,R.anim.rotate_clockwise)
        var isOpen = false


        main_optionmenu_floatingactionbutton.setOnClickListener{
            if(isOpen){
                appeal_fab.startAnimation(close_fab)
                drawproject_fab.startAnimation(close_fab)
                main_optionmenu_floatingactionbutton.startAnimation(rclockwise_rotate)

                isOpen = false
            }

            else{
                appeal_fab.startAnimation(open_fab)
                drawproject_fab.startAnimation(open_fab)
                main_optionmenu_floatingactionbutton.startAnimation(anticlockwise_rotate)


                appeal_fab.isClickable
                drawproject_fab.isClickable

//                if(drawproject_fab.isClickable && isOpen == true){
////                    Toast.makeText(this, "버튼 성공", Toast.LENGTH_SHORT).show()
////                }

                isOpen = true

            }
        }


    }



}

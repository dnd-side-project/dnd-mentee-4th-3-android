package com.thisteampl.jackpot.main.userpage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.thisteampl.jackpot.R
import kotlinx.android.synthetic.main.activity_profile_edit_change_state.*

//https://m.blog.naver.com/l5547/221845481754, OnActivityResult

class ProfileEditChangeStateActivity: AppCompatActivity() {

    private var stateBtn = arrayOfNulls<Button>(3)
    private var sGradeBtn = arrayOfNulls<Button>(4)
    private var state = "result"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit_change_state)

        setupView()
    }

    private fun setupView(){
        profile_edit_change_state_back_button.setOnClickListener { onBackPressed() }
        makeBtnFunc()
        profile_edit_change_state_confirm_button.setOnClickListener {
            when (state) {
                "result" -> {
                    Toast.makeText(this, "현재 상태를 선택해주세요.", Toast.LENGTH_SHORT).show()
                }
                "학생" -> {
                    Toast.makeText(this, "학년을 선택해주세요.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val intent = Intent().putExtra("state", state)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        }
    }

    //버튼 선택 리스너. 선택 시 테두리 색과 텍스트 색상이 바뀌게 했다.
    private fun btnOnClick(v : View) {
        var id = v.id

        // 상태 선택 버튼의 리스너 설정
        if(id == R.id.profile_edit_state_student || id == R.id.profile_edit_state_jobfinder
            || id == R.id.profile_edit_state_junior) {
            var pos = Integer.parseInt(v.contentDescription.toString())
            for(i in 0..2) {
                if(i == pos) {
                    stateBtn[i]?.background = ContextCompat.getDrawable(this,R.drawable.radius_background_transparent_select)
                    stateBtn[i]?.setTextColor(ContextCompat.getColor(this, R.color.colorButtonSelect))
                    state = stateBtn[i]?.text.toString()
                    if(i == 0) {
                        profile_edit_state_grade_layout.visibility = View.VISIBLE
                    } else {
                        profile_edit_state_grade_layout.visibility = View.GONE
                        for(j in 0..3) {
                            sGradeBtn[j]?.background = ContextCompat.getDrawable(this, R.drawable.radius_button_effect)
                            sGradeBtn[j]?.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
                        }
                    }
                }
                else {
                    stateBtn[i]?.background = ContextCompat.getDrawable(this, R.drawable.radius_button_effect)
                    stateBtn[i]?.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
                }
            }
        }
        // 학년 선택 버튼의 리스너 설정
        else if(id == R.id.profile_edit_student_grade_one || id == R.id.profile_edit_student_grade_two
            || id == R.id.profile_edit_student_grade_three || id == R.id.profile_edit_student_grade_four) {
            var pos = Integer.parseInt(v.contentDescription.toString())
            for (i in 0..3) {
                if (i == pos) {
                    sGradeBtn[i]?.background = ContextCompat.getDrawable(
                        this,
                        R.drawable.radius_background_transparent_select
                    )
                    sGradeBtn[i]?.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.colorButtonSelect
                        )
                    )
                    state = "학생 " + sGradeBtn[i]?.text.toString()
                } else {
                    sGradeBtn[i]?.background =
                        ContextCompat.getDrawable(this, R.drawable.radius_button_effect)
                    sGradeBtn[i]?.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
                }
            }
        }
    }

    private fun makeBtnFunc() {
        //상태 선택 리스너
        stateBtn[0] = profile_edit_state_student
        stateBtn[1] = profile_edit_state_jobfinder
        stateBtn[2] = profile_edit_state_junior
        for(i in 0..2) {
            stateBtn[i]?.setOnClickListener { stateBtn[i]?.let{it1 -> this.btnOnClick(it1)} }
        }

        //학생 학년 선택 리스너
        sGradeBtn[0] = profile_edit_student_grade_one
        sGradeBtn[1] = profile_edit_student_grade_two
        sGradeBtn[2] = profile_edit_student_grade_three
        sGradeBtn[3] = profile_edit_student_grade_four
        for(i in 0..3) {
            sGradeBtn[i]?.setOnClickListener { sGradeBtn[i]?.let{it1 -> this.btnOnClick(it1)} }
        }
    }

}
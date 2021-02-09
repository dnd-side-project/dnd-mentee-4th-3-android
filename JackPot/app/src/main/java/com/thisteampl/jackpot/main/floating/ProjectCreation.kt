package com.thisteampl.jackpot.main.floating

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import com.thisteampl.jackpot.R
import kotlinx.android.synthetic.main.activity_project_creation.*
import kotlinx.android.synthetic.main.activity_signup.*

class ProjectCreation : AppCompatActivity() {
    
    // 해야할 것
    // 1. 아무것도 눌리지 않았을 때 다음 page 넘어가지 않게 하기
    // 2. 2 page로 구성하기
    // 3. Database에 넘기기


    // SignUpActivity 참고함
    private val stackTool = mutableListOf<String>()
    private var page: Int = 0

    private var stacklistdeveloper = "개발자" // 개발자 list 저장용
    private var stacklistdesigner = "디자이너" // 디자이너 list 저장용
    private var stacklistregions = "지역" // 지역 list 저장용

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_creation)

        createproject_minusbutton_imageview.setOnClickListener {
            finish()
        }


        createprojectpage1()
//        createprojectpage2()
    }


    private fun createprojectpage1() {   // 1쪽
        stackTool.clear()


        // 스택, 툴 버튼 설정 메서드 (signupactivity btnOnClick 이용)
        // 모집포지션
        for (i in 0 until projectcreate_position_linearlayout.childCount) {
            val child: View = projectcreate_position_linearlayout.getChildAt(i)

            // 해당 버튼에 효과 주기
            if(child is Button) {
                child.background = ContextCompat.getDrawable(this@ProjectCreation, R.drawable.radius_button_effect)
                child.setOnClickListener {

                    if(!stackTool.contains(child.text.toString())) {
                        child.background = ContextCompat.getDrawable(this@ProjectCreation, R.drawable.radius_background_transparent_select)
                        stackTool.add(child.text.toString())
                    } else {
                        child.background = ContextCompat.getDrawable(this@ProjectCreation, R.drawable.radius_button_effect)
                        stackTool.remove(child.text.toString())
                    }

                }
            }
        }

        // 사용 예정 스택, 개발자
        var stacktobeusingdeveloper = listOf("JAVA","C++","Python","JavaScript","Html/CSS","Swift","Spring","Kotlin",
            "Django","React.js","Flask")
        // 스택 선택을 해 줄 배열과 액티비티의 스피너와 연결해줄 어댑터.
        createproject_developer_spinner.setItems(stacktobeusingdeveloper) // 스피너와 어댑터를 연결
        createproject_developer_spinner.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            stacklistdeveloper = newItem
        }

        // 사용 예정 스택, 디자이너
        var stacktobeusingdesigner = listOf("Adobe Photoshop","Adobe Illustrator","Adobe XD","Figma","Sketch","Principle",
            "Adobe Indesign","Adobe After Effects","Adobe Premiere","C4D","Protopie","Zeplin")

        createproject_designer_spinner.setItems(stacktobeusingdesigner)
        createproject_designer_spinner.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            stacklistdesigner = newItem
        }


        // 프로젝트 방식
        for (i in 0 until projectcreate_projectsystem_linearlayout.childCount) {
            val child: View = projectcreate_projectsystem_linearlayout.getChildAt(i)

            // 해당 버튼에 효과 주기
            if(child is Button) {
                child.background = ContextCompat.getDrawable(this@ProjectCreation, R.drawable.radius_button_effect)
                child.setOnClickListener {

                    if(!stackTool.contains(child.text.toString())) {
                        child.background = ContextCompat.getDrawable(this@ProjectCreation, R.drawable.radius_background_transparent_select)
                        stackTool.add(child.text.toString())
                    } else {
                        child.background = ContextCompat.getDrawable(this@ProjectCreation, R.drawable.radius_button_effect)
                        stackTool.remove(child.text.toString())
                    }

                }
            }
        }


        // 지역
        var regions = listOf("서울","부산","대구","인천","광주",
            "대전","울산","세종","경기도","강원도","충청북도","충청남도","전라북도","전라남도","경상북도","경상남도","제주도")

        createproject_regions_spinner.setItems(regions)
        createproject_regions_spinner.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            stacklistregions = newItem
        }


        projectcreate_participating_members_linearlayout


        // 참여중인 멤버

        // 프로젝트 예상시간
        for (i in 0 until projectcreate_month_linearlayout.childCount) {
            val child: View = projectcreate_month_linearlayout.getChildAt(i)

            // 해당 버튼에 효과 주기
            if(child is Button) {
                child.background = ContextCompat.getDrawable(this@ProjectCreation, R.drawable.radius_button_effect)
                child.setOnClickListener {

                    if(!stackTool.contains(child.text.toString())) {
                        child.background = ContextCompat.getDrawable(this@ProjectCreation, R.drawable.radius_background_transparent_select)
                        stackTool.add(child.text.toString())
                    } else {
                        child.background = ContextCompat.getDrawable(this@ProjectCreation, R.drawable.radius_button_effect)
                        stackTool.remove(child.text.toString())
                    }

                }
            }
        }

        // 분야 IT ~ 요리
        for (i in 0 until projectcreate_field_linearlayout.childCount) {
            val child: View = projectcreate_field_linearlayout.getChildAt(i)

            // 해당 버튼에 효과 주기
            if(child is Button) {
                child.background = ContextCompat.getDrawable(this@ProjectCreation, R.drawable.radius_button_effect)
                child.setOnClickListener {

                    if(!stackTool.contains(child.text.toString())) {
                        child.background = ContextCompat.getDrawable(this@ProjectCreation, R.drawable.radius_background_transparent_select)
                        stackTool.add(child.text.toString())
                    } else {
                        child.background = ContextCompat.getDrawable(this@ProjectCreation, R.drawable.radius_button_effect)
                        stackTool.remove(child.text.toString())
                    }

                }
            }
        }

        // 분야 취미 ~ 자기계발
        for (i in 0 until projectcreate_field2_linearlayout.childCount) {
            val child: View = projectcreate_field2_linearlayout.getChildAt(i)

            // 해당 버튼에 효과 주기
            if(child is Button) {
                child.background = ContextCompat.getDrawable(this@ProjectCreation, R.drawable.radius_button_effect)
                child.setOnClickListener {

                    if(!stackTool.contains(child.text.toString())) {
                        child.background = ContextCompat.getDrawable(this@ProjectCreation, R.drawable.radius_background_transparent_select)
                        stackTool.add(child.text.toString())
                    } else {
                        child.background = ContextCompat.getDrawable(this@ProjectCreation, R.drawable.radius_button_effect)
                        stackTool.remove(child.text.toString())
                    }

                }
            }
        }


        createproject_writerecruitment_button.setOnClickListener {
            projectcreate_write_recruitment_article_constraintlayout.visibility = View.GONE
            projectcreate_write_recruitment_article2_constraintlayout.visibility = View.VISIBLE
        }

        
    }


//    private fun createprojectpage2() {    // 2쪽
//    }
}
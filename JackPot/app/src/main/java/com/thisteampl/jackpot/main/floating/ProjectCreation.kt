package com.thisteampl.jackpot.main.floating

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.userController.CheckResponse
import kotlinx.android.synthetic.main.activity_project_creation.*
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectCreation : AppCompatActivity() {
    

    // SignUpActivity 참고함
    // 모집 포지션, 분야를 위한 stack 선언
    private val stackToolposition = mutableListOf<String>()
    private val stackToolfield = mutableListOf<String>()

    // 프로젝트 방식, 프로젝트 예상 기간을 위한 arrayOfNulls 선언
    private var openoffbtn = arrayOfNulls<Button>(2)
    private var periodbtn = arrayOfNulls<Button>(3)
    private var openofftext = "openoff"
    private var periodtext = "period"

    private var page: Int = 1

    private var stacklistregions = "지역" // 지역 list 저장용


    // 사용자가 선택한 item
    private val selecteddeveloperItems = ArrayList<String>() // 개발자 스택
    private val selecteddesignerItems = ArrayList<String>()  // 디자이너 스택
    private val selectAllItems = ArrayList<String>()         // 개발자, 디자이너 스택 합치기
    private val selectpositionItems = ArrayList<String>()    // 포지션
    private val selectedfieldItems = ArrayList<String>()     // 분야

    // API
    private val projectapi = projectAPI.create()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_creation)

        createproject_minusbutton_imageview.setOnClickListener {
            finish()
        }


        setWriteProjectRecruitment()
    }


    private fun setWriteProjectRecruitment() {

        // 1 page
        stackToolposition.clear()
        stackToolfield.clear()

        // 스택, 툴 버튼 설정 메서드 (signupactivity btnOnClick 이용)
        // 모집포지션
        for (i in 0 until projectcreate_position_linearlayout.childCount) {
            val child: View = projectcreate_position_linearlayout.getChildAt(i)

            // 해당 버튼에 효과 주기
            if (child is Button) {
                child.background =
                    ContextCompat.getDrawable(this@ProjectCreation, R.drawable.radius_button_effect)
                child.setOnClickListener {

                    if (!stackToolposition.contains(child.text.toString())) {
                        child.background = ContextCompat.getDrawable(
                            this@ProjectCreation,
                            R.drawable.radius_background_transparent_select
                        )
                        stackToolposition.add(child.text.toString())
                    } else {
                        child.background = ContextCompat.getDrawable(
                            this@ProjectCreation,
                            R.drawable.radius_button_effect
                        )
                        stackToolposition.remove(child.text.toString())
                    }

                }
            }
        }


        // 사용 예정 스택, 개발자
        var stacktobeusingdeveloper = arrayOf<String>(
            "JAVA", "C++", "Python", "JavaScript", "Html/CSS", "Swift", "Spring", "Kotlin",
            "Django", "React.js", "Flask"
        )
        StackTobeUsed(createproject_developer_button, stacktobeusingdeveloper)


        // 사용 예정 스택, 디자이너
        var stacktobeusingdesigner = arrayOf<String>(
            "Adobe Photoshop", "Adobe Illustrator", "Adobe XD", "Figma", "Sketch", "Principle",
            "Adobe Indesign", "Adobe After Effects", "Adobe Premiere", "C4D", "Protopie"
        )

        // 개발자, 디자이너 개수 맞춘다. (기술 스택 개수)
        StackTobeUsed(createproject_designer_button, stacktobeusingdesigner)


        // 프로젝트 방식

        openoffbtn[0] = findViewById(R.id.projectcreate_offperiod_button)
        openoffbtn[1] = findViewById(R.id.projectcreate_openperiod_button)
        openoffbtn[0]?.setOnClickListener {
            openoffbtn[0]?.let { it1 ->
                this.onClickBtn(
                    it1,
                    0
                )
            }
        }
        openoffbtn[1]?.setOnClickListener {
            openoffbtn[1]?.let { it2 ->
                this.onClickBtn(
                    it2,
                    1
                )
            }
        }


        // 지역
        var regions = listOf(
            "서울",
            "부산",
            "대구",
            "인천",
            "광주",
            "대전",
            "울산",
            "세종",
            "경기도",
            "강원도",
            "충청북도",
            "충청남도",
            "전라북도",
            "전라남도",
            "경상북도",
            "경상남도",
            "제주도"
        )

        createproject_regions_spinner.setItems(regions)
        createproject_regions_spinner.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            stacklistregions = newItem
        }


        // 프로젝트 예상시간
        periodbtn[0] = findViewById(R.id.projectcreate_month_button)
        periodbtn[1] = findViewById(R.id.projectcreate_month2_button)
        periodbtn[2] = findViewById(R.id.projectcreate_month3_button)
        periodbtn[0]?.setOnClickListener {
            periodbtn[0]?.let { it1 ->
                this.onClickBtn(
                    it1,
                    0
                )
            }
        }
        periodbtn[1]?.setOnClickListener {
            periodbtn[1]?.let { it1 ->
                this.onClickBtn(
                    it1,
                    1
                )
            }
        }
        periodbtn[2]?.setOnClickListener {
            periodbtn[2]?.let { it1 ->
                this.onClickBtn(
                    it1,
                    2
                )
            }
        }

        // 분야 IT ~ 요리
        for (i in 0 until projectcreate_field_linearlayout.childCount) {
            val child: View = projectcreate_field_linearlayout.getChildAt(i)

            // 해당 버튼에 효과 주기
            if (child is Button) {
                child.background = ContextCompat.getDrawable(
                    this@ProjectCreation,
                    R.drawable.radius_button_effect
                )
                child.setOnClickListener {

                    // child
                    if (!stackToolfield.contains(child.text.toString())) {
                        child.background = ContextCompat.getDrawable(
                            this@ProjectCreation,
                            R.drawable.radius_background_transparent_select
                        )
                        stackToolfield.add(child.text.toString())
                    } else {
                        child.background = ContextCompat.getDrawable(
                            this@ProjectCreation,
                            R.drawable.radius_button_effect
                        )
                        stackToolfield.remove(child.text.toString())
                    }

                }
            }
        }

        // 분야 취미 ~ 자기계발
        for (i in 0 until projectcreate_field2_linearlayout.childCount) {
            val child: View = projectcreate_field2_linearlayout.getChildAt(i)

            // 해당 버튼에 효과 주기
            if (child is Button) {
                child.background = ContextCompat.getDrawable(
                    this@ProjectCreation,
                    R.drawable.radius_button_effect
                )
                child.setOnClickListener {

                    if (!stackToolfield.contains(child.text.toString())) {
                        child.background = ContextCompat.getDrawable(
                            this@ProjectCreation,
                            R.drawable.radius_background_transparent_select
                        )
                        stackToolfield.add(child.text.toString())
                    } else {
                        child.background = ContextCompat.getDrawable(
                            this@ProjectCreation,
                            R.drawable.radius_button_effect
                        )
                        stackToolfield.remove(child.text.toString())
                    }

                }
            }
        }


        // 버튼 눌렸을 때 1 page, 2 page 구분
        createproject_writerecruitment_button.setOnClickListener {
            if (checkPageButton()) {
                page = 2
                projectcreate_write_recruitment_article_constraintlayout.visibility =
                    View.GONE
                projectcreate_write_recruitment_article2_constraintlayout.visibility =
                    View.VISIBLE

                // 사용 예정 스택, 모집 포지션, 분야
                selectAllItems.addAll(selecteddeveloperItems)
                selectAllItems.addAll(selecteddesignerItems)
                selectpositionItems.addAll(stackToolposition)
                selectedfieldItems.addAll(stackToolfield)

                createproject_line1_button.background = ContextCompat.getDrawable(
                    this@ProjectCreation, R.drawable.page_line_background
                )

                createproject_line2_button.background = ContextCompat.getDrawable(
                    this@ProjectCreation,
                    R.drawable.page_line_background_select
                )

                SelectPage2()

            }
            projectcreate_page_textview.text = "$page  /  2"
        }


    }

    // Page2를 선택했을 때 (마지막 page)
    private fun SelectPage2() {
        createproject_submitrecruitment_button.setOnClickListener {

            var recruitmentproject = ProjectCreationElement(
                selectpositionItems,
                selectAllItems,
                openofftext,
                stacklistregions,
                periodtext,
                selectedfieldItems,
                createproject_projecttitle_edittext.text.toString(),
                createproject_projectdetail_edittext.text.toString()
            )



            // API 작성 DB에 넘김
            projectapi?.getRecruitmentProject(recruitmentproject)
                ?.enqueue(object : Callback<CheckResponse> {
                    override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                        Log.d("TAG : ","projectapi - onFailure() called /")
                    }
                    override fun onResponse(
                        call: Call<CheckResponse>,
                        response: Response<CheckResponse>
                    ) {
                        if(response.code().toString() == "200"){ // 200 : 요청이 정상적으로 처리 되었다면
                            ToastmakeTextPrint ("프로젝트 모집글 작성 완료되었습니다.")
                        }else{
                            ToastmakeTextPrint ("프로젝트 모집글 작성 실패하였습니다.")
                        }
                    }

                })


            finish ()




        }


    }

    // 모집 포지션, 사용 예정 스택, 프로젝트 방식 지역, 프로젝트 예상기간, 분야 선택 안되었을 때
    private fun checkPageButton(): Boolean {
        if (stackToolposition.size == 0) {
            ToastmakeTextPrint("모집 포지션 선택해주세요."); return false
        }
        if (selecteddeveloperItems.isEmpty() && selecteddesignerItems.isEmpty()) {
            ToastmakeTextPrint("사용 예정 스택 입력해주세요."); return false
        }
        if (openofftext.equals("openoff")) {
            ToastmakeTextPrint("프로젝트 방식을 선택해주세요."); return false
        }
        if (stacklistregions.equals("지역")) {
            ToastmakeTextPrint("$stacklistregions 지역을 입력해주세요."); return false
        }
        if (periodtext.equals("period")) {
            ToastmakeTextPrint("프로젝트 예상 기간을 선택해주세요."); return false
        }
        if (stackToolfield.size == 0) {
            ToastmakeTextPrint("분야를 선택해주세요."); return false
        }

        return true
    }


    private fun ToastmakeTextPrint(word: String) {
        Toast.makeText(this, word, Toast.LENGTH_SHORT).show()
    }

    // 버튼 둘 중 하나만 선택되게 하기 위해 사용
    private fun onClickBtn(v: View, index: Int) {
        var id = v.id

        // 프로젝트 방식
        if (id == R.id.projectcreate_offperiod_button || id == R.id.projectcreate_openperiod_button) {
            for (i in 0..1) {
                if (i == index) {
                    openoffbtn[i]?.background = ContextCompat.getDrawable(
                        this@ProjectCreation,
                        R.drawable.radius_background_transparent_select
                    )
                    openofftext = openoffbtn[i]?.text.toString()
                } else {
                    openoffbtn[i]?.background = ContextCompat.getDrawable(
                        this@ProjectCreation,
                        R.drawable.radius_button_effect
                    )
                }
            }
        }


        // 프로젝트 예상 기간
        if (id == R.id.projectcreate_month_button || id == R.id.projectcreate_month2_button
            || id == R.id.projectcreate_month3_button
        ) {
            for (i in 0..2) {
                if (i == index) {
                    periodbtn[i]?.background = ContextCompat.getDrawable(
                        this@ProjectCreation,
                        R.drawable.radius_background_transparent_select
                    )
                    periodtext = periodbtn[i]?.text.toString()
                } else {
                    periodbtn[i]?.background = ContextCompat.getDrawable(
                        this@ProjectCreation,
                        R.drawable.radius_button_effect
                    )
                }
            }
        }
    }


    var developerbool: BooleanArray = BooleanArray(11)
    var designerbool: BooleanArray = BooleanArray(11)

    private fun StackTobeUsed(btn: Button, stack: Array<String>) {
        btn.setOnClickListener {

            booleanArrayOf()

            if (stack[0].equals("JAVA")) {
                val dialog = AlertDialog.Builder(this@ProjectCreation)
                dialog.setMultiChoiceItems(stack,
                    booleanArrayOf(
                        developerbool[0],
                        developerbool[1],
                        developerbool[2],
                        developerbool[3],
                        developerbool[4],
                        developerbool[5],
                        developerbool[6],
                        developerbool[7],
                        developerbool[8],
                        developerbool[9],
                        developerbool[10]
                    ),
                    object : DialogInterface.OnMultiChoiceClickListener {
                        override fun onClick(
                            dialog: DialogInterface?,
                            which: Int,
                            isChecked: Boolean
                        ) {
                            if (isChecked) {
                                developerbool[which] = true
                                selecteddeveloperItems.add(stack[which])
                            } else {
                                developerbool[which] = false
                                selecteddeveloperItems.remove(stack[which])
                            }

                        }
                    }).setPositiveButton("확인", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        // 사용자가 확인 버튼 눌렸을 때
                        if (selecteddeveloperItems.size == 0) {
                            createproject_developer_button.background =
                                ContextCompat.getDrawable(
                                    this@ProjectCreation,
                                    R.drawable.radius_background_transparent
                                )
                        } else {
                            var items = ""
                            for (selitem in selecteddeveloperItems) {
                                items += (selitem + ", ")
                            }
                            createproject_developer_button.background =
                                ContextCompat.getDrawable(
                                    this@ProjectCreation,
                                    R.drawable.radius_background_transparent_select
                                )
                        }
                    }
                }).create().show()
            } else {

                val dialog = AlertDialog.Builder(this@ProjectCreation)
                dialog.setMultiChoiceItems(stack,
                    booleanArrayOf(
                        designerbool[0],
                        designerbool[1],
                        designerbool[2],
                        designerbool[3],
                        designerbool[4],
                        designerbool[5],
                        designerbool[6],
                        designerbool[7],
                        designerbool[8],
                        designerbool[9],
                        designerbool[10]
                    ),
                    object : DialogInterface.OnMultiChoiceClickListener {
                        override fun onClick(
                            dialog: DialogInterface?,
                            which: Int,
                            isChecked: Boolean
                        ) {
                            if (isChecked) {
                                designerbool[which] = true
                                selecteddesignerItems.add(stack[which])
                            } else {
                                designerbool[which] = false
                                selecteddesignerItems.remove(stack[which])
                            }

                        }
                    }).setPositiveButton("확인", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        // 사용자가 확인 버튼 눌렸을 때
                        if (selecteddesignerItems.size == 0) {
                            createproject_designer_button.background = ContextCompat.getDrawable(
                                this@ProjectCreation,
                                R.drawable.radius_background_transparent
                            )
                        } else {
                            var items = ""
                            for (selitem in selecteddesignerItems) {
                                items += (selitem + ", ")
                            }

                            createproject_designer_button.background = ContextCompat.getDrawable(
                                this@ProjectCreation,
                                R.drawable.radius_background_transparent_select
                            )
                        }
                    }

                }).create().show()
            }

        }

    }
}

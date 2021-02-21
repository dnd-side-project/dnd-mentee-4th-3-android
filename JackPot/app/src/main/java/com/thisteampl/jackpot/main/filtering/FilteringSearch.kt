package com.thisteampl.jackpot.main.filtering

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectController.ProjectElement
import com.thisteampl.jackpot.main.projectController.projectAPI
import kotlinx.android.synthetic.main.activity_filtering_search.*
import kotlinx.android.synthetic.main.activity_project_creation.*
import retrofit2.Call
import retrofit2.Response

class FilteringSearch : AppCompatActivity() {


    // 프로젝트 찾기
    // 1) 기술 스택
    private val projectfind_stackToolTechnologyStack = mutableListOf<String>()

    // 2) 프로젝트 방식
    private var projectfind_onoffbtn = arrayOfNulls<Button>(2)
    private var projectfind_onofftext = "onoff"

    // 3) 지역
    private var projectfind_regiontext = "지역" // 지역 list 저장용

    // 4) 기간
    private var projectfind_durationbtn = arrayOfNulls<Button>(3)
    private var projectfind_durationtext = "duration"

    // 5) 관심분야
    private var projectfind_projectfieldbtn = arrayOfNulls<Button>(8)
    private var projectfind_projectfieldtext = "field"

    // 멤버찾기
    // 1) 포지션
    private val memberfind_selectpositionItems = mutableListOf<String>()    // 포지션

    // 2) 개발언어
    private val memberfind_stackToolTechnologyStack = mutableListOf<String>()

    // 3) 프로젝트 방식
    private var memberfind_onoffbtn = arrayOfNulls<Button>(2)
    private var memberfind_onofftext = "onoff"

    // 4) 지역
    private val memberfind_stackToolposition = mutableListOf<String>()
    private var memberfind_regiontext = "지역" // 지역 list 저장용

    // 5) 기간
    private var memberfind_durationbtn = arrayOfNulls<Button>(3)
    private var memberfind_durationtext = "duration"

    // 6) 관심분야
    private var memberfind_projectfieldbtn = arrayOfNulls<Button>(8)
    private var memberfind_projectfieldtext = "field"


    private var page: Int = 1

    private var regiontext = "지역" // 지역 list 저장용


    private var projectapi = projectAPI.projectRetrofitService()

    var developer_btn: Boolean = false
    var designer_btn: Boolean = false
    var planner_btn: Boolean = false
    var user = String()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtering_search)

        // 개인정보를 통해 개발자, 디자이너 얻어옴
        user = "개발자"

        // 개발자 or 디자이너 선택됨

        // 첫 시작할 때
        findproject()
        searchfindbtn()

    }

    // 프로젝트 찾기, 멤버 찾기 버튼
    private fun searchfindbtn() {
        filtersearch_findproject_textview.setOnClickListener {
            filtersearch_write_recruitment_article_constraintlayout.visibility = View.VISIBLE
            filtersearch_page2_write_recruitment_article_constraintlayout.visibility = View.GONE
            findproject()
        }

        filtersearch_findmember_textview.setOnClickListener {
            filtersearch_write_recruitment_article_constraintlayout.visibility = View.GONE
            filtersearch_page2_write_recruitment_article_constraintlayout.visibility = View.VISIBLE
            findmember()
        }

        filtersearch_reset_button.setOnClickListener {
            projectfind_stackToolTechnologyStack.clear()

        }
    }


    // 프로젝트 찾기 화면
    private fun findproject() {

        projectfind_stackToolTechnologyStack.clear()


        // 툴
        if(user.equals("개발자")){
            stackLanguage(user, 1)
            userdeveloper()
        }else if(user.equals("디자이너")){
            stackLanguage(user, 1)
        }



    }

    private fun userdeveloper() {
        //프로젝트 방식
        var project_regions = listOf(
            "서울", "경기", "인천", "대전", "광주", "울산", "세종", "대구", "부산", "강원도",
            "충청북도", "충청남도", "전라북도", "전라남도", "경상남도", "경상북도", "제주도",
            "해외"
        )

        filtersearch_regions_spinner.setItems(project_regions)
        filtersearch_regions_spinner.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            projectfind_regiontext = newItem
        }

        // 프로젝트 방식

        projectfind_onoffbtn[0] = findViewById(R.id.filtersearch_offline_button)
        projectfind_onoffbtn[1] = findViewById(R.id.filtersearch_online_button)
        projectfind_onoffbtn[0]?.setOnClickListener {


            projectfind_onoffbtn[0]?.let { it1 ->
                this.onClickBtn(
                    it1,
                    0, 2
                )
            }
        }
        projectfind_onoffbtn[1]?.setOnClickListener {


            projectfind_onoffbtn[1]?.let { it2 ->
                this.onClickBtn(
                    it2,
                    1, 2
                )
            }
        }


        // 프로젝트 예상기간
        projectfind_durationbtn[0] = findViewById(R.id.filtersearch_month_button)
        projectfind_durationbtn[1] = findViewById(R.id.filtersearch_month2_button)
        projectfind_durationbtn[2] = findViewById(R.id.filtersearch_month3_button)
        projectfind_durationbtn[0]?.setOnClickListener {
            projectfind_durationbtn[0]?.let { it1 ->
                this.onClickBtn(
                    it1,
                    0, 3
                )
            }
        }
        projectfind_durationbtn[1]?.setOnClickListener {
            projectfind_durationbtn[1]?.let { it1 ->
                this.onClickBtn(
                    it1,
                    1, 3
                )
            }
        }
        projectfind_durationbtn[2]?.setOnClickListener {
            projectfind_durationbtn[2]?.let { it1 ->
                this.onClickBtn(
                    it1,
                    2, 3
                )
            }
        }

        // 분야
        projectfind_projectfieldbtn[0] =
            findViewById(R.id.filtersearch_selfdeveloper_textview)
        projectfind_projectfieldbtn[1] = findViewById(R.id.filtersearch_hobby_textview)
        projectfind_projectfieldbtn[2] =
            findViewById(R.id.filtersearch_economy_textview)
        projectfind_projectfieldbtn[3] = findViewById(R.id.filtersearch_cook_textview)
        projectfind_projectfieldbtn[4] = findViewById(R.id.filtersearch_it_textview)
        projectfind_projectfieldbtn[5] = findViewById(R.id.filtersearch_rest_textview)
        projectfind_projectfieldbtn[6] = findViewById(R.id.filtersearch_health_textview)
        projectfind_projectfieldbtn[7] =
            findViewById(R.id.filtersearch_holiday_textview)


        projectfind_projectfieldbtn[0]?.setOnClickListener {
            projectfind_projectfieldbtn[0]?.let { it1 ->
                this.onClickBtn(
                    it1, 0, 8
                )
            }
        }

        projectfind_projectfieldbtn[1]?.setOnClickListener {
            projectfind_projectfieldbtn[1]?.let { it1 ->
                this.onClickBtn(
                    it1, 1, 8
                )
            }
        }

        projectfind_projectfieldbtn[2]?.setOnClickListener {
            projectfind_projectfieldbtn[2]?.let { it1 ->
                this.onClickBtn(
                    it1, 2, 8
                )
            }
        }
        projectfind_projectfieldbtn[3]?.setOnClickListener {
            projectfind_projectfieldbtn[3]?.let { it1 ->
                this.onClickBtn(
                    it1, 3, 8
                )
            }
        }
        projectfind_projectfieldbtn[4]?.setOnClickListener {
            projectfind_projectfieldbtn[4]?.let { it1 ->
                this.onClickBtn(
                    it1, 4, 8
                )
            }
        }
        projectfind_projectfieldbtn[5]?.setOnClickListener {
            projectfind_projectfieldbtn[5]?.let { it1 ->
                this.onClickBtn(
                    it1, 5, 8
                )
            }
        }
        projectfind_projectfieldbtn[6]?.setOnClickListener {
            projectfind_projectfieldbtn[6]?.let { it1 ->
                this.onClickBtn(
                    it1, 6, 8
                )
            }
        }
        projectfind_projectfieldbtn[7]?.setOnClickListener {
            projectfind_projectfieldbtn[7]?.let { it1 ->
                this.onClickBtn(
                    it1, 7, 8
                )
            }
        }
    }

    // 멤버 찾기 화면
    private fun findmember() {

    }

    private fun ToastmakeTextPrint(word: String) {
        Toast.makeText(this, word, Toast.LENGTH_SHORT).show()
    }

    private fun stackLanguage(location: String, page_position: Int) {
        if (page_position == 1) {
            if (location.equals("개발자")) {

                for (i in 0 until filtersearch_projectstackbutton_constraintLayout.childCount) {
                    val child: View = filtersearch_projectstackbutton_constraintLayout.getChildAt(i)
                    // 해당 버튼에 효과 주기
                    if (child is Button) {
                        child.background = ContextCompat.getDrawable(
                            this@FilteringSearch,
                            R.drawable.radius_button_effect
                        )

                        child.setOnClickListener {

                            if (!projectfind_stackToolTechnologyStack.contains(child.text.toString())) {
                                child.background = ContextCompat.getDrawable(
                                    this@FilteringSearch,
                                    R.drawable.radius_background_transparent_select
                                )
                                child.setTextColor(
                                    ContextCompat.getColor(
                                        this@FilteringSearch,
                                        R.color.colorButtonSelect
                                    )
                                )
                                projectfind_stackToolTechnologyStack.add(child.text.toString())
                            } else {
                                child.background = ContextCompat.getDrawable(
                                    this@FilteringSearch,
                                    R.drawable.radius_button_effect
                                )
                                child.setTextColor(
                                    ContextCompat.getColor(
                                        this@FilteringSearch,
                                        R.color.colorButtonNoSelect
                                    )
                                )
                                projectfind_stackToolTechnologyStack.remove(child.text.toString())
                            }

                        }

                    }


                }

            } else if (location.equals("디자이너")) {
                for (i in 0 until filtersearch_projectstackdesignerbutton_constraintLayout.childCount) {
                    val child: View = filtersearch_projectstackdesignerbutton_constraintLayout.getChildAt(i)
                    // 해당 버튼에 효과 주기
                    if (child is Button) {
                        child.background = ContextCompat.getDrawable(
                            this@FilteringSearch,
                            R.drawable.radius_button_effect
                        )

                        child.setOnClickListener {

                            if (!projectfind_stackToolTechnologyStack.contains(child.text.toString())) {
                                child.background = ContextCompat.getDrawable(
                                    this@FilteringSearch,
                                    R.drawable.radius_background_transparent_select
                                )
                                child.setTextColor(
                                    ContextCompat.getColor(
                                        this@FilteringSearch,
                                        R.color.colorButtonSelect
                                    )
                                )
                                projectfind_stackToolTechnologyStack.add(child.text.toString())
                            } else {
                                child.background = ContextCompat.getDrawable(
                                    this@FilteringSearch,
                                    R.drawable.radius_button_effect
                                )
                                child.setTextColor(
                                    ContextCompat.getColor(
                                        this@FilteringSearch,
                                        R.color.colorButtonNoSelect
                                    )
                                )
                                projectfind_stackToolTechnologyStack.remove(child.text.toString())
                            }

                        }

                    }


                }
            }
        }
    }

    // ProjectCreation에서 사용된 코드 이용
    var checkoffout: Int = -1
    var checkexpectedduration: Int = -1
    var checkexpectedfield: Int = -1

    private fun onClickBtn(v: View, index: Int, btnsize: Int) {
        var id = v.id

        var btn = R.id.filtersearch_offline_button

        var checkoff: Int = btnsize - 1

        // 프로젝트 방식
        if (id == R.id.filtersearch_offline_button || id == R.id.filtersearch_online_button) {


            // 선택된 상태라면 체크 취소하기
            if (index == checkoffout) {

                // 오프라인 부분이면 지역부분 빼기
                if (index == 0) {
                    filtersearch_regions_textview.visibility = View.GONE
                    filtersearch_regions_linearlayout.visibility = View.GONE
                    filtersearch_regions_spinner.text = "지역"
                    regiontext = "지역"
                }
                projectfind_onoffbtn[index]?.background = ContextCompat.getDrawable(
                    this@FilteringSearch,
                    R.drawable.radius_button_effect
                )
                projectfind_onoffbtn[index]?.setTextColor(
                    ContextCompat.getColor(
                        this@FilteringSearch,
                        R.color.colorButtonNoSelect
                    )
                )

                // 초기화
                projectfind_onofftext = "onoff"
                checkoffout = -1
            } else {

                if (index == 0) {
                    filtersearch_regions_textview.visibility = View.VISIBLE
                    filtersearch_regions_linearlayout.visibility = View.VISIBLE

                } else {
                    filtersearch_regions_textview.visibility = View.GONE
                    filtersearch_regions_linearlayout.visibility = View.GONE
                    filtersearch_regions_spinner.text = "지역"
                    regiontext = "지역"
                }

                projectfind_onoffbtn[index]?.background = ContextCompat.getDrawable(
                    this@FilteringSearch,
                    R.drawable.radius_background_transparent_select
                )
                projectfind_onoffbtn[index]?.setTextColor(
                    ContextCompat.getColor(
                        this@FilteringSearch,
                        R.color.colorButtonSelect
                    )
                )


                projectfind_onoffbtn[checkoff - index]?.background = ContextCompat.getDrawable(
                    this@FilteringSearch,
                    R.drawable.radius_button_effect
                )
                projectfind_onoffbtn[checkoff - index]?.setTextColor(
                    ContextCompat.getColor(
                        this@FilteringSearch,
                        R.color.colorButtonNoSelect
                    )
                )

                projectfind_onofftext = projectfind_onoffbtn[index]?.text.toString()
                checkoffout = index
            }

        }


        // 프로젝트 예상 기간
        if (id == R.id.filtersearch_month_button || id == R.id.filtersearch_month2_button
            || id == R.id.filtersearch_month3_button
        ) {
            for (i in 0..2) {

                // 1. 이미 버튼 on 되어 있는 곳에 한 번 더 눌렸을 때 off
                // 2. 해당 자리 버튼일 때 버튼 on
                // 3. 이외의 버튼(버튼 적용되는 곳 이외) off
                if (checkexpectedduration == i) {
                    projectfind_durationbtn[i]?.background = ContextCompat.getDrawable(
                        this@FilteringSearch,
                        R.drawable.radius_button_effect
                    )
                    projectfind_durationbtn[i]?.setTextColor(
                        ContextCompat.getColor(
                            this@FilteringSearch,
                            R.color.colorButtonNoSelect
                        )
                    )

                    checkexpectedduration = -1
                } else if (i == index) {
                    projectfind_durationbtn[i]?.background = ContextCompat.getDrawable(
                        this@FilteringSearch,
                        R.drawable.radius_background_transparent_select
                    )
                    projectfind_durationbtn[i]?.setTextColor(
                        ContextCompat.getColor(
                            this@FilteringSearch,
                            R.color.colorButtonSelect
                        )
                    )

                    checkexpectedduration = i
                    projectfind_durationtext = projectfind_durationbtn[i]?.text.toString()
                } else {
                    projectfind_durationbtn[i]?.background = ContextCompat.getDrawable(
                        this@FilteringSearch,
                        R.drawable.radius_button_effect
                    )
                    projectfind_durationbtn[i]?.setTextColor(
                        ContextCompat.getColor(
                            this@FilteringSearch,
                            R.color.colorButtonNoSelect
                        )
                    )
                }
            }
        }


        // 분야
        if (id == R.id.filtersearch_selfdeveloper_textview || id == R.id.filtersearch_hobby_textview
            || id == R.id.filtersearch_economy_textview || id == R.id.filtersearch_cook_textview
            || id == R.id.filtersearch_it_textview || id == R.id.filtersearch_rest_textview
            || id == R.id.filtersearch_health_textview || id == R.id.filtersearch_holiday_textview
        ) {
            for (i in 0..7) {

                // 1. 이미 버튼 on 되어 있는 곳에 한 번 더 눌렸을 때 off
                // 2. 해당 자리 버튼일 때 버튼 on
                // 3. 이외의 버튼(버튼 적용되는 곳 이외) off

                if (checkexpectedfield == i) {
                    projectfind_projectfieldbtn[i]?.background =
                        ContextCompat.getDrawable(
                            this@FilteringSearch,
                            R.drawable.radius_button_effect
                        )
                    projectfind_projectfieldbtn[i]?.setTextColor(
                        ContextCompat.getColor(
                            this@FilteringSearch,
                            R.color.colorButtonNoSelect
                        )
                    )

                    checkexpectedfield = -1
                } else if (i == index) {
                    projectfind_projectfieldbtn[i]?.background =
                        ContextCompat.getDrawable(
                            this@FilteringSearch,
                            R.drawable.radius_background_transparent_select
                        )
                    projectfind_projectfieldbtn[i]?.setTextColor(
                        ContextCompat.getColor(
                            this@FilteringSearch,
                            R.color.colorButtonSelect
                        )
                    )


                    checkexpectedfield = i
                    projectfind_projectfieldtext =
                        projectfind_projectfieldbtn[i]?.text.toString()
                } else {
                    projectfind_projectfieldbtn[i]?.background =
                        ContextCompat.getDrawable(
                            this@FilteringSearch,
                            R.drawable.radius_button_effect
                        )
                    projectfind_projectfieldbtn[i]?.setTextColor(
                        ContextCompat.getColor(
                            this@FilteringSearch,
                            R.color.colorButtonNoSelect
                        )
                    )

                }
            }
        }
    }
}

package com.thisteampl.jackpot.main.filtering

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectController.projectAPI
import kotlinx.android.synthetic.main.activity_filtering_search.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile_edit_change_emoji.view.*
import kotlinx.android.synthetic.main.activity_project_creation.*

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
    private var projectfind_durationbtn = mutableListOf<String>()
    private var projectfind_durationtext = "duration"

    // 5) 관심분야
    private var projectfind_projectfieldbtn =mutableListOf<String>()
    private var projectfind_projectfieldtext = "field"


    // 멤버찾기
    // 1) 포지션
    private val memberfind_selectpositionItems = mutableListOf<String>()    // 포지션

    // 2) 개발언어
    private val memberfind_stackToolTechnologyStack = mutableListOf<String>()
    private val stackTooldeveloper = mutableListOf<String>() // 개발자 스택
    private val stackTooldesigner = mutableListOf<String>()  // 디자이너 스택

    // 3) 프로젝트 방식
    private var memberfind_onoffbtn = arrayOfNulls<Button>(2)
    private var memberfind_onofftext = "onoff"

    // 4) 지역
    private var memberfind_regiontext = "지역" // 지역 list 저장용

    // 5) 관심분야
    private var memberfind_projectfieldbtn = mutableListOf<String>()
    private var memberfind_projectfieldtext = "field"


    private var page: Int = 1
    var regions = listOf(
        "서울", "경기", "인천", "대전", "광주", "울산", "세종", "대구", "부산", "강원도",
        "충청북도", "충청남도", "전라북도", "전라남도", "경상남도", "경상북도", "제주도",
        "해외"
    )


    private var projectapi = projectAPI.projectRetrofitService()

    var user = String()


    var developer_btn:Boolean = false
    var designer_btn:Boolean = false
    var planner_btn:Boolean = false

    // 가독성 떨어짐
    // 개발자 툴 name
    var java:Boolean = false ; var cplus:Boolean = false ; var python:Boolean = false ; var js:Boolean = false ; var html:Boolean = false
    var swift:Boolean = false ; var spring:Boolean = false ; var kotlin:Boolean = false ; var django:Boolean = false
    var reactjs:Boolean = false; var flask:Boolean = false
    var developercheck_index:Int = 0

    // 디자이너 툴 name
    var photoshop = false ; var illusrator = false ; var xd = false; var figma = false; var sketch = false; var principle= false;
    var protopie = false ; var after_effects = false; var premiere = false; var indesign = false; var c4d = false; var zeplin = false;
    var designercheck_index:Int = 0


    // 개발자 툴 관련 소스
    // selectbtn : 선택, selectoutbtn : 선택하지 않았을 때
    private fun selectinbtn(btn: Button){
        btn.background = ContextCompat.getDrawable(
            this@FilteringSearch,
            R.drawable.radius_background_transparent_select
        )
        btn.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonSelect))

        developercheck_index++
        stackTooldeveloper.add(btn.text.toString())
    }

    private fun selectoutbtn(btn: Button){
        btn.background = ContextCompat.getDrawable(
            this@FilteringSearch,
            R.drawable.radius_background_transparent
        )
        btn.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonNoSelect))

        developercheck_index--
        stackTooldeveloper.remove(btn.text.toString())
    }


    // 개발자 툴
    private fun developertool(){
        filtersearch_page2_java_Button.setOnClickListener {
            if(java == false){
                selectinbtn(filtersearch_page2_java_Button)
                java = true
            }else{
                java = false
                selectoutbtn(filtersearch_page2_java_Button)
            }

        }

        filtersearch_page2_cpluse_Button.setOnClickListener {
            if(cplus == false){
                selectinbtn(filtersearch_page2_cpluse_Button)
                cplus = true
            }else{
                cplus = false
                selectoutbtn(filtersearch_page2_cpluse_Button)
            }

        }

        filtersearch_page2_python_Button.setOnClickListener {
            if(python == false){
                selectinbtn(filtersearch_page2_python_Button)
                python = true
            }else{
                python = false
                selectoutbtn(filtersearch_page2_python_Button)
            }
        }

        filtersearch_page2_javascript_Button.setOnClickListener {
            if(js == false){
                selectinbtn(filtersearch_page2_javascript_Button)
                js = true
            }else{
                js = false
                selectoutbtn(filtersearch_page2_javascript_Button)
            }
        }

        filtersearch_page2_html_css_Button.setOnClickListener {
            if(html == false){
                selectinbtn(filtersearch_page2_html_css_Button)
                html = true
            }else{
                html = false
                selectoutbtn(filtersearch_page2_html_css_Button)
            }
        }

        filtersearch_page2_swift_Button.setOnClickListener {
            if(swift == false){
                selectinbtn(filtersearch_page2_swift_Button)
                swift = true
            }else{
                swift = false
                selectoutbtn(filtersearch_page2_swift_Button)
            }
        }

        filtersearch_page2_spring_Button.setOnClickListener {
            if(spring == false){
                selectinbtn(filtersearch_page2_spring_Button)
                spring = true
            }else{
                spring = false
                selectoutbtn(filtersearch_page2_spring_Button)
            }
        }
        filtersearch_page2_kotlin_Button.setOnClickListener {
            if(kotlin == false){
                selectinbtn(filtersearch_page2_kotlin_Button)
                kotlin = true
            }else{
                kotlin = false
                selectoutbtn(filtersearch_page2_kotlin_Button)
            }
        }
        filtersearch_page2_django_Button.setOnClickListener {
            if(django == false){
                selectinbtn(filtersearch_page2_django_Button)
                django = true
            }else{
                django = false
                selectoutbtn(filtersearch_page2_django_Button)
            }
        }
        filtersearch_page2_reactjs_Button.setOnClickListener {
            if(reactjs == false){
                selectinbtn(filtersearch_page2_reactjs_Button)
                reactjs = true
            }else{
                reactjs = false
                selectoutbtn(filtersearch_page2_reactjs_Button)
            }
        }
        filtersearch_page2_flask_Button.setOnClickListener {
            if(flask == false){
                selectinbtn(filtersearch_page2_flask_Button)
                flask = true
            }else{
                flask = false
                selectoutbtn(filtersearch_page2_flask_Button)
            }
        }

    }

    // 디자이너 툴 관련 소스
    // selectindesignerbtn : 선택, selectoutdesignerbtn : 선택하지 않았을 때
    private fun selectindesignerbtn(btn: Button){
        btn.background = ContextCompat.getDrawable(
            this@FilteringSearch,
            R.drawable.radius_background_transparent_select
        )
        btn.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonSelect))
        designercheck_index++
        stackTooldesigner.add(btn.text.toString())
    }

    private fun selectoutdesignerbtn(btn: Button){
        btn.background = ContextCompat.getDrawable(
            this@FilteringSearch,
            R.drawable.radius_background_transparent
        )
        btn.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonNoSelect))
        designercheck_index--
        stackTooldesigner.remove(btn.text.toString())
    }

    private fun designertool(){
        filtersearch_page2_photoshop_Button.setOnClickListener {
            if(photoshop == false){
                selectindesignerbtn(filtersearch_page2_photoshop_Button)
                photoshop = true
            }else{
                photoshop = false
                selectoutdesignerbtn(filtersearch_page2_photoshop_Button)
            }
        }
        filtersearch_page2_illustrator_Button.setOnClickListener {
            if(illusrator == false){
                selectindesignerbtn(filtersearch_page2_illustrator_Button)
                illusrator = true
            }else{
                illusrator = false
                selectoutdesignerbtn(filtersearch_page2_illustrator_Button)
            }
        }
        filtersearch_page2_xd_Button.setOnClickListener {
            if(xd == false){
                selectindesignerbtn(filtersearch_page2_xd_Button)
                xd = true
            }else{
                xd = false
                selectoutdesignerbtn(filtersearch_page2_xd_Button)
            }
        }

        filtersearch_page2_figma_Button.setOnClickListener {
            if(figma == false){
                selectindesignerbtn(filtersearch_page2_figma_Button)
                figma = true
            }else{
                figma = false
                selectoutdesignerbtn(filtersearch_page2_figma_Button)
            }
        }

        filtersearch_page2_sketch_Button.setOnClickListener {
            if(sketch == false){
                selectindesignerbtn(filtersearch_page2_sketch_Button)
                sketch = true
            }else{
                sketch = false
                selectoutdesignerbtn(filtersearch_page2_sketch_Button)
            }
        }

        filtersearch_page2_Principle_Button.setOnClickListener {
            if(principle == false){
                selectindesignerbtn(filtersearch_page2_Principle_Button)
                principle = true
            }else{
                principle = false
                selectoutdesignerbtn(filtersearch_page2_Principle_Button)
            }
        }
        filtersearch_page2_protopie_Button.setOnClickListener {
            if(protopie == false){
                selectindesignerbtn(filtersearch_page2_protopie_Button)
                protopie = true
            }else{
                protopie = false
                selectoutdesignerbtn(filtersearch_page2_protopie_Button)
            }
        }
        filtersearch_page2_after_effects_Button.setOnClickListener {
            if(after_effects == false){
                selectindesignerbtn(filtersearch_page2_after_effects_Button)
                after_effects = true
            }else{
                after_effects = false
                selectoutdesignerbtn(filtersearch_page2_after_effects_Button)
            }
        }
        filtersearch_page2_premiere_Button.setOnClickListener {
            if(premiere == false){
                selectindesignerbtn(filtersearch_page2_premiere_Button)
                premiere = true
            }else{
                premiere = false
                selectoutdesignerbtn(filtersearch_page2_premiere_Button)
            }
        }
        filtersearch_page2_Indesign_Button.setOnClickListener {
            if(indesign== false){
                selectindesignerbtn(filtersearch_page2_Indesign_Button)
                indesign = true
            }else{
                indesign = false
                selectoutdesignerbtn(filtersearch_page2_Indesign_Button)
            }
        }
        filtersearch_page2_c4d_Button.setOnClickListener {
            if(c4d == false){
                selectindesignerbtn(filtersearch_page2_c4d_Button)
                c4d = true
            }else{
                c4d = false
                selectoutdesignerbtn(filtersearch_page2_c4d_Button)
            }
        }
        filtersearch_page2_Zeplin_Button.setOnClickListener {
            if(zeplin == false){
                selectindesignerbtn(filtersearch_page2_Zeplin_Button)
                zeplin = true
            }else{
                zeplin = false
                selectoutdesignerbtn(filtersearch_page2_Zeplin_Button)
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtering_search)

        filtersearch_minusbutton_button.setOnClickListener {
            finish()
        }

        // 개인정보를 통해 개발자, 디자이너 얻어옴
        user = "개발자"

        
        // 개발자일 때 개발자 툴만, 디자이너 일 때 디자이너 툴만
        if(user.equals("개발자")){
            filtersearch_projectstack_constraintLayout.visibility = View.VISIBLE
            filtersearch_projectstackdesigner_constraintLayout.visibility = View.GONE
            filtersearch_page2_projectstack_constraintLayout.visibility = View.VISIBLE
            filtersearch_page2_projectstackdesigner_constraintLayout.visibility = View.GONE
        }else{
            filtersearch_projectstack_constraintLayout.visibility = View.GONE
            filtersearch_projectstackdesigner_constraintLayout.visibility = View.VISIBLE
            filtersearch_page2_projectstack_constraintLayout.visibility = View.GONE
            filtersearch_page2_projectstackdesigner_constraintLayout.visibility = View.VISIBLE
        }

        // 첫 시작할 때
        findproject()
        searchfindbtn()

    }

    // 프로젝트 찾기, 멤버 찾기 버튼
    private fun searchfindbtn() {
        filtersearch_findproject_textview.setOnClickListener {
            filtersearch_write_recruitment_article_constraintlayout.visibility = View.VISIBLE
            filtersearch_page2_write_recruitment_article_constraintlayout.visibility = View.GONE
            filtersearch_findproject_textview.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorbrightly))
            filtersearch_findmember_textview.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colordarkly))

            // page 1
            page = 1
            findproject()
        }

        filtersearch_findmember_textview.setOnClickListener {

            filtersearch_write_recruitment_article_constraintlayout.visibility = View.GONE
            filtersearch_page2_write_recruitment_article_constraintlayout.visibility = View.VISIBLE

            filtersearch_findmember_textview

            filtersearch_findproject_textview.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colordarkly))
            filtersearch_findmember_textview.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorbrightly))

            // page 2
            page = 2
            findmember()
        }

        filtersearch_reset_button.setOnClickListener {

            if (page == 1) {
                onClearConstraintLayout(filtersearch_projectstackbutton_constraintLayout)
                onClearConstraintLayout(filtersearch_projectstackdesignerbutton_constraintLayout)
                onClearLinearLayout(filtersearch_projectonoff_linearlayout)
                onClearLinearLayout(filtersearch_month_linearlayout)
                onClearLinearLayout(filtersearch_field_linearlayout)
                onClearLinearLayout(filtersearch_field2_linearlayout)
                ToastmakeTextPrint("필터 초기화")
            }else if(page == 2){
                onClearConstraintLayout(filtersearch_page2_projectstackbutton_constraintLayout)
                onClearConstraintLayout(filtersearch_page2_projectstackdesignerbutton_constraintLayout)
                onClearLinearLayout(filtersearch_page2_position_linearlayout)
                onClearLinearLayout(filtersearch_page2_projectonoff_linearlayout)
                onClearLinearLayout(filtersearch_page2_field_linearlayout)
                onClearLinearLayout(filtersearch_page2_field2_linearlayout)

            }


        }
        filtersearch_applyfilter_button.setOnClickListener {

            if(page == 1){

            }else if(page == 2){
                
                // page 2 : 멤버찾기, 포지션 및 개발 툴, 디자인 툴
                Log.d("tag","멤버 찾기 페이지")

                Log.d("","선택된 : 포지션")
                for(num in 1..memberfind_selectpositionItems.size-1){
                    Log.d("tag : ","${memberfind_selectpositionItems[num]}")
                }

                memberfind_stackToolTechnologyStack.addAll(stackTooldeveloper)
                memberfind_stackToolTechnologyStack.addAll(stackTooldesigner)

                Log.d("","기술 툴")
                for(n in 1..memberfind_stackToolTechnologyStack.size-1){
                    Log.d("", "${memberfind_stackToolTechnologyStack[n]}")
                }

                Log.d("","프로젝트 방식")
                Log.d("","${memberfind_onofftext}")
                
                Log.d("","지역")
                Log.d("","${memberfind_regiontext}")

                Log.d("","관심분야")
                Log.d("","${memberfind_projectfieldtext}")


            }


        }

    }

    // 필터초기화, ConstraintLayout
    private fun onClearConstraintLayout(constraintlayout: ConstraintLayout) {

        for (i in 0 until constraintlayout.childCount) {
            val child: View = constraintlayout.getChildAt(i)
            // 해당 버튼에 효과 빼기
            if (child is Button) {
                child.background = ContextCompat.getDrawable(
                    this@FilteringSearch,
                    R.drawable.radius_background_transparent
                )
                child.setTextColor(
                    ContextCompat.getColor(
                        this@FilteringSearch,
                        R.color.colorButtonNoSelect
                    )
                )
            }
        }


        // 툴
        if(constraintlayout == filtersearch_projectstackbutton_constraintLayout
            || constraintlayout == filtersearch_projectstackdesignerbutton_constraintLayout
        ){
            // 스택
            projectfind_stackToolTechnologyStack.clear()

        }else if (constraintlayout == filtersearch_page2_projectstackbutton_constraintLayout
            || constraintlayout == filtersearch_page2_projectstackdesignerbutton_constraintLayout
        ){
            // 멤버찾기 기술 스택
            stackTooldeveloper.clear()
            stackTooldesigner.clear()
            memberfind_stackToolTechnologyStack.clear()
        }
    }

    // 필터초기화, LinearLayout
    private fun onClearLinearLayout(linearlayout: LinearLayout) {

        for (i in 0 until linearlayout.childCount) {
            val child: View = linearlayout.getChildAt(i)
            // 해당 버튼에 효과 빼기
            if (child is Button) {
                child.background = ContextCompat.getDrawable(
                    this@FilteringSearch,
                    R.drawable.radius_background_transparent
                )
                child.setTextColor(
                    ContextCompat.getColor(
                        this@FilteringSearch,
                        R.color.colorButtonNoSelect
                    )
                )
            }
        }

        // 프로젝트 방식, 지역
        if (linearlayout == filtersearch_projectonoff_linearlayout) {
            filtersearch_regions_textview.visibility = View.GONE
            filtersearch_regions_linearlayout.visibility = View.GONE
            projectcheckoffout = -1
            filtersearch_regions_spinner.text = "지역"
            projectfind_onofftext = "onoff"
        }// 기간
        else if(linearlayout==filtersearch_month_linearlayout){
            projectfind_durationtext = "duration"
            projectfind_durationbtn.clear()
        }// 지역
        else if(linearlayout==filtersearch_field_linearlayout
            || linearlayout == filtersearch_field2_linearlayout
        ){
            projectfind_projectfieldtext = "field"
            projectfind_projectfieldbtn.clear()
        }else if(linearlayout == filtersearch_page2_position_linearlayout){
            memberfind_selectpositionItems.clear()
        }else if(linearlayout == filtersearch_page2_projectonoff_linearlayout){
            memberfind_onofftext = "onoff"
        }else if(linearlayout == filtersearch_page2_regions_linearlayout){
            memberfind_regiontext = "지역"
        }else if(linearlayout == filtersearch_page2_field_linearlayout
            || linearlayout == filtersearch_page2_field2_linearlayout
        ){
            memberfind_projectfieldbtn.clear()
            memberfind_projectfieldtext = "field"
        }

    }


    // 프로젝트 찾기 화면
    private fun findproject() {

        projectfind_stackToolTechnologyStack.clear()

        stackLanguage(user, 1)
        contentofproject()

    }


    var month = false
    var month2 = false
    var month3 = false

    var checkbool = Array(4){i->false}
    var checkbool2 = Array(4){i->false}
    val intentionbtn = arrayOfNulls<Button>(4)
    val intentionbtn2 = arrayOfNulls<Button>(4)

    var check = false

    private fun contentofproject() {

        if(check == false){
            intentionbtn[0] =filtersearch_selfdeveloper_textview
            intentionbtn[1] =filtersearch_hobby_textview
            intentionbtn[2] =filtersearch_economy_textview
            intentionbtn[3] =filtersearch_cook_textview

            intentionbtn2[0] =filtersearch_it_textview
            intentionbtn2[1] =filtersearch_rest_textview
            intentionbtn2[2] =filtersearch_health_textview
            intentionbtn2[3] =filtersearch_holiday_textview
            check = true

        }


        //프로젝트 방식
        filtersearch_regions_spinner.setItems(regions)
        filtersearch_regions_spinner.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            projectfind_regiontext = newItem
        }

        projectfind_onoffbtn[0] = findViewById(R.id.filtersearch_offline_button)
        projectfind_onoffbtn[1] = findViewById(R.id.filtersearch_online_button)
        projectfind_onoffbtn[0]?.setOnClickListener {


            projectfind_onoffbtn[0]?.let { it1 ->
                this.onClickProjectBtn(
                    it1,
                    0, 2
                )
            }
        }
        projectfind_onoffbtn[1]?.setOnClickListener {

           
            projectfind_onoffbtn[1]?.let { it2 ->
                this.onClickProjectBtn(
                    it2,
                    1, 2
                )
            }
        }


        // 프로젝트 예상기간
        filtersearch_month_button.setOnClickListener {
            if(month == false){
                filtersearch_month_button.background = ContextCompat.getDrawable(this@FilteringSearch, R.drawable.radius_background_transparent_select)
                filtersearch_month_button.setTextColor(ContextCompat.getColor(this@FilteringSearch, R.color.colorButtonSelect))
                month = true
                projectfind_durationbtn.add(filtersearch_month_button.text.toString())
            }
            else{
                filtersearch_month_button.background = ContextCompat.getDrawable(this@FilteringSearch, R.drawable.radius_background_transparent)
                filtersearch_month_button.setTextColor(ContextCompat.getColor(this@FilteringSearch, R.color.colorButtonNoSelect))
                month = false
                projectfind_durationbtn.remove(filtersearch_month_button.text.toString())
            }
        }

        filtersearch_month2_button.setOnClickListener {
            if(month2 == false){
                filtersearch_month2_button.background = ContextCompat.getDrawable(this@FilteringSearch, R.drawable.radius_background_transparent_select)
                filtersearch_month2_button.setTextColor(ContextCompat.getColor(this@FilteringSearch, R.color.colorButtonSelect))
                month2 = true
                projectfind_durationbtn.add(filtersearch_month2_button.text.toString())
            }
            else{
                filtersearch_month2_button.background = ContextCompat.getDrawable(this@FilteringSearch, R.drawable.radius_background_transparent)
                filtersearch_month2_button.setTextColor(ContextCompat.getColor(this@FilteringSearch, R.color.colorButtonNoSelect))
                month2 = false
                projectfind_durationbtn.remove(filtersearch_month2_button.text.toString())
            }
        }

        filtersearch_month3_button.setOnClickListener {
            if(month3 == false){
                filtersearch_month3_button.background = ContextCompat.getDrawable(this@FilteringSearch, R.drawable.radius_background_transparent_select)
                filtersearch_month3_button.setTextColor(ContextCompat.getColor(this@FilteringSearch, R.color.colorButtonSelect))
                month3 = true
                projectfind_durationbtn.add(filtersearch_month3_button.text.toString())
            }
            else{
                filtersearch_month3_button.background = ContextCompat.getDrawable(this@FilteringSearch, R.drawable.radius_background_transparent)
                filtersearch_month3_button.setTextColor(ContextCompat.getColor(this@FilteringSearch, R.color.colorButtonNoSelect))
                month3 = false
                projectfind_durationbtn.remove(filtersearch_month3_button.text.toString())
            }
        }



        // 관심분야
        for (i in 0 until filtersearch_field_linearlayout.childCount) {
            filtersearch_field_linearlayout[i].setOnClickListener {
                if(checkbool[i] == false){
                    filtersearch_field_linearlayout[i].background = ContextCompat.getDrawable(this@FilteringSearch, R.drawable.radius_background_transparent_select)
                    intentionbtn[i]?.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonSelect))
                    checkbool[i] = true
                    projectfind_projectfieldbtn.add(filtersearch_field_linearlayout[i].toString())
                }else{
                    filtersearch_field_linearlayout[i].background = ContextCompat.getDrawable(this@FilteringSearch, R.drawable.radius_background_transparent)
                    intentionbtn[i]?.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonNoSelect))
                    checkbool[i] = false
                    projectfind_projectfieldbtn.remove(filtersearch_field_linearlayout[i].toString())
                }
            }

        }

        for (i in 0 until filtersearch_field2_linearlayout.childCount) {
            filtersearch_field2_linearlayout[i].setOnClickListener {
                if(checkbool2[i] == false){
                    filtersearch_field2_linearlayout[i].background = ContextCompat.getDrawable(this@FilteringSearch, R.drawable.radius_background_transparent_select)
                    intentionbtn2[i]?.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonSelect))
                    checkbool2[i] = true
                    projectfind_projectfieldbtn.add(filtersearch_field_linearlayout[i].toString())
                }else{
                    filtersearch_field2_linearlayout[i].background = ContextCompat.getDrawable(this@FilteringSearch, R.drawable.radius_background_transparent)
                    intentionbtn2[i]?.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonNoSelect))
                    checkbool2[i] = false
                    projectfind_projectfieldbtn.remove(filtersearch_field_linearlayout[i].toString())
                }
            }

        }

    }

    // 멤버 찾기 화면
    private fun findmember() {

        projectfind_stackToolTechnologyStack.clear()

        // 툴
        stackLanguage(user,2)
        membercontents()

    }


    var membercheckbool = Array(4){i->false}
    var membercheckbool2 = Array(4){i->false}
    val memberintentionbtn = arrayOfNulls<Button>(4)
    val memberintentionbtn2 = arrayOfNulls<Button>(4)

    var memberintentioncheck = false



    private fun membercontents() {

        if(memberintentioncheck  == false){
            memberintentionbtn[0] =filtersearch_page2_myappeal_textview
            memberintentionbtn[1] =filtersearch_page2_hobby_textview
            memberintentionbtn[2] =filtersearch_page2_economy_textview
            memberintentionbtn[3] =filtersearch_page2_cook_textview

            memberintentionbtn2[0] =filtersearch_page2_it_textview
            memberintentionbtn2[1] =filtersearch_page2_rest_textview
            memberintentionbtn2[2] =filtersearch_page2_health_textview
            memberintentionbtn2[3] =filtersearch_page2_holiday_textview
            check = true

        }



        // 포지션, 개발, 디자이너 툴
        // 개발자 버튼 클릭했을 때
        filtersearch_page2_developer_Button.setOnClickListener {

            filtersearch_page2_projectstack_constraintLayout.visibility = View.VISIBLE
            filtersearch_page2_projectstackdesigner_constraintLayout.visibility = View.GONE

            if(designercheck_index == 0){
                filtersearch_page2_designer_Button.background = ContextCompat.getDrawable(
                    this@FilteringSearch,
                    R.drawable.radius_background_transparent
                )
                filtersearch_page2_designer_Button.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonNoSelect))


                designer_btn = false
                memberfind_selectpositionItems.remove(filtersearch_page2_designer_Button.text.toString())
            }

            // 개발자 툴이 닫혀있을 때
            if(!developer_btn){
                filtersearch_page2_developer_Button.background = ContextCompat.getDrawable(
                    this@FilteringSearch,
                    R.drawable.radius_background_transparent_select
                )
                filtersearch_page2_developer_Button.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonSelect))

                filtersearch_page2_projectstack_constraintLayout.visibility = View.VISIBLE
                filtersearch_page2_projectstackdesigner_constraintLayout.visibility = View.GONE

                // 개발자 툴 오픈
                developertool()
                memberfind_selectpositionItems.add(filtersearch_page2_developer_Button.text.toString())
                developer_btn = true
            }else if(developer_btn && developercheck_index == 0){
                // 개발자 버튼을 눌릴 때 : 개발자 툴에 아무런 버튼 입력 없을 때 버튼 효과 빼기
                filtersearch_page2_developer_Button.background = ContextCompat.getDrawable(
                    this@FilteringSearch,
                    R.drawable.radius_background_transparent
                )
                filtersearch_page2_developer_Button.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonNoSelect))

                memberfind_selectpositionItems.remove(filtersearch_page2_developer_Button.text.toString())
                developer_btn = false
                filtersearch_page2_projectstack_constraintLayout.visibility = View.GONE
                filtersearch_page2_projectstackdesigner_constraintLayout.visibility = View.GONE
            }

        }


        // 디자이너 버튼 클릭했을 때
        filtersearch_page2_designer_Button.setOnClickListener {
            filtersearch_page2_projectstack_constraintLayout.visibility = View.GONE
            filtersearch_page2_projectstackdesigner_constraintLayout.visibility = View.VISIBLE

            if (developercheck_index == 0) {
                filtersearch_page2_developer_Button.background = ContextCompat.getDrawable(
                    this@FilteringSearch,
                    R.drawable.radius_background_transparent
                )
                filtersearch_page2_developer_Button.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonNoSelect))

                developer_btn = false
                memberfind_selectpositionItems.remove(filtersearch_page2_developer_Button.text.toString())
            }


            // 디자이너 툴이 닫혀있을 때
            if(!designer_btn){
                filtersearch_page2_designer_Button.background = ContextCompat.getDrawable(
                    this@FilteringSearch,
                    R.drawable.radius_background_transparent_select
                )
                filtersearch_page2_designer_Button.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonSelect))

                filtersearch_page2_projectstack_constraintLayout.visibility = View.GONE
                filtersearch_page2_projectstackdesigner_constraintLayout.visibility = View.VISIBLE

                // 디자이너 툴 오픈
                designertool()
                designer_btn = true
                memberfind_selectpositionItems.add( filtersearch_page2_designer_Button.text.toString())
            }else if(designer_btn && designercheck_index == 0){
                // 디자이너 버튼을 눌릴 때 : 디자이너 툴에 아무런 버튼 입력 없을 때 버튼 효과 빼기
                filtersearch_page2_designer_Button.background = ContextCompat.getDrawable(
                    this@FilteringSearch,
                    R.drawable.radius_background_transparent
                )
                filtersearch_page2_designer_Button.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonNoSelect))

                designer_btn = false
                filtersearch_page2_projectstack_constraintLayout.visibility = View.GONE
                filtersearch_page2_projectstackdesigner_constraintLayout.visibility = View.GONE
                memberfind_selectpositionItems.remove( filtersearch_page2_designer_Button.text.toString())
            }

        }

        filtersearch_page2_planner_Button.setOnClickListener{
            filtersearch_page2_projectstack_constraintLayout.visibility = View.GONE
            filtersearch_page2_projectstackdesigner_constraintLayout.visibility = View.GONE

            // 개발자, 디자이너 툴 아무런 입력이 없을 때
            if(designercheck_index == 0 || developercheck_index == 0){

                if(developercheck_index == 0){
                    filtersearch_page2_developer_Button.background = ContextCompat.getDrawable(
                        this@FilteringSearch,
                        R.drawable.radius_background_transparent
                    )
                    filtersearch_page2_developer_Button.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonNoSelect))


                    developer_btn = false
                    memberfind_selectpositionItems.remove(filtersearch_page2_developer_Button.text.toString())


                }

                if(designercheck_index == 0){
                    filtersearch_page2_designer_Button.background = ContextCompat.getDrawable(
                        this@FilteringSearch,
                        R.drawable.radius_background_transparent
                    )
                    filtersearch_page2_designer_Button.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonNoSelect))

                    designer_btn = false
                    memberfind_selectpositionItems.remove( filtersearch_page2_designer_Button.text.toString())

                }

            }


            // 기획자 버튼 닫혀있을 때
            if(!planner_btn){
                filtersearch_page2_planner_Button.background = ContextCompat.getDrawable(
                    this@FilteringSearch,
                    R.drawable.radius_background_transparent_select
                )
                filtersearch_page2_planner_Button.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonSelect))

                planner_btn = true
                memberfind_selectpositionItems.add(filtersearch_page2_planner_Button.text.toString())


            }else{  // 기획자 버튼 열려있을 때
                filtersearch_page2_planner_Button.background = ContextCompat.getDrawable(
                    this@FilteringSearch,
                    R.drawable.radius_background_transparent
                )
                filtersearch_page2_planner_Button.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonNoSelect))

                planner_btn = false
                memberfind_selectpositionItems.remove(filtersearch_page2_planner_Button.text.toString())
            }


        }


        // 프로젝트 방식

        filtersearch_page2_regions_spinner.setItems(regions)
        filtersearch_page2_regions_spinner.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            memberfind_regiontext = newItem
        }

        // 프로젝트 방식

        memberfind_onoffbtn[0] = findViewById(R.id.filtersearch_page2_offline_button)
        memberfind_onoffbtn[1] = findViewById(R.id.filtersearch_page2_online_button)
        memberfind_onoffbtn[0]?.setOnClickListener {

            memberfind_onoffbtn[0]?.let { it1 ->
                this.onClickMemberBtn(
                    it1,
                    0, 2
                )
            }
        }
        memberfind_onoffbtn[1]?.setOnClickListener {

            memberfind_onoffbtn[1]?.let { it2 ->
                this.onClickMemberBtn(
                    it2,
                    1, 2
                )
            }
        }



        // 관심 분야
        for (i in 0 until filtersearch_page2_field_linearlayout.childCount) {
            filtersearch_page2_field_linearlayout[i].setOnClickListener {
                if(membercheckbool[i] == false){
                    filtersearch_page2_field_linearlayout[i].background = ContextCompat.getDrawable(this@FilteringSearch, R.drawable.radius_background_transparent_select)
                    memberintentionbtn[i]?.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonSelect))
                    membercheckbool[i] = true
                    memberfind_projectfieldbtn.add(filtersearch_page2_field_linearlayout[i].toString())
                }else{
                    filtersearch_page2_field_linearlayout[i].background = ContextCompat.getDrawable(this@FilteringSearch, R.drawable.radius_background_transparent)
                    memberintentionbtn[i]?.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonNoSelect))
                    membercheckbool[i] = false
                    memberfind_projectfieldbtn.remove(filtersearch_page2_field_linearlayout[i].toString())
                }
            }

        }

        for (i in 0 until filtersearch_page2_field2_linearlayout.childCount) {
            filtersearch_page2_field2_linearlayout[i].setOnClickListener {
                if(membercheckbool2[i] == false){
                    filtersearch_page2_field2_linearlayout[i].background = ContextCompat.getDrawable(this@FilteringSearch, R.drawable.radius_background_transparent_select)
                    memberintentionbtn2[i]?.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonSelect))
                    membercheckbool2[i] = true
                    memberfind_projectfieldbtn.add(filtersearch_page2_field_linearlayout[i].toString())
                }else{
                    filtersearch_page2_field2_linearlayout[i].background = ContextCompat.getDrawable(this@FilteringSearch, R.drawable.radius_background_transparent)
                    memberintentionbtn2[i]?.setTextColor(ContextCompat.getColor(this@FilteringSearch,R.color.colorButtonNoSelect))
                    membercheckbool2[i] = false
                    memberfind_projectfieldbtn.remove(filtersearch_page2_field_linearlayout[i].toString())
                }
            }

        }

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
                                memberfind_stackToolTechnologyStack.add(child.text.toString())
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
                    val child: View =
                        filtersearch_projectstackdesignerbutton_constraintLayout.getChildAt(i)
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
        }else if(page == 2){
            if (location.equals("개발자")) {

                for (i in 0 until filtersearch_page2_projectstackbutton_constraintLayout.childCount) {
                    val child: View = filtersearch_page2_projectstackbutton_constraintLayout.getChildAt(i)
                    // 해당 버튼에 효과 주기
                    if (child is Button) {
                        child.background = ContextCompat.getDrawable(
                            this@FilteringSearch,
                            R.drawable.radius_button_effect
                        )

                        child.setOnClickListener {

                            if (!memberfind_stackToolTechnologyStack.contains(child.text.toString())) {
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
                                memberfind_stackToolTechnologyStack.add(child.text.toString())
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
                                memberfind_stackToolTechnologyStack.remove(child.text.toString())
                            }

                        }

                    }

                }

            } else if (location.equals("디자이너")) {
                for (i in 0 until filtersearch_page2_projectstackdesigner_constraintLayout.childCount) {
                    val child: View =
                        filtersearch_page2_projectstackdesigner_constraintLayout.getChildAt(i)
                    // 해당 버튼에 효과 주기
                    if (child is Button) {
                        child.background = ContextCompat.getDrawable(
                            this@FilteringSearch,
                            R.drawable.radius_button_effect
                        )

                        child.setOnClickListener {

                            if (!memberfind_stackToolTechnologyStack.contains(child.text.toString())) {
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
                                memberfind_stackToolTechnologyStack.add(child.text.toString())
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
                                memberfind_stackToolTechnologyStack.remove(child.text.toString())
                            }

                        }

                    }


                }
            }
        }
    }

    // FilteringSearch에서 사용된 코드 이용
    var projectcheckoffout: Int = -1
    var projectcheckexpectedduration: Int = -1
    var projectcheckexpectedfield: Int = -1
    var projectcheckoff:Int = -1

    private fun onClickProjectBtn(v: View, index: Int, btnsize: Int) {
        var id = v.id

        var btn = R.id.filtersearch_offline_button

        projectcheckoff = btnsize - 1

        // 프로젝트 방식
        if (id == R.id.filtersearch_offline_button || id == R.id.filtersearch_online_button) {


            // 선택된 상태라면 체크 취소하기
            if (index == projectcheckoffout) {

                // 오프라인 부분이면 지역부분 빼기
                if (index == 0) {
                    filtersearch_regions_textview.visibility = View.GONE
                    filtersearch_regions_linearlayout.visibility = View.GONE
                    filtersearch_regions_spinner.text = "지역"
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
                projectcheckoffout = -1
            } else {

                if (index == 0) {
                    filtersearch_regions_textview.visibility = View.VISIBLE
                    filtersearch_regions_linearlayout.visibility = View.VISIBLE

                } else {
                    filtersearch_regions_textview.visibility = View.GONE
                    filtersearch_regions_linearlayout.visibility = View.GONE
                    filtersearch_regions_spinner.text = "지역"
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


                projectfind_onoffbtn[projectcheckoff - index]?.background = ContextCompat.getDrawable(
                    this@FilteringSearch,
                    R.drawable.radius_button_effect
                )
                projectfind_onoffbtn[projectcheckoff - index]?.setTextColor(
                    ContextCompat.getColor(
                        this@FilteringSearch,
                        R.color.colorButtonNoSelect
                    )
                )

                projectfind_onofftext = projectfind_onoffbtn[index]?.text.toString()
                projectcheckoffout = index
            }

        }


    }


    // FilteringSearch에서 사용된 코드 이용
    var membercheckoffout: Int = -1
    var membercheckexpectedfield: Int = -1
    var membercheckoff:Int = -1


    private fun onClickMemberBtn(v: View, index: Int, btnsize: Int) {
        var id = v.id

        var btn = R.id.filtersearch_page2_offline_button

        membercheckoff = btnsize - 1
        // 프로젝트 방식
        if (id == R.id.filtersearch_page2_offline_button || id == R.id.filtersearch_page2_online_button) {


            // 선택된 상태라면 체크 취소하기
            if (index == membercheckoffout) {

                // 오프라인 부분이면 지역부분 빼기
                if (index == 0) {
                    filtersearch_page2_regions_textview.visibility = View.GONE
                    filtersearch_page2_regions_linearlayout.visibility = View.GONE
                    filtersearch_page2_regions_spinner.text = "지역"
                }
                memberfind_onoffbtn[index]?.background = ContextCompat.getDrawable(
                    this@FilteringSearch,
                    R.drawable.radius_button_effect
                )
                memberfind_onoffbtn[index]?.setTextColor(
                    ContextCompat.getColor(
                        this@FilteringSearch,
                        R.color.colorButtonNoSelect
                    )
                )

                // 초기화
                memberfind_onofftext = "onoff"
                membercheckoffout = -1
            } else {

                if (index == 0) {
                    filtersearch_page2_regions_textview.visibility = View.VISIBLE
                    filtersearch_page2_regions_linearlayout.visibility = View.VISIBLE

                } else {
                    filtersearch_page2_regions_textview.visibility = View.GONE
                    filtersearch_page2_regions_linearlayout.visibility = View.GONE
                    filtersearch_page2_regions_spinner.text = "지역"
                }

                memberfind_onoffbtn[index]?.background = ContextCompat.getDrawable(
                    this@FilteringSearch,
                    R.drawable.radius_background_transparent_select
                )
                memberfind_onoffbtn[index]?.setTextColor(
                    ContextCompat.getColor(
                        this@FilteringSearch,
                        R.color.colorButtonSelect
                    )
                )


                memberfind_onoffbtn[membercheckoff - index]?.background = ContextCompat.getDrawable(
                    this@FilteringSearch,
                    R.drawable.radius_button_effect
                )
                memberfind_onoffbtn[membercheckoff - index]?.setTextColor(
                    ContextCompat.getColor(
                        this@FilteringSearch,
                        R.color.colorButtonNoSelect
                    )
                )

                memberfind_onofftext = memberfind_onoffbtn[index]?.text.toString()
                membercheckoffout = index
            }

        }



    }
}

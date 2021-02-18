package com.thisteampl.jackpot.main.floating

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.marginTop
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.projectController.ProjectElement
import com.thisteampl.jackpot.main.projectController.projectAPI
import kotlinx.android.synthetic.main.activity_project_creation.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectCreation : AppCompatActivity() {


    // SignUpActivity 참고함
    // 모집 포지션, 분야를 위한 stack 선언
    private val stackToolposition = mutableListOf<String>()
    private val stackToolfield = mutableListOf<String>()
    private val stackTooldeveloper = mutableListOf<String>() // 개발자 스택
    private val stackTooldesigner = mutableListOf<String>()  // 디자이너 스택


    // 프로젝트 방식, 프로젝트 예상 기간을 위한 arrayOfNulls 선언
    private var onoffbtn = arrayOfNulls<Button>(2)
    private var durationbtn = arrayOfNulls<Button>(3)
    private var onofftext = "onoff"
    private var durationtext = "duration"

    private var page: Int = 1

    private var stacklistregions = "지역" // 지역 list 저장용


    // 사용자가 선택한 item

    private val stackToolAll = mutableListOf<String>()         // 개발자, 디자이너 스택 합치기
    private val selectpositionItems = mutableListOf<String>()    // 포지션
    private val selectedfieldItems = mutableListOf<String>()     // 분야

    private var projectapi = projectAPI.projectRetrofitService()

//    private var developerbuttons = ArrayList<Button>(10)
//    private var designerbuttons = ArrayList<Button>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_creation)

        createproject_minusbutton_imageview.setOnClickListener {
            finish()
        }


        setWriteProjectRecruitment()
    }


    // 개발자 (시간 없어 기초로 만들었습니다.)

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
            this@ProjectCreation,
            R.drawable.radius_background_transparent_select
        )
        developercheck_index++
        stackTooldeveloper.add(btn.text.toString())
    }

    private fun selectoutbtn(btn: Button){
        btn.background = ContextCompat.getDrawable(
            this@ProjectCreation,
            R.drawable.radius_background_transparent
        )
        developercheck_index--
        stackTooldeveloper.remove(btn.text.toString())
    }


    // 개발자 툴
    private fun developertool(){
        createproject_java_Button.setOnClickListener {
            if(java == false){
                selectinbtn(createproject_java_Button)
                java = true
            }else{
                java = false
                selectoutbtn(createproject_java_Button)
            }

        }

        createproject_cpluse_Button.setOnClickListener {
            if(cplus == false){
                selectinbtn(createproject_cpluse_Button)
                cplus = true
            }else{
                cplus = false
                selectoutbtn(createproject_cpluse_Button)
            }

        }

        createproject_python_Button.setOnClickListener {
            if(python == false){
                selectinbtn(createproject_python_Button)
                python = true
            }else{
                python = false
                selectoutbtn(createproject_python_Button)
            }
        }

        createproject_javascript_Button.setOnClickListener {
            if(js == false){
                selectinbtn(createproject_javascript_Button)
                js = true
            }else{
                js = false
                selectoutbtn(createproject_javascript_Button)
            }
        }

        createproject_html_css_Button.setOnClickListener {
            if(html == false){
                selectinbtn(createproject_html_css_Button)
                html = true
            }else{
                html = false
                selectoutbtn(createproject_html_css_Button)
            }
        }

        createproject_swift_Button.setOnClickListener {
            if(swift == false){
                selectinbtn(createproject_swift_Button)
                swift = true
            }else{
                swift = false
                selectoutbtn(createproject_swift_Button)
            }
        }

        createproject_spring_Button.setOnClickListener {
            if(spring == false){
                selectinbtn(createproject_spring_Button)
                spring = true
            }else{
                spring = false
                selectoutbtn(createproject_spring_Button)
            }
        }
        createproject_kotlin_Button.setOnClickListener {
            if(kotlin == false){
                selectinbtn(createproject_kotlin_Button)
                kotlin = true
            }else{
                kotlin = false
                selectoutbtn(createproject_kotlin_Button)
            }
        }
        createproject_django_Button.setOnClickListener {
            if(django == false){
                selectinbtn(createproject_django_Button)
                django = true
            }else{
                django = false
                selectoutbtn(createproject_django_Button)
            }
        }
        createproject_reactjs_Button.setOnClickListener {
            if(reactjs == false){
                selectinbtn(createproject_reactjs_Button)
                reactjs = true
            }else{
                reactjs = false
                selectoutbtn(createproject_reactjs_Button)
            }
        }
        createproject_flask_Button.setOnClickListener {
            if(flask == false){
                selectinbtn(createproject_flask_Button)
                flask = true
            }else{
                flask = false
                selectoutbtn(createproject_flask_Button)
            }
        }

    }

    // 디자이너 툴 관련 소스
    // selectindesignerbtn : 선택, selectoutdesignerbtn : 선택하지 않았을 때
    private fun selectindesignerbtn(btn: Button){
        btn.background = ContextCompat.getDrawable(
            this@ProjectCreation,
            R.drawable.radius_background_transparent_select
        )
        designercheck_index++
        stackTooldesigner.add(btn.text.toString())
    }

    private fun selectoutdesignerbtn(btn: Button){
        btn.background = ContextCompat.getDrawable(
            this@ProjectCreation,
            R.drawable.radius_background_transparent
        )
        designercheck_index--
        stackTooldesigner.remove(btn.text.toString())
    }

    private fun designertool(){
        createproject_photoshop_Button.setOnClickListener {
            if(photoshop == false){
                selectindesignerbtn(createproject_photoshop_Button)
                photoshop = true
            }else{
                photoshop = false
                selectoutdesignerbtn(createproject_photoshop_Button)
            }
        }
        createproject_illustrator_Button.setOnClickListener {
            if(illusrator == false){
                selectindesignerbtn(createproject_illustrator_Button)
                illusrator = true
            }else{
                illusrator = false
                selectoutdesignerbtn(createproject_illustrator_Button)
            }
        }
        createproject_xd_Button.setOnClickListener {
            if(xd == false){
                selectindesignerbtn(createproject_xd_Button)
                xd = true
            }else{
                xd = false
                selectoutdesignerbtn(createproject_xd_Button)
            }
        }

        createproject_figma_Button.setOnClickListener {
            if(figma == false){
                selectindesignerbtn(createproject_figma_Button)
                figma = true
            }else{
                figma = false
                selectoutdesignerbtn(createproject_figma_Button)
            }
        }

        createproject_sketch_Button.setOnClickListener {
            if(sketch == false){
                selectindesignerbtn(createproject_sketch_Button)
                sketch = true
            }else{
                sketch = false
                selectoutdesignerbtn(createproject_sketch_Button)
            }
        }

        createproject_principle_Button.setOnClickListener {
            if(principle == false){
                selectindesignerbtn(createproject_principle_Button)
                principle = true
            }else{
                principle = false
                selectoutdesignerbtn(createproject_principle_Button)
            }
        }
        createproject_protopie_Button.setOnClickListener {
            if(protopie == false){
                selectindesignerbtn(createproject_protopie_Button)
                protopie = true
            }else{
                protopie = false
                selectoutdesignerbtn(createproject_protopie_Button)
            }
        }
        createproject_after_effects_Button.setOnClickListener {
            if(after_effects == false){
                selectindesignerbtn(createproject_after_effects_Button)
                after_effects = true
            }else{
                after_effects = false
                selectoutdesignerbtn(createproject_after_effects_Button)
            }
        }
        createproject_premiere_Button.setOnClickListener {
            if(premiere == false){
                selectindesignerbtn(createproject_premiere_Button)
                premiere = true
            }else{
                premiere = false
                selectoutdesignerbtn(createproject_premiere_Button)
            }
        }
        createproject_indesign_Button.setOnClickListener {
            if(indesign== false){
                selectindesignerbtn(createproject_indesign_Button)
                indesign = true
            }else{
                indesign = false
                selectoutdesignerbtn(createproject_indesign_Button)
            }
        }
        createproject_c4d_Button.setOnClickListener {
            if(c4d == false){
                selectindesignerbtn(createproject_c4d_Button)
                c4d = true
            }else{
                c4d = false
                selectoutdesignerbtn(createproject_c4d_Button)
            }
        }
        createproject_zeplin_Button.setOnClickListener {
            if(zeplin == false){
                selectindesignerbtn(createproject_zeplin_Button)
                zeplin = true
            }else{
                zeplin = false
                selectoutdesignerbtn(createproject_zeplin_Button)
            }
        }

    }


    private fun setWriteProjectRecruitment() {

        // 1 page
        stackToolposition.clear()
        stackToolfield.clear()

        // 개발자 버튼 클릭했을 때
        createproject_developer_Button.setOnClickListener {

            createproject_projectstack_constraintLayout.visibility = View.VISIBLE
            createproject_projectstackdesigner_constraintLayout.visibility = View.GONE

            if(designercheck_index == 0){
                createproject_designer_Button.background = ContextCompat.getDrawable(
                    this@ProjectCreation,
                    R.drawable.radius_background_transparent
                )
                designer_btn = false
                stackToolposition.remove(createproject_designer_Button.text.toString())
            }

            // 개발자 툴이 닫혀있을 때
            if(!developer_btn){
                createproject_developer_Button.background = ContextCompat.getDrawable(
                    this@ProjectCreation,
                    R.drawable.radius_background_transparent_select
                )
                createproject_projectstack_constraintLayout.visibility = View.VISIBLE
                createproject_projectstackdesigner_constraintLayout.visibility = View.GONE

                // 개발자 툴 오픈
                developertool()
                stackToolposition.add(createproject_developer_Button.text.toString())
                developer_btn = true
            }else if(developer_btn && developercheck_index == 0){
                // 개발자 버튼을 눌릴 때 : 개발자 툴에 아무런 버튼 입력 없을 때 버튼 효과 빼기
                createproject_developer_Button.background = ContextCompat.getDrawable(
                    this@ProjectCreation,
                    R.drawable.radius_background_transparent
                )
                stackToolposition.remove(createproject_developer_Button.text.toString())
                developer_btn = false
                createproject_projectstack_constraintLayout.visibility = View.GONE
                createproject_projectstackdesigner_constraintLayout.visibility = View.GONE
            }

        }


        // 디자이너 버튼 클릭했을 때
        createproject_designer_Button.setOnClickListener {
            createproject_projectstack_constraintLayout.visibility = View.GONE
            createproject_projectstackdesigner_constraintLayout.visibility = View.VISIBLE

            if (developercheck_index == 0) {
                createproject_developer_Button.background = ContextCompat.getDrawable(
                    this@ProjectCreation,
                    R.drawable.radius_background_transparent
                )
                developer_btn = false
                stackToolposition.remove(createproject_developer_Button.text.toString())
            }


            // 디자이너 툴이 닫혀있을 때
            if(!designer_btn){
                createproject_designer_Button.background = ContextCompat.getDrawable(
                    this@ProjectCreation,
                    R.drawable.radius_background_transparent_select
                )
                createproject_projectstack_constraintLayout.visibility = View.GONE
                createproject_projectstackdesigner_constraintLayout.visibility = View.VISIBLE
                
                // 디자이너 툴 오픈
                designertool()
                designer_btn = true
                stackToolposition.add(createproject_designer_Button.text.toString())
            }else if(designer_btn && designercheck_index == 0){
                // 디자이너 버튼을 눌릴 때 : 디자이너 툴에 아무런 버튼 입력 없을 때 버튼 효과 빼기
                createproject_designer_Button.background = ContextCompat.getDrawable(
                    this@ProjectCreation,
                    R.drawable.radius_background_transparent
                )
                designer_btn = false
                createproject_projectstack_constraintLayout.visibility = View.GONE
                createproject_projectstackdesigner_constraintLayout.visibility = View.GONE
                stackToolposition.remove(createproject_designer_Button.text.toString())
            }

        }
        createproject_planner_Button.setOnClickListener{

//
//            // 개발자, 디자이너 툴 아무런 입력이 없을 때
//            if(designercheck_index == 0 || developercheck_index == 0){
//
//                if(developercheck_index == 0){
//                    createproject_developer_Button.background = ContextCompat.getDrawable(
//                        this@ProjectCreation,
//                        R.drawable.radius_background_transparent
//                    )
//                    developer_btn = false
//                    stackToolposition.remove(createproject_developer_Button.text.toString())
//                    createproject_projectstack_constraintLayout.visibility = View.GONE
//
//                }
//
//                if(designercheck_index == 0){
//                    createproject_designer_Button.background = ContextCompat.getDrawable(
//                        this@ProjectCreation,
//                        R.drawable.radius_background_transparent
//                    )
//                    designer_btn = false
//                    stackToolposition.remove(createproject_designer_Button.text.toString())
//                    createproject_projectstackdesigner_constraintLayout.visibility = View.GONE
//                }
//
//            }


            // 기획자 버튼 닫혀있을 때
            if(!planner_btn){
                createproject_planner_Button.background = ContextCompat.getDrawable(
                    this@ProjectCreation,
                    R.drawable.radius_background_transparent_select
                )
                planner_btn = true
                stackToolposition.add(createproject_planner_Button.text.toString())



            }else{  // 기획자 버튼 열려있을 때
                createproject_planner_Button.background = ContextCompat.getDrawable(
                    this@ProjectCreation,
                    R.drawable.radius_background_transparent
                )

                planner_btn = false
                stackToolposition.remove(createproject_planner_Button.text.toString())
            }


        }

        // 지역
        var regions = listOf(
            "서울", "경기", "인천", "대전", "광주", "울산", "세종","대구","부산","강원도",
            "충청북도","충청남도","전라북도","전라남도","경상남도","경상북도","제주도",
            "해외"
        )

        createproject_regions_spinner.setItems(regions)
        createproject_regions_spinner.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            stacklistregions = newItem
        }


        // 프로젝트 방식

        onoffbtn[0] = findViewById(R.id.projectcreate_offduration_button)
        onoffbtn[1] = findViewById(R.id.projectcreate_onduration_button)
        onoffbtn[0]?.setOnClickListener {


            onoffbtn[0]?.let { it1 ->
                this.onClickBtn(
                    it1,
                    0,2
                )
            }
        }
        onoffbtn[1]?.setOnClickListener {


            onoffbtn[1]?.let { it2 ->
                this.onClickBtn(
                    it2,
                    1,2
                )
            }
        }


        // 프로젝트 예상시간
        durationbtn[0] = findViewById(R.id.projectcreate_month_button)
        durationbtn[1] = findViewById(R.id.projectcreate_month2_button)
        durationbtn[2] = findViewById(R.id.projectcreate_month3_button)
        durationbtn[0]?.setOnClickListener {
            durationbtn[0]?.let { it1 ->
                this.onClickBtn(
                    it1,
                    0,3
                )
            }
        }
        durationbtn[1]?.setOnClickListener {
            durationbtn[1]?.let { it1 ->
                this.onClickBtn(
                    it1,
                    1,3
                )
            }
        }
        durationbtn[2]?.setOnClickListener {
            durationbtn[2]?.let { it1 ->
                this.onClickBtn(
                    it1,
                    2,3
                )
            }
        }

        // 분야 자기계발 ~ 요리
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

        // 분야 IT ~ 휴식
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


                for (i in 0..stackToolfield.size-1) {
                    // 백엔드에서는 / 를 사용할 수 없어 변환 과정
                    if (stackToolfield[i].equals("예술/창작")) {
                        stackToolfield[i] = "예술_창작"
                        Log.d("tag ","${stackToolfield[i]}")
                    }
                }


                // 사용 예정 스택, 모집 포지션, 분야
                stackToolAll.addAll(stackTooldeveloper)
                stackToolAll.addAll(stackTooldesigner)

                for(i in 0..stackToolAll.size-1){
                    if(stackToolAll[i].equals("Html/CSS")){
                        stackToolAll[i] = "Html_CSS"
                    }

                    if(stackToolAll[i].equals("React.JS")){
                        stackToolAll[i] = "React_js"
                    }
                    if(stackToolAll[i].equals("After Effects")){
                        stackToolAll[i] = "After_Effects"
                    }

                    Log.d("tag : ","${stackToolAll[i].toString()}")
                }

                createproject_minusbutton_imageview.visibility = View.GONE
                createproject_beforebutton_imageview.visibility = View.VISIBLE

                selectpositionItems.addAll(stackToolposition)
                selectedfieldItems.addAll(stackToolfield)


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
                durationtext,
                selectedfieldItems,
                onofftext,
                selectpositionItems,
                stacklistregions,
                createproject_projectdetail_edittext.text.toString(),
                stackToolAll,
                createproject_projecttitle_edittext.text.toString()
            )


            // API 작성 DB에 넘김
            projectapi?.postRecruitmentProject(recruitmentproject)
                ?.enqueue(object : retrofit2.Callback<ProjectElement> {
                    override fun onFailure(call: Call<ProjectElement>, t: Throwable) {
                        Log.d("tag : ", "error")

                    }

                    override fun onResponse(
                        call: Call<ProjectElement>,
                        response: Response<ProjectElement>
                    ) {

                        // 데이터 전달하지 못했다면
                        if(!response.isSuccessful){
                            Log.d("tag : ","실패")
                            Log.d("tag : ", "${response.code().toString()}")
                        }else{
                            Log.d("tag : ","성공")
                            Log.d("tag : ", "${response.code().toString()}")
                            ToastmakeTextPrint("프로젝트 모집글 작성 완료되었습니다.")
                        }
                    }
                })

            finish()


        }


    }

    // 모집 포지션, 사용 예정 스택, 프로젝트 방식 지역, 프로젝트 예상기간, 분야 선택 안되었을 때
    private fun checkPageButton(): Boolean {
        if (stackToolposition.size == 0) {
            ToastmakeTextPrint("모집 포지션 선택해주세요."); return false
        }
        if(stackTooldeveloper.size == 0 && stackTooldesigner.size == 0){
            ToastmakeTextPrint("툴 선택해주세요."); return false
        }
        if (onofftext.equals("onoff")) {
            ToastmakeTextPrint("프로젝트 방식을 선택해주세요."); return false
        }
        if (stacklistregions.equals("지역")&&onofftext.equals("오프라인")) {
            ToastmakeTextPrint("$stacklistregions 지역을 입력해주세요."); return false
        }
        if (durationtext.equals("duration")) {
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


    var checkoffout:Int = -1
    var checkexpectedduration:Int = -1

    // 버튼 둘 중 하나만 선택되게 하기 위해 사용
    private fun onClickBtn(v: View, index: Int,btnsize: Int) {
        var id = v.id


        var checkoff:Int = btnsize-1

        // 프로젝트 방식
        if (id == R.id.projectcreate_offduration_button || id == R.id.projectcreate_onduration_button) {


            // 선택된 상태라면 체크 취소하기
            if(index==checkoffout){

                // 오프라인 부분이면 지역부분 빼기
                if(index==0){
                    createproject_regions_textview.visibility = View.GONE
                    projectcreate_regions_linearlayout.visibility = View.GONE
                    createproject_regions_spinner.text = "지역"
                    stacklistregions = "지역"
                }
                onoffbtn[index]?.background = ContextCompat.getDrawable(
                    this@ProjectCreation,
                    R.drawable.radius_button_effect
                )

                // 초기화
                onofftext = "onoff"
                checkoffout = -1
            }
            else{

                if(index==0){
                    createproject_regions_textview.visibility = View.VISIBLE
                    projectcreate_regions_linearlayout.visibility = View.VISIBLE

                }else{
                    createproject_regions_textview.visibility = View.GONE
                    projectcreate_regions_linearlayout.visibility = View.GONE
                    createproject_regions_spinner.text = "지역"
                    stacklistregions = "지역"
                }

                onoffbtn[index]?.background = ContextCompat.getDrawable(
                    this@ProjectCreation,
                    R.drawable.radius_background_transparent_select
                )
                onoffbtn[checkoff-index]?.background = ContextCompat.getDrawable(
                    this@ProjectCreation,
                    R.drawable.radius_button_effect
                )
                onofftext = onoffbtn[index]?.text.toString()
                checkoffout = index
            }

        }


        // 프로젝트 예상 기간
        if (id == R.id.projectcreate_month_button || id == R.id.projectcreate_month2_button
            || id == R.id.projectcreate_month3_button
        ) {
            for (i in 0..2) {

                // 1. 이미 버튼 on 되어 있는 곳에 한 번 더 눌렸을 때 off
                // 2. 해당 자리 버튼일 때 버튼 on
                // 3. 이외의 버튼(버튼 적용되는 곳 이외) off
                if(checkexpectedduration == i){
                    durationbtn[i]?.background = ContextCompat.getDrawable(
                        this@ProjectCreation,
                        R.drawable.radius_button_effect
                    )
                    checkexpectedduration = -1
                } else if(i == index) {
                    durationbtn[i]?.background = ContextCompat.getDrawable(
                        this@ProjectCreation,
                        R.drawable.radius_background_transparent_select
                    )
                    checkexpectedduration = i
                    durationtext = durationbtn[i]?.text.toString()
                } else {
                    durationbtn[i]?.background = ContextCompat.getDrawable(
                        this@ProjectCreation,
                        R.drawable.radius_button_effect
                    )
                }
            }
        }
    }


}

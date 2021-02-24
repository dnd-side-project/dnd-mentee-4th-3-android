package com.thisteampl.jackpot.main.projectController

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.floating.ProjectCreationElement
import com.thisteampl.jackpot.main.projectdetail.ProjectViewDetail
import com.thisteampl.jackpot.main.userController.CheckResponse
import kotlinx.android.synthetic.main.activity_project_creation.*
import kotlinx.android.synthetic.main.activity_project_modification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectModification : AppCompatActivity() {

    
    // Project 모집글 수정 page

    // 모집 포지션, 분야를 위한 stack 선언
    private val stackToolposition = mutableListOf<String>()

    private val stackTooldeveloper = mutableListOf<String>() // 개발자 스택
    private val stackTooldesigner = mutableListOf<String>()  // 디자이너 스택


    // 프로젝트 방식, 프로젝트 예상 기간을 위한 arrayOfNulls 선언
    private var onoffbtn = arrayOfNulls<Button>(2)
    private var durationbtn = arrayOfNulls<Button>(3)
    private var projectfieldbtn = arrayOfNulls<Button>(8)
    private var onofftext = "onoff"
    private var durationtext = "duration"
    private var projectfieldtext = "field"

    private var page: Int = 1

    private var regiontext = "지역" // 지역 list 저장용



    // 사용자가 선택한 item

    private val stackToolAll = mutableListOf<String>()         // 개발자, 디자이너 스택 합치기
    private val selectpositionItems = mutableListOf<String>()    // 포지션

    private var projectapi = projectAPI.projectRetrofitService()


    var id : Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_modification)

        projectmodification_minusbutton_button.setOnClickListener {
            finish()
        }


        // id 받는 곳
        id = intent.getLongExtra("id",0)



        setModfiyProject()

    }


    var developerbool = Array(11) { i -> false }
    var designerbool = Array(12) { i -> false }
    var developer = arrayOfNulls<Button>(11)
    var designer = arrayOfNulls<Button>(12)
    var developercheck_index = 0
    var designercheck_index = 0
    var developer_btn:Boolean = false
    var designer_btn:Boolean = false
    var planner_btn:Boolean = false



    var interestbool = Array(4) { i -> false }
    var interestbool2 = Array(4) { i -> false }
    val interestbtn = arrayOfNulls<Button>(4)
    val interestbtn2 = arrayOfNulls<Button>(4)

    var intentioncheck = false


    private fun setModfiyProject() {
        stackToolposition.clear()

        if (intentioncheck == false) {

            developer[0] = projectmodification_java_Button
            developer[1] = projectmodification_cpluse_Button
            developer[2] = projectmodification_python_Button
            developer[3] = projectmodification_javascript_Button
            developer[4] = projectmodification_django_Button
            developer[5] = projectmodification_html_css_Button
            developer[6] = projectmodification_swift_Button
            developer[7] = projectmodification_kotlin_Button
            developer[8] = projectmodification_spring_Button
            developer[9] = projectmodification_flask_Button
            developer[10] = projectmodification_reactjs_Button

            designer[0] = projectmodification_photoshop_Button
            designer[1] = projectmodification_illustrator_Button
            designer[2] = projectmodification_xd_Button
            designer[3] = projectmodification_sketch_Button
            designer[4] = projectmodification_figma_Button
            designer[5] = projectmodification_Principle_Button
            designer[6] = projectmodification_protopie_Button
            designer[7] = projectmodification_after_effects_Button
            designer[8] = projectmodification_premiere_Button
            designer[9] = projectmodification_Indesign_Button
            designer[10] = projectmodification_c4d_Button
            designer[11] = projectmodification_Zeplin_Button

            intentioncheck = true
        }




        // 포지션, 개발, 디자이너 툴
        // 개발자 버튼 클릭했을 때
        projectmodification_developer_Button.setOnClickListener {

            projectmodification_projectstack_constraintLayout.visibility = View.VISIBLE
            projectmodification_projectstackdesigner_constraintLayout.visibility = View.GONE

            if(designercheck_index == 0){
                projectmodification_designer_Button.background = ContextCompat.getDrawable(
                    this@ProjectModification,
                    R.drawable.radius_background_transparent
                )
                projectmodification_designer_Button.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonNoSelect))


                designer_btn = false

                stackToolposition.remove(projectmodification_developer_Button.text.toString())
            }

            // 개발자 툴이 닫혀있을 때
            if(!developer_btn){
                projectmodification_developer_Button.background = ContextCompat.getDrawable(
                    this@ProjectModification,
                    R.drawable.radius_background_transparent_select
                )
                projectmodification_developer_Button.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonSelect))

                projectmodification_projectstack_constraintLayout.visibility = View.VISIBLE
                projectmodification_projectstackdesigner_constraintLayout.visibility = View.GONE

                // 개발자 툴 오픈
                for (i in 0 until projectmodification_projectstackbutton_constraintLayout.childCount) {
                    projectmodification_projectstackbutton_constraintLayout[i].setOnClickListener {
                        if(developerbool[i] == false){
                            projectmodification_projectstackbutton_constraintLayout[i].background = ContextCompat.getDrawable(this@ProjectModification, R.drawable.radius_background_transparent_select)
                            developer[i]?.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonSelect))
                            developerbool[i] = true
                            developercheck_index++
                            Log.d("tag","확인 버튼 클릭")
                            stackTooldeveloper.add(developer[i]?.text.toString())
                        }else{
                            projectmodification_projectstackbutton_constraintLayout[i].background = ContextCompat.getDrawable(this@ProjectModification, R.drawable.radius_background_transparent)
                            developer[i]?.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonNoSelect))
                            developerbool[i] = false
                            developercheck_index--
                            Log.d("tag","취소 버튼 클릭")
                            stackTooldeveloper.remove(developer[i]?.text.toString())
                        }
                    }

                }
                stackToolposition.add(projectmodification_developer_Button.text.toString())
                developer_btn = true
            }else if(developer_btn && developercheck_index == 0){
                // 개발자 버튼을 눌릴 때 : 개발자 툴에 아무런 버튼 입력 없을 때 버튼 효과 빼기
                projectmodification_developer_Button.background = ContextCompat.getDrawable(
                    this@ProjectModification,
                    R.drawable.radius_background_transparent
                )
                Log.d("tag","개발자 버튼 빼기")
                projectmodification_developer_Button.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonNoSelect))

                stackToolposition.remove(projectmodification_developer_Button.text.toString())
                developer_btn = false
                projectmodification_projectstack_constraintLayout.visibility = View.GONE
                projectmodification_projectstackdesigner_constraintLayout.visibility = View.GONE
            }

        }


        // 디자이너 버튼 클릭했을 때
        projectmodification_designer_Button.setOnClickListener {
            projectmodification_projectstack_constraintLayout.visibility = View.GONE
            projectmodification_projectstackdesigner_constraintLayout.visibility = View.VISIBLE
            Log.d("tag","디자이너 버튼 클릭")
            if (developercheck_index == 0) {
                projectmodification_developer_Button.background = ContextCompat.getDrawable(
                    this@ProjectModification,
                    R.drawable.radius_background_transparent
                )
                projectmodification_developer_Button.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonNoSelect))

                developer_btn = false
                stackToolposition.remove(projectmodification_developer_Button.text.toString())
            }


            // 디자이너 툴이 닫혀있을 때
            if(!designer_btn){
                projectmodification_designer_Button.background = ContextCompat.getDrawable(
                    this@ProjectModification,
                    R.drawable.radius_background_transparent_select
                )
                projectmodification_designer_Button.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonSelect))

                projectmodification_projectstack_constraintLayout.visibility = View.GONE
                projectmodification_projectstackdesigner_constraintLayout.visibility = View.VISIBLE

                // 디자이너 툴 오픈

                for (i in 0 until projectmodification_projectstackdesignerbutton_constraintLayout.childCount) {
                    projectmodification_projectstackdesignerbutton_constraintLayout[i].setOnClickListener {
                        if(designerbool[i] == false){
                            projectmodification_projectstackdesignerbutton_constraintLayout[i].background = ContextCompat.getDrawable(this@ProjectModification, R.drawable.radius_background_transparent_select)
                            designer[i]?.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonSelect))
                            designerbool[i] = true
                            designercheck_index++
                            Log.d("tag","확인 버튼 클릭")
                            stackTooldesigner.add(designer[i]?.text.toString())
                        }else{
                            projectmodification_projectstackdesignerbutton_constraintLayout[i].background = ContextCompat.getDrawable(this@ProjectModification, R.drawable.radius_background_transparent)
                            designer[i]?.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonNoSelect))
                            designerbool[i] = false
                            designercheck_index--
                            Log.d("tag","취소 버튼 클릭")
                            stackTooldesigner.remove(designer[i]?.text.toString())
                        }
                    }

                }

                designer_btn = true
                stackToolposition.add( projectmodification_designer_Button.text.toString())
            }else if(designer_btn && designercheck_index == 0){
                // 디자이너 버튼을 눌릴 때 : 디자이너 툴에 아무런 버튼 입력 없을 때 버튼 효과 빼기
                projectmodification_designer_Button.background = ContextCompat.getDrawable(
                    this@ProjectModification,
                    R.drawable.radius_background_transparent
                )
                projectmodification_designer_Button.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonNoSelect))

                designer_btn = false
                projectmodification_projectstack_constraintLayout.visibility = View.GONE
                projectmodification_projectstackdesigner_constraintLayout.visibility = View.GONE
                stackToolposition.remove( projectmodification_designer_Button.text.toString())
            }

        }

        projectmodification_planner_Button.setOnClickListener{
            projectmodification_projectstack_constraintLayout.visibility = View.GONE
            projectmodification_projectstackdesigner_constraintLayout.visibility = View.GONE

            // 개발자, 디자이너 툴 아무런 입력이 없을 때
            if(designercheck_index == 0 || developercheck_index == 0){

                if(developercheck_index == 0){
                    projectmodification_developer_Button.background = ContextCompat.getDrawable(
                        this@ProjectModification,
                        R.drawable.radius_background_transparent
                    )
                    projectmodification_developer_Button.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonNoSelect))


                    developer_btn = false
                    stackToolposition.remove(projectmodification_developer_Button.text.toString())


                }

                if(designercheck_index == 0){
                    projectmodification_designer_Button.background = ContextCompat.getDrawable(
                        this@ProjectModification,
                        R.drawable.radius_background_transparent
                    )
                    projectmodification_designer_Button.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonNoSelect))

                    designer_btn = false
                    stackToolposition.remove( projectmodification_designer_Button.text.toString())

                }

            }


            // 기획자 버튼 닫혀있을 때
            if(!planner_btn){
                projectmodification_planner_Button.background = ContextCompat.getDrawable(
                    this@ProjectModification,
                    R.drawable.radius_background_transparent_select
                )
                projectmodification_planner_Button.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonSelect))

                planner_btn = true
                stackToolposition.add(projectmodification_planner_Button.text.toString())


            }else{  // 기획자 버튼 열려있을 때
                projectmodification_planner_Button.background = ContextCompat.getDrawable(
                    this@ProjectModification,
                    R.drawable.radius_background_transparent
                )
                projectmodification_planner_Button.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonNoSelect))

                planner_btn = false
                stackToolposition.remove(projectmodification_planner_Button.text.toString())
            }


        }



        //


        // 지역
        var regions = listOf(
            "서울", "경기", "인천", "대전", "광주", "울산", "세종","대구","부산","강원도",
            "충청북도","충청남도","전라북도","전라남도","경상남도","경상북도","제주도",
            "해외"
        )

        projectmodification_regions_spinner.setItems(regions)
        projectmodification_regions_spinner.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            regiontext = newItem
        }


        // 프로젝트 방식

        onoffbtn[0] = findViewById(R.id.projectmodification_offline_button)
        onoffbtn[1] = findViewById(R.id.projectmodification_online_button)
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
        durationbtn[0] = findViewById(R.id.projectmodification_month_button)
        durationbtn[1] = findViewById(R.id.projectmodification_month2_button)
        durationbtn[2] = findViewById(R.id.projectmodification_month3_button)
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

        // 분야
        projectfieldbtn[0] = findViewById(R.id.projectmodification_selfdeveploer_textview)
        projectfieldbtn[1] = findViewById(R.id.projectmodification_hobby_textview)
        projectfieldbtn[2] = findViewById(R.id.projectmodification_economy_textview)
        projectfieldbtn[3] = findViewById(R.id.projectmodification_cook_textview)
        projectfieldbtn[4] = findViewById(R.id.projectmodification_it_textview)
        projectfieldbtn[5] = findViewById(R.id.projectmodification_rest_textview)
        projectfieldbtn[6] = findViewById(R.id.projectmodification_health_textview)
        projectfieldbtn[7] = findViewById(R.id.projectmodification_holiday_textview)


        projectfieldbtn[0]?.setOnClickListener{
            projectfieldbtn[0]?.let {it1->
                this.onClickBtn(
                    it1,0,8
                )
            }
        }

        projectfieldbtn[1]?.setOnClickListener{
            projectfieldbtn[1]?.let {it1->
                this.onClickBtn(
                    it1,1,8
                )
            }
        }

        projectfieldbtn[2]?.setOnClickListener{
            projectfieldbtn[2]?.let {it1->
                this.onClickBtn(
                    it1,2,8
                )
            }
        }
        projectfieldbtn[3]?.setOnClickListener{
            projectfieldbtn[3]?.let {it1->
                this.onClickBtn(
                    it1,3,8
                )
            }
        }
        projectfieldbtn[4]?.setOnClickListener{
            projectfieldbtn[4]?.let {it1->
                this.onClickBtn(
                    it1,4,8
                )
            }
        }
        projectfieldbtn[5]?.setOnClickListener{
            projectfieldbtn[5]?.let {it1->
                this.onClickBtn(
                    it1,5,8
                )
            }
        }
        projectfieldbtn[6]?.setOnClickListener{
            projectfieldbtn[6]?.let {it1->
                this.onClickBtn(
                    it1,6,8
                )
            }
        }
        projectfieldbtn[7]?.setOnClickListener{
            projectfieldbtn[7]?.let {it1->
                this.onClickBtn(
                    it1,7,8
                )
            }
        }



        // 버튼 눌렸을 때 1 page, 2 page 구분
        projectmodification_writerecruitment_button.setOnClickListener {
            if (checkPageButton()) {
                page = 2
                projectmodification_write_recruitment_article_constraintlayout.visibility =
                    View.GONE
                projectmodification_write_recruitment_article2_constraintlayout.visibility =
                    View.VISIBLE


                projectmodification_minusbutton_button.visibility = View.GONE
                projectmodification_beforebutton_button.visibility = View.VISIBLE

                // 이전 page로 이동하기 위해 뒤로가기 버튼
                projectmodification_beforebutton_button.setOnClickListener {

                    projectmodification_write_recruitment_article_constraintlayout.visibility =
                        View.VISIBLE
                    projectmodification_write_recruitment_article2_constraintlayout.visibility =
                        View.GONE
                    projectmodification_minusbutton_button.visibility = View.VISIBLE
                    projectmodification_beforebutton_button.visibility = View.GONE

                    projectmodification_line2_button.background = ContextCompat.getDrawable(
                        this@ProjectModification,
                        R.drawable.page_line_background
                    )

                    page = 1
                    projectmodification_page_textview.text = "$page  /  2"
                }



                projectmodification_line2_button.background = ContextCompat.getDrawable(
                    this@ProjectModification,
                    R.drawable.page_line_background_select
                )

                SelectPage2()

            }
            projectmodification_page_textview.text = "$page  /  2"
        }

    }




    // Page2를 선택했을 때 (마지막 page)
    private fun SelectPage2() {

        projectmodification_submitrecruitment_button.setOnClickListener {

            // 사용 예정 스택, 모집 포지션, 분야
            stackToolAll.addAll(stackTooldeveloper)
            stackToolAll.addAll(stackTooldesigner)


            for(i in 0..stackToolAll.size-1){
                if(stackToolAll[i] == "Html/CSS"){
                    stackToolAll[i] = "Html_CSS"
                }

                if(stackToolAll[i] == "React.JS"){
                    stackToolAll[i] = "React_js"
                }
                if(stackToolAll[i] == "After Effects"){
                    stackToolAll[i] = "After_Effects"
                }
                if(stackToolAll[i] == "C++"){
                    stackToolAll[i] = "Cplus"
                }
                if(stackToolAll[i] == "FLASK") stackToolAll[i] = "Flask"


            }

            if(projectfieldtext.equals("예술/창작")){
                projectfieldtext = "예술_창작"
            }

            selectpositionItems.addAll(stackToolposition)


            var recruitmentproject = ProjectCreationElement(
                durationtext,
                projectfieldtext,
                onofftext,
                selectpositionItems,
                regiontext,
                projectmodification_projectdetail_edittext.text.toString(),
                stackToolAll,
                projectmodification_projecttitle_edittext.text.toString()
            )


            // API 작성 DB에 넘김
            projectapi?.getProjectModify(id,recruitmentproject)
                ?.enqueue(object : Callback<CheckResponse> {
                    override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                        Log.d("tag : ", "error : "+t.localizedMessage)

                    }

                    override fun onResponse(
                        call: Call<CheckResponse>,
                        response: Response<CheckResponse>
                    ) {

                        // 데이터 전달하지 못했다면
                        if(response.isSuccessful){
                            ToastmakeTextPrint("프로젝트 모집글 수정 완료 되었습니다.")
                            Log.d("tag","결과 : ${response.code().toString()}")
                        }else{
                            ToastmakeTextPrint("프로젝트 모집글 수정 완료되지 않았습니다.")
                            Log.d("tag","${response.code().toString()}")
                            Log.e("tag","onFailure" + response.message())
                        }
                    }
                })


            // ProjectViewDetail 되돌아감
            val intent = Intent(baseContext, ProjectViewDetail::class.java).putExtra("id", id)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
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
        if (regiontext.equals("지역")&&onofftext.equals("오프라인")) {
            ToastmakeTextPrint("지역을 입력해주세요."); return false
        }
        if (durationtext.equals("duration")) {
            ToastmakeTextPrint("프로젝트 예상 기간을 선택해주세요."); return false
        }
        if (projectfieldtext.equals("field")) {
            ToastmakeTextPrint("분야를 선택해주세요."); return false
        }

        return true
    }


    private fun ToastmakeTextPrint(word: String) {
        Toast.makeText(this, word, Toast.LENGTH_SHORT).show()
    }


    var checkoffout:Int = -1
    var checkexpectedduration:Int = -1
    var checkexpectedfield:Int = -1

    // 버튼 둘 중 하나만 선택되게 하기 위해 사용
    private fun onClickBtn(v: View, index: Int,btnsize: Int) {
        var id = v.id



        var checkoff:Int = btnsize-1

        // 프로젝트 방식
        if (id == R.id.projectmodification_offline_button || id == R.id.projectmodification_online_button) {


            // 선택된 상태라면 체크 취소하기
            if(index==checkoffout){

                // 오프라인 부분이면 지역부분 빼기
                if(index==0){
                    projectmodification_regions_textview.visibility = View.GONE
                    projectmodification_regions_linearlayout.visibility = View.GONE
                    projectmodification_regions_spinner.text = "지역"
                    regiontext = "지역"
                }
                onoffbtn[index]?.background = ContextCompat.getDrawable(
                    this@ProjectModification,
                    R.drawable.radius_button_effect
                )
                onoffbtn[index]?.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonNoSelect))

                // 초기화
                onofftext = "onoff"
                checkoffout = -1
            }
            else{

                if(index==0){
                    projectmodification_regions_textview.visibility = View.VISIBLE
                    projectmodification_regions_linearlayout.visibility = View.VISIBLE

                }else{
                    projectmodification_regions_textview.visibility = View.GONE
                    projectmodification_regions_linearlayout.visibility = View.GONE
                    projectmodification_regions_spinner.text = "지역"
                    regiontext = "지역"
                }

                onoffbtn[index]?.background = ContextCompat.getDrawable(
                    this@ProjectModification,
                    R.drawable.radius_background_transparent_select
                )
                onoffbtn[index]?.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonSelect))


                onoffbtn[checkoff-index]?.background = ContextCompat.getDrawable(
                    this@ProjectModification,
                    R.drawable.radius_button_effect
                )
                onoffbtn[checkoff-index]?.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonNoSelect))

                onofftext = onoffbtn[index]?.text.toString()
                checkoffout = index
            }

        }


        // 프로젝트 예상 기간
        if (id == R.id.projectmodification_month_button || id == R.id.projectmodification_month2_button
            || id == R.id.projectmodification_month3_button
        ) {
            for (i in 0..2) {

                // 1. 이미 버튼 on 되어 있는 곳에 한 번 더 눌렸을 때 off
                // 2. 해당 자리 버튼일 때 버튼 on
                // 3. 이외의 버튼(버튼 적용되는 곳 이외) off
                if(checkexpectedduration == i){
                    durationbtn[i]?.background = ContextCompat.getDrawable(
                        this@ProjectModification,
                        R.drawable.radius_button_effect
                    )
                    durationbtn[i]?.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonNoSelect))

                    checkexpectedduration = -1
                } else if(i == index) {
                    durationbtn[i]?.background = ContextCompat.getDrawable(
                        this@ProjectModification,
                        R.drawable.radius_background_transparent_select
                    )
                    durationbtn[i]?.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonSelect))

                    checkexpectedduration = i
                    durationtext = durationbtn[i]?.text.toString()
                } else {
                    durationbtn[i]?.background = ContextCompat.getDrawable(
                        this@ProjectModification,
                        R.drawable.radius_button_effect
                    )
                    durationbtn[i]?.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonNoSelect))
                }
            }
        }


        // 분야
        if (id == R.id.projectmodification_selfdeveploer_textview || id == R.id.projectmodification_hobby_textview
            || id == R.id.projectmodification_economy_textview || id == R.id.projectmodification_cook_textview
            || id == R.id.projectmodification_it_textview || id == R.id.projectmodification_rest_textview
            || id == R.id.projectmodification_health_textview || id == R.id.projectmodification_holiday_textview
        ) {
            for (i in 0..7) {

                // 1. 이미 버튼 on 되어 있는 곳에 한 번 더 눌렸을 때 off
                // 2. 해당 자리 버튼일 때 버튼 on
                // 3. 이외의 버튼(버튼 적용되는 곳 이외) off
                if(checkexpectedfield == i){
                    projectfieldbtn[i]?.background = ContextCompat.getDrawable(
                        this@ProjectModification,
                        R.drawable.radius_button_effect
                    )
                    projectfieldbtn[i]?.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonNoSelect))

                    checkexpectedfield = -1
                } else if(i == index) {
                    projectfieldbtn[i]?.background = ContextCompat.getDrawable(
                        this@ProjectModification,
                        R.drawable.radius_background_transparent_select
                    )
                    projectfieldbtn[i]?.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonSelect))


                    checkexpectedfield = i
                    projectfieldtext = projectfieldbtn[i]?.text.toString()
                } else {
                    projectfieldbtn[i]?.background = ContextCompat.getDrawable(
                        this@ProjectModification,
                        R.drawable.radius_button_effect
                    )
                    projectfieldbtn[i]?.setTextColor(ContextCompat.getColor(this@ProjectModification,R.color.colorButtonNoSelect))

                }
            }
        }
    }

}
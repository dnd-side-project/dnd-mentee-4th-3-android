package com.thisteampl.jackpot.main.projectdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.common.GlobalApplication
import com.thisteampl.jackpot.main.projectController.CheckProject
import com.thisteampl.jackpot.main.projectController.PostComment
import com.thisteampl.jackpot.main.projectController.ProjectModification
import com.thisteampl.jackpot.main.projectController.projectAPI
import com.thisteampl.jackpot.main.userController.CheckMyProfile
import com.thisteampl.jackpot.main.userController.CheckProfile
import com.thisteampl.jackpot.main.userController.CheckResponse
import com.thisteampl.jackpot.main.userController.userAPI
import com.thisteampl.jackpot.main.userpage.MyPageActivity
import com.thisteampl.jackpot.main.userpage.MyProject
import com.thisteampl.jackpot.main.userpage.ProfileActivity
import kotlinx.android.synthetic.main.activity_my_page.*
import kotlinx.android.synthetic.main.activity_project_view_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectViewDetail : AppCompatActivity() {


    private val projectApi = projectAPI.create()
    private var projectID = 0
    private val userApi = userAPI.create()
    private var checkMyProject = false
    private var watcherName = "NO_NAME_NEED_INITIALIZE"
    lateinit var mPrjCommentAdapter: ProjectCommentAdapter
    private var mMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        isMyProject() // 내 프로젝트인지 확인
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_view_detail)

        projectID = intent.getLongExtra("id", 0).toInt()
        if(projectID == 0) {
            finish()
        }




        //댓글 리사이클러뷰 어댑터 설정
        mPrjCommentAdapter = ProjectCommentAdapter()
        project_detail_comment_recyclerview.adapter = mPrjCommentAdapter
        project_detail_comment_recyclerview.layoutManager = LinearLayoutManager(this)

        getProject(projectID)

        setSupportActionBar(project_detail_toolbar) // 기본액션바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 제목 없애기
    }

    private fun setUpView() {
        if (GlobalApplication.prefs.getString("token", "NO_TOKEN") == "NO_TOKEN") {
            project_detail_comment_edittext.hint = "로그인한 유저만 댓글을 달 수 있습니다."
            project_detail_comment_input_button.isEnabled = false
            project_detail_comment_edittext.isEnabled = false
            project_detail_project_scrap_button.isEnabled = false
            project_detail_project_register_button.isEnabled = false
        }

        if(checkMyProject) {
            project_detail_project_scrap_button.visibility = View.GONE
            project_detail_project_register_button.visibility = View.GONE
            project_detail_watch_applicant_button.visibility = View.VISIBLE
        }

        project_detail_back_button.setOnClickListener { super.onBackPressed() }

        //댓글 입력 완료 버튼
        project_detail_comment_input_button.setOnClickListener {
            if(project_detail_comment_edittext.text.toString().trim().length < 5) {
                Toast.makeText(this, "댓글은 최소 5자 이상 적어주세요.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                var body = project_detail_comment_edittext.text.toString()
                val privacy = !project_detail_comment_privacy_checkbox.isChecked
                //비밀글로 작성이므로 체크 안돼야 true(open) 체크 시 false(close)
                projectApi?.postComment(PostComment(body, privacy, projectID.toLong()))?.enqueue(object : Callback<CheckResponse> {
                    override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                        // userAPI에서 타입이나 이름 안맞췄을때
                        Log.e("tag ", "onFailure" + t.localizedMessage)
                    }

                    override fun onResponse(
                        call: Call<CheckResponse>,
                        response: Response<CheckResponse>
                    ) {
                        if(response.code().toString() == "200") {
                            Toast.makeText(baseContext, "댓글이 작성되었습니다.", Toast.LENGTH_SHORT)
                                .show()
                            finish()
                            val intent = Intent(baseContext, ProjectViewDetail::class.java).putExtra("id", projectID.toLong())
                            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        } else {
                            Toast.makeText(baseContext, "댓글 작성에 실패했습니다.\n에러 코드 : " + response.code() + "\n" + response.body().toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                })
            }
        }
    }

    //내 프로젝트일 경우 메뉴바 생성
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(!checkMyProject) {return false}
        val inflater = menuInflater
        inflater.inflate(R.menu.project_menu, menu)
        mMenu = menu
        return true
    }

    // 메뉴바 선택시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(!checkMyProject) {return false}
        when(item.itemId) {
            R.id.project_detail_menu -> {
                //누를 때 프로젝트가 어떤 상태인지에 따라 나오는 버튼이 달라짐
                when (project_detail_status_text.text) {
                    "모집중" -> {
                        mMenu?.findItem(R.id.project_detail_change_recruit_menu)?.isVisible = false
                        mMenu?.findItem(R.id.project_detail_change_complete_menu)?.isVisible = false
                        mMenu?.findItem(R.id.project_detail_change_progress_menu)?.isVisible = true
                    }
                    "진행중" -> {
                        mMenu?.findItem(R.id.project_detail_change_recruit_menu)?.isVisible = true
                        mMenu?.findItem(R.id.project_detail_change_complete_menu)?.isVisible = true
                        mMenu?.findItem(R.id.project_detail_change_progress_menu)?.isVisible = false
                    }
                    else -> { // 완료 상태
                        mMenu?.findItem(R.id.project_detail_change_recruit_menu)?.isVisible = false
                        mMenu?.findItem(R.id.project_detail_change_complete_menu)?.isVisible = false
                        mMenu?.findItem(R.id.project_detail_change_progress_menu)?.isVisible = false
                        mMenu?.findItem(R.id.project_detail_delete_menu)?.isVisible = false
                        mMenu?.findItem(R.id.project_detail_edit_menu)?.isVisible = false
                    }
                }
            }
            R.id.project_detail_delete_menu -> {
                //프로젝트 삭제버튼
                deleteProject(projectID.toLong())
            }
            R.id.project_detail_edit_menu -> {
                //프로젝트 수정버튼
                val intent = Intent(baseContext, ProjectModification::class.java).putExtra("id",projectID.toLong())
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
            R.id.project_detail_change_recruit_menu -> {
                //프로젝트 모집 중 버튼
                projectStatusChange(projectID.toLong(), "모집중")
            }
            R.id.project_detail_change_progress_menu -> {
                //프로젝트 진행 중 버튼
                projectStatusChange(projectID.toLong(), "진행중")
            }
            R.id.project_detail_change_complete_menu -> {
                //프로젝트 완료버튼
                projectStatusChange(projectID.toLong(), "완료")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //프로젝트 정보를 가져온다.
    private fun getProject(id: Int) {
        projectApi?.getprojectsID(id)?.enqueue(object : Callback<CheckProject> {
            override fun onFailure(call: Call<CheckProject>, t: Throwable) {
                // userAPI에서 타입이나 이름 안맞췄을때
                Log.e("tag ", "onFailure" + t.localizedMessage)
            }

            override fun onResponse(
                call: Call<CheckProject>,
                response: Response<CheckProject>
            ) {
                if(response.code().toString() == "200") {
                    response.body()?.result?.userIndex?.let { getWriterProfile(it) }
                    project_detail_name_text.text = response.body()?.result?.title
                    project_detail_introduce_text.text = response.body()?.result?.shortDesc
                    project_detail_status_text.text = response.body()?.result?.status
                    if(response.body()?.result?.online == "온라인") {
                        project_detail_region_text.text = "온라인"
                    } else {
                        project_detail_region_text.text = response.body()?.result?.region
                    }
                    var dateText = response.body()?.result?.
                    createdDateTime?.substring(0, 10)
                    project_detail_date_text.text = dateText

                    //댓글 리사이클러뷰에 추가
                    for(comment in response.body()!!.result.comments) {
                        mPrjCommentAdapter.items.add(ProjectDetailComment
                            (projectID.toLong(), checkMyProject, comment.id ,comment.authorPosition, comment.authorName, watcherName, comment.body, comment.date, comment.emoticon, comment.privacy))
                    }
                    mPrjCommentAdapter.notifyDataSetChanged()

                    //분야에 따른 대표이미지 설정
                    when (response.body()?.result?.interest) {
                        "IT" -> {project_detail_kind_icon.setImageResource(R.drawable.field_it)}
                        "예술_창작" -> {project_detail_kind_icon.setImageResource(R.drawable.field_art)}
                        "건강" -> {project_detail_kind_icon.setImageResource(R.drawable.field_health)}
                        "요리" -> {project_detail_kind_icon.setImageResource(R.drawable.field_cook)}
                        "취미" -> {project_detail_kind_icon.setImageResource(R.drawable.field_hobby)}
                        "휴식" -> {project_detail_kind_icon.setImageResource(R.drawable.field_repose)}
                        "자기계발" -> {project_detail_kind_icon.setImageResource(R.drawable.field_selfdeveloper)}
                        else -> {project_detail_kind_icon.setImageResource(R.drawable.field_economy)} // 경제
                    }

                    // 모집 포지션 동적 추가
                    for (i in response.body()?.result!!.position) {
                        var layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        layoutParams.setMargins(0, 0, 20, 0)
                        val textView = TextView(baseContext)
                        textView.text = i
                        textView.setPadding(40, 10, 40, 10)
                        textView.layoutParams = layoutParams

                        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14F)
                        textView.setTextColor(ContextCompat.getColor(baseContext, R.color.colorBlack))
                        textView.background =
                            ContextCompat.getDrawable(baseContext, R.drawable.radius_background_transparent)
                        textView.isSingleLine = true

                        project_detail_position_layout.addView(textView)
                    }

                    // 스택 동적 추가
                    for (i in response.body()?.result!!.stacks) {
                        var layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        layoutParams.setMargins(0, 0, 20, 0)
                        val textView = TextView(baseContext)
                        textView.text = i
                        textView.setPadding(40, 10, 40, 10)
                        textView.layoutParams = layoutParams

                        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14F)
                        textView.setTextColor(ContextCompat.getColor(baseContext, R.color.colorBlack))
                        textView.background =
                            ContextCompat.getDrawable(baseContext, R.drawable.radius_background_transparent)
                        textView.isSingleLine = true

                        project_detail_using_program_layout.addView(textView)
                    }

                    //유저 동적 추가 직군에 따라 들어가는 그림 다르게하기 추후에 배경 크기 수정
                    for(i in response.body()?.result!!.participants) {
                        var layoutParams = LinearLayout.LayoutParams(135, 135)
                        layoutParams.setMargins(0, 0, 20, 0)
                        val textView = TextView(baseContext)
                        textView.text = i.emoticon
                        textView.gravity = Gravity.CENTER
                        //textView.setPadding(20, 20, 20, 20)
                        textView.layoutParams = layoutParams

                        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 21F)
                        textView.setTextColor(ContextCompat.getColor(baseContext, R.color.colorBlack))

                        when (i.position) {
                            "개발자" -> {
                                var drawble =
                                    ContextCompat.getDrawable(baseContext, R.drawable.circle_developer)
                                drawble?.setBounds(0,0, 72, 72)
                                textView.background = drawble
                            }
                            "디자이너" -> {
                                var drawble =
                                    ContextCompat.getDrawable(baseContext, R.drawable.circle_designer)
                                drawble?.setBounds(0,0, 72, 72)
                                textView.background = drawble
                            }
                            else -> {
                                var drawble =
                                    ContextCompat.getDrawable(baseContext, R.drawable.circle_director)
                                drawble?.setBounds(0,0, 72, 72)
                                textView.background = drawble
                            }
                        }
                        textView.isSingleLine = true

                        textView.setOnClickListener {
                            val intent = Intent(baseContext, ProfileActivity::class.java)
                            if(i.name == watcherName) {
                                intent.putExtra("title", "내 프로필")
                            }else {
                                intent.putExtra("title", "멤버 프로필").putExtra("id", i.userIndex)
                            }
                            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }

                        project_detail_member_layout.addView(textView)
                    }
                    setUpView() // 뷰 셋업
                } else {
                    finish()
                }
            }
        })
    }

    //프로젝트 삭제
    private fun deleteProject(id: Long) {
        projectApi?.getProjectDelete(id)?.enqueue(object : Callback<CheckResponse> {
            override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                // userAPI에서 타입이나 이름 안맞췄을때
                Log.e("tag ", "onFailure" + t.localizedMessage)
            }

            override fun onResponse(
                call: Call<CheckResponse>,
                response: Response<CheckResponse>
            ) {
                if(response.code().toString() == "200") {
                    Toast.makeText(baseContext, "게시물이 삭제되었습니다.", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                } else {
                    Toast.makeText(baseContext, "게시물 삭제에 실패했습니다.\n에러 코드 : " + response.code() + "\n" + response.body().toString(), Toast.LENGTH_SHORT)
                    .show()
                }
            }
        })
    }

    //내 프로젝트 인지 확인.
    private fun isMyProject() {
        userApi?.getProfile()?.enqueue(
            object : Callback<CheckMyProfile> {
                override fun onFailure(call: Call<CheckMyProfile>, t: Throwable) {
                    // userAPI에서 타입이나 이름 안맞췄을때
                    Log.e("tag ", "onFailure, " + t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<CheckMyProfile>,
                    response: Response<CheckMyProfile>
                ) {
                    when {
                        response.code().toString() == "200" -> {
                            watcherName = response.body()?.result!!.name
                            for(i in response.body()?.result!!.myprojects) {
                                if(i.id == projectID.toLong()) {
                                    checkMyProject = true
                                    break
                                }
                            }
                    }
                        response.code().toString() == "401" -> {
                            GlobalApplication.prefs.setString("token", "NO_TOKEN")
                        }
                    }
                }
            })
    }

    // 작성자의 프로필을 가져오는 메서드.
    private fun getWriterProfile(id: Long){
        userApi?.getUserProfile(id)?.enqueue(
            object : Callback<CheckProfile> {
                override fun onFailure(call: Call<CheckProfile>, t: Throwable) {
                    // userAPI에서 타입이나 이름 안맞췄을때
                    Log.e("tag ", "onFailure, " + t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<CheckProfile>,
                    response: Response<CheckProfile>
                ) {
                    when {
                        response.code().toString() == "200" -> {
                            project_detail_writer_text.text = response.body()?.result?.name
                        }
                        else -> {
                            Toast.makeText(
                                baseContext, "에러가 발생했습니다. 에러코드 : " + response.code()
                                , Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
    }

    private fun projectStatusChange(id: Long, status: String) {
        projectApi?.getProjectStatusChange(id, status)?.enqueue(object : Callback<CheckResponse> {
            override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                // userAPI에서 타입이나 이름 안맞췄을때
                Log.e("tag ", "onFailure" + t.localizedMessage)
            }

            override fun onResponse(
                call: Call<CheckResponse>,
                response: Response<CheckResponse>
            ) {
                if(response.code().toString() == "200") {
                    Toast.makeText(baseContext, "프로젝트 상태가 $status(으)로 변경되었습니다.", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                    val intent = Intent(baseContext, ProjectViewDetail::class.java).putExtra("id", projectID.toLong())
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                } else {
                    Toast.makeText(baseContext, "프로젝트 상태 변경에 실패했습니다.\n에러 코드 : " + response.code() + "\n" + response.body().toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }
}
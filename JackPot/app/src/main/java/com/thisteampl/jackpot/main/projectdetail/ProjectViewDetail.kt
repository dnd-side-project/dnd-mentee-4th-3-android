package com.thisteampl.jackpot.main.projectdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.common.GlobalApplication
import com.thisteampl.jackpot.main.projectController.CheckProject
import com.thisteampl.jackpot.main.projectController.projectAPI
import com.thisteampl.jackpot.main.userController.CheckResponse
import com.thisteampl.jackpot.main.userController.userAPI
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_project_view_detail.*
import kotlinx.android.synthetic.main.holder_mypage_myproject.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectViewDetail : AppCompatActivity() {

    private val projectApi = projectAPI.create()
    private var projectID = 0
    private val userApi = userAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_view_detail)

        projectID = intent.getLongExtra("id", 0).toInt()
        if(projectID == 0) {
            finish()
        }
        getProject(projectID)

        setSupportActionBar(project_detail_toolbar) // 기본액션바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 제목 없애기
        setUpView()
    }

    private fun setUpView() {
        if (GlobalApplication.prefs.getString("token", "NO_TOKEN") == "NO_TOKEN") {
            project_detail_comment_edittext.hint = "로그인한 유저만 댓글을 달 수 있습니다."
            project_detail_comment_input_button.isEnabled = false
            project_detail_comment_edittext.isEnabled = false
            project_detail_project_scrap_button.isEnabled = false
            project_detail_project_register_button.isEnabled = false
        }

        project_detail_back_button.setOnClickListener { super.onBackPressed() }
    }

    //내 프로젝트일 경우 메뉴바 생성
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //if, 내프로젝트 아닐경우 return false
        val inflater = menuInflater
        inflater.inflate(R.menu.project_menu, menu)
        return true
    }

    // 메뉴바 선택시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //if, 내프로젝트 아닐경우 return false
        when(item.itemId) {
            R.id.project_detail_menu -> {
            }
            R.id.project_detail_delete_menu -> {
                deleteProject(projectID.toLong())
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
                        var layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        layoutParams.setMargins(0, 0, 20, 0)
                        val textView = TextView(baseContext)
                        textView.text = i.emoticon
                        textView.setPadding(40, 10, 40, 10)
                        textView.layoutParams = layoutParams

                        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12F)
                        textView.setTextColor(ContextCompat.getColor(baseContext, R.color.colorBlack))
                        when (i.position) {
                            "개발자" -> {
                                textView.background =
                                    ContextCompat.getDrawable(baseContext, R.drawable.circle_developer)
                            }
                            "디자이너" -> {
                                textView.background =
                                    ContextCompat.getDrawable(baseContext, R.drawable.circle_designer)
                            }
                            else -> {
                                textView.background =
                                    ContextCompat.getDrawable(baseContext, R.drawable.circle_director)
                            }
                        }
                        textView.isSingleLine = true
                        project_detail_member_layout.addView(textView)
                    }

                } else {
                    finish()
                }
            }
        })
    }

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
    private fun isMyProject(id: Long) : Boolean {
        return false
    }
}
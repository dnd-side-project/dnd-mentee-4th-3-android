package com.thisteampl.jackpot.main.userpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.common.GlobalApplication.Companion.prefs
import com.thisteampl.jackpot.main.LoginActivity
import com.thisteampl.jackpot.main.setting.SettingActivity
import com.thisteampl.jackpot.main.userController.*
import kotlinx.android.synthetic.main.activity_my_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageActivity : AppCompatActivity() {

    private val userApi = userAPI.create()
    lateinit var myProjectAdapter: MyProjectAdapter
    lateinit var myRegisterProjectAdapter: AnotherProjectAdapter
    lateinit var myScrapProjectAdapter: AnotherProjectAdapter
    lateinit var myCommentProjectAdapter: AnotherProjectAdapter
    lateinit var myScrapMemberAdapter: ScrapMemberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)
        if(prefs.getString("token", "NO_TOKEN") == "NO_TOKEN") {
            finish()
        }

        setUpRecyclerView()
        getProfile()
    }

    override fun onResume() {
        super.onResume()
        if(prefs.getString("token", "NO_TOKEN") == "NO_TOKEN") {
            finish()
        }
    }


        // 마이페이지 뷰 셋팅하는 메서드. 로그인 돼 있을 경우와 로그아웃 돼 있을 경우를 분리한다.
        private fun setupView(){
            mypage_project_num_text.text = myProjectAdapter.items.size.toString() + "개"
            mypage_registerproject_num_text.text = myRegisterProjectAdapter.items.size.toString() + "개"

            if(myProjectAdapter.items.size == 0) {
                mypage_no_project_text.visibility = View.VISIBLE
            } else { mypage_no_project_text.visibility = View.GONE }

            if(myRegisterProjectAdapter.items.size == 0) {
                mypage_no_registerproject_text.visibility = View.VISIBLE
            } else { mypage_no_registerproject_text.visibility = View.GONE }

            if(myScrapProjectAdapter.items.size == 0 && myScrapMemberAdapter.items.size == 0) {
                mypage_no_scrapcomment_text.visibility = View.VISIBLE
            } else { mypage_no_scrapcomment_text.visibility = View.GONE }

            mypage_back_button.setOnClickListener { super.onBackPressed() }

            //댓글 단 프로젝트 버튼
            mypage_mycomment_button.setOnClickListener {
                mypage_myscrapcomment_recyclerview.adapter = myCommentProjectAdapter
                mypage_myscrapmember_recyclerview.visibility = View.GONE

                if(myCommentProjectAdapter.items.size == 0) {
                    mypage_no_scrapcomment_text.visibility = View.VISIBLE
                } else { mypage_no_scrapcomment_text.visibility = View.GONE }

                mypage_mycomment_button.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
                mypage_select_mycomment_bottombar.visibility = View.VISIBLE
                mypage_no_scrapcomment_text.text = "아직 댓글을 단 프로젝트가 없어요."

                mypage_select_myscrap_bottombar.visibility = View.GONE
                mypage_myscrap_button.setTextColor(ContextCompat.getColor(this, R.color.colorLightGray))
            }

            //나의 스크랩 버튼
            mypage_myscrap_button.setOnClickListener {
                mypage_myscrapcomment_recyclerview.adapter = myScrapProjectAdapter
                mypage_myscrapmember_recyclerview.visibility = View.VISIBLE

                if(myScrapProjectAdapter.items.size == 0 && myScrapMemberAdapter.items.size == 0) {
                    mypage_no_scrapcomment_text.visibility = View.VISIBLE
                } else { mypage_no_scrapcomment_text.visibility = View.GONE }

                mypage_no_scrapcomment_text.text = "아직 스크랩한 멤버나 프로젝트가 없어요.\n관심있는 멤버나 프로젝트를 스크랩 해보세요!"
                mypage_select_myscrap_bottombar.visibility = View.VISIBLE
                mypage_myscrap_button.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))

                mypage_mycomment_button.setTextColor(ContextCompat.getColor(this, R.color.colorLightGray))
                mypage_select_mycomment_bottombar.visibility = View.GONE
            }

            //프로필 보기 버튼
            mypage_watchprofile_button.setOnClickListener {
                val intent = Intent(baseContext, ProfileActivity::class.java).putExtra("title", "내 프로필")
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
            
            //설정 버튼
            detail_add_content_button.setOnClickListener {
                val intent = Intent(baseContext, SettingActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
        }

    // 프로필을 가져오는 메서드.
    private fun getProfile(){
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
                            Log.e("getProfile ", "User : " + response.body()!!.result.toString())
                            mypage_job_text.text = response.body()!!.result.position + " ・ " + response.body()!!.result.career
                            mypage_name_text.text = response.body()!!.result.name
                            mypage_job_icon_text.text = response.body()!!.result.emoticon
                            if(response.body()!!.result.privacy) {
                                mypage_profile_close_image.visibility = View.GONE
                            }

                            for(myprj in response.body()!!.result.participantProject) {
                                var list = mutableListOf<String>()
                                for(j in myprj.participants) {
                                    list.add(j.position)
                                }
                                myProjectAdapter.items.add(MyProject(myprj.id, myprj.interest, myprj.title, list))
                            }
                            myProjectAdapter.notifyDataSetChanged()

                            for(parprj in response.body()!!.result.participantRequest) {
                                myRegisterProjectAdapter.items.add(AnotherProject(parprj.id, parprj.title,
                                    parprj.interest, parprj.position, parprj.stacks))
                            }
                            myRegisterProjectAdapter.notifyDataSetChanged()

                            for(scrprj in response.body()!!.result.scrapProjects) {
                                myScrapProjectAdapter.items.add(AnotherProject(scrprj.id, scrprj.title,
                                    scrprj.interest, scrprj.position, scrprj.stacks))
                            }
                            myScrapProjectAdapter.notifyDataSetChanged()

                            for(comprj in response.body()!!.result.commentProjects) {
                                myCommentProjectAdapter.items.add(AnotherProject(comprj.id, comprj.title,
                                    comprj.interest, comprj.position, comprj.stacks))
                            }
                            myCommentProjectAdapter.notifyDataSetChanged()

                            when (response.body()!!.result.position) {
                                "개발자" -> {
                                    mypage_job_background_image.setImageResource(R.drawable.background_developer)
                                }
                                "디자이너" -> {
                                    mypage_job_background_image.setImageResource(R.drawable.background_designer)
                                }
                                else -> {
                                    mypage_job_background_image.setImageResource(R.drawable.background_director)
                                }
                            }
                            getMyScrapMember() // 스크랩 멤버도 가져오기
                        }
                        response.code().toString() == "401" -> {
                            Toast.makeText(
                                baseContext,
                                "토큰 유효기간이 만료됐습니다.\n 로그인 페이지로 이동합니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            prefs.setString("token", "NO_TOKEN")
                            val intent = Intent(baseContext, LoginActivity::class.java)
                            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                            finish()
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

    private fun getMyScrapMember() {
        userApi?.getMyScrapUsers()?.enqueue(
            object : Callback<CheckMyScrapUser> {
                override fun onFailure(call: Call<CheckMyScrapUser>, t: Throwable) {
                    // userAPI에서 타입이나 이름 안맞췄을때
                    Log.e("tag ", "onFailure, " + t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<CheckMyScrapUser>,
                    response: Response<CheckMyScrapUser>
                ) {
                    when {
                        response.code().toString() == "200" -> {
                            for(i in response.body()!!.result) {
                                myScrapMemberAdapter.items.add(
                                    ScrapMember(i.userIndex, i.position,
                                    i.name, i.emoticon)
                                )
                            }
                            myScrapMemberAdapter.notifyDataSetChanged()
                            setupView() // 정보 불러오고 난 후 뷰 셋업

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

    private fun setUpRecyclerView(){
        myProjectAdapter = MyProjectAdapter()
        mypage_myproject_recyclerview.adapter = myProjectAdapter
        mypage_myproject_recyclerview.layoutManager = LinearLayoutManager(this)

        myRegisterProjectAdapter = AnotherProjectAdapter()
        mypage_myregisterproject_recyclerview.adapter = myRegisterProjectAdapter
        mypage_myregisterproject_recyclerview.layoutManager = LinearLayoutManager(this)

        myScrapProjectAdapter = AnotherProjectAdapter()
        mypage_myscrapcomment_recyclerview.adapter = myScrapProjectAdapter
        mypage_myscrapcomment_recyclerview.layoutManager = LinearLayoutManager(this)

        myCommentProjectAdapter = AnotherProjectAdapter()

        myScrapMemberAdapter = ScrapMemberAdapter()
        mypage_myscrapmember_recyclerview.adapter = myScrapMemberAdapter
        mypage_myscrapmember_recyclerview.layoutManager = LinearLayoutManager(this)
    }
}
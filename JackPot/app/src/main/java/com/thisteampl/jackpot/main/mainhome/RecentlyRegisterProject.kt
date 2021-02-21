package com.thisteampl.jackpot.main.mainhome

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.thisteampl.jackpot.R
import com.thisteampl.jackpot.main.MainActivity
import com.thisteampl.jackpot.main.projectController.*
import kotlinx.android.synthetic.main.fragment_attention_project.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 참고 자료 : https://youtu.be/BT206iXW9bk
// 최근 등록된 프로젝트
class RecentlyRegisterProject : Fragment() {
    var recentlyregister: MutableList<RecentlyRegisterList> = mutableListOf()
    var file_empty2 = mutableListOf<String>()
    var pgeresponse : Response<ProjectGetElement> ?= null
    // 최근등록된 내용을 DB에서 받아서 화면에 출력하기

    // init 초기화할 때, list를 삽입한다.
    init {
        var file_empty = String()

        file_empty2.add("")
        file_empty = ""

        var image = null
        var projectapi = projectAPI.projectRetrofitService()

        var recruitmentproject = ProjectPostLatest(
            file_empty2,file_empty2,0,10,file_empty,"최신순",file_empty2
        )
        // 백엔드 호출
        projectapi?.getprojectcontents(recruitmentproject)
            ?.enqueue(object : Callback<ProjectGetElement> {
                override fun onFailure(call: Call<ProjectGetElement>, t: Throwable) {
                    Log.e("tag ", "onFailure, " + t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ProjectGetElement>,
                    response: Response<ProjectGetElement>
                ) {

                    if(response.isSuccessful ){
                        Log.d("tag","-------------------")
                        Log.i("tag","성공")
                        Log.i("tag","${response.code().toString()}")
//                        Log.i("tag","ID 호출 ${response.body()?.contents}")
//                        Log.i("tag","size 호출 ${response.body()?.pageSize}")
//                        Log.i("tag","number 호출 ${response.body()?.pageNumber}")
//                        Log.d("tag","-------------------")
//                        pgeresponse = response
//                        Log.d("tag","넣은 id 결과 : ${pgeresponse?.body()?.contents?.get(1)?.id}")
//                        Log.d("tag","관심 분야 : ${pgeresponse?.body()?.contents?.get(1)?.interest}")
//
//                        Log.d("tag","포지션 : ${pgeresponse?.body()?.contents?.get(1)?.position}")
//                        Log.d("tag","넣은 id 결과 : ${pgeresponse?.body()?.contents?.get(1)?.scrapped}")
//                        Log.d("tag","제목 : ${pgeresponse?.body()?.contents?.get(1)?.title}")



                        // 기술 스택 arrayListOf
                        // ListOf 처리
                        val v = pgeresponse?.body()?.contents?.isEmpty()
                        if(v == true){
                            Log.d("tag","null 이 아닐 때 : ${pgeresponse?.body()?.contents?.get(1)?.title}")
                            pgeresponse?.body()?.contents?.get(1)?.id
                        }else{
                            Log.d("tag","null 이 일 때 : ${pgeresponse?.body()?.contents?.get(1)?.title}")
                            pgeresponse?.body()?.contents?.get(1)?.id
                        }

                        val len = pgeresponse?.body()?.contents?.size

                        if (len != null) {
                            for(n in 0..len-1){
                                Log.d("tag","제목 : ${pgeresponse?.body()?.contents?.get(n)?.title}")
                                Log.d("tag","폐지 : ${pgeresponse?.body()?.contents?.get(n)?.scrapped}")
                                Log.d("tag","넣은 id 결과 : ${pgeresponse?.body()?.contents?.get(n)?.id}")
                                Log.d("tag","관심 분야 : ${pgeresponse?.body()?.contents?.get(n)?.interest}")
                                Log.d("tag","포지션 : ${pgeresponse?.body()?.contents?.get(n)?.position}")
                                Log.d("tag","스택 : ${pgeresponse?.body()?.contents?.get(n)?.stacks}")

                            }
                        }

                        if (len != null) {
                            for(n in 0..len-1){
                                // 관심분야 이미지 삽입되게
                                image= pgeresponse?.body()?.contents?.get(n)?.interest as Nothing?

                                var image2:Int = 0
                                if(image!!.equals("자기계발")){
                                    image2 = R.drawable.field_selfdeveloper
                                }else if(image!!.equals("취미")){
                                    image2 = R.drawable.field_hobby
                                }else if(image!!.equals("경제")){
                                    image2 = R.drawable.field_economy
                                }else if(image!!.equals("요리")){
                                    image2 = R.drawable.field_cook
                                }else if(image!!.equals("IT")){
                                    image2 = R.drawable.field_it
                                }else if(image!!.equals("예술/장착")){
                                    image2 = R.drawable.field_art
                                }else if(image!!.equals("건강")){
                                    image2 = R.drawable.field_health
                                }else if(image!!.equals("휴식")){
                                    image2 = R.drawable.field_repose
                                }

                            }
                        }

//                        recentlyregister = arrayListOf(
//                            RecentlyRegisterList(R.drawable.field_selfdeveloper,pgeresponse?.body()?.contents?.get(0)?.title,
//                                pgeresponse?.body()?.contents?.get(0)?.position, "30분",
//                                pgeresponse?.body()?.contents?.get(0)?.stacks)
//                        )

                    }
                }

            })
        
        // 넘기기
        var str = arrayListOf<String>()
        recentlyregister = arrayListOf(
            RecentlyRegisterList(image, pgeresponse?.body()?.contents?.get(0)?.title,str,"2020.01.31",str),
            RecentlyRegisterList(image,pgeresponse?.body()?.contents?.get(1)?.title,str,"2020.01.31",str),
            RecentlyRegisterList(image,pgeresponse?.body()?.contents?.get(2)?.title,str,"2020.01.31",str)
        )
    }

    fun imageselection(iv:String): Int {
        var ivselect = iv
        ivselect = "자기계발"
        if(ivselect.equals("자기계발")) return 1
        if(ivselect.equals("취미")) return 2
        return 3
    }



    companion object {
        fun newInstance(): RecentlyRegisterProject{
            return RecentlyRegisterProject()
        }

    }

    // View가 만들어진 후, onViewCreated() 콜백된다.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        main_attentionprojectlist_recyclerview.layoutManager = LinearLayoutManager((activity as MainActivity),
            LinearLayoutManager.VERTICAL,false)
        main_attentionprojectlist_recyclerview.setHasFixedSize(true)
        main_attentionprojectlist_recyclerview.adapter = RecentlyRegisterListAdapter(recentlyregister,
            pgeresponse?.body()?.contents?.get(1)?.id
        )
    }

    // 액티비티 프래그먼트 연결될 때 onAttach
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    // onCreate 후에 화면을 구성할 때 호출되는 부분
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_attention_project,container,false)
        return view
    }
}
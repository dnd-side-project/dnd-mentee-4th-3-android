package com.thisteampl.jackpot.main.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

//import java.util.*

/*
    DAO : 데이터 접근 객체, DB에 있는 정보를 접근하기 위한 인터페이스
    Memo 테이블에 id, title, date 를 가져올 수 있게 해준다.
    (id, title, date) 순 ex.(1, "내일 할 일", 2020-07-25)
 */

@Dao
interface UserDao {
    @Query ("SELECT * FROM User")
    fun getAll() : List<User> // 모든 리스트를 나타내 준다. 로그인 돼있는지 확인.

    @Query ("SELECT * FROM User WHERE id = :id")
    fun get(id: Long) : User // id로 찾아준다

    @Insert
    fun insert(memo: User) : Long // 유저를 추가해 준다. 파라미터로 호출 시 추가해줌.

    @Delete
    fun delete(memo: User) // 유저 삭제, 로그아웃.
}
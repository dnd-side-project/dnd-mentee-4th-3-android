package com.thisteampl.jackpot.common

import androidx.room.*
import com.thisteampl.jackpot.main.user.User
import com.thisteampl.jackpot.main.user.UserDao

/*
    Room을 사용하기 위한 클래스.
    DB의 기본적인 사용을 정의하는 곳.
    entities : Room에서 관리하는 클래스들
    version : 디비의 테이블 등이 수정되면 버전을 올려주고 마이그레이션(디비 정보 변경)을 하기 위함이다.
    Companion object : 데이터베이스 접근을 한 곳으로 하기위한 오브젝트 생성
    Room.databaseBuilder : db 관련 설정 추가
 */

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao // 사용하는 DAO를 미리 선언한다.
    //abstract fun projectDao(): ProjectDao 추후에 Project도 추가

    companion object {
        val instance = Room.databaseBuilder(
            GlobalApplication.instance,
            AppDatabase::class.java, "jackpot.db"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}
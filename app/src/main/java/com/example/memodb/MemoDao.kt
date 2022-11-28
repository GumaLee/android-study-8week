package com.example.memodb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemoDao {
    // 메모 추가
    @Insert
    fun insert(memo:MemoData)

    // 메모 삭제
    @Delete
    fun delete(memo:MemoData)

    // 메모 열기 -> 메모의 모든 데이터 불러오기
    @Query("SELECT * FROM MemoData")
    fun selectAll(): List<MemoData>

    // 메모 ID로 메모 찾기
    @Query("SELECT * FROM MemoData WHERE memoId = :memoId")
    fun selectByMemoId(memoId: Int):MemoData

    // 제목으로 메모 찾기
    @Query("SELECT * FROM MemoData WHERE title = :title")
    fun selectByMemoTitle(title:String):MemoData

    //
    @Query("SELECT memoId FROM MemoData WHERE title = :title")
    fun getIdByMemoTitle(title: String): Int

    @Query("UPDATE MemoData SET title = :title WHERE memoId = :memoId")
    fun updateTitleByMemoId(memoId: Int, title:String)

    @Query("UPDATE MEMODATA SET content = :content WHERE memoId =:memoId")
    fun updateContentByMemoId(memoId: Int, content:String)
}
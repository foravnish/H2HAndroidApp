package com.h2h.medical.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.h2h.medical.room.table.CommentList

@Dao
interface CommentDao {

    @Query("SELECT * FROM Comment_TB ORDER BY srno Asc")
    fun getAllData(): LiveData<List<CommentList>>

    @Query("SELECT COUNT(srno) FROM Comment_TB")
    fun getRowCountChat(): LiveData<Int?> //with LiveData

    @Query("SELECT * FROM Comment_TB ORDER BY srno DESC LIMIT :s")
    fun getAllDataBySr(s: Int): LiveData<List<CommentList>>

    @Query("SELECT * FROM Comment_TB WHERE msgID= :msgID")
    fun getDataById(msgID:String): LiveData<List<CommentList>>

    @Query("DELETE FROM Comment_TB WHERE msgID= :msgID")
    fun deleteSingleData(msgID:String)

    @Query("DELETE FROM Comment_TB")
    fun deleteAllTask()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(word: CommentList): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertList(mData: ArrayList<CommentList>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(mData: CommentList)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateList(mData: ArrayList<CommentList>)

    @Query("DELETE FROM Comment_TB")
    fun deleteAll()

    @Transaction
    fun insertOrUpdate(objList: CommentList) {
        val insertResult = insert(objList)
        if (insertResult == -1L) update(objList)
    }

    @Transaction
    fun insertOrUpdate(objList: ArrayList<CommentList>) {
        try {
            val insertResult = insertList(objList)
            val updateList = ArrayList<CommentList>()
            for (i in insertResult.indices) {
                if (insertResult[i] == -1L) updateList.add(objList[i])
            }
            if (updateList.isNotEmpty()) updateList(updateList)
        } catch (e: Exception) {
        }
    }

}
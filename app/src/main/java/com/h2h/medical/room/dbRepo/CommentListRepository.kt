
package com.h2h.medical.Repo

import androidx.lifecycle.LiveData
import com.h2h.medical.room.H2HDatabase
import com.h2h.medical.room.table.CommentList


class CommentListRepository {

    private var mRepository: CommentListRepository? = null

    var databaseHelper: H2HDatabase? = null

    private fun initializeDB(): H2HDatabase {
        if (H2HDatabase.INSTANCE != null) return H2HDatabase.INSTANCE!!
        return H2HDatabase.getDatabase()
    }

    init {
        databaseHelper = initializeDB()
    }

    fun getInstance(): CommentListRepository {
        if (mRepository == null) mRepository = CommentListRepository()
        return mRepository as CommentListRepository
    }


    fun insertItem(data: ArrayList<CommentList>) {
        databaseHelper?.getCommentList()?.insertOrUpdate(data)
    }

//    fun getDataById(id: String): LiveData<List<CommentList>>? {
//        return databaseHelper?.getCommentList()?.getDataById(id)
//    }

    fun getDataByAll(): LiveData<List<CommentList>>? {
        return databaseHelper?.getCommentList()?.getAllData()
    }
}
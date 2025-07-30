package com.crmtrail.ViewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.h2h.medical.Repo.CommentListRepository
import com.h2h.medical.room.table.CommentList

class CommentViewModel (application: Application) : AndroidViewModel(application)  {

    private lateinit var mContext: Context
    private var mApiRepository: CommentListRepository? = null
    fun init(context: Context) {
        this.mContext = context
    }

    suspend fun insertItem(data: ArrayList<CommentList>){
        mApiRepository = CommentListRepository().getInstance()
        mApiRepository!!.insertItem(data)
    }


//    fun getDataById() : LiveData<List<CommentList>>? {
//        mApiRepository = CommentListRepository().getInstance()
//        return  mApiRepository!!.getDataById()
//    }

    fun getDataByAll() : LiveData<List<CommentList>>? {
        mApiRepository = CommentListRepository().getInstance()
        return  mApiRepository!!.getDataByAll()
    }
}
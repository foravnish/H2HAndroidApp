package com.h2h.medical.room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.h2h.medical.room.dao.CommentDao
import com.h2h.medical.room.table.CommentList
import com.h2h.medical.utils.AppController


@Database(
    entities = [CommentList::class],
    version = 1, exportSchema = false
)
abstract class H2HDatabase : RoomDatabase() {

    abstract fun getCommentList(): CommentDao


    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearAllTables() {}

    companion object {
        @Volatile
        var INSTANCE: H2HDatabase? = null
        val DB_NAME = "h2h_database.db"


        fun getDatabase(): H2HDatabase {
            if (INSTANCE != null) return INSTANCE!!
            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(
                        AppController.context!!,
                        H2HDatabase::class.java,
                        DB_NAME
                    ).fallbackToDestructiveMigration().build()
                return INSTANCE!!
            }
        }


    }
}

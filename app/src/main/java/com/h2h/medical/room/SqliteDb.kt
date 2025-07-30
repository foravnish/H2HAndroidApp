package com.h2h.medical.room

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteDb(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    // Creating Tables
    override fun onCreate(db: SQLiteDatabase) {

        val Query1 =
            "create table CommentList(sr INTEGER PRIMARY KEY AUTOINCREMENT,message text,msgId text,tabId text)"
        db.execSQL(Query1)
    }

    // Upgrading database
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        if (oldVersion >= newVersion) return

        onCreate(db)
    }


    fun insertCommentData(
        message: String,
        msgId: String,
        tabId: String,
    ) {
        var cr: Cursor? = null
        val db = this.writableDatabase
        try {
            val initialValues = ContentValues()
            initialValues.put("message", message)
            initialValues.put("msgId", msgId)
            initialValues.put("tabId", tabId)
            val selectQuery = "SELECT msgId FROM CommentList where msgId='${msgId}'"
            cr = db.rawQuery(selectQuery, null)
            if (cr.moveToFirst()) db.update(
                "CommentList", initialValues, "msgId=?", arrayOf(msgId)
            ) else db.insert("CommentList", null, initialValues)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun deleteComment(msgId:String){
        try {
            val db = this.writableDatabase
            val Query =
                "delete from CommentList where msgId='$msgId'"
            db.execSQL(Query)
        } catch (e: Exception) {
        }
    }
    fun getComment(tabId: String): Cursor {
        var cursor: Cursor? = null
        try {
            val db = this.writableDatabase
            val query = "select * from CommentList where tabId='$tabId'"

            cursor = db.rawQuery(query, null)
            return cursor
        } catch (e: Exception) {
        } finally {
        }
        return cursor!!
    }

    //Work End of Location Logs---

    companion object {
        // Database Version
        private const val DATABASE_VERSION = 1

        // Database Name
        private const val DATABASE_NAME = "h2h_database.db"

        // Table name
        private const val TABLE_REMINDERS = "ReminderTable"


    }
}
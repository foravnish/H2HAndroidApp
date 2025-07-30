package com.h2h.medical.room.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "Comment_TB",
    indices = [Index(value = ["msgID"], unique = true)]
)
data class CommentList(
    @field:ColumnInfo(name = "message")
    val message: String,
    @field:ColumnInfo(name = "msgID")
    val msgID: String,
    @field:ColumnInfo(name = "tabId")
    val tabId: String,

) : Serializable {
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "srno")
    var srno: Int = 0
}


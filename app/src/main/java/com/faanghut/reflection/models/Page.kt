package com.faanghut.reflection.models

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

/*@Entity
@Keep
data class Page(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "body") val body: String?,
    @ColumnInfo(name = "created_timestamp") val createdTimestamp: LocalTime,
    @ColumnInfo(name = "last_edited_timestamp") val lastEditedTimestamp: LocalTime
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}*/

@Keep
@Entity
data class Page(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "body") val body: String?,
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "created_timestamp") val createdTimestamp: LocalTime,
    @ColumnInfo(name = "last_edited_timestamp") val lastEditedTimestamp: LocalTime
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
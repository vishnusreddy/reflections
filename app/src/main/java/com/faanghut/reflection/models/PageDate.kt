package com.faanghut.reflection.models

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Keep
@Entity
data class PageDate(
    @PrimaryKey
    @ColumnInfo(name = "date")
    val date: LocalDate
)
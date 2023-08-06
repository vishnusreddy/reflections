package com.faanghut.reflection.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class PageDate(
    @PrimaryKey
    @ColumnInfo(name = "date")
    val date: LocalDate
)
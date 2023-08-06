package com.faanghut.reflection.models

import androidx.room.Embedded
import androidx.room.Relation

data class PageDateWithPages(
    @Embedded val pageDate: PageDate,
    @Relation(
        parentColumn = "date",
        entityColumn = "date"
    )
    val notes: List<Page>
)
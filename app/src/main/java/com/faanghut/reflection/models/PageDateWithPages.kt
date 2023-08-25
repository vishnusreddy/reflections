package com.faanghut.reflection.models

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Relation

@Keep
data class PageDateWithPages(
    @Embedded val pageDate: PageDate,
    @Relation(
        parentColumn = "date",
        entityColumn = "date"
    )
    val pages: List<Page>
)
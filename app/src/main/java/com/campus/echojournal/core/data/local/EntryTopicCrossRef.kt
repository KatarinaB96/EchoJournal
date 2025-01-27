package com.campus.echojournal.core.data.local

import androidx.room.Entity

@Entity(primaryKeys = ["entryId", "topicId"])
data class EntryTopicCrossRef(
    val entryId: Int = 0,
    val topicId: Int = 0
)
package com.campus.echojournal.core.data.local

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.campus.echojournal.core.data.local.entity.EntryEntity
import com.campus.echojournal.core.data.local.entity.TopicEntity

data class EntryWithTopics(
    @Embedded val entry: EntryEntity,
    @Relation(
        parentColumn = "entryId",
        entityColumn = "topicId",
        associateBy = Junction(EntryTopicCrossRef::class)
    )
    val topics: List<TopicEntity>
)


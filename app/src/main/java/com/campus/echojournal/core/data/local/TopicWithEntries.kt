package com.campus.echojournal.core.data.local

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.campus.echojournal.core.data.local.entity.EntryEntity
import com.campus.echojournal.core.data.local.entity.TopicEntity

data class TopicWithEntries(
    @Embedded val topic: TopicEntity,
    @Relation(
        parentColumn = "topicId",
        entityColumn = "entryId",
        associateBy = Junction(EntryTopicCrossRef::class)
    )
    val entries: List<EntryEntity>
)
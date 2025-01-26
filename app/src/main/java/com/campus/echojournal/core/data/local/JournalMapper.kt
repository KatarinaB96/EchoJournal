package com.campus.echojournal.core.data.local

import com.campus.echojournal.core.data.local.entity.EntryEntity
import com.campus.echojournal.core.data.local.entity.TopicEntity
import com.campus.echojournal.core.domain.models.Entry
import com.campus.echojournal.core.domain.models.Topic

fun EntryEntity.toDomainModel(topics: List<Topic>): Entry {
    return Entry(
        id = entryId ?: 0,
        title = title,
        moodIndex = moodIndex,
        recordingPath = recordingPath,
        topics = topics,
        description = description,
        createdDate = createdDate
    )
}

fun TopicEntity.toDomainModel(): Topic {
    return Topic(
        id = topicId ?: 0,
        name = name,
        isDefaultTopic = isDefaultTopic
    )
}

fun Entry.toInsertTopicEntity(): EntryEntity {
    return EntryEntity(
        entryId = null,
        title = title,
        moodIndex = moodIndex,
        recordingPath = recordingPath,
        description = description,
        createdDate = createdDate
        )
}

fun Topic.toInsertTopicEntity(): TopicEntity {
    return TopicEntity(
        topicId = null,
        name = name,
        isDefaultTopic = isDefaultTopic
    )
}
fun EntryWithTopics.toDomainModel(): Entry {
    return Entry(
        id = entry.entryId ?: 0,
        title = entry.title,
        moodIndex = entry.moodIndex,
        recordingPath = entry.recordingPath,
        description = entry.description,
        createdDate = entry.createdDate,
        topics = topics.map { topicEntity ->
            Topic(
                id = topicEntity.topicId ?: 0,
                name = topicEntity.name,
                isDefaultTopic = topicEntity.isDefaultTopic
            )
        }
    )
}


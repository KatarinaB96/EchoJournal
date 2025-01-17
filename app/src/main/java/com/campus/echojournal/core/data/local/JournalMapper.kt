package com.campus.echojournal.core.data.local

import com.campus.echojournal.core.domain.models.Topic

fun TopicEntity.toDomainModel(): Topic {
    return Topic(
        id = id ?: 0,
        name = name
    )
}

fun Topic.toInsertEntity(): TopicEntity {
    return TopicEntity(
        id = null,
        name = name
    )
}
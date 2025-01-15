package com.campus.echojournal.core.domain

import com.campus.echojournal.core.domain.models.Topic
import kotlinx.coroutines.flow.Flow

interface JournalRepository {
    fun getAllTopics(): Flow<List<Topic>>

    suspend fun addTopic(topic: Topic)

    suspend fun deleteTopicById(topicId: Int)
}
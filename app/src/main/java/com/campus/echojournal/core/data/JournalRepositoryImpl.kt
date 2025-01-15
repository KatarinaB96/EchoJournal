package com.campus.echojournal.core.data

import com.campus.echojournal.core.data.local.JournalDao
import com.campus.echojournal.core.data.local.toDomainModel
import com.campus.echojournal.core.data.local.toInsertEntity
import com.campus.echojournal.core.domain.JournalRepository
import com.campus.echojournal.core.domain.models.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class JournalRepositoryImpl(private val dao: JournalDao) : JournalRepository {
    override fun getAllTopics(): Flow<List<Topic>> {
        return dao.getAllTopics().map { entities ->
            entities.map {
                it.toDomainModel()
            }
        }
    }

    override suspend fun addTopic(topic: Topic) {
        return dao.insertTopic(topic.toInsertEntity())
    }

    override suspend fun deleteTopicById(topicId: Int) {
        return dao.deleteTopicById(topicId)
    }
}
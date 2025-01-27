package com.campus.echojournal.core.data

import com.campus.echojournal.core.data.local.JournalDao
import com.campus.echojournal.core.data.local.toDomainModel
import com.campus.echojournal.core.data.local.toInsertTopicEntity
import com.campus.echojournal.core.domain.JournalRepository
import com.campus.echojournal.core.domain.models.Entry
import com.campus.echojournal.core.domain.models.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class JournalRepositoryImpl(private val dao: JournalDao) : JournalRepository {
    override fun getAllDefaultTopics(): Flow<List<Topic>> {
        return dao.getAllTopics().map { entities ->
            entities.filter { it.isDefaultTopic }.map {
                it.toDomainModel()
            }
        }
    }

    override suspend fun addTopic(topic: Topic) {
        return dao.insertTopic(topic.toInsertTopicEntity())
    }

    override suspend fun deleteTopicById(topicId: Int) {
        return dao.deleteTopicById(topicId)
    }

    override fun getAllEntriesWithTopics(): Flow<List<Entry>> {
        return dao.getEntryWithTopics().map { entriesWithTopics ->
            entriesWithTopics.map { it.toDomainModel() }
        }
    }

    override suspend fun insertEntryWithTopics(entry: Entry) {
        val entryEntity = entry.toInsertTopicEntity()
        val topicEntities = entry.topics.map { topic ->
            topic.toInsertTopicEntity()
        }
        dao.insertEntryWithTopics(entryEntity, topicEntities)
    }
}
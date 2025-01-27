package com.campus.echojournal.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.campus.echojournal.core.data.local.entity.EntryEntity
import com.campus.echojournal.core.data.local.entity.TopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {
    @Upsert
    suspend fun insertTopic(topicEntity: TopicEntity)

    @Query("SELECT * FROM topic_table")
    fun getAllTopics(): Flow<List<TopicEntity>>

    @Query("DELETE FROM topic_table WHERE topicId = :topicId")
    suspend fun deleteTopicById(topicId: Int)

    @Transaction
    @Query("SELECT * FROM entry_table")
    fun getEntryWithTopics(): Flow<List<EntryWithTopics>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: EntryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntryTopics(topics: List<TopicEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntryTopicCrossRefs(crossRefs: List<EntryTopicCrossRef>)


    @Query("SELECT * FROM topic_table WHERE name = :name LIMIT 1")
    suspend fun getTopicByName(name: String): TopicEntity?

    @Transaction
    suspend fun insertEntryWithTopics(entry: EntryEntity, topics: List<TopicEntity>): Int {
        val entryId = insertEntry(entry).toInt()
        val topicIds = topics.map { topic ->
            val existingTopic = getTopicByName(topic.name) // Check if topic exists by name
            existingTopic?.topicId ?: insertTopicAndGetId(topic) // Insert new topic if needed
        }
        val crossRefs = topicIds.map { topicId ->
            EntryTopicCrossRef(entryId = entryId, topicId = topicId)
        }

        insertEntryTopicCrossRefs(crossRefs)
        return entryId
    }


    suspend fun insertTopicAndGetId(topic: TopicEntity): Int {
        insertTopic(topic)
        return getTopicByName(topic.name)?.topicId ?: throw IllegalStateException("Topic insertion failed")
    }
}

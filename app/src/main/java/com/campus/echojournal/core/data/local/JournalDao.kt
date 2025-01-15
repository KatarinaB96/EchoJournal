package com.campus.echojournal.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {
    @Upsert
    suspend fun insertTopic(topicEntity: TopicEntity)

    @Query("SELECT * FROM topic_table")
    fun getAllTopics(): Flow<List<TopicEntity>>

    @Query("DELETE FROM topic_table WHERE id = :topicId")
    suspend fun deleteTopicById(topicId: Int)
}

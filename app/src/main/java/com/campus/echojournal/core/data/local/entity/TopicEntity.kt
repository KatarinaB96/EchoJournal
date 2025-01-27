package com.campus.echojournal.core.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "topic_table", indices = [Index(value = ["name"], unique = true)])
data class TopicEntity(
    @PrimaryKey(autoGenerate = true) val topicId: Int? = 0,
    val name: String,
    val isDefaultTopic: Boolean,
)

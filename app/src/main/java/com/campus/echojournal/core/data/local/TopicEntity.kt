package com.campus.echojournal.core.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "topic_table", indices = [Index(value = ["name"], unique = true)])
data class TopicEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    val name: String
)
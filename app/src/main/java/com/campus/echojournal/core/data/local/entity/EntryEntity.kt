package com.campus.echojournal.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry_table")
data class EntryEntity(
    @PrimaryKey(autoGenerate = true) val entryId: Int? = 0,
    val title: String,
    val moodIndex: Int,
    val recordingPath: String,
    val description: String,
    val audioDuration: Int
    val createdDate: Long = System.currentTimeMillis()
)

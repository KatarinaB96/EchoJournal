package com.campus.echojournal.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.campus.echojournal.core.data.local.entity.EntryEntity
import com.campus.echojournal.core.data.local.entity.TopicEntity

@Database(
    entities = [TopicEntity::class, EntryEntity::class, EntryTopicCrossRef::class],
    version = 1
)
abstract class EchoJournalDatabase : RoomDatabase() {
    abstract val journalDao: JournalDao
}

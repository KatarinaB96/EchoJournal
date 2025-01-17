package com.campus.echojournal.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TopicEntity::class],
    version = 1
)
abstract class EchoJournalDatabase : RoomDatabase() {
    abstract val journalDao: JournalDao
}

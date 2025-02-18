package com.campus.echojournal.core.di

import androidx.room.Room
import com.campus.echojournal.core.data.JournalRepositoryImpl
import com.campus.echojournal.core.data.local.EchoJournalDatabase
import com.campus.echojournal.core.domain.JournalRepository
import com.campus.echojournal.core.utils.DataStoreManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
    single { DataStoreManager(androidContext()) }
    single { Room.databaseBuilder(get(), EchoJournalDatabase::class.java, "echo_journal_db.db").build() }
    single { get<EchoJournalDatabase>().journalDao }
    singleOf(::JournalRepositoryImpl).bind<JournalRepository>()

}
package com.campus.echojournal

import android.app.Application
import com.campus.echojournal.core.di.coreModule
import com.campus.echojournal.entries.di.entriesModule
import com.campus.echojournal.settings.di.settingsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class EchoJournalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@EchoJournalApplication)
            // Load modules
            modules(
                coreModule,
                settingsModule,
                entriesModule
            )
        }
    }
}
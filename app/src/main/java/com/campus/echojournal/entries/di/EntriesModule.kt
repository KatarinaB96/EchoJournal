package com.campus.echojournal.entries.di

import com.campus.echojournal.entries.presentation.EntriesViewModel
import com.campus.echojournal.entries.presentation.util.AudioWaveManager
import kotlinx.coroutines.Dispatchers
import linc.com.amplituda.Amplituda
import com.campus.echojournal.entries.presentation.NewEntryViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val entriesModule = module {
    single {
        Amplituda(androidApplication())
    }

    single { Dispatchers.IO }

    single { AudioWaveManager(get(), get()) }
    viewModel { NewEntryViewModel(get(), get(), get()) }
    viewModel{
        EntriesViewModel(androidApplication(), get())
    }

}
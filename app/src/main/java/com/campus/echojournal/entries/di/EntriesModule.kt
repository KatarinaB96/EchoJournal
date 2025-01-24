package com.campus.echojournal.entries.di

import com.campus.echojournal.entries.presentation.EntriesViewModel
import com.campus.echojournal.entries.presentation.NewEntryViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val entriesModule = module {
    viewModel { NewEntryViewModel(get(), get()) }
    viewModel{
        EntriesViewModel(androidApplication())
    }

}
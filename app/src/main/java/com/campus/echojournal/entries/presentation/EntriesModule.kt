package com.campus.echojournal.entries.presentation

import org.koin.core.module.dsl.viewModel

import org.koin.dsl.module


val entriesModule = module {
    viewModel { NewEntryViewModel(get(), get()) }
}
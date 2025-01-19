package com.campus.echojournal.entries.di

import com.campus.echojournal.entries.presentation.EntriesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val entriesModule = module {
    viewModelOf(
        ::EntriesViewModel
    )
}
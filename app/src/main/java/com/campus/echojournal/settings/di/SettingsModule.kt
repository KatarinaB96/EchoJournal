package com.campus.echojournal.settings.di

import org.koin.core.module.dsl.viewModel
import com.campus.echojournal.settings.presentation.SettingsViewModel
import org.koin.dsl.module

val settingsModule = module {
    viewModel { SettingsViewModel(get(), get()) }
}
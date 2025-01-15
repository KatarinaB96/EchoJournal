package com.campus.echojournal.core.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userPreference")
        val SELECTED_MOOD_INDEX = intPreferencesKey("user_preference")
    }

    suspend fun saveSelectedMoodIndex(index: Int) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_MOOD_INDEX] = index
        }
    }

    val getSavedMoodIndex: Flow<Int> = context.dataStore.data
        .catch { exception ->
            Log.e("DataStoreManager", "Error reading mood index: ${exception.message}")
            emit(emptyPreferences())
        }
        .map { preferences ->
            preferences[SELECTED_MOOD_INDEX] ?: -1
        }
}

package com.example.logger.data

import android.content.Context

interface AppContainer {
    val entryRepository: EntryRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val entryRepository: EntryRepository by lazy {
        OfflineEntryRepository(ExerciseEntryDatabase.getDatabase(context).exerciseEntryDao())
    }
}
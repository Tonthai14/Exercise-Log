package com.example.logger.shared.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.logger.data.EntryRepository
import com.example.logger.data.ExerciseEntry

class EntriesViewModel(private val entryRepository: EntryRepository): ViewModel() {
    var exerciseEntries = mutableStateListOf<ExerciseEntry>()
        private set

    suspend fun loadEntriesByDate(date: String) {
        entryRepository.getEntriesOnDateStream(date).collect {
            exerciseEntries.clear()
            exerciseEntries.addAll(it)
        }
    }
}
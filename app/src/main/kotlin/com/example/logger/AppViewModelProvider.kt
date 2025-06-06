package com.example.logger

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.logger.shared.viewmodels.EntriesViewModel
import com.example.logger.shared.viewmodels.EntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            EntryViewModel(exerciseLoggerApplication().container.entryRepository)
        }
        initializer {
            EntriesViewModel(exerciseLoggerApplication().container.entryRepository)
        }
    }
}

fun CreationExtras.exerciseLoggerApplication(): ExerciseLoggerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ExerciseLoggerApplication)
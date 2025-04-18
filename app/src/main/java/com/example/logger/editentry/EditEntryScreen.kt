package com.example.logger.editentry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logger.AppViewModelProvider
import com.example.logger.shared.viewmodels.EntryViewModel
import com.example.logger.shared.components.ExerciseInstanceForm
import kotlinx.coroutines.launch

@Composable
fun EditEntryScreen(
    id: Long,
    onNavigateBack: () -> Unit,
    viewModel: EntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.loadExistingData(id)
    }

    ExerciseInstanceForm(
        title = "Editing Entry",
        onSaveInstance = {
            coroutineScope.launch {
                viewModel.updateEntry(id)
                onNavigateBack()
            }
        },
        viewModel = viewModel
    )
}
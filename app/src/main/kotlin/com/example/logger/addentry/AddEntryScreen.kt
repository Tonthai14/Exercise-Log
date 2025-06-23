package com.example.logger.addentry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logger.AppViewModelProvider
import com.example.logger.shared.components.ExerciseInstanceForm
import com.example.logger.shared.viewmodels.EntryViewModel
import kotlinx.coroutines.launch

@Composable
fun AddEntryScreen(
    date: String?,
    onNavigateBack: () -> Unit,
    viewModel: EntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    ExerciseInstanceForm(
        title = "Adding Entry",
        onSaveInstance = {
            coroutineScope.launch {
                viewModel.saveEntry(date)
                onNavigateBack()
            }
        },
        onNavigateBack = onNavigateBack,
        viewModel = viewModel
    )
}

@Preview
@Composable
fun AddEntryScreenPreview() {
    AddEntryScreen("2024-04-11", {})
}
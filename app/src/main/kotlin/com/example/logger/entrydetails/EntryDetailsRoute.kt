package com.example.logger.entrydetails

import androidx.compose.runtime.Composable
import com.example.logger.shared.components.ExerciseDetailsCard

@Composable
fun EntryDetailsRoute(
    id: Long,
    onNavigateToEdit: (entryId: Long) -> Unit,
    onNavigateBack: () -> Unit
) {
    ExerciseDetailsCard(
        id = id,
        onNavigateToEdit = onNavigateToEdit,
        onNavigateBack = onNavigateBack
    )
}
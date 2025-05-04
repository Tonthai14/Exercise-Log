package com.example.logger.day

import androidx.compose.runtime.Composable

@Composable
fun DayRoute(
    date: String?,
    onNavigateToAddEntry: (date: String?) -> Unit,
    onNavigateToEntry: (entryId: Long) -> Unit,
    onNavigateToEdit: (entryId: Long) -> Unit
) {
    DayScreen(
        date = date,
        onNavigateToAddEntry = onNavigateToAddEntry,
        onNavigateToEntry = onNavigateToEntry,
        onNavigateToEdit = onNavigateToEdit
    )
}
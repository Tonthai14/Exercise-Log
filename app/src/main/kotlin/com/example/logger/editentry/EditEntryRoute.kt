package com.example.logger.editentry

import androidx.compose.runtime.Composable

@Composable
fun EditEntryRoute(id: Long, onNavigateBack: () -> Unit) {
    EditEntryScreen(id = id, onNavigateBack = onNavigateBack)
}
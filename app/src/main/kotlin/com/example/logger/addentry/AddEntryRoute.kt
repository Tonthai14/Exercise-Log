package com.example.logger.addentry

import androidx.compose.runtime.Composable

@Composable
fun AddEntryRoute(date: String?, onNavigateBack: () -> Unit) {
    AddEntryScreen(date = date, onNavigateBack = onNavigateBack)
}
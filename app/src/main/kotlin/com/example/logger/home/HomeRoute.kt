package com.example.logger.home

import androidx.compose.runtime.Composable

@Composable
fun HomeRoute(
    onNavigateToDay: (date: String) -> Unit
) {
    HomeScreen(onNavigateToDay = onNavigateToDay)
}
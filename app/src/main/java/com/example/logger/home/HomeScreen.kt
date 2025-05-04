package com.example.logger.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(onNavigateToDay: (date: String) -> Unit) {
    val sdf = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
    val calendar = Calendar.getInstance()
    val today: Date = calendar.time

    Scaffold(
        topBar = { HomePageTopBar(onNavigateToDay) }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            DayButton(text = "Today's Exercises", onNavigateToDay, sdf.format(today))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageTopBar(onNavigateToDay: (date: String) -> Unit) {
    var showDatePicker by remember {mutableStateOf(false)}
    var selectedDate by remember { mutableStateOf<Long?>(null) }

    TopAppBar(
        title = {
            Text(text = "Home", fontWeight = FontWeight.Bold)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        actions = {
            IconButton(onClick = {showDatePicker = !showDatePicker}) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select Date")
            }
        }
    )

    if (showDatePicker) {
        DatePickerModal(
            onDateSelected = {
                onNavigateToDay(it)
                println(it)
            },
            onDismiss = {showDatePicker = false}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val sdf = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    println(datePickerState.selectedDateMillis)
                    onDateSelected(sdf.format(datePickerState.selectedDateMillis))
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
fun DayButton(
    text: String,
    onNavigateToDay: (date: String) -> Unit,
    date: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 24.dp)
    ) {
        Button(
            onClick = { onNavigateToDay(date) },
            modifier = modifier.fillMaxWidth()
        ) {
            Text(text = text)
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(onNavigateToDay = {})
}
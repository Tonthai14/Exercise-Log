package com.example.logger.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.logger.font.cascadiaMonoFamily
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HomeScreen(onNavigateToDay: (date: String) -> Unit) {
    val dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy")

    Column(
        verticalArrangement = Arrangement.spacedBy(space = 40.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = Color.hsv(240F, 0.02F, 0.15F))
            .padding(10.dp)
            .fillMaxSize()
    ) {
        QuickAccessSection(onNavigateToDay, dateFormat)
        DatePickerSection(onNavigateToDay, dateFormat)
    }

//    Scaffold(
//        topBar = { HomePageTopBar(onNavigateToDay) }
//    ) {
//        Column(
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .padding(it)
//                .fillMaxSize()
//        ) {
//            DayButton(text = "Today's Exercises", onNavigateToDay, sdf.format(today))
//        }
//    }
}

@Composable
fun QuickAccessSection(onNavigateToDay: (date: String) -> Unit, dateFormat: DateTimeFormatter) {
    val today = LocalDate.now()
    val yesterday = today.minusDays(1)
    val tomorrow = today.plusDays(1)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                color = Color.hsv(269F, 0.05F, 0.35F),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(20.dp)
    ) {
        Text(text = "Quick Access", fontFamily = cascadiaMonoFamily, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onNavigateToDay(today.format(dateFormat)) }) {
            Text(text = "Today")
        }
        Button(onClick = { onNavigateToDay(yesterday.format(dateFormat)) }) {
            Text(text = "Yesterday")
        }
        Button(onClick = { onNavigateToDay(tomorrow.format(dateFormat)) }) {
            Text(text = "Tomorrow")
        }
    }
}

@Composable
fun DatePickerSection(onNavigateToDay: (date: String) -> Unit, dateFormat: DateTimeFormatter) {
    var showDatePicker by remember {mutableStateOf(false)}

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                color = Color.hsv(269F, 0.05F, 0.35F),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(20.dp)
    ) {
        Text("Specific Date", fontFamily = cascadiaMonoFamily, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        IconButton(onClick = {showDatePicker = !showDatePicker}) {
            Icon(
                imageVector = Icons.Default.DateRange,
                tint = Color.White,
                contentDescription = "Select Date",
                modifier = Modifier.size(128.dp)
            )
        }
    }

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
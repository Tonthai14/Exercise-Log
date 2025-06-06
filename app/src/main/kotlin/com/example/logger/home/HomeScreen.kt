package com.example.logger.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.logger.font.cascadiaMonoFamily
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
        val modifier = Modifier
            .fillMaxWidth(0.7F)
            .background(
                color = Color.hsv(269F, 0.05F, 0.35F),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(20.dp)
        QuickAccessSection(onNavigateToDay, dateFormat, modifier = modifier)
        DatePickerSection(onNavigateToDay, dateFormat, modifier = modifier)
    }
}

@Composable
fun QuickAccessSection(onNavigateToDay: (date: String) -> Unit,
                       dateFormat: DateTimeFormatter,
                       modifier: Modifier = Modifier
) {
    val today = LocalDate.now()
    val yesterday = today.minusDays(1)
    val tomorrow = today.plusDays(1)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = "Quick Access", fontFamily = cascadiaMonoFamily, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        val buttonModifier = Modifier.fillMaxWidth(0.8F)
        Button(
            onClick = { onNavigateToDay(today.format(dateFormat)) },
            modifier = buttonModifier
        ) {
            Text(text = "Today")
        }
        Button(
            onClick = { onNavigateToDay(yesterday.format(dateFormat)) },
            modifier = buttonModifier
        ) {
            Text(text = "Yesterday")
        }
        Button(
            onClick = { onNavigateToDay(tomorrow.format(dateFormat)) },
            modifier = buttonModifier
        ) {
            Text(text = "Tomorrow")
        }
    }
}

@Composable
fun DatePickerSection(onNavigateToDay: (date: String) -> Unit,
                      dateFormat: DateTimeFormatter,
                      modifier: Modifier = Modifier
) {
    var showDatePicker by remember {mutableStateOf(false)}

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
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
            },
            onDismiss = {showDatePicker = false},
            dateFormat = dateFormat
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    dateFormat: DateTimeFormatter
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val selectedDate = datePickerState.selectedDateMillis
                    val formattedDate = LocalDate.parse(selectedDate.toString(), dateFormat)
                    onDateSelected(formattedDate.toString())
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

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(onNavigateToDay = {})
}
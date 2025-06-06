package com.example.logger.day

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logger.AppViewModelProvider
import com.example.logger.data.ExerciseEntry
import com.example.logger.font.cascadiaMonoFamily
import com.example.logger.shared.viewmodels.EntriesViewModel
import com.example.logger.shared.viewmodels.EntryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayScreen(
    date: String?,
    onNavigateToAddEntry: (date: String?) -> Unit,
    onNavigateToEntry: (entryId: Long) -> Unit,
    onNavigateToEdit: (entryId: Long) -> Unit,
    viewModel: EntriesViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.loadEntriesByDate(date!!)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = date!!, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = { onNavigateToAddEntry(date) }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Entry")
                    }
                }
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .background(color = Color.hsv(240F, 0.02F, 0.15F))
                .fillMaxSize()
        ) {
            for (entry in viewModel.exerciseEntries) {
                ExerciseInstanceDisplay(entry, onNavigateToEntry, onNavigateToEdit)
            }
        }
    }
}

@Composable
fun ExerciseInstanceDisplay(
    entry: ExerciseEntry,
    onNavigateToEntry: (entryId: Long) -> Unit,
    onNavigateToEdit: (entryId: Long) -> Unit
) {
    var showDetails by remember { mutableStateOf(false) }
    Column(
        verticalArrangement =  Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {
//                Log.d("Day Screen", "Entry ID ${entry.id}")
//                onNavigateToEntry(entry.id)
                showDetails = !showDetails
            }
            .padding(4.dp)
            .heightIn(min = 80.dp)
            .background(color = Color.hsv(260F, 0.03F, .40F), shape = RoundedCornerShape(8.dp))
            .shadow(elevation = 1.dp, shape = RoundedCornerShape(2.dp))
            .fillMaxWidth()
    ) {
        BasicDetailsDisplay(entry)
        AnimatedVisibility(visible = showDetails) {
            ExpandedDetailsDisplay(entry, onNavigateToEntry, onNavigateToEdit)
        }
    }
}

@Composable
fun BasicDetailsDisplay(entry: ExerciseEntry) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = entry.exerciseName,
            fontFamily = cascadiaMonoFamily,
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier.padding(4.dp))
        Text(
            text = "- ${entry.volumeDetails.sets}x${entry.volumeDetails.reps} @ ${entry.resistanceTypeDetails.weightAmount} lbs",
            fontFamily = cascadiaMonoFamily,
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier.padding(4.dp))
    }
}

@Composable
fun ExpandedDetailsDisplay(
    entry: ExerciseEntry,
    onNavigateToEntry: (entryId: Long) -> Unit,
    onNavigateToEdit: (entryId: Long) -> Unit
) {
    var confirmDeletion by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(color = Color.hsv(260F,  0.20F, .20F), shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { onNavigateToEdit(entry.id) }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    tint = Color.White,
                    contentDescription = "Edit entry")
            }
            IconButton(onClick = { onNavigateToEntry(entry.id) }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    tint = Color.White,
                    contentDescription = "View in another screen"
                )
            }
            IconButton(onClick = { confirmDeletion = true }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    tint = Color.White,
                    contentDescription = "Delete entry"
                )
            }
        }
        HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(4.dp))
        DetailRow(label = "Resistance Type", value = "${entry.resistanceTypeDetails.name}")
        DetailRow(label = "Volume", value = "${entry.volumeDetails.plan}")
        HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(4.dp))
    }

    if (confirmDeletion) {
        DeleteEntry(entry.id)
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            textAlign = TextAlign.Right,
            fontWeight = FontWeight.SemiBold,
            fontFamily = cascadiaMonoFamily,
            color = Color.White,
            modifier = Modifier.weight(0.45F))
        Spacer(modifier = Modifier.weight(0.15F))
        Text(
            text = value,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Light,
            fontFamily = cascadiaMonoFamily,
            color = Color.White,
            modifier = Modifier.weight(0.4F))
    }
}

@Composable
fun DeleteEntry(
    id: Long,
    viewModel: EntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.deleteEntry(id)
    }
    Toast.makeText(LocalContext.current, "Entry Deleted", Toast.LENGTH_SHORT).show()
}

@Preview
@Composable
fun DayScreenPreview() {
    DayScreen(
        date = "03-13-2025",
        onNavigateToAddEntry = {},
        onNavigateToEntry = {},
        onNavigateToEdit = {}
    )
}
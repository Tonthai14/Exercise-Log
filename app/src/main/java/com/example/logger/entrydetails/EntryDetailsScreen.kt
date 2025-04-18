package com.example.logger.entrydetails


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logger.AppViewModelProvider
import com.example.logger.shared.viewmodels.EntryViewModel

@Preview
@Composable
fun EntryDetailsScreenPreview() {
    EntryDetailsScreen(id = 1, {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryDetailsScreen(
    id: Long,
    onNavigateToEdit: (entryId: Long) -> Unit,
    viewModel: EntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.loadExistingData(id)
    }

    var showDeleteDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = viewModel.exerciseName, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete Entry")
                    }
                    IconButton(onClick = { onNavigateToEdit(id) }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit Entry")
                    }
                }
            )
        })
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            DetailRow(label = "Intensity", value = "Placeholder")
            DetailRow(label = "Exercise Type", value = viewModel.resistanceType.toString())
            DetailRow(label = "Weight Type", value = "Placeholder")
            DetailRow(label = "Weight", value = viewModel.weightAmount.toString())
            DetailRow(label = "Unit of Measurement", value = viewModel.weightUnitOfMeasurement.toString())
            DetailRow(label = "Program Type", value = "Placeholder")
        }
    }

    if (showDeleteDialog) {
        DeleteEntryDialog(viewModel, id)
    }
}

@Composable
fun DeleteEntryDialog(viewModel: EntryViewModel, id: Long) {
    var dismissAlertDialog by remember { mutableStateOf(false) }
    var confirmDeletion by remember { mutableStateOf(false) }

    if (dismissAlertDialog) return
    AlertDialog(
        title = { Text("Delete Entry") },
        text = { Text("Are you sure you want to delete this entry?") },
        onDismissRequest = { dismissAlertDialog = true },
        confirmButton = {
            TextButton(onClick = { confirmDeletion = true }) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = { dismissAlertDialog = true }) {
                Text("No")
            }
        }
    )

    if (confirmDeletion) {
        EntryDeletion(viewModel, id)
    }
}

@Composable
fun EntryDeletion(viewModel: EntryViewModel, id: Long) {
    LaunchedEffect(Unit) {
        viewModel.deleteEntry(id)
    }
    Toast.makeText(LocalContext.current, "Entry Deleted", Toast.LENGTH_SHORT).show()
}

@Composable
fun DetailRow(label: String, value: String?) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = label, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = value ?: "No Input", fontSize = 18.sp)
    }
}
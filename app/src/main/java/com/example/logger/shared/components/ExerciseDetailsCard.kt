package com.example.logger.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logger.AppViewModelProvider
import com.example.logger.font.cascadiaMonoFamily
import com.example.logger.shared.viewmodels.EntryViewModel

@Composable
fun ExerciseDetailsCard(
    id: Long,
    onNavigateToEdit: (entryId: Long) -> Unit,
    viewModel: EntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.loadExistingData(id)
    }

    Box(
        modifier = Modifier
            .background(color = Color.hsv(264F, 0.08F, 0.17F))
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.hsv(269F, 0.05F, 0.35F))
                .align(Alignment.Center)
                .padding(8.dp)
                .shadow(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            HeaderSection(viewModel)
            HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(4.dp))
            DetailsSection(viewModel)
            HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun HeaderSection(viewModel: EntryViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = viewModel.exerciseName,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            fontFamily = cascadiaMonoFamily,
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun DetailsSection(viewModel: EntryViewModel) {
    DetailRow(label = "Resistance Type", value = "${viewModel.resistanceType?.label}")
    DetailRow(value = "${viewModel.weightAmount} ${viewModel.weightUnitOfMeasurement.name}")
    DetailRow(label = "Volume", value = "${viewModel.exerciseVolume?.label}")
    DetailRow(value = "${viewModel.numberOfSets} sets")
    DetailRow(value = "${viewModel.numberOfReps} reps")
}

@Composable
fun DetailRow(label: String = "EMPTY", value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        var spacerWeight = 0.15F
        if (!label.equals("EMPTY")) {
            Text(
                text = label,
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.SemiBold,
                fontFamily = cascadiaMonoFamily,
                color = Color.White,
                modifier = Modifier.weight(0.45F))
        } else {
            spacerWeight = 0.6F
        }
        Spacer(modifier = Modifier.weight(spacerWeight))
        Text(
            text = value,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Light,
            fontFamily = cascadiaMonoFamily,
            color = Color.White,
            modifier = Modifier.weight(0.4F))
    }
}

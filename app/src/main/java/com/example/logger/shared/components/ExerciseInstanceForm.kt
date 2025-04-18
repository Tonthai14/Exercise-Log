package com.example.logger.shared.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logger.data.fieldoptions.ExerciseStructure
import com.example.logger.data.fieldoptions.ResistanceType
import com.example.logger.data.fieldoptions.WeightMeasurementStandard
import com.example.logger.font.cascadiaMonoFamily
import com.example.logger.shared.viewmodels.EntryViewModel
import kotlinx.coroutines.launch

@Composable
fun ExerciseInstanceForm(
    title: String,
    onSaveInstance: () -> Unit = {},
    viewModel: EntryViewModel = viewModel()
) {
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
            ExerciseNameSection(viewModel)
            Column(modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(12.dp))
            ) {
                ExerciseIntervalPicker()
                ExerciseIntervalSection(viewModel)
                HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(4.dp))
                ResistanceTypePicker()
                WeightSection(viewModel)
            }
            if (onSaveInstance !== {}) {
                Button(
                    shape = RectangleShape,
                    onClick = onSaveInstance,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("SAVE")
                }
            }
        }
    }
}

@Composable
fun ExerciseNameSection(viewModel: EntryViewModel) {
    Row(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .fillMaxWidth()
    ) {
        val exerciseNameConfig = EditableTextFieldConfig(
            label = "Exercise Name",
            value = viewModel.exerciseName,
            onValueChanged = { input: String -> viewModel.onExerciseNameChange(input) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
        )
        EditableTextField(config = exerciseNameConfig)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseIntervalPicker() {
    val types: List<String> = ExerciseStructure.entries.map { value -> value.toString() }
    val pagerState = rememberPagerState(pageCount = {types.size})
    val scope = rememberCoroutineScope()

    Row(modifier = Modifier.fillMaxWidth()) {
        IconButton(modifier = Modifier.weight(0.1F),
            onClick = {
                scope.launch {  pagerState.scrollToPage(pagerState.currentPage - 1) }
            }
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Scroll Option Left")
        }
        HorizontalPager(
            modifier = Modifier
                .weight(0.8F)
                .align(Alignment.CenterVertically),
            state = pagerState,
        ) { index ->
            Text(
                text = types[index],
                textAlign = TextAlign.Center,
                fontFamily = cascadiaMonoFamily,
                modifier = Modifier.fillMaxWidth()
            )
        }
        IconButton(modifier = Modifier.weight(0.1F),
            onClick = {
                scope.launch { pagerState.scrollToPage(pagerState.currentPage + 1) }
            }
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Scroll Option Right")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ResistanceTypePicker() {
    val types: List<String> = ResistanceType.entries.map { value -> value.toString() }
    val pagerState = rememberPagerState(pageCount = {types.size})
    val scope = rememberCoroutineScope()

    Row(modifier = Modifier.fillMaxWidth()) {
        IconButton(modifier = Modifier.weight(0.1F),
            onClick = {
                scope.launch {  pagerState.scrollToPage(pagerState.currentPage - 1) }
            }
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Scroll Option Left")
        }
        HorizontalPager(
            modifier = Modifier
                .weight(0.8F)
                .align(Alignment.CenterVertically),
            state = pagerState,
        ) { index ->
            Text(
                text = types[index],
                textAlign = TextAlign.Center,
                fontFamily = cascadiaMonoFamily,
                modifier = Modifier.fillMaxWidth()
            )
        }
        IconButton(modifier = Modifier.weight(0.1F),
            onClick = {
                scope.launch { pagerState.scrollToPage(pagerState.currentPage + 1) }
            }
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Scroll Option Right")
        }
    }
}


@Composable
fun WeightSection(viewModel: EntryViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.weight(0.75f)
        ) {
            val weightAmountConfig = EditableTextFieldConfig(
                label = "Weight",
                value = if (viewModel.weightAmount != null) viewModel.weightAmount.toString() else "",
                onValueChanged = { input ->
                    viewModel.onWeightAmountChange(if (input.isEmpty()) null else input.toFloat())
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            EditableTextField(config = weightAmountConfig)
        }
        Box(
            modifier = Modifier.weight(0.25f)
        ) {
            val measurementUnits = WeightMeasurementStandard.entries.map { value -> value.toString() }
            DropdownOptions(
                selectableOptions = measurementUnits,
                currentValue = viewModel.weightUnitOfMeasurement.toString(),
                onValueChanged = viewModel::onWeightUnitOfMeasurementChange,
            )
        }
    }
}

@Composable
fun ExerciseIntervalSection(viewModel: EntryViewModel) {
    Row {
        val plannedSetsConfig = EditableTextFieldConfig(
            label = "# Sets",
            value = if (viewModel.numberOfSets != null) viewModel.numberOfSets.toString() else "",
            onValueChanged = { input ->
                viewModel.onNumberOfSetsChange(if (input.isEmpty()) null else input.toInt())
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f),
        )
        EditableTextField(config = plannedSetsConfig)

        val plannedRepsConfig = EditableTextFieldConfig(
            label = "# Reps",
            value = if (viewModel.numberOfReps != null) viewModel.numberOfReps.toString() else "",
            onValueChanged = { input ->
                viewModel.onNumberOfRepsChange(if (input.isEmpty()) null else input.toInt())
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )
        EditableTextField(config = plannedRepsConfig)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDisplay(title: String) {
    TopAppBar(
        title = {
            Text(text = title, fontWeight = FontWeight.Bold)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun DropdownOptions(
    selectableOptions: List<String>,
    currentValue: String,
    onValueChanged: (newValue: String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Button(
        onClick = { expanded = !expanded },
        shape = RectangleShape
    ) {
        Text(text = currentValue)
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = null
        )
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        selectableOptions.forEach { option ->
            DropdownMenuItem(
                text = { Text(text = option ) },
                onClick = {
                    expanded = false
                    onValueChanged(option)
                }
            )
        }
    }
}
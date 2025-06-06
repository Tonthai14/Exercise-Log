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
import androidx.compose.foundation.pager.PagerState
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logger.data.fieldoptions.ExerciseVolume
import com.example.logger.data.fieldoptions.ResistanceType
import com.example.logger.data.fieldoptions.WeightMeasurementStandard
import com.example.logger.font.cascadiaMonoFamily
import com.example.logger.shared.viewmodels.EntryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseInstanceForm(
    title: String,
    onSaveInstance: () -> Unit = {},
    viewModel: EntryViewModel = viewModel()
) {
    val volumePagerState = rememberPagerState(pageCount = {ExerciseVolume.entries.size})
    val resistancePagerState = rememberPagerState(pageCount = {ResistanceType.entries.size})

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
                ExerciseVolumePicker(pagerState = volumePagerState, viewModel = viewModel)
                ExerciseVolumeSection(pagerState = volumePagerState, viewModel = viewModel)
                HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(4.dp))
                ResistanceTypePicker(pagerState = resistancePagerState, viewModel = viewModel)
                ResistanceTypeSection(pagerState = resistancePagerState, viewModel = viewModel)
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
fun ExerciseVolumePicker(pagerState: PagerState, viewModel: EntryViewModel) {
    val types: List<String> = ExerciseVolume.entries.map { value -> value.toString() }
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
            viewModel.onExerciseVolumeChange(types[index])
            Text(
                text = viewModel.exerciseVolume!!.label,
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
fun ExerciseVolumeSection(pagerState: PagerState, viewModel: EntryViewModel) {
    Row {
        val selection = ExerciseVolume.entries.toList()[pagerState.currentPage]
        when (selection) {
            ExerciseVolume.SETS_AND_REPS -> {
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
            ExerciseVolume.SETS_AND_TIME -> {
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

                val plannedTimeConfig = EditableTextFieldConfig(
                    label = "Time",
                    value = if (viewModel.duration != null) viewModel.duration.toString() else "",
                    onValueChanged = { input ->
                        viewModel.onDurationChange(if (input.isEmpty()) null else input.toLong())
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                EditableTextField(config = plannedTimeConfig)
            }
            ExerciseVolume.REPS -> {
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
            ExerciseVolume.TIME -> {
                val plannedTimeConfig = EditableTextFieldConfig(
                    label = "Time",
                    value = if (viewModel.duration != null) viewModel.duration.toString() else "",
                    onValueChanged = { input ->
                        viewModel.onDurationChange(if (input.isEmpty()) null else input.toLong())
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                EditableTextField(config = plannedTimeConfig)
            }
            ExerciseVolume.ONE_REP_MAX -> {
                val plannedRepsConfig = EditableTextFieldConfig(
                    label = "1 Rep Max",
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
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ResistanceTypePicker(pagerState: PagerState, viewModel: EntryViewModel) {
    val types: List<String> = ResistanceType.entries.map { value -> value.toString() }
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
            viewModel.onResistanceTypeChange(types[index])
            Text(
                text = viewModel.resistanceType!!.label,
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
fun ResistanceTypeSection(pagerState: PagerState, viewModel: EntryViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        val selection = ResistanceType.entries.toList()[pagerState.currentPage]
        if (selection == ResistanceType.WEIGHTS || selection == ResistanceType.BODY_WEIGHT) {
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
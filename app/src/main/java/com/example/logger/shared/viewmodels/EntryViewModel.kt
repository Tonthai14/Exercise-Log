package com.example.logger.shared.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.logger.data.EntryRepository
import com.example.logger.data.ExerciseEntry
import com.example.logger.data.fieldoptions.ExerciseVolume
import com.example.logger.data.fieldoptions.ResistanceType
import com.example.logger.data.fieldoptions.WeightMeasurementStandard
import kotlinx.coroutines.flow.first

class EntryViewModel(private val entryRepository: EntryRepository) : ViewModel() {
    var date: String? = null

    var exerciseName by mutableStateOf("")
        private set
    fun onExerciseNameChange(input: String) {
        exerciseName = input
    }

    var resistanceType by mutableStateOf(ResistanceType.WEIGHTS)
        private set
    fun onExerciseTypeChange(input: String) {
        resistanceType = ResistanceType.valueOf(input)
    }

    private var _weightAmount = mutableStateOf<Float?>(null)
    val weightAmount: Float?
        get() = _weightAmount.value
    fun onWeightAmountChange(input: Float?) {
        _weightAmount.value = input
    }

    var weightUnitOfMeasurement by mutableStateOf(WeightMeasurementStandard.LBS)
        private set
    fun onWeightUnitOfMeasurementChange(input: String) {
        weightUnitOfMeasurement = WeightMeasurementStandard.valueOf(input)
    }

    var exerciseVolume by mutableStateOf(ExerciseVolume.SETS_AND_REPS)
        private set
    fun onExerciseStructureChange(input: String) {
        exerciseVolume = ExerciseVolume.valueOf(input)
    }

    private var _numberOfSets = mutableStateOf<Int?>(null)
    val numberOfSets: Int?
        get() = _numberOfSets.value
    fun onNumberOfSetsChange(input: Int?) {
        _numberOfSets.value = input
    }

    private var _numberOfReps = mutableStateOf<Int?>(null)
    val numberOfReps: Int?
        get() = _numberOfReps.value

    fun onNumberOfRepsChange(input: Int?) {
        _numberOfReps.value = input
    }

    suspend fun saveEntry(date: String?) {
        val newEntry = ExerciseEntry(
            id = 0,
            date = date!!,
            exerciseName = exerciseName,
            resistanceType = resistanceType,
            weightAmount = weightAmount,
            weightUnitOfMeasurement = weightUnitOfMeasurement,
            structure = exerciseVolume,
            sets = numberOfSets,
            reps = numberOfReps,
            elapsedTime = null,
        )
        entryRepository.addEntry(newEntry)
    }

    suspend fun updateEntry(id: Long) {
        val editedEntry = ExerciseEntry(
            id = id,
            date = date!!,
            exerciseName = exerciseName,
            resistanceType = resistanceType,
            weightAmount = weightAmount,
            weightUnitOfMeasurement = weightUnitOfMeasurement,
            structure = exerciseVolume,
            sets = numberOfSets,
            reps = numberOfReps,
            elapsedTime = null
        )
        entryRepository.updateEntry(editedEntry)
    }

    suspend fun deleteEntry(id: Long) {
        val entryStream = entryRepository.getEntryStream(id)
        entryRepository.deleteEntry(entryStream.first())
    }

    suspend fun loadExistingData(id: Long) {
        val entryStream = entryRepository.getEntryStream(id)
        entryStream.collect {
            date = it.date
            exerciseName = it.exerciseName
            resistanceType = it.resistanceType
            _weightAmount.value = it.weightAmount
            weightUnitOfMeasurement = it.weightUnitOfMeasurement ?: WeightMeasurementStandard.LBS
            exerciseVolume = it.structure
            _numberOfSets.value = it.sets
            _numberOfReps.value = it.reps
        }
    }
}
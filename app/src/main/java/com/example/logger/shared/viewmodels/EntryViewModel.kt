package com.example.logger.shared.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.logger.data.EntryRepository
import com.example.logger.data.ExerciseEntry
import com.example.logger.data.ExerciseVolumeDetails
import com.example.logger.data.ResistanceTypeDetails
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

    var exerciseVolume by mutableStateOf<ExerciseVolume?>(ExerciseVolume.SETS_AND_REPS)
        private set
    fun onExerciseVolumeChange(input: String) {

        exerciseVolume = ExerciseVolume.from(input)
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

    private var _duration = mutableStateOf<Long?>(null)
    val duration: Long?
        get() = _duration.value
    fun onDurationChange(input: Long?) {
        _duration.value = input
    }

    var resistanceType by mutableStateOf<ResistanceType?>(ResistanceType.WEIGHTS)
        private set
    fun onResistanceTypeChange(input: String) {
        resistanceType = ResistanceType.from(input)
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

    suspend fun saveEntry(date: String?) {
        val newEntry = ExerciseEntry(
            id = 0,
            date = date!!,
            exerciseName = exerciseName,
            volumeDetails = ExerciseVolumeDetails(
                plan = exerciseVolume,
                sets = numberOfSets,
                reps = numberOfReps,
                duration = duration
            ),
            resistanceTypeDetails = ResistanceTypeDetails(
                name = resistanceType,
                weightAmount = weightAmount,
                unitOfMeasurement = weightUnitOfMeasurement
            )
        )
        entryRepository.addEntry(newEntry)
    }

    suspend fun updateEntry(id: Long) {
        val editedEntry = ExerciseEntry(
            id = id,
            date = date!!,
            exerciseName = exerciseName,
            volumeDetails = ExerciseVolumeDetails(
                plan = exerciseVolume,
                sets = numberOfSets,
                reps = numberOfReps,
                duration = duration
            ),
            resistanceTypeDetails = ResistanceTypeDetails(
                name = resistanceType,
                weightAmount = weightAmount,
                unitOfMeasurement = weightUnitOfMeasurement
            )
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
            exerciseVolume = it.volumeDetails.plan
            _numberOfSets.value = it.volumeDetails.sets
            _numberOfReps.value = it.volumeDetails.reps
            _duration.value = it.volumeDetails.duration
            resistanceType = it.resistanceTypeDetails.name
            _weightAmount.value = it.resistanceTypeDetails.weightAmount
            weightUnitOfMeasurement = it.resistanceTypeDetails.unitOfMeasurement ?: WeightMeasurementStandard.LBS
        }
    }
}
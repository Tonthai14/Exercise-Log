package com.example.logger.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.logger.data.fieldoptions.ExerciseVolume
import com.example.logger.data.fieldoptions.ResistanceType
import com.example.logger.data.fieldoptions.WeightMeasurementStandard

@Entity(tableName = "exercise_entry")
data class ExerciseEntry(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val date: String,
    val exerciseName: String,
    @Embedded val volumeDetails: ExerciseVolumeDetails,
    @Embedded val resistanceTypeDetails: ResistanceTypeDetails,
)

data class ExerciseVolumeDetails(
    val plan: ExerciseVolume?,
    val sets: Int?,
    val reps: Int?,
    val duration: Long?,
)

data class ResistanceTypeDetails(
    val name: ResistanceType?,
    val weightAmount: Float?,
    val unitOfMeasurement: WeightMeasurementStandard?,
)
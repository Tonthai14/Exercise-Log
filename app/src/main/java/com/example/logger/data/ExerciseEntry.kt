package com.example.logger.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.logger.data.fieldoptions.ExerciseStructure
import com.example.logger.data.fieldoptions.ResistanceType
import com.example.logger.data.fieldoptions.WeightMeasurementStandard
import kotlin.time.Duration

@Entity(tableName = "exercise_entry")
data class ExerciseEntry(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val date: String,
    val exerciseName: String,
    val resistanceType: ResistanceType,
    val weightAmount: Float?,
    val weightUnitOfMeasurement: WeightMeasurementStandard?,
    val structure: ExerciseStructure,
    val sets: Int?,
    val reps: Int?,
    val elapsedTime: Duration?
)
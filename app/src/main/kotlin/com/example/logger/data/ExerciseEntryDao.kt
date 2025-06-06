package com.example.logger.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseEntryDao {
    @Insert
    suspend fun addEntry(entry: ExerciseEntry)

    @Query("SELECT * FROM exercise_entry WHERE date = :date")
    fun getEntriesOnDate(date: String): Flow<List<ExerciseEntry>>

    @Query("SELECT * FROM exercise_entry WHERE id = :id")
    fun getEntry(id: Long): Flow<ExerciseEntry>

    @Update
    suspend fun updateEntry(entry: ExerciseEntry)

    @Delete
    suspend fun deleteEntry(entry: ExerciseEntry)
}

package com.example.logger.data

import kotlinx.coroutines.flow.Flow

interface EntryRepository {
    fun getEntriesOnDateStream(date: String): Flow<List<ExerciseEntry>>
    fun getEntryStream(id: Long): Flow<ExerciseEntry>
    suspend fun addEntry(entry: ExerciseEntry)
    suspend fun updateEntry(entry: ExerciseEntry)
    suspend fun deleteEntry(entry: ExerciseEntry)
}

class OfflineEntryRepository(private val dao: ExerciseEntryDao): EntryRepository {
    override fun getEntriesOnDateStream(date: String): Flow<List<ExerciseEntry>> =
        dao.getEntriesOnDate(date)
    override fun getEntryStream(id: Long): Flow<ExerciseEntry> =
        dao.getEntry(id)
    override suspend fun addEntry(entry: ExerciseEntry) = dao.addEntry(entry)
    override suspend fun updateEntry(entry: ExerciseEntry) = dao.updateEntry(entry)
    override suspend fun deleteEntry(entry: ExerciseEntry) = dao.deleteEntry(entry)
}
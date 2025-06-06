package com.example.logger.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ExerciseEntry::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ExerciseEntryDatabase : RoomDatabase() {
    abstract fun exerciseEntryDao(): ExerciseEntryDao

    companion object {
        @Volatile
        private var Instance: ExerciseEntryDatabase? = null

        fun getDatabase(context: Context): ExerciseEntryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ExerciseEntryDatabase::class.java, "exercise_entry_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
package com.example.logger

import android.app.Application
import com.example.logger.data.AppContainer
import com.example.logger.data.AppDataContainer

class ExerciseLoggerApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
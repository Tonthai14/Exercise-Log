package com.example.logger

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.logger.font.cascadiaMonoTypography

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExerciseLogNavHost()
        }
    }

    @Composable
    fun GlobalConfigs() {
        MaterialTheme(typography = cascadiaMonoTypography) {}
    }
}
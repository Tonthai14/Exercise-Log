package com.example.logger.data.fieldoptions

enum class ExerciseVolume(val label: String) {
    SETS_AND_REPS("SETS/REPS"),
    SETS_AND_TIME("SETS/TIME"),
    REPS("REPS"),
    TIME("TIMED"),
    ONE_REP_MAX("1 REP MAX");

    override fun toString(): String {
        return label;
    }
}
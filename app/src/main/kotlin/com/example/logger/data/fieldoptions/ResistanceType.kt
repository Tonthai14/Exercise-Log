package com.example.logger.data.fieldoptions

enum class ResistanceType(val label: String) {
    WEIGHTS("WEIGHTS"),
    BODY_WEIGHT("BODY WEIGHT"),
    CARDIO("CARDIO");

    override fun toString(): String {
        return label
    }

    companion object {
        fun from(label: String): ResistanceType? = ResistanceType.entries.find { it.label == label }
    }
}
package com.appspell.sportintervaltimer.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedInterval(
    @PrimaryKey(
        autoGenerate = true
    )
    val id: Int?,
    val order: Int,
    val name: String,
    val sets: Int,
    val workSeconds: Int,
    val restSeconds: Int
)
package com.appspell.sportintervaltimer.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SavedIntervalDao {

    @Query("SELECT * FROM savedinterval")
    fun fetchAll(): List<SavedInterval>

    @Query("SELECT * FROM savedinterval WHERE name LIKE :name LIMIT 1")
    fun fetchByName(name: String): SavedInterval?

    @Query(
        "UPDATE savedinterval SET sets = :sets, workSeconds = :workSeconds, restSeconds = :restSeconds WHERE name LIKE :name"
    )
    fun updateByName(
        name: String,
        sets: Int,
        workSeconds: Int,
        restSeconds: Int
    )

    @Insert
    fun insert(savedInterval: SavedInterval)
}
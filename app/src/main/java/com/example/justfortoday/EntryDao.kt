package com.example.justfortoday

import androidx.room.Dao
import androidx.room.Query

@Dao
interface EntryDao {
    @Query("SELECT * FROM jft")
    fun getAll(): List<Entry>
}
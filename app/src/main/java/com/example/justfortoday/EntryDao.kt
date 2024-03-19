package com.example.justfortoday

import androidx.room.Dao
import androidx.room.Query

@Dao
interface EntryDao {
    @Query("SELECT * FROM jft WHERE month = :month AND day = :day")
    fun findByMonthAndDay(month: Int, day: Int): Entry
}
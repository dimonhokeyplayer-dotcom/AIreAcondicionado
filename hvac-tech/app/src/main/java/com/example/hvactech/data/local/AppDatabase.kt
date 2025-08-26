package com.example.hvactech.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ACUnitEntity::class, ErrorCodeEntity::class, HistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun acUnitDao(): ACUnitDao
    abstract fun errorCodeDao(): ErrorCodeDao
    abstract fun historyDao(): HistoryDao
}


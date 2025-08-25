package com.example.hvactech.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ac_units")
data class ACUnitEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val brand: String,
    val model: String,
    val refrigerant: String,
    val powerKw: Double,
    val factoryChargeGrams: Int,
    val maxPipeLengthMeters: Int,
    val pipeLiquidMm: Double,
    val pipeGasMm: Double,
    val cableIndoorMm2: Double,
    val cableOutdoorMm2: Double
)

@Entity(tableName = "error_codes")
data class ErrorCodeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val brand: String,
    val code: String,
    val description: String,
    val suggestions: String
)

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String,
    val timestamp: Long,
    val input: String,
    val result: String
)


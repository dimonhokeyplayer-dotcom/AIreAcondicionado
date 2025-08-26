package com.example.hvactech.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ACUnitDao {
    @Query("SELECT * FROM ac_units WHERE brand LIKE :brand AND model LIKE :model LIMIT 1")
    suspend fun findByBrandModel(brand: String, model: String): ACUnitEntity?

    @Query("SELECT * FROM ac_units WHERE brand LIKE :brand")
    suspend fun listByBrand(brand: String): List<ACUnitEntity>

    @Query("SELECT * FROM ac_units WHERE refrigerant = :refrigerant")
    suspend fun listByRefrigerant(refrigerant: String): List<ACUnitEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(units: List<ACUnitEntity>)
}

@Dao
interface ErrorCodeDao {
    @Query("SELECT * FROM error_codes WHERE brand = :brand AND code = :code LIMIT 1")
    suspend fun get(brand: String, code: String): ErrorCodeEntity?

    @Query("SELECT * FROM error_codes WHERE brand = :brand")
    suspend fun list(brand: String): List<ErrorCodeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(errors: List<ErrorCodeEntity>)
}

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history ORDER BY timestamp DESC LIMIT :limit")
    suspend fun recent(limit: Int): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: HistoryEntity)
}


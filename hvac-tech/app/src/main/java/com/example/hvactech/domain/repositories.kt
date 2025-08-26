package com.example.hvactech.domain

import com.example.hvactech.data.local.ACUnitDao
import com.example.hvactech.data.local.ACUnitEntity
import com.example.hvactech.data.local.ErrorCodeDao
import com.example.hvactech.data.local.HistoryDao
import com.example.hvactech.data.local.HistoryEntity
import com.example.hvactech.data.remote.ACApiService
import com.example.hvactech.data.remote.AiApiService
import com.example.hvactech.data.remote.AiRequest

class ACRepository(
    private val acUnitDao: ACUnitDao,
    private val errorCodeDao: ErrorCodeDao,
    private val api: ACApiService
) {
    suspend fun findLocal(brand: String, model: String): ACUnitEntity? =
        acUnitDao.findByBrandModel("%$brand%", "%$model%")

    suspend fun byRefrigerant(refrigerant: String) = acUnitDao.listByRefrigerant(refrigerant)

    suspend fun sync(brand: String?, model: String?, refrigerant: String?) {
        val list = api.search(brand, model, refrigerant)
        val mapped = list.map {
            ACUnitEntity(
                brand = it.brand,
                model = it.model,
                refrigerant = it.refrigerant,
                powerKw = it.powerKw,
                factoryChargeGrams = it.factoryChargeGrams,
                maxPipeLengthMeters = it.maxPipeLengthMeters,
                pipeLiquidMm = it.pipeLiquidMm,
                pipeGasMm = it.pipeGasMm,
                cableIndoorMm2 = it.cableIndoorMm2,
                cableOutdoorMm2 = it.cableOutdoorMm2
            )
        }
        acUnitDao.upsertAll(mapped)
    }
}

class AIRepository(private val api: AiApiService) {
    suspend fun ask(brand: String?, model: String?, message: String): String {
        return api.assist(AiRequest(brand, model, message)).answer
    }
}

class HistoryRepository(private val dao: HistoryDao) {
    suspend fun add(type: String, input: String, result: String) {
        dao.insert(HistoryEntity(type = type, input = input, result = result, timestamp = System.currentTimeMillis()))
    }
    suspend fun recent(limit: Int = 50) = dao.recent(limit)
}

class CalculatorRepository(
    private val acDao: ACUnitDao,
    private val history: HistoryRepository
) {
    data class Input(
        val brand: String,
        val model: String,
        val pipeDiameterLiquidMm: Double,
        val pipeDiameterGasMm: Double,
        val pipeLengthMeters: Double
    )

    data class Result(val recommendedExtraGrams: Int, val rationale: String)

    suspend fun calculate(input: Input): Result {
        val unit = acDao.findByBrandModel("%${input.brand}%", "%${input.model}%")
        val base = unit?.factoryChargeGrams ?: 0
        val perMeter = when (unit?.refrigerant) {
            "R32" -> 15.0
            "R410A" -> 20.0
            "R134a" -> 12.0
            else -> 15.0
        }
        val deltaLength = (input.pipeLengthMeters - 5.0).coerceAtLeast(0.0)
        val calc = (deltaLength * perMeter * 0.95) // safety factor: prefer underfill
        val extra = (calc).toInt()
        val rationale = "Base ${base}g + ${extra}g for ${deltaLength}m (refrigerant=${unit?.refrigerant ?: "unknown"})"
        history.add("calculator", input.toString(), "$extra")
        return Result(recommendedExtraGrams = extra, rationale = rationale)
    }
}

class ComparisonRepository(
    private val acDao: ACUnitDao,
    private val history: HistoryRepository
) {
    data class Input(
        val brand: String,
        val model: String,
        val suctionPressureBar: Double,
        val dischargePressureBar: Double,
        val indoorTempC: Double,
        val outdoorTempC: Double
    )
    data class Result(val ok: Boolean, val message: String)

    suspend fun compare(input: Input): Result {
        val unit = acDao.findByBrandModel("%${input.brand}%", "%${input.model}%")
        val expectedSuction = when (unit?.refrigerant) {
            "R32" -> 6.0
            "R410A" -> 8.0
            "R134a" -> 2.0
            else -> 6.0
        }
        val expectedDischarge = when (unit?.refrigerant) {
            "R32" -> 25.0
            "R410A" -> 30.0
            "R134a" -> 12.0
            else -> 25.0
        }
        val suctionOk = (input.suctionPressureBar in (expectedSuction - 1.5)..(expectedSuction + 1.5))
        val dischargeOk = (input.dischargePressureBar in (expectedDischarge - 3.0)..(expectedDischarge + 3.0))
        val ok = suctionOk && dischargeOk
        val msg = if (ok) "Valores correctos" else "Valores fuera de rango"
        history.add("comparison", input.toString(), msg)
        return Result(ok, msg)
    }
}


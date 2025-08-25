package com.example.hvactech.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class ACUnitDto(
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

data class ErrorCodeDto(
    val brand: String,
    val code: String,
    val description: String,
    val suggestions: String
)

interface ACApiService {
    @GET("ac/search")
    suspend fun search(
        @Query("brand") brand: String?,
        @Query("model") model: String?,
        @Query("refrigerant") refrigerant: String?
    ): List<ACUnitDto>
}

data class AiRequest(val brand: String?, val model: String?, val message: String)
data class AiResponse(val answer: String)

interface AiApiService {
    @POST("ai/assist")
    suspend fun assist(@Body body: AiRequest): AiResponse
}


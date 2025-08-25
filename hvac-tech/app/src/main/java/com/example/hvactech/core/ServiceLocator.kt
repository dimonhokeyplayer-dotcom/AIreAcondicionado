package com.example.hvactech.core

import android.content.Context
import androidx.room.Room
import com.example.hvactech.data.local.AppDatabase
import com.example.hvactech.data.remote.ACApiService
import com.example.hvactech.data.remote.AiApiService
import com.example.hvactech.domain.ACRepository
import com.example.hvactech.domain.AIRepository
import com.example.hvactech.domain.CalculatorRepository
import com.example.hvactech.domain.ComparisonRepository
import com.example.hvactech.domain.HistoryRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceLocator {
    lateinit var database: AppDatabase
        private set

    lateinit var acApi: ACApiService
        private set

    lateinit var aiApi: AiApiService
        private set

    lateinit var acRepository: ACRepository
        private set

    lateinit var aiRepository: AIRepository
        private set

    lateinit var historyRepository: HistoryRepository
        private set

    lateinit var calculatorRepository: CalculatorRepository
        private set

    lateinit var comparisonRepository: ComparisonRepository
        private set

    fun init(context: Context) {
        database = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "hvactech.db"
        ).fallbackToDestructiveMigration()
            .build()

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        acApi = retrofit.create(ACApiService::class.java)
        aiApi = retrofit.create(AiApiService::class.java)

        acRepository = ACRepository(database.acUnitDao(), database.errorCodeDao(), acApi)
        aiRepository = AIRepository(aiApi)
        historyRepository = HistoryRepository(database.historyDao())
        calculatorRepository = CalculatorRepository(database.acUnitDao(), historyRepository)
        comparisonRepository = ComparisonRepository(database.acUnitDao(), historyRepository)
    }
}


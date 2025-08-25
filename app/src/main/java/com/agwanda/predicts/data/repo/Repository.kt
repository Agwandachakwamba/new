package com.agwanda.predicts.data.repo

import com.agwanda.predicts.data.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Repository(
    private val oddsApiKey: String,
    private val footballApiKey: String,
    private val oddsBaseUrl: String,
    private val footballBaseUrl: String
) {
    private val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

    private val oddsRetrofit by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        Retrofit.Builder()
            .baseUrl(oddsBaseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }

    private val footballRetrofit by lazy {
        val tokenInterceptor = Interceptor { chain ->
            val original = chain.request()
            val req = original.newBuilder()
                .addHeader("X-Auth-Token", footballApiKey)
                .build()
            chain.proceed(req)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(logging)
            .build()
        Retrofit.Builder()
            .baseUrl(footballBaseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }

    private val oddsApi = oddsRetrofit.create(OddsApi::class.java)
    private val footballApi = footballRetrofit.create(FootballApi::class.java)

    suspend fun fetchSoccerOdds() = withContext(Dispatchers.IO) {
        runCatching { oddsApi.getSoccerOdds(oddsApiKey) }.getOrElse { emptyList() }
    }

    suspend fun fetchScheduledMatches() = withContext(Dispatchers.IO) {
        runCatching { footballApi.getMatches("SCHEDULED").matches }.getOrElse { emptyList() }
    }
}

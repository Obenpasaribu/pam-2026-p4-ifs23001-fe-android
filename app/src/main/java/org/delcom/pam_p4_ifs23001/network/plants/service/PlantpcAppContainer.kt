package org.delcom.pam_p4_ifs23001.network.plants.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.delcom.pam_p4_ifs23001.BuildConfig
import java.util.concurrent.TimeUnit

// 1. Pastikan Interface ini sudah ada di file IPlantpcAppContainer.kt
class PlantpcAppContainer : IPlantpcAppContainer {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
        else HttpLoggingInterceptor.Level.NONE
    }

    private val okHttpClient = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(loggingInterceptor)
        }
        connectTimeout(2, TimeUnit.MINUTES)
        readTimeout(2, TimeUnit.MINUTES)
        writeTimeout(2, TimeUnit.MINUTES)
    }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL_PC_API)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val retrofitService: PlantApiService by lazy {
        retrofit.create(PlantApiService::class.java)
    }

    // 2. Gunakan PlantpcRepository (Implementasi khusus untuk PC)
    override val plantpcRepository: IPlantpcRepository by lazy {
        PlantpcRepository(retrofitService)
    }
}
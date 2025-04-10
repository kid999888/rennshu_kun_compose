/**
 * File Name: Modules.kt
 * Project Name: Rennshu Kun
 * Creator: Gyoushin Ou
 * Creation Date: 2025/04/10
 * Copyright: © 2025 Gyoushin Ou. All rights reserved.
 * Description: DIモジュール
 */

package com.example.rennshukun_compose.di

import android.app.Application
import com.example.rennshukun_compose.data.api.DirectionAPIManager
import com.example.rennshukun_compose.data.repository.HomeRepository
import com.example.rennshukun_compose.data.room.MainDatabase
import com.example.rennshukun_compose.ui.screen.bar_code_scanner.view_model.BarCodeScannerViewModel
import com.example.rennshukun_compose.ui.screen.home.view_model.HomeViewModel
import com.example.rennshukun_compose.ui.screen.notifications.view_model.NotificationsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModules = module {

    // android
    single { androidContext().applicationContext as Application }

    // DemoDatabase
    single { MainDatabase.getDatabase(androidContext()) }

    // Dao
    single { get<MainDatabase>().messageListDao() }

    // API
    // HttpLoggingInterceptor
    single {
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    // OkHttpClient
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single { DirectionAPIManager.provideDirectionsApiService(get()) }

    // Repository
    single { HomeRepository(get()) }

    // ViewModel
    viewModel { HomeViewModel(get()) }
    viewModel { NotificationsViewModel() }
    viewModel { BarCodeScannerViewModel() }
}

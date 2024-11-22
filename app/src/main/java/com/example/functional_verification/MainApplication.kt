package com.example.functional_verification

import android.app.Application
import com.example.functional_verification.data.api.DirectionAPIManager
import com.example.functional_verification.data.repository.HomeRepository
import com.example.functional_verification.data.room.MainDatabase
import com.example.functional_verification.ui.screen.home.viewModel.HomeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MainApplication)
            modules(mainAppModule)
        }
    }

    private val mainAppModule = module {
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
    }
}
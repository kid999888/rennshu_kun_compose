package com.example.functional_verification

import android.app.Application
import com.example.functional_verification.data.repository.HomeRepository
import com.example.functional_verification.data.room.MainDatabase
import com.example.functional_verification.ui.screen.home.viewModel.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext
import org.koin.dsl.module

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

        // Repository
        single { HomeRepository(get()) }

        // ViewModel
        viewModel { HomeViewModel(get()) }
    }
}
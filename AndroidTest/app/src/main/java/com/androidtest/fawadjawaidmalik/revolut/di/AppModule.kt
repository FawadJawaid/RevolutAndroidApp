package com.androidtest.fawadjawaidmalik.revolut.di

import androidx.room.Room
import com.androidtest.fawadjawaidmalik.revolut.data.network.RatesService
import com.androidtest.fawadjawaidmalik.revolut.data.persistance.RevolutDatabase
import com.androidtest.fawadjawaidmalik.revolut.presentation.presenter.MainPresenter
import com.androidtest.fawadjawaidmalik.revolut.presentation.viewmodel.MainViewModel
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val timeOutOfConnection = 15
private const val timeOutOfReading = 30
private const val timeOutOfWriting = 30
private const val apiURL = "https://revolut.duckdns.org/"

// Declaring modules for Koin Dependency Injection.
// ViewModels Module
val vmModule = module {

    // MainViewModel viewModel
    viewModel { MainViewModel() }

}

// Module for JobsRepository
val presenterModule = module {
    factory { MainPresenter(get(), get(), get()) }
}


// Network Module for the API call
val networkModule = module {
    factory { provideOkHttpClient() }
    factory { provideCurrencyApi(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(apiURL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .connectTimeout(timeOutOfConnection.toLong(), TimeUnit.SECONDS)
        .readTimeout(timeOutOfReading.toLong(), TimeUnit.SECONDS)
        .writeTimeout(timeOutOfWriting.toLong(), TimeUnit.SECONDS)
        .build()
}

fun provideCurrencyApi(retrofit: Retrofit): RatesService = retrofit.create(
    RatesService::class.java)

// Database Module
val dbModule = module {

    // Initializing the database, Room database instance.
    single {
        Room.databaseBuilder(androidContext(), RevolutDatabase::class.java,
            "revolutDB")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    // dao instance (get instance from JobsDatabase)
    single { get<RevolutDatabase>().currencyDao() }
}

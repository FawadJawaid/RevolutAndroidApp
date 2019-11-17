package com.androidtest.fawadjawaidmalik.revolut

import android.app.Application
import com.androidtest.fawadjawaidmalik.revolut.di.dbModule
import com.androidtest.fawadjawaidmalik.revolut.di.presenterModule
import com.androidtest.fawadjawaidmalik.revolut.di.networkModule
import com.androidtest.fawadjawaidmalik.revolut.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Author: Muhammad Fawad Jawaid Malik
 * This is the Application class which runs before any class.
 */
class RevolutApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin (dependency injection) context
        startKoin {
            androidContext(this@RevolutApp)
            androidLogger()
            modules(listOf(
                networkModule,
                dbModule,
                vmModule,
                presenterModule
            ))
        }
    }

}
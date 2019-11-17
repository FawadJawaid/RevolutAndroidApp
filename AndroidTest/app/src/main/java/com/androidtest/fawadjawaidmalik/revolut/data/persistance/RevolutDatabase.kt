package com.androidtest.fawadjawaidmalik.revolut.data.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androidtest.fawadjawaidmalik.revolut.domain.model.Rate

/**
 * Author: Muhammad Fawad Jawaid Malik
 * This class is to create Rate items database.
 */
@Database(entities = [(Rate::class)], version = 1, exportSchema = false)
abstract class RevolutDatabase : RoomDatabase() {
    abstract fun currencyDao(): RatesDao
}
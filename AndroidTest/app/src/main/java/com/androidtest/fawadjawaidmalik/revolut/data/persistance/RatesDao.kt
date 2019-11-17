package com.androidtest.fawadjawaidmalik.revolut.data.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androidtest.fawadjawaidmalik.revolut.domain.model.Rate

/**
 * Author: Muhammad Fawad Jawaid Malik
 * This is the DAO (Data Access Object) class for jobs database. It contains all the queries for the database.
 */
@Dao
abstract class RatesDao {

    @Query("SELECT * FROM rates ORDER BY position")
    abstract fun retrieveRates(): List<Rate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(rates: List<Rate>)
}
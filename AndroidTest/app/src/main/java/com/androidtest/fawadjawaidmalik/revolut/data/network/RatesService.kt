package com.androidtest.fawadjawaidmalik.revolut.data.network

import com.androidtest.fawadjawaidmalik.revolut.domain.model.RemoteRate
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Author: Muhammad Fawad Jawaid Malik
 * This is the interface which defines the webservice for getting currency rate items list.
 */
interface RatesService {
    @GET("latest")
    fun latestRates(@Query("base") base: String): Observable<RemoteRate>
}
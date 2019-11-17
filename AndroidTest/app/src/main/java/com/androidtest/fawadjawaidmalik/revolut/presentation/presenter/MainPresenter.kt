package com.androidtest.fawadjawaidmalik.revolut.presentation.presenter

import android.annotation.SuppressLint
import com.androidtest.fawadjawaidmalik.revolut.data.network.RatesService
import com.androidtest.fawadjawaidmalik.revolut.domain.model.Rate
import com.androidtest.fawadjawaidmalik.revolut.data.persistance.RatesDao
import com.androidtest.fawadjawaidmalik.revolut.presentation.view.RateView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Author: Muhammad Fawad Jawaid Malik
 * This is the Presenter/ Repository class which fetches the data from the webservice and database.
 */
class MainPresenter(private val webService: RatesService, private val dao: RatesDao, private val view: RateView) {

    private var loadFromDatabase: Disposable? = null
    private var webServiceObservable: Disposable? = null

    // Function which gets the data from the webservices and saves it in a database which further add it to the view and apply recurring functionality.
    fun getCurrencies(base: Rate, loadFromDB: Boolean) {
        loadFromDatabase.safeDispose()

        if (loadFromDB) {
            loadFromDB()
        }

        webServiceObservable.safeDispose()
        webServiceObservable = webService.latestRates(base.key)
            .map { remoteCurrency -> remoteCurrency.toRateList() }
            .doOnNext { currencies ->
                dao.insertAll(currencies)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                },
                { error -> view.onError(error) },
                { loadFromDB() }
            )
    }

    // Loading data from the database with for recurring emissions.
    @SuppressLint("CheckResult")
    private fun loadFromDB() {
        view.loadingStarted()
        Observable.defer {
            getRecurringEmission(dao.retrieveRates())
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                view::add,
                { error -> throw error },
                {
                    view.loadingCompleted()
                })
    }


    // Creates and returns Observables where each item is emitted after some delay.
    private fun getRecurringEmission(list: List<Rate>): Observable<Rate> {
        return Observable.create { emitter ->
            try {
                for (value in list) {
                    emitter.onNext(value)
                    Thread.sleep(16)
                }
                emitter.onComplete()
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    // Extension functions for Rx classes.
    private fun Disposable?.safeDispose() {
        if (this == null) return
        if (!this.isDisposed) {
            this.dispose()
        }
    }

    fun destroy() {
        loadFromDatabase.safeDispose()
        webServiceObservable.safeDispose()
    }
}

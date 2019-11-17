package com.androidtest.fawadjawaidmalik.revolut.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.androidtest.fawadjawaidmalik.revolut.domain.model.Rate
import com.androidtest.fawadjawaidmalik.revolut.presentation.presenter.MainPresenter

/**
 * Author: Muhammad Fawad Jawaid Malik
 * This is the ViewModel class for Main Screen. It prepares all the data for the activity.
 * And business logic has been kept abstracted from MainActivity.
 * This class has no knowledge whether data is coming from API or Database, so fulfilling complete abstraction of Data Layer.
 * It also handles the configuration changes such as screen rotation.
 */
class MainViewModel : ViewModel() {
    var presenter: MainPresenter? = null
    var currencyCount: Double? = 0.0

    fun getCurrencies(baseRate: Rate, loadFromLocal: Boolean) {
        presenter?.getCurrencies(baseRate, loadFromLocal)
    }

    override fun onCleared() {
        presenter?.destroy()
    }
}
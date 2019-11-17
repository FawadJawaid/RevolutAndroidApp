package com.androidtest.fawadjawaidmalik.revolut.presentation.view

import com.androidtest.fawadjawaidmalik.revolut.domain.model.Rate

/**
 * Author: Muhammad Fawad Jawaid Malik
 * This is the View for interaction with {@link MainPresenter}. We are using MVP pattern.
 */
interface RateView {
    fun onError(error: Throwable)

    fun add(rate: Rate)

    fun loadingStarted()

    fun loadingCompleted()
}
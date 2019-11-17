package com.androidtest.fawadjawaidmalik.revolut

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.androidtest.fawadjawaidmalik.revolut.data.network.RatesService
import com.androidtest.fawadjawaidmalik.revolut.data.persistance.RatesDao
import com.androidtest.fawadjawaidmalik.revolut.presentation.view.RateView
import com.nhaarman.mockito_kotlin.mock
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MainPresenterTest {

    @Suppress("unused")
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    //Insert all rate items test case.
    @Test
    fun `should add all rate items`() {
        val dao: RatesDao =  mock()
        val webservice : RatesService = mock()
        val view : RateView = mock()

    }
}

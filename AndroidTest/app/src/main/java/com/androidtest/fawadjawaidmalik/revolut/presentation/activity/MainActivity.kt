package com.androidtest.fawadjawaidmalik.revolut.presentation.activity

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import com.androidtest.fawadjawaidmalik.revolut.R
import com.androidtest.fawadjawaidmalik.revolut.domain.model.Rate
import com.androidtest.fawadjawaidmalik.revolut.presentation.view.RateView
import com.androidtest.fawadjawaidmalik.revolut.presentation.viewmodel.MainViewModel
import com.androidtest.fawadjawaidmalik.revolut.presentation.adapter.RateAdapter
import com.androidtest.fawadjawaidmalik.revolut.presentation.presenter.MainPresenter
import com.androidtest.fawadjawaidmalik.revolut.presentation.utils.RateDiffUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.getKoin
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

/**
 * Author: Muhammad Fawad Jawaid Malik
 * This is the MainActivity of the application which shows all the Items in RecyclerView.
 */
class MainActivity : AppCompatActivity(){

    private val mainViewModel : MainViewModel by viewModel()
    private val currencyAdapter =
        RateAdapter()
    private val handler: Handler = Handler()
    private val refresh = Runnable {
        mainViewModel.getCurrencies(currentBaseRate, false)
    }
    private var currentBaseRate: Rate =
        Rate("EUR", position = 0)
    var newList = mutableListOf<Rate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.presenter = MainPresenter(getKoin().get(), getKoin().get(), view)
        mainViewModel.getCurrencies(currentBaseRate, true)
        recyclerView.setHasFixedSize(true)

        currencyAdapter.setHasStableIds(true)

        // restore state after screen rotation
        currencyAdapter.inputValue = mainViewModel.currencyCount
        currencyAdapter.interactionListener = object : RateAdapter.InteractionListener {
            override fun itemClicked(item: Rate) {
                if (item == currentBaseRate) {
                    return
                }
                currentBaseRate =
                    Rate(item.key)

                with(currencyAdapter) {
                    // shift selected currency to top of list. All other shift by one down
                    val newList = ArrayList(rates)

                    val index = rates.indexOf(item)
                    for (i in 0 until rates.size) {
                        newList[i].multiplier /= item.multiplier
                        when (i) {
                        // increase position only for items above selected.
                            in 0 until index -> newList[i].position++
                            index -> {
                                newList[i].position = 0
                                newList[i].multiplier = 1.0
                            }
                        }
                    }
                    newList.sortBy { it.position }

                    // evaluate diffs for better performance
                    val diffResult = DiffUtil.calculateDiff(
                        RateDiffUtil(
                            currencyAdapter.rates,
                            newList
                        )
                    )
                    currencyAdapter.rates = newList
                    diffResult.dispatchUpdatesTo(currencyAdapter)

                    // make sure user is on top of the list
                    recyclerView.scrollToPosition(0)
                }
            }
        }
        recyclerView.adapter = currencyAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.currencyCount = currencyAdapter.inputValue
        handler.removeCallbacks(refresh)
    }

    private val view = object :
        RateView {
        override fun onError(error: Throwable) {
            Toast.makeText(this@MainActivity, error.localizedMessage, Toast.LENGTH_LONG).show()
        }

        override fun add(rate: Rate) {
            with(currencyAdapter)
            {
                val index = rates.indexOf(rate)
                when (index) {
                    in 1 until rates.size -> {
                        rates[index] = rate
                        notifyItemChanged(index)
                    }
                    0 -> {
                    }// do nothing
                    else -> {
                        rates.add(rate)
                        notifyItemInserted(rates.size - 1)
                    }
                }
            }
            newList.add(rate)
        }

        override fun loadingStarted() {
        }

        override fun loadingCompleted() {
            handler.removeCallbacks(refresh)
            handler.postDelayed(refresh,
                REFRESH_DELAY
            )
        }
    }

    companion object {
        val REFRESH_DELAY = TimeUnit.SECONDS.toMillis(1)
    }
}

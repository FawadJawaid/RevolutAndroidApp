package com.androidtest.fawadjawaidmalik.revolut.domain.model

/**
 * Author: Muhammad Fawad Jawaid Malik
 * This is the POJO class for response from the web service. Kept class immutable by declaring all fields as val.
 */
class RemoteRate {
    private val base: String? = null
    private val date: String = ""
    private val rates: Map<String, Double> = HashMap()

    // Function for mapping between data models
    fun toRateList(): List<Rate> {
        val list = ArrayList<Rate>()
        var position = 0

        // add base currency
        if (this.base != null) {
            list.add(
                Rate(
                    this.base,
                    position = position++
                )
            )
        }
        for (currency in this.rates) {
            list.add(
                Rate(
                    currency.key,
                    currency.key,
                    currency.value,
                    position++
                )
            )
        }
        return list
    }

}
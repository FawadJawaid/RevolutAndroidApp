package com.androidtest.fawadjawaidmalik.revolut.presentation.utils

import android.content.Context

/**
 * Author: Muhammad Fawad Jawaid Malik
 * This returns the currency name resource id using its symbol
 */
fun getCurrencyNameResId(context: Context, symbol: String) =
        context.resources.getIdentifier("currency_" + symbol + "_name", "string",
                                        context.packageName)
/**
 * Author: Muhammad Fawad Jawaid Malik
 * This returns the currency flag resource id using its symbol
 */
fun getCurrencyFlagResId(context: Context, symbol: String) = context.resources.getIdentifier(
        "ic_" + symbol + "_flag", "drawable", context.packageName)
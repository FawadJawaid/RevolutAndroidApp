package com.androidtest.fawadjawaidmalik.revolut.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import com.androidtest.fawadjawaidmalik.revolut.domain.model.Rate

/**
 * Author: Muhammad Fawad Jawaid Malik
 * This is DiffUtils for Rate.
 */
class RateDiffUtil(private val old: List<Rate>, private val newValues: List<Rate>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].key == newValues[newItemPosition].key
    }

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return newValues.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].contentSame(newValues[newItemPosition])
    }
}
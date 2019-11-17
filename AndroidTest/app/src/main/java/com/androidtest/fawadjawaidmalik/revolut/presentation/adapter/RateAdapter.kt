package com.androidtest.fawadjawaidmalik.revolut.presentation.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidtest.fawadjawaidmalik.revolut.domain.model.Rate
import java.text.NumberFormat
import java.text.ParseException
import com.androidtest.fawadjawaidmalik.revolut.R
import com.androidtest.fawadjawaidmalik.revolut.presentation.utils.getCurrencyFlagResId
import com.androidtest.fawadjawaidmalik.revolut.presentation.utils.getCurrencyNameResId
import java.util.*

/**
 * Author: Muhammad Fawad Jawaid Malik
 * This is an Adapter for displaying list of rates in the RecyclerView.
 */
class RateAdapter : RecyclerView.Adapter<RateAdapter.CurrencyViewHolder>() {
    var rates: MutableList<Rate> = mutableListOf()
    var inputValue: Double? = 0.0
    var numberFormat = NumberFormat.getInstance()!!
    var interactionListener: InteractionListener? = null
    private var baseCountView: EditText? = null

    private val listener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (!s.isNullOrEmpty()) {
                try {
                    inputValue = numberFormat.parse(s.toString()).toDouble()
                    // update all items except the first one
                    notifyItemRangeChanged(1, itemCount - 1)
                } catch (nfe: ParseException) {
                    nfe.printStackTrace()
                }
            }
        }
    }

    private val clickListener = View.OnClickListener { v ->
        val tag = v!!.tag

        // request focus for item that is selected as base
        val futureBaseView = v.findViewById<EditText>(R.id.rateCount)
        futureBaseView.requestFocus()
        futureBaseView.setSelection(futureBaseView.length())

        if (tag is Rate) {
            interactionListener?.itemClicked(tag)
            inputValue = tag.value

            if (baseCountView != futureBaseView) {
                // update text watchers
                baseCountView?.removeTextChangedListener(listener)
                futureBaseView.addTextChangedListener(listener)
            }
        }
    }

    override fun onViewRecycled(holder: CurrencyViewHolder) {
        with(holder) {
            // remove text watcher in case view is recycled
            if (adapterPosition == 0) {
                rateCount.removeTextChangedListener(listener)
            }
        }
        super.onViewRecycled(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rate_item_rv, parent, false)
        return CurrencyViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val item = rates[position]
        item.value = if (position == 0) inputValue else item.multiplier * inputValue!!
        with(holder) {
            val nameId = getCurrencyNameResId(itemView.context, item.key.toLowerCase(Locale.ENGLISH))
            val flagId = getCurrencyFlagResId(itemView.context, item.key.toLowerCase(Locale.ENGLISH))

            rateSymbol.text = itemView.context.getString(nameId)
            rateImage.setImageResource(flagId)

            rateName.text = item.key
            rateCount.tag = item
            parent.tag = item
            rateCount.post { rateCount.setText(numberFormat.format(item.value)) }
            if (position == 0) {
                rateCount.addTextChangedListener(listener)
                baseCountView = rateCount
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return rates[position].key.hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return rates.size
    }

    inner class CurrencyViewHolder(view: View, listener: View.OnClickListener) : RecyclerView.ViewHolder(view) {
        val rateCount: EditText = view.findViewById(R.id.rateCount)
        val rateName: TextView = view.findViewById(R.id.rateName)
        val rateSymbol: TextView = view.findViewById(R.id.rateSymbol)
        val rateImage: ImageView = view.findViewById(R.id.rateFlag)
        val parent: ViewGroup = view.findViewById(R.id.parent_layout)

        init {
            parent.setOnClickListener(listener)
            rateCount.setOnClickListener(listener)
        }

    }

    interface InteractionListener {
        fun itemClicked(item: Rate)
    }
}
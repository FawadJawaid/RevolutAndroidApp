package com.androidtest.fawadjawaidmalik.revolut

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.Espresso.onView
import com.androidtest.fawadjawaidmalik.revolut.presentation.activity.MainActivity
import com.androidtest.fawadjawaidmalik.revolut.presentation.viewmodel.MainViewModel

@RunWith(AndroidJUnit4::class)
class MainActivityTest : AutoCloseKoinTest() {

    @Rule
    @JvmField
    val rule = ActivityTestRule<MainActivity>(MainActivity::class.java, false, false)

    val vm: MainViewModel by inject()

    // Feature unit test for the List. Checking if the list got the data.
    @Test
    fun recyclerViewGotData() {

        rule.launchActivity(null)

        if (getRVCount() > 0) {
            onView(ViewMatchers.withId(R.id.recyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    ViewActions.click()
                )
            )
        }
    }

    private fun getRVCount(): Int {
        val recyclerView =
            rule.activity.findViewById(R.id.recyclerView) as RecyclerView
        return recyclerView.adapter!!.itemCount
    }
}

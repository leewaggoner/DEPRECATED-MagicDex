package com.wreckingball.magicdex

import android.app.Instrumentation
import android.content.Intent
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wreckingball.magicdex.adapters.NewsAdapter
import com.wreckingball.magicdex.models.News
import com.wreckingball.magicdex.ui.home.HomeFragment
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HomeEspressoTest {
//    lateinit var url: String

    @get:Rule
    var intentsRule: IntentsTestRule<MainActivity> = IntentsTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        //TODO: figure a way to get the link from the adapter item
//        val url = getNewsItem0().link!!
    }

    @Test
    fun newsList() {
        val url = "https://magic.wizards.com/en/articles/archive/feature/where-find-ikoria-and-c20-previews-2020-03-31"
        val expectedIntent = allOf(hasAction(Intent.ACTION_VIEW), hasData(url))
        intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))
        onView(withId(R.id.recyclerViewNews))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        intended(expectedIntent)
    }

    private fun getNewsItem0() : News {
        val recyclerView: RecyclerView = intentsRule.activity.findViewById(R.id.recyclerViewNews)
        val adapter = recyclerView.adapter as NewsAdapter
        return adapter.getItem(0)
    }

    @Test
    fun magicDexMenuItem() {
        val mockNavController = mock(NavController::class.java)
        val homeFragment = launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme)

        homeFragment.onFragment {fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        onView(withId(R.id.recyclerViewMenu))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        verify(mockNavController).navigate(ActionOnlyNavDirections(R.id.action_homeFragment_to_magicDexFragment))
    }

    @Test
    fun setsMenuItem() {
        val mockNavController = mock(NavController::class.java)
        val homeFragment = launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme)

        homeFragment.onFragment {fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        onView(withId(R.id.recyclerViewMenu))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        verify(mockNavController).navigate(ActionOnlyNavDirections(R.id.action_homeFragment_to_setsFragment))
    }

    @Test
    fun typesMenuItem() {
        val mockNavController = mock(NavController::class.java)
        val homeFragment = launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme)

        homeFragment.onFragment {fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        onView(withId(R.id.recyclerViewMenu))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        verify(mockNavController).navigate(ActionOnlyNavDirections(R.id.action_homeFragment_to_typesFragment))
    }

    @Test
    fun formatsMenuItem() {
        val mockNavController = mock(NavController::class.java)
        val homeFragment = launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme)

        homeFragment.onFragment {fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        onView(withId(R.id.recyclerViewMenu))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        verify(mockNavController).navigate(ActionOnlyNavDirections(R.id.action_homeFragment_to_formatsFragment))
    }

    @Test
    fun inputText() {
        onView(withId(R.id.editTextSearch))
            .perform(typeText("arachnid"), closeSoftKeyboard())
        onView(withId(R.id.imageViewSearch))
            .perform(click())
        onView(withId(R.id.editTextSearch))
            .check(matches(withText("arachnid")))
    }
}

package com.wwe

import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Test

class CountriesFragmentTest {

    @Test
    fun testSendData() {
        // GIVEN
        val scenario = FragmentScenario.launchInContainer(CountriesFragment::class.java)
        var receivedData: String = ""
        scenario.onFragment { fragment ->
            fragment.parentFragmentManager.setFragmentResultListener(
                KeyConstant.KEY_SELECTED_COUNTRY,
                fragment
            ) { _, bundle ->
                receivedData = bundle.getString("name").toString()
            }
        }

        // WHEN
        onView(withId(R.id.button)).perform(click())

        // THEN
        assert(receivedData == "Roman")
    }

    // @Test
    //fun shouldReceiveData() {
    //    val scenario = FragmentScenario.launchInContainer(FragmentA::class.java)
    //
    //    // Pass data using the parent fragment manager
    //    scenario.onFragment { fragment ->
    //        val data = bundleOf(KEY_DATA to "value")
    //        fragment.parentFragmentManager.setFragmentResult("aKey", data)
    //    }
    //
    //    // Verify data is received, for example, by verifying it's been displayed on the UI
    //   onView(withId(R.id.textView)).check(matches(withText("value")))
    //}
}
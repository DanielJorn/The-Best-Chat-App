package com.danjorn.views


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.action.ViewActions.typeTextIntoFocusedView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.danjorn.features.login.SignUpActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SignUpUsernameTest {

    @Rule
    @JvmField
    val rule: ActivityTestRule<SignUpActivity> = ActivityTestRule(SignUpActivity::class.java)

    @Test
    fun whenTypeNameSmallDelay_notShowErrorImage() {

    }


    /** This function extends Int which is id of edit text we're going to fill.
     *  The function types in [text] making a pause in [delay] milliseconds.
     *  Use it when you need to test on-fly validation or auto-completion logic.
     */
    private fun Int.typeDelay(text: String, delay: Long) {
        if (text.isEmpty()) return

        onView(withId(this)).perform(typeText(text.substring(0, 1)))

        for (i in 1 until text.length) {
            Thread.sleep(delay)
            onView(withId(this)).perform(typeTextIntoFocusedView(text.substring(i, i + 1)))
        }
    }
}

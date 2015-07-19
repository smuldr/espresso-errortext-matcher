/*
 * Copyright 2015 Steven Mulder
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package smuldr.espresso.errormatcher;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Example tests using the {@link ErrorTextMatchers} class to match error texts.
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

  @Rule
  public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

  @Test
  public void checkWithRawText() {
    Espresso
        .onView(ViewMatchers.withId(R.id.email_sign_in_button))
        .perform(ViewActions.click());
    Espresso
        .onView(ViewMatchers.withId((R.id.email)))
        .check(ViewAssertions.matches(ErrorTextMatchers.withErrorText("This field is required")));
  }

  @Test
  public void checkWithStringId() {
    Espresso
        .onView(ViewMatchers.withId((R.id.email)))
        .perform(ViewActions.typeText("invalid"));
    Espresso
        .onView(ViewMatchers.withId(R.id.email_sign_in_button))
        .perform(ViewActions.click());
    Espresso
        .onView(ViewMatchers.withId((R.id.email)))
        .check(ViewAssertions.matches(ErrorTextMatchers.withErrorText(R.string.error_invalid_email)));
  }

  @Test
  public void checkWithMatcher() {
    Espresso
        .onView(ViewMatchers.withId((R.id.email)))
        .perform(ViewActions.typeText("user@example.com"));
    Espresso
        .onView(ViewMatchers.withId((R.id.password)))
        .perform(ViewActions.typeText("foo"));
    Espresso
        .onView(ViewMatchers.withId(R.id.email_sign_in_button))
        .perform(ViewActions.click());
    Espresso
        .onView(ViewMatchers.withId((R.id.password)))
        .check(ViewAssertions.matches(ErrorTextMatchers.withErrorText(Matchers.containsString("too short"))));
  }
}

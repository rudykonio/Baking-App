package rodionkonioshko.com.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class BakingAppBasicTests
{
    private IdlingResource mIdlingResource;
    //random number between 0-3 that we will test with,to not test always the same number.
    private int randPosition;
    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    public BakingAppBasicTests()
    {
        Random rand = new Random();
        randPosition = rand.nextInt(3);
    }

    @Before
    public void registerIdlingResource()
    {
        mIdlingResource = mMainActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    /**
     * This test will check if we retrieved the network data and the image Recycler at a random possible position is clickable
     */
    @Test
    public void mainActivityBasicTest()
    {
        onView(withId(R.id.recyclerview_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(randPosition, click()));
    }

    /**
     * This test will check if we can access the IngredientsActivity and we can scroll all the way down,meaning all items were handled correctly in Recycler.
     */
    @Test
    public void ingredientsActivityBasicTest()
    {
        mainActivityBasicTest();
        onView(withId(R.id.ingredientsTextView)).perform(click());
        onView(withId(R.id.recyclerview_ingredients)).perform
                (RecyclerViewActions.actionOnItemAtPosition(IngredientsActivity.ingredientsLengthForTest - 1, scrollTo()));
    }

    /**
     * This test will check if the ingredients TextView within the RecipeActivity exists by finding it by its name, and attempt to click it.
     */
    @Test
    public void RecipeActivityBasicTest()
    {
        mainActivityBasicTest();
        onView(withText("ingredients")).perform(click());
    }


    @After
    public void unregisterIdlingResource()
    {
        if (mIdlingResource != null)
        {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }


}

package rodionkonioshko.com.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import java.util.ArrayList;

import rodionkonioshko.com.bakingapp.data.Ingredient;
import rodionkonioshko.com.bakingapp.data.RecipeAdapter;
import rodionkonioshko.com.bakingapp.data.Steps;


public class RecipeActivity extends AppCompatActivity
{
    //recipe id
    private int mId;

    //recipe name
    private String mName;

    //recipe ingredients,will map an array of ingredients
    private Ingredient[] mIngredients;

    //recipe steps,will map an array of steps to make the recipe
    static Steps[] mSteps;

    //number of servings
    private int mServings;

    private CardView mCardView;

    public static ArrayList<String> shortDescription;

    private MasterFragment masterFragment;

    static int positionRecycler = -1;

    static int mPosition;
    static boolean backPressed = false;
    private DetailFragment detailFragment;

    //flag that will indicate if we need to change the video rotation
    private boolean isDetailFragmentCreated;
    private long pausePosition;
    private int clickedPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
        {
            returnIntentExtras();
        } else
        {
            returnSavedInstanceData(savedInstanceState);
        }
        putShortDescription();

        if (savedInstanceState != null)
        {
            if (masterFragment == null)
            {
                masterFragment = savedInstanceState.getParcelable("fragment");
            }
            if (savedInstanceState.getInt("positionRecycler") >= 0)
            {
                positionRecycler = savedInstanceState.getInt("positionRecycler");
            }

            if (savedInstanceState.getBoolean("isDetailFragmentCreated"))
            {
                isDetailFragmentCreated = savedInstanceState.getBoolean("isDetailFragmentCreated");
            }

            if (savedInstanceState.getLong("pausePosition") >= 0)
            {
                pausePosition = savedInstanceState.getLong("pausePosition");
            }

            if (savedInstanceState.getInt("clickedPosition") >= 0)
            {
                clickedPosition = savedInstanceState.getInt("clickedPosition");
            }
        }

        setContentView(R.layout.activity_recipe);
        ingredientsOnClickListener();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putInt(RecipeAdapter.ID, mId);
        outState.putString(RecipeAdapter.NAME, mName);
        outState.putParcelableArray(RecipeAdapter.INGREDIENTS, mIngredients);
        outState.putParcelableArray(RecipeAdapter.STEPS, mSteps);
        outState.putInt(RecipeAdapter.SERVINGS, mServings);
        outState.putInt("positionRecycler", MasterFragment.mLayoutManager.findFirstCompletelyVisibleItemPosition());
        outState.putBoolean("backPressed", backPressed);

        outState.putBoolean("isDetailFragmentCreated", DetailFragment.isDetailFragmentCreated);
        outState.putLong("pausePosition", DetailFragment.pausePosition);
        outState.putInt("clickedPosition", DetailFragment.clickedPosition);

    }

    void returnSavedInstanceData(@Nullable Bundle savedInstanceState)
    {
        if (savedInstanceState != null)
        {
            mId = savedInstanceState.getInt(RecipeAdapter.ID);
            mName = savedInstanceState.getString(RecipeAdapter.NAME);

            Parcelable[] ingredientsParcel = savedInstanceState.getParcelableArray(RecipeAdapter.INGREDIENTS);
            if (ingredientsParcel != null)
            {
                mIngredients = new Ingredient[ingredientsParcel.length];
                for (int i = 0; i < ingredientsParcel.length; i++)
                {
                    mIngredients[i] = (Ingredient) ingredientsParcel[i];
                }
            }

            Parcelable[] stepsParcel = savedInstanceState.getParcelableArray(RecipeAdapter.STEPS);
            if (stepsParcel != null)
            {
                mSteps = new Steps[stepsParcel.length];
                for (int i = 0; i < stepsParcel.length; i++)
                {
                    mSteps[i] = (Steps) stepsParcel[i];
                }
            }

            mServings = savedInstanceState.getInt(RecipeAdapter.SERVINGS);


            if (savedInstanceState.containsKey("backPressed"))
            {
                backPressed = savedInstanceState.getBoolean("backPressed");
            }

        }
    }

    void returnIntentExtras()
    {
        if (getIntent().getExtras() != null)
        {
            mId = getIntent().getExtras().getInt(RecipeAdapter.ID);
            mName = getIntent().getExtras().getString(RecipeAdapter.NAME);

            Bundle ingredientsBundle = getIntent().getExtras().getBundle(RecipeAdapter.INGREDIENTS_BUNDLE);
            if (ingredientsBundle != null)
            {
                Parcelable[] ingredientsParcelableArray = ingredientsBundle.getParcelableArray(RecipeAdapter.INGREDIENTS);
                if (ingredientsParcelableArray != null)
                {
                    mIngredients = new Ingredient[ingredientsParcelableArray.length];
                    for (int i = 0; i < ingredientsParcelableArray.length; i++)
                    {
                        mIngredients[i] = (Ingredient) ingredientsParcelableArray[i];
                    }
                }
            }

            Bundle stepsBundle = getIntent().getExtras().getBundle(RecipeAdapter.STEPS_BUNDLE);
            if (stepsBundle != null)
            {
                Parcelable[] stepsParcelableArray = stepsBundle.getParcelableArray(RecipeAdapter.STEPS);
                if (stepsParcelableArray != null)
                {
                    mSteps = new Steps[stepsParcelableArray.length];
                    for (int i = 0; i < stepsParcelableArray.length; i++)
                    {
                        mSteps[i] = (Steps) stepsParcelableArray[i];
                    }
                }
            }

            mServings = getIntent().getExtras().getInt(RecipeAdapter.SERVINGS);
        }
    }

    void ingredientsOnClickListener()
    {
        mCardView = findViewById(R.id.recipe_card);
        mCardView.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, IngredientsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArray(RecipeAdapter.INGREDIENTS, mIngredients);
            intent.putExtra(RecipeAdapter.INGREDIENTS_BUNDLE, bundle);
            startActivity(intent);
        });
    }

    private void putShortDescription()
    {
        shortDescription = new ArrayList<>();
        for (int i = 0; i < mSteps.length; i++)
        {
            shortDescription.add(mSteps[i].getmShortDescription());
        }
    }

    @Override
    protected void onStart()
    {
        if (masterFragment == null)
        {
            masterFragment = new MasterFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArray(RecipeAdapter.STEPS, mSteps);
            masterFragment.setArguments(bundle);
        }

        if (!isTablet(this) && !DetailFragment.isDetailFragmentCreated)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.testframe, masterFragment).commit();

        } else if (!isTablet(this) && DetailFragment.isDetailFragmentCreated)
        {
            detailFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("clickedPosition", DetailFragment.clickedPosition);
            bundle.putLong("pausePosition", DetailFragment.pausePosition);
            detailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.testframe, detailFragment).commit();
            findViewById(R.id.recipe_card).setVisibility(View.GONE);
        } else
        {
            getSupportFragmentManager().beginTransaction().add(R.id.testframe, masterFragment).commit();
            detailFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("clickedPosition", 0);
            detailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.tabFrameLayout600, detailFragment).commit();
        }

        super.onStart();
    }

    @Override
    protected void onPause()
    {
        getSupportFragmentManager().beginTransaction().remove(masterFragment).commit();
        super.onPause();
    }

    static boolean isTablet(Context context)
    {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    @Override
    public void onBackPressed()
    {

        backPressed = true;
        DetailFragment.isOnBackPressed = true;
        positionRecycler = 0;
        DetailFragment.isDetailFragmentCreated = false;
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp()
    {

        backPressed = true;
        DetailFragment.isOnNavigationUpPressed = true;
        positionRecycler = 0;
        DetailFragment.isDetailFragmentCreated = false;
        return super.onSupportNavigateUp();
    }

}

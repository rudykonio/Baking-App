package rodionkonioshko.com.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import rodionkonioshko.com.bakingapp.data.Ingredient;
import rodionkonioshko.com.bakingapp.data.IngredientsAdapter;
import rodionkonioshko.com.bakingapp.data.RecipeAdapter;
import rodionkonioshko.com.bakingapp.widget.WidgetUpdateService;

public class IngredientsActivity extends AppCompatActivity
{
    //recipe ingredients,will map an array of ingredients
    private Ingredient[] mIngredients;
    private RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager = null;
    static int ingredientsLengthForTest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        if (savedInstanceState == null)
        {
            returnIntentExtras();
            initializeScreen();
        } else
        {
            returnSavedInstanceData(savedInstanceState);
            initializeScreen();
        }

        //method will start the widget service
        startWidgetService();

        ingredientsLengthForTest = mIngredients.length;
    }


    private void returnIntentExtras()
    {
        if (getIntent().getExtras() != null)
        {
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
        }
    }

    private void returnSavedInstanceData(@Nullable Bundle savedInstanceState)
    {
        if (savedInstanceState != null)
        {
            Parcelable[] ingredientsParcel = savedInstanceState.getParcelableArray(RecipeAdapter.INGREDIENTS);
            if (ingredientsParcel != null)
            {
                mIngredients = new Ingredient[ingredientsParcel.length];
                for (int i = 0; i < ingredientsParcel.length; i++)
                {
                    mIngredients[i] = (Ingredient) ingredientsParcel[i];
                }
            }
        }
    }

    /**
     * method will initialize the screen of the IngredientsActivity
     */
    private void initializeScreen()
    {
        mRecyclerView = findViewById(R.id.recyclerview_ingredients);
        mLinearLayoutManager = new LinearLayoutManager(IngredientsActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(mIngredients);
        mRecyclerView.setAdapter(ingredientsAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(IngredientsActivity.this,
                DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray(RecipeAdapter.INGREDIENTS, mIngredients);
    }

    /**
     * will trigger the WidgetUpdateService to update the Widget to the last recipe that the user has seen.
     */
    void startWidgetService()
    {
        Intent i = new Intent(this, WidgetUpdateService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(MainActivity.INGREDIENTS, mIngredients);
        i.putExtra(MainActivity.BUNDLE, bundle);
        i.setAction(WidgetUpdateService.WIDGET_UPDATE_ACTION);
        startService(i);
    }
}

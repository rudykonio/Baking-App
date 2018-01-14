package rodionkonioshko.com.bakingapp;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import rodionkonioshko.com.bakingapp.data.RecipeAdapter;
import rodionkonioshko.com.bakingapp.data.Steps;


/**
 * class that will represent The Master class in the Master-Detail pattern
 */
public class MasterFragment extends Fragment
{

    RecyclerView recyclerView;
    public static int top = -1;
    public static LinearLayoutManager mLayoutManager;
    private ArrayList<Steps> mSteps;

    public MasterFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_master, container, false);
        recyclerView = rootView.findViewById(R.id.list_view_master);
        mSteps = getStepsArrayList();
        ShortDescAdapter shortDescAdapter = new ShortDescAdapter(RecipeActivity.shortDescription, mSteps);
        recyclerView.setAdapter(shortDescAdapter);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        return rootView;
    }

    @Override
    public void onResume()
    {
        mLayoutManager.scrollToPosition(RecipeActivity.positionRecycler);
        super.onResume();
        View v = recyclerView.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - recyclerView.getPaddingTop());
    }



    ArrayList<Steps> getStepsArrayList()
    {
        Steps[] stepsArr = null;
        Parcelable[] steps = getArguments().getParcelableArray(RecipeAdapter.STEPS);
        if (steps != null)
        {
            stepsArr = new Steps[steps.length];
            for (int i = 0; i < steps.length; i++)
            {
                stepsArr[i] = (Steps) steps[i];
            }
        }
        return new ArrayList<>(Arrays.asList(stepsArr));
    }

}

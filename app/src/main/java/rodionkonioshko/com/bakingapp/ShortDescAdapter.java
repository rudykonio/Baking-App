package rodionkonioshko.com.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import rodionkonioshko.com.bakingapp.data.Steps;

public class ShortDescAdapter extends RecyclerView.Adapter<ShortDescAdapter.ViewHolder>
{
    private static final String TAG = ShortDescAdapter.class.getSimpleName();
    private final ArrayList<String> mShortDescription;
    private final ArrayList<Steps> mSteps;
    private DetailFragment detailFragment;
    private Context context;

    public ShortDescAdapter(ArrayList<String> mShortDescription, ArrayList<Steps> mSteps)
    {
        this.mShortDescription = mShortDescription;
        this.mSteps = mSteps;
    }


    @Override
    public ShortDescAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.short_description_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new ShortDescAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShortDescAdapter.ViewHolder holder, int position)
    {
        holder.shortDescriptionTextView.setText(mShortDescription.get(position));
        if (!RecipeActivity.isTablet(context))
            holder.shortDescriptionTextView.setOnClickListener(v ->
            {
                detailFragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("clickedPosition", position);
                detailFragment.setArguments(bundle);
                ((RecipeActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.testframe, detailFragment).commit();
                ((RecipeActivity) context).findViewById(R.id.recipe_card).setVisibility(View.GONE);
            });
        else
        {
            holder.shortDescriptionTextView.setOnClickListener(v ->
            {
                detailFragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("clickedPosition", position);
                detailFragment.setArguments(bundle);
                ((RecipeActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.tabFrameLayout600, detailFragment).commit();
            });
        }
    }

    @Override
    public int getItemCount()
    {
        if (mShortDescription == null)
            return 0;
        return mShortDescription.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView shortDescriptionTextView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            shortDescriptionTextView = itemView.findViewById(R.id.mShortDescription);
        }
    }
}

package com.ar.bakingapp.activities.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ar.bakingapp.R;
import com.ar.bakingapp.SimpleIdlingResource;
import com.ar.bakingapp.activities.recipe.RecipeActivity;
import com.ar.bakingapp.adapters.RecipeAdapter;
import com.ar.bakingapp.network.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements RecipeAdapter.ListItemOnClickListener {

    @Nullable
    public static SimpleIdlingResource mIdlingResource;
    HomeViewModel viewModel;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    RecipeAdapter adapter;
    List<Recipe> list;
    RecyclerView.LayoutManager layoutManager;
    private String TAG = HomeActivity.class.getName();

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public static IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            synchronized (HomeActivity.class) {
                if (mIdlingResource == null) {
                    mIdlingResource = new SimpleIdlingResource();
                }
            }
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        getIdlingResource();
        mIdlingResource.setIdleState(false);
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        viewModel.init();

        bindAdapter();

        setupObjserver();
    }

    private void setupObjserver() {
        viewModel.getRecipesList().observe(this, recipes -> {
            adapter.addAllItem(recipes);
            mIdlingResource.setIdleState(true);
        });
    }

    private void bindAdapter() {
        adapter = new RecipeAdapter(list, this);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = new GridLayoutManager(this, 3);
            // In landscape
        } else {
            // In portrait
            layoutManager = new GridLayoutManager(this, 1);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(Recipe selectedObject, View view) {
        Log.d(TAG, "clicked :" + selectedObject.getName());
        RecipeActivity.startRecipeActivity(this, selectedObject.getId());
    }
}

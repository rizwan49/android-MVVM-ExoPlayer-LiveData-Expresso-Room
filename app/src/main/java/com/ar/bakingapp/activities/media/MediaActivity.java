package com.ar.bakingapp.activities.media;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.ar.bakingapp.R;
import com.ar.bakingapp.fragments.PlayerFragment;
import com.ar.bakingapp.network.model.Recipe;
import com.ar.bakingapp.network.model.StepsItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ar.bakingapp.activities.recipe.RecipeActivity.RECIPE_ID;
import static com.ar.bakingapp.activities.recipe.RecipeActivity.SELECTED_POSITION;

public class MediaActivity extends AppCompatActivity implements PlayerFragment.OnFragmentInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Recipe selectedRecipe;
    List<StepsItem> list;
    PlayerFragment mediaPlayerFragment;
    private MediaViewModel viewModel;
    private int selectedRecipeId;
    private int selectedPosition;
    private String TAG = MediaActivity.class.getName();

    public static void startMediaActivity(Context context, int id, int position) {
        Intent intent = new Intent(context, MediaActivity.class);
        intent.putExtra(RECIPE_ID, id);
        intent.putExtra(SELECTED_POSITION, position);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(MediaViewModel.class);
        selectedRecipeId = getIntent().getIntExtra(RECIPE_ID, -1);
        selectedPosition = getIntent().getIntExtra(SELECTED_POSITION, -1);
        if (selectedRecipeId == -1 || selectedPosition == -1) {
            doFinish();
            return;
        }

        init();
        setupObserver();


    }

    private void init() {
        viewModel.init(selectedRecipeId);
        mediaPlayerFragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.mediaFragment);
    }

    private void setupObserver() {
        viewModel.getRecipes().observe(this, recipe -> {
            if (recipe == null) {
                doFinish();
                return;
            }
            selectedRecipe = recipe;
            list = recipe.getSteps();
            setupToolbar(list.get(selectedPosition).getShortDescription() + "");
            mediaPlayerFragment.setListofSteps(list, selectedPosition);
            // FIXME: 06/01/19 add fra
        });
    }

    private void doFinish() {
        Toast.makeText(this, getString(R.string.recipe_not_selected), Toast.LENGTH_LONG).show();
        finish();
    }

    private void setupToolbar(String recipeName) {
        toolbar.setTitle(recipeName);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(view -> onBackPressed());

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(TAG, "uri:" + uri);
    }
}

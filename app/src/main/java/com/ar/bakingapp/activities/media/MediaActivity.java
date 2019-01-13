package com.ar.bakingapp.activities.media;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ar.bakingapp.R;
import com.ar.bakingapp.activities.home.HomeActivity;
import com.ar.bakingapp.fragments.PlayerFragment;
import com.ar.bakingapp.network.model.Recipe;
import com.ar.bakingapp.network.model.StepsItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ar.bakingapp.activities.recipe.RecipeActivity.RECIPE_ID;
import static com.ar.bakingapp.activities.recipe.RecipeActivity.SELECTED_POSITION;

public class MediaActivity extends AppCompatActivity {

    Recipe selectedRecipe;
    List<StepsItem> list;
    PlayerFragment mediaPlayerFragment;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.btnPrev)
    Button btnPrev;
    @BindView(R.id.viewBottom)
    View bottomView;
    private MediaViewModel viewModel;
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
        setContentView(R.layout.media_fragment);
        ButterKnife.bind(this);
        HomeActivity.getIdlingResource();
        HomeActivity.mIdlingResource.setIdleState(false);
        viewModel = ViewModelProviders.of(this).get(MediaViewModel.class);
        if (viewModel.selectedPosition == 0) {
            viewModel.selectedRecipeId = getIntent().getIntExtra(RECIPE_ID, -1);
            viewModel.selectedPosition = getIntent().getIntExtra(SELECTED_POSITION, -1);
        }
        if (viewModel.selectedRecipeId == -1 || viewModel.selectedPosition == -1) {
            doFinish();
            return;
        }

        init();
        setupObserver();

        onConfigurationChanged(getResources().getConfiguration().orientation);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void onConfigurationChanged(int orientation) {
        // Checks the orientation of the screen
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUiFullScreen();
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            showSystemUiPortrait();
        }
    }

    private void showSystemUiPortrait() {
        bottomView.setVisibility(View.VISIBLE);
        getSupportActionBar().show();
        mediaPlayerFragment.showView();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private void hideSystemUiFullScreen() {
        bottomView.setVisibility(View.GONE);
        getSupportActionBar().hide();
        mediaPlayerFragment.hideView();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void init() {
        viewModel.init(viewModel.selectedRecipeId);
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
            setupInfo(list.get(viewModel.selectedPosition));
            setupToolbar(recipe.getName());
        });
    }

    private void setupInfo(StepsItem item) {
        mediaPlayerFragment.setStepInfo(item);
    }

    private void doFinish() {
        Toast.makeText(this, getString(R.string.recipe_not_selected), Toast.LENGTH_LONG).show();
        finish();
    }

    private void setupToolbar(String recipeName) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(recipeName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    @OnClick(R.id.btnPrev)
    public void doPrev() {
        viewModel.selectedPosition--;
        if (viewModel.selectedPosition < 0) {
            viewModel.selectedPosition++;
            return;
        }
        setupInfo(list.get(viewModel.selectedPosition));
    }

    @OnClick(R.id.btnNext)
    public void doNext() {
        viewModel.selectedPosition++;
        if (viewModel.selectedPosition >= list.size()) {
            viewModel.selectedPosition--;
            return;
        }
        HomeActivity.mIdlingResource.setIdleState(false);
        setupInfo(list.get(viewModel.selectedPosition));
    }

}

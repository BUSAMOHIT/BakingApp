package com.oganbelema.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.oganbelema.bakingapp.Constants;
import com.oganbelema.bakingapp.R;
import com.oganbelema.network.data.Recipe;

public class MainActivity extends AppCompatActivity {

    private NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        handleIntentFromWidget(isTablet);

        if (isTablet) {
            navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.tabletContainer);
        } else {
            navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.mainContainer);
        }

        if (navHostFragment != null) {
            navHostFragment.getNavController().addOnDestinationChangedListener(
                    (controller, destination, arguments) -> {
                        if (getSupportActionBar() != null) {
                            if (isTablet) {
                                if (destination.getId() != R.id.ingredientAndStepFragment) {
                                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                                } else {
                                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                                }
                            } else {
                                if (destination.getId() != R.id.recipeFragment) {
                                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                                } else {
                                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                                }
                            }
                        }
                    }
            );
        }

    }

    private void handleIntentFromWidget(boolean isTablet) {
        Intent intent = getIntent();

        if (intent.hasExtra(Constants.RECIPE_KEY)) {
            Recipe recipe = intent.getParcelableExtra(Constants.RECIPE_KEY);
            if (isTablet) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("recipe", recipe);
                Navigation.findNavController(this, R.id.tabletContainer)
                        .navigate(R.id.ingredientAndStepFragment, bundle);
            } else {
                RecipeFragmentDirections.ActionRecipeFragmentToIngredientAndStepFragment action =
                        RecipeFragmentDirections
                                .actionRecipeFragmentToIngredientAndStepFragment(recipe);
                Navigation.findNavController(this, R.id.mainContainer).navigate(action);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            navHostFragment.getNavController().popBackStack();
        }

        return super.onOptionsItemSelected(item);
    }
}

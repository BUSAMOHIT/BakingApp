package com.oganbelema.bakingapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.oganbelema.bakingapp.recipe.RecipeAdapter;
import com.oganbelema.bakingapp.repository.RecipeRepository;
import com.oganbelema.network.data.Recipe;

import java.util.List;

public class RecipeViewModel extends ViewModel {

    private final RecipeRepository mRecipeRepository;

    private final RecipeAdapter mRecipeAdapter;

    public RecipeViewModel(RecipeRepository recipeRepository,
                           RecipeAdapter recipeAdapter) {
        this.mRecipeRepository = recipeRepository;
        this.mRecipeAdapter = recipeAdapter;
    }

    public RecipeAdapter getRecipeAdapter() {
        return mRecipeAdapter;
    }

    public LiveData<Boolean> getNetworkStatus() {
        return mRecipeRepository.getNetworkStatus();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipeRepository.getRecipes();
    }

    public LiveData<Throwable> getError() {
        return mRecipeRepository.getError();
    }

    @Override
    protected void onCleared() {
        mRecipeRepository.dispose();
        super.onCleared();
    }
}

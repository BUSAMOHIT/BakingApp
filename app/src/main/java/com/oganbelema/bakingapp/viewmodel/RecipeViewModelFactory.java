package com.oganbelema.bakingapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.oganbelema.bakingapp.recipe.RecipeAdapter;
import com.oganbelema.bakingapp.repository.RecipeRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RecipeViewModelFactory implements ViewModelProvider.Factory {

    private final RecipeRepository mRecipeRepository;

    private final RecipeAdapter mRecipeAdapter;

    @Inject
    public RecipeViewModelFactory(RecipeRepository mRecipeRepository, RecipeAdapter mRecipeAdapter) {
        this.mRecipeRepository = mRecipeRepository;
        this.mRecipeAdapter = mRecipeAdapter;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeViewModel(mRecipeRepository, mRecipeAdapter);
    }
}

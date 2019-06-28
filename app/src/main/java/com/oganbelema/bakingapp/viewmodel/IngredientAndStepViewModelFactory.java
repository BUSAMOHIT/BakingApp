package com.oganbelema.bakingapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.oganbelema.bakingapp.ingredient.IngredientAdapter;
import com.oganbelema.bakingapp.step.StepAdapter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IngredientAndStepViewModelFactory implements ViewModelProvider.Factory {


    private final StepAdapter mStepAdapter;

    private final IngredientAdapter mIngredientAdapter;

    @Inject
    public IngredientAndStepViewModelFactory(StepAdapter stepAdapter, IngredientAdapter
            ingredientAdapter) {
        this.mStepAdapter = stepAdapter;
        this.mIngredientAdapter = ingredientAdapter;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new IngredientAndStepViewModel(mStepAdapter, mIngredientAdapter);
    }
}

package com.oganbelema.bakingapp.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.oganbelema.bakingapp.step.StepAdapter;
import com.oganbelema.network.data.Recipe;

public class IngredientAndStepViewModel extends ViewModel {

    @Nullable
    private  Recipe mRecipe;

    private final StepAdapter mStepAdapter;

    public IngredientAndStepViewModel(StepAdapter stepAdapter) {
        mStepAdapter = stepAdapter;
    }

    public StepAdapter getStepAdapter() {
        return mStepAdapter;
    }

    @Nullable
    public Recipe getRecipe(){
        return mRecipe;
    }

    public void setRecipe(Recipe recipe){
        mRecipe = recipe;
        mStepAdapter.setSteps(recipe.getSteps());
    }
}

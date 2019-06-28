package com.oganbelema.bakingapp.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.oganbelema.bakingapp.ingredient.IngredientAdapter;
import com.oganbelema.bakingapp.step.StepAdapter;
import com.oganbelema.network.data.Recipe;

public class IngredientAndStepViewModel extends ViewModel {

    @Nullable
    private  Recipe mRecipe;

    private final StepAdapter mStepAdapter;

    private final IngredientAdapter mIngredientAdapter;

    public IngredientAndStepViewModel(StepAdapter stepAdapter, IngredientAdapter ingredientAdapter) {
        mStepAdapter = stepAdapter;
        mIngredientAdapter = ingredientAdapter;
    }

    public StepAdapter getStepAdapter() {
        return mStepAdapter;
    }

    public IngredientAdapter getIngredientAdapter() {
        return mIngredientAdapter;
    }

    @Nullable
    public Recipe getRecipe(){
        return mRecipe;
    }

    public void setRecipe(Recipe recipe){
        mRecipe = recipe;
        mStepAdapter.setSteps(recipe.getSteps());
        mIngredientAdapter.setIngredients(recipe.getIngredients());
    }


}

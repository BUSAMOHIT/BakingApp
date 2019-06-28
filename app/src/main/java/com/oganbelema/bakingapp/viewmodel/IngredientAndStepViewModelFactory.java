package com.oganbelema.bakingapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.oganbelema.bakingapp.step.StepAdapter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IngredientAndStepViewModelFactory implements ViewModelProvider.Factory {


    private final StepAdapter mStepAdapter;

    @Inject
    public IngredientAndStepViewModelFactory(StepAdapter stepAdapter) {
        this.mStepAdapter = stepAdapter;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new IngredientAndStepViewModel(mStepAdapter);
    }
}

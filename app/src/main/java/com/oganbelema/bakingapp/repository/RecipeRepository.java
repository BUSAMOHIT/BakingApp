package com.oganbelema.bakingapp.repository;

import androidx.lifecycle.LiveData;

import com.oganbelema.network.data.Recipe;
import com.oganbelema.network.source.RecipeNetworkSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RecipeRepository {

    private final RecipeNetworkSource mRecipeNetworkSource;

    @Inject
    public RecipeRepository(RecipeNetworkSource mRecipeNetworkSource) {
        this.mRecipeNetworkSource = mRecipeNetworkSource;
    }

    public LiveData<Boolean> getNetworkStatus(){
        return mRecipeNetworkSource.getNetworkStatus();
    }

    public LiveData<List<Recipe>> getRecipes(){
        mRecipeNetworkSource.getRecipeRemote();
        return mRecipeNetworkSource.getRecipes();
    }

    public LiveData<Throwable> getError(){
        return mRecipeNetworkSource.getError();
    }

    public void dispose(){
        mRecipeNetworkSource.dispose();
    }
}

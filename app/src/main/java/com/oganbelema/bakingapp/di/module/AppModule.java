package com.oganbelema.bakingapp.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.oganbelema.bakingapp.Constants;
import com.oganbelema.bakingapp.ingredient.IngredientAdapter;
import com.oganbelema.bakingapp.recipe.RecipeAdapter;
import com.oganbelema.bakingapp.step.StepAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mApplication;
    }

    @Provides
    public RecipeAdapter provideRecipeAdapter() {
        return new RecipeAdapter();
    }

    @Provides
    public StepAdapter provideStepAdapter() {
        return new StepAdapter();
    }

    @Provides
    public IngredientAdapter provideIngredientAdapter() {
        return new IngredientAdapter();
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(Constants.SHARED_PREFERENCE_ID, Context.MODE_PRIVATE);
    }
}

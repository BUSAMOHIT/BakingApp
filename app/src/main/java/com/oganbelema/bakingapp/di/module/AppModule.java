package com.oganbelema.bakingapp.di.module;

import android.app.Application;
import android.content.Context;

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
    public Context provideContext(){
        return mApplication;
    }

    @Provides
    public RecipeAdapter provideRecipeAdapter(){
        return new RecipeAdapter();
    }

    @Provides
    public StepAdapter provideStepAdapter(){
        return new StepAdapter();
    }
}

package com.oganbelema.bakingapp.di.component;

import com.oganbelema.bakingapp.di.module.AppModule;
import com.oganbelema.bakingapp.di.module.NetworkModule;
import com.oganbelema.bakingapp.ui.IngredientAndStepFragment;
import com.oganbelema.bakingapp.ui.RecipeFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    void inject(RecipeFragment target);

    void inject(IngredientAndStepFragment target);
}

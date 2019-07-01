package com.oganbelema.bakingapp.ui;

import androidx.test.espresso.IdlingResource;

import javax.annotation.Nullable;

public class MyIdlingResource implements IdlingResource {

    @Nullable
    private ResourceCallback callback;

    @Override
    public String getName() {
        return MyIdlingResource.class.getSimpleName();
    }

    @Override
    public boolean isIdleNow() {

        boolean idle = RecipeFragment.sIndlingResourceCounter == 0;

        if (idle){
            if (callback != null){
                callback.onTransitionToIdle();
            }
        }

        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }
}

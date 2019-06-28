package com.oganbelema.bakingapp;

import android.app.Application;

import com.oganbelema.bakingapp.di.component.AppComponent;
import com.oganbelema.bakingapp.di.component.DaggerAppComponent;
import com.oganbelema.bakingapp.di.module.AppModule;

public class BakingApp extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent(){
        return mAppComponent;
    }
}

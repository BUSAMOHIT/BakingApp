package com.oganbelema.bakingapp.di.module;

import android.content.Context;

import com.google.gson.Gson;
import com.oganbelema.bakingapp.BuildConfig;
import com.oganbelema.bakingapp.Constants;
import com.oganbelema.network.BaseUrl;
import com.oganbelema.network.CacheInterceptor;
import com.oganbelema.network.RecipeApi;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    @Named(Constants.NAMED_BASE_URL)
    public String provideBaseUrl() {
        return BaseUrl.BASE_URL;
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(
                BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE
        );
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    public Cache provideCache(Context context) {
        return new Cache(new File(context.getCacheDir(), "http-cache"),
                10 * 1024 * 1024); //10 MB
    }

    @Provides
    @Singleton
    public CacheInterceptor provideCacheInterceptor() {
        return new CacheInterceptor();
    }

    @Provides
    @Singleton
    public OkHttpClient provideClient(HttpLoggingInterceptor httpLoggingInterceptor,
                                      Cache cache, CacheInterceptor cacheInterceptor) {
        return new OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideBaseRetrofit(@Named(Constants.NAMED_BASE_URL) String baseUrl,
                                        OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public RecipeApi provideRecipeApi(Retrofit retrofit) {
        return retrofit.create(RecipeApi.class);
    }

    @Provides
    public Gson provideGson() {
        return new Gson();
    }
}

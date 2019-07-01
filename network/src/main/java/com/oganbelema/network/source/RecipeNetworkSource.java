package com.oganbelema.network.source;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.oganbelema.network.NetworkUtil;
import com.oganbelema.network.RecipeApi;
import com.oganbelema.network.data.Recipe;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

@Singleton
public class RecipeNetworkSource {

    private final RecipeApi mRecipeApi;

    private final NetworkUtil mNetworkUtil;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<List<Recipe>> mRecipes = new MutableLiveData<>();

    private final MutableLiveData<Throwable> mError = new MutableLiveData<>();

    private final MutableLiveData<Boolean> mNetworkStatus = new MutableLiveData<>();

    @Inject
    public RecipeNetworkSource(NetworkUtil networkUtil, RecipeApi recipeApi) {
        this.mNetworkUtil = networkUtil;
        this.mRecipeApi = recipeApi;
    }

    public void getRecipeRemote() {

        mNetworkStatus.setValue(mNetworkUtil.isConnected());

        if (mNetworkUtil.isConnected()) {
            mRecipeApi.getRecipes()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<Response<List<Recipe>>>() {
                        @Override
                        public void onSubscribe(Disposable disposable) {
                            disposables.add(disposable);
                        }

                        @Override
                        public void onSuccess(Response<List<Recipe>> response) {
                            handleSuccessfulRecipeRequest(response);
                        }

                        @Override
                        public void onError(Throwable error) {
                            mError.postValue(error);
                        }
                    });
        }
    }

    private void handleSuccessfulRecipeRequest(Response<List<Recipe>> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                List<Recipe> recipes = response.body();
                mRecipes.postValue(recipes);
            }
        }
    }

    public LiveData<Boolean> getNetworkStatus() {
        return mNetworkStatus;
    }

    public LiveData<Throwable> getError() {
        return mError;
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    public void dispose() {
        disposables.dispose();
    }
}

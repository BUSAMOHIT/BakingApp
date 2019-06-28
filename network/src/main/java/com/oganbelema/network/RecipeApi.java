package com.oganbelema.network;

import com.oganbelema.network.data.Recipe;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface RecipeApi {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Single<Response<List<Recipe>>> getRecipes();
}

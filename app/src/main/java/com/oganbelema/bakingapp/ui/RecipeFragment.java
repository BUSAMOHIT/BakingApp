package com.oganbelema.bakingapp.ui;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.oganbelema.bakingapp.BakingApp;
import com.oganbelema.bakingapp.Constants;
import com.oganbelema.bakingapp.R;
import com.oganbelema.bakingapp.databinding.FragmentRecipeBinding;
import com.oganbelema.bakingapp.recipe.RecipeAdapter;
import com.oganbelema.bakingapp.viewmodel.RecipeViewModel;
import com.oganbelema.bakingapp.viewmodel.RecipeViewModelFactory;
import com.oganbelema.network.data.Recipe;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment implements RecipeAdapter.RecipeItemOnClickListener {

    private static final String TAG = RecipeFragment.class.getSimpleName();

    public static int sIndlingResourceCounter = 1;

    @Inject
    RecipeViewModelFactory mRecipeViewModelFactory;

    @Inject
    SharedPreferences mSharedPreferences;

    private RecipeViewModel mRecipeViewModel;

    private FragmentRecipeBinding mFragmentRecipeBinding;

    private boolean mIsTablet;

    @Inject
    Gson mGson;

    public RecipeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentRecipeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe,
                container, false);

        return mFragmentRecipeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null)
            ((BakingApp) getActivity().getApplication()).getAppComponent().inject(this);

        if (getContext() != null)
            mIsTablet = getContext().getResources().getBoolean(R.bool.isTablet);

        mRecipeViewModel = ViewModelProviders.of(this, mRecipeViewModelFactory)
                .get(RecipeViewModel.class);

        mRecipeViewModel.getRecipeAdapter().setRecipeItemOnClickListener(this);

        mFragmentRecipeBinding.recipesRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        mFragmentRecipeBinding.recipesRecyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mFragmentRecipeBinding.recipesRecyclerView.setAdapter(mRecipeViewModel.getRecipeAdapter());


        mRecipeViewModel.getNetworkStatus().observe(this, networkStatus -> {
            if (!networkStatus) {
                Toast.makeText(getContext(), "No internet", Toast.LENGTH_LONG).show();
            }
        });

        mRecipeViewModel.getRecipes().observe(this, recipes -> {

            if (recipes != null) {
                mFragmentRecipeBinding.foodAnimationViews.setVisibility(View.GONE);
                mRecipeViewModel.getRecipeAdapter().setRecipes(recipes);
                String recipeJson = mGson.toJson(recipes.get(0));
                mSharedPreferences.edit().putString(Constants.RECIPE_KEY, recipeJson)
                        .apply();
                sIndlingResourceCounter = 0;

            }
        });

        mRecipeViewModel.getError().observe(this, error -> {
            if (error != null) {
                Log.e(TAG, error.getLocalizedMessage(), error);
            }
        });

    }

    @Override
    public void onRecipeItemClicked(Recipe recipe) {
        if (getActivity() != null)
            if (mIsTablet) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("recipe", recipe);
                Navigation.findNavController(getActivity(), R.id.tabletContainer)
                        .navigate(R.id.ingredientAndStepFragment, bundle);
            } else {
                RecipeFragmentDirections.ActionRecipeFragmentToIngredientAndStepFragment action =
                        RecipeFragmentDirections.actionRecipeFragmentToIngredientAndStepFragment(recipe);
                Navigation.findNavController(getActivity(), R.id.mainContainer).navigate(action);
            }
    }
}

package com.oganbelema.bakingapp.recipe;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.oganbelema.bakingapp.R;
import com.oganbelema.bakingapp.databinding.RecipeItemBinding;
import com.oganbelema.network.data.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipes = new ArrayList<>();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Boolean diffIsOperating = false;

    public interface RecipeItemOnClickListener {
        void onRecipeItemClicked(Recipe recipe);
    }

    private RecipeItemOnClickListener mRecipeItemOnClickListener;

    public void setRecipeItemOnClickListener(RecipeItemOnClickListener recipeItemOnClickListener) {
        mRecipeItemOnClickListener = recipeItemOnClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.recipe_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bindData(mRecipes.get(position));
    }

    @Override
    public int getItemCount() {
        if (mRecipes != null){
            return mRecipes.size();
        }

        return 0;
    }

    public void setRecipes(List<Recipe> recipes){

        if (diffIsOperating) return;

        diffIsOperating = true;

        compositeDisposable.add(
                Observable.fromCallable(() -> DiffUtil
                        .calculateDiff(new RecipeDiffCallback(mRecipes, recipes)))
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(diffResult -> {
                                diffResult.dispatchUpdatesTo(RecipeAdapter.this);
                                mRecipes = recipes;
                                diffIsOperating = false;
                        })
        );
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        compositeDisposable.dispose();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        private final RecipeItemBinding mRecipeItemBinding;

        RecipeViewHolder(@NonNull RecipeItemBinding recipeItemBinding) {
            super(recipeItemBinding.getRoot());
            mRecipeItemBinding = recipeItemBinding;
        }

        void bindData(Recipe recipe){
            mRecipeItemBinding.recipeNameTextView.setText(recipe.getName());
            if (!recipe.getImage().isEmpty()){
                Picasso.get().load(recipe.getImage())
                        .error(R.drawable.ic_error_24dp)
                        .into(mRecipeItemBinding.recipeImageView);
            }
            mRecipeItemBinding.getRoot().setOnClickListener(view -> {
                if (mRecipeItemOnClickListener != null){
                    mRecipeItemOnClickListener.onRecipeItemClicked(recipe);
                }
            });
        }
    }
}

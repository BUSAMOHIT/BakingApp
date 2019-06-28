package com.oganbelema.bakingapp.ingredient;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.oganbelema.bakingapp.R;
import com.oganbelema.bakingapp.databinding.IngredientItemBinding;
import com.oganbelema.network.data.Ingredient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{

    private List<Ingredient> ingredients = new ArrayList<>();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Boolean diffIsOperating = false;

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.ingredient_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.bindData(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        if (ingredients != null){
            return ingredients.size();
        }
        return 0;
    }

    public void setIngredients(List<Ingredient> ingredients){

        if (diffIsOperating) return;

        diffIsOperating = true;

        compositeDisposable.add(
                Observable.fromCallable(() -> DiffUtil
                        .calculateDiff(new IngredientDiffCallback(this.ingredients, ingredients)))
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(diffResult -> {
                            diffResult.dispatchUpdatesTo(IngredientAdapter.this);
                            this.ingredients = ingredients;
                            diffIsOperating = false;
                        })
        );
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        compositeDisposable.dispose();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        IngredientItemBinding ingredientItemBinding;

        public IngredientViewHolder(@NonNull IngredientItemBinding ingredientItemBinding) {
            super(ingredientItemBinding.getRoot());
            this.ingredientItemBinding = ingredientItemBinding;
        }

        void bindData(Ingredient ingredient){
            ingredientItemBinding.ingredientTextView.setText(
                    ingredientItemBinding.getRoot().getContext().getString(R.string.ingredient_item,
                            ingredient.getQuantity(), ingredient.getMeasure(),
                            ingredient.getIngredient())
            );
        }
    }
}

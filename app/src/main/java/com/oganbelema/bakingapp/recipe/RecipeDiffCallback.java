package com.oganbelema.bakingapp.recipe;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.oganbelema.network.data.Recipe;

import java.util.List;

public class RecipeDiffCallback extends DiffUtil.Callback {

    @NonNull
    private final List<Recipe> oldRecipeList;

    @NonNull
    private final List<Recipe> newRecipeList;

    public RecipeDiffCallback(@NonNull List<Recipe> oldRecipeList, @NonNull List<Recipe> newRecipeList) {
        this.oldRecipeList = oldRecipeList;
        this.newRecipeList = newRecipeList;
    }

    @Override
    public int getOldListSize() {
        return oldRecipeList.size();
    }

    @Override
    public int getNewListSize() {
        return newRecipeList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRecipeList.get(oldItemPosition).getId()
                == newRecipeList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRecipeList.get(oldItemPosition).equals(newRecipeList.get(newItemPosition));
    }
}

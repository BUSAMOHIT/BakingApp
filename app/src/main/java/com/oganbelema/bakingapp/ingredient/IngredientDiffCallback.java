package com.oganbelema.bakingapp.ingredient;

import androidx.recyclerview.widget.DiffUtil;

import com.oganbelema.network.data.Ingredient;

import java.util.List;

public class IngredientDiffCallback extends DiffUtil.Callback {

    private final List<Ingredient> oldIngredientList;

    private final List<Ingredient> newIngredientList;

    public IngredientDiffCallback(List<Ingredient> oldIngredientList, List<Ingredient>
            newIngredientList) {
        this.oldIngredientList = oldIngredientList;
        this.newIngredientList = newIngredientList;
    }

    @Override
    public int getOldListSize() {
        if (oldIngredientList != null){
            return oldIngredientList.size();
        }

        return 0;
    }

    @Override
    public int getNewListSize() {
        if (newIngredientList != null){
            return newIngredientList.size();
        }
        return 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldIngredientList.get(oldItemPosition).getIngredient()
                .equals(newIngredientList.get(newItemPosition).getIngredient());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldIngredientList.get(oldItemPosition).equals(newIngredientList.get(newItemPosition));
    }
}

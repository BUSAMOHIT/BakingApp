package com.oganbelema.bakingapp.step;

import androidx.recyclerview.widget.DiffUtil;

import com.oganbelema.network.data.Step;

import java.util.List;

public class StepDiffCallback extends DiffUtil.Callback {

    private final List<Step> oldStepList;

    private final List<Step> newStepList;

    public StepDiffCallback(List<Step> oldStepList, List<Step> newStepList) {
        this.oldStepList = oldStepList;
        this.newStepList = newStepList;
    }

    @Override
    public int getOldListSize() {
        return oldStepList.size();
    }

    @Override
    public int getNewListSize() {
        return newStepList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldStepList.get(oldItemPosition).getId() == newStepList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldStepList.get(oldItemPosition).equals(newStepList.get(newItemPosition));
    }
}

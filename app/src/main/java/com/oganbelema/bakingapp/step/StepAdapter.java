package com.oganbelema.bakingapp.step;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.oganbelema.bakingapp.R;
import com.oganbelema.bakingapp.databinding.StepItemBinding;
import com.oganbelema.network.data.Step;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private List<Step> mSteps = new ArrayList<>();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Boolean diffIsOperating = false;

    public interface StepItemOnClickListener {
        void onStepItemClicked(Step step);
    }

    private StepItemOnClickListener mStepItemOnClickListener;

    public void setRecipeItemOnClickListener(StepItemOnClickListener stepItemOnClickListener) {
        mStepItemOnClickListener = stepItemOnClickListener;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StepViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.step_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bindData(mSteps.get(position));
    }

    @Override
    public int getItemCount() {

        if (mSteps != null) {
            return mSteps.size();
        }

        return 0;
    }

    public void setSteps(List<Step> steps) {
        if (diffIsOperating) return;

        diffIsOperating = true;

        compositeDisposable.add(
                Observable.fromCallable(() -> DiffUtil
                        .calculateDiff(new StepDiffCallback(mSteps, steps)))
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(diffResult -> {
                            diffResult.dispatchUpdatesTo(StepAdapter.this);
                            mSteps = steps;
                            diffIsOperating = false;
                        })
        );
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        compositeDisposable.dispose();
    }

    class StepViewHolder extends RecyclerView.ViewHolder {

        StepItemBinding mStepItemBinding;

        public StepViewHolder(@NonNull StepItemBinding stepItemBinding) {
            super(stepItemBinding.getRoot());

            mStepItemBinding = stepItemBinding;
        }

        public void bindData(Step step) {
            mStepItemBinding.stepDescriptionTextView.setText(step.getDescription());

            mStepItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mStepItemOnClickListener != null) {
                        mStepItemOnClickListener.onStepItemClicked(step);
                    }
                }
            });
        }
    }
}

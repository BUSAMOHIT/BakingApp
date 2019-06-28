package com.oganbelema.bakingapp.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oganbelema.bakingapp.BakingApp;
import com.oganbelema.bakingapp.R;
import com.oganbelema.bakingapp.databinding.FragmentIngredientAndStepBinding;
import com.oganbelema.bakingapp.step.StepAdapter;
import com.oganbelema.bakingapp.viewmodel.IngredientAndStepViewModel;
import com.oganbelema.bakingapp.viewmodel.IngredientAndStepViewModelFactory;
import com.oganbelema.network.data.Recipe;
import com.oganbelema.network.data.Step;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientAndStepFragment extends Fragment implements StepAdapter.StepItemOnClickListener {

    private FragmentIngredientAndStepBinding fragmentIngredientAndStepBinding;

    @Inject
    IngredientAndStepViewModelFactory mIngredientAndStepViewModelFactory;

    private IngredientAndStepViewModel mIngredientAndStepViewModel;
    private Recipe mRecipe;


    public IngredientAndStepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentIngredientAndStepBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_ingredient_and_step, container, false);
        return fragmentIngredientAndStepBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((BakingApp) getActivity().getApplication()).getAppComponent().inject(this);

        mIngredientAndStepViewModel = ViewModelProviders.of(this,
                mIngredientAndStepViewModelFactory).get(IngredientAndStepViewModel.class);

        mIngredientAndStepViewModel.getStepAdapter().setRecipeItemOnClickListener(this);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL);

        fragmentIngredientAndStepBinding.stepsRecyclerView
                .setLayoutManager(new LinearLayoutManager(getContext()));

        fragmentIngredientAndStepBinding.stepsRecyclerView
                .addItemDecoration(dividerItemDecoration);

        fragmentIngredientAndStepBinding.stepsRecyclerView
                .setAdapter(mIngredientAndStepViewModel.getStepAdapter());

        fragmentIngredientAndStepBinding.ingredientsRecyclerView.
                setLayoutManager(new LinearLayoutManager(getContext()));

        fragmentIngredientAndStepBinding.ingredientsRecyclerView
                .addItemDecoration(dividerItemDecoration);

        fragmentIngredientAndStepBinding.ingredientsRecyclerView
                .setAdapter(mIngredientAndStepViewModel.getIngredientAdapter());

        if (getArguments() != null){
            IngredientAndStepFragmentArgs args =
                    IngredientAndStepFragmentArgs.fromBundle(getArguments());

            mRecipe = args.getRecipe();

            getActivity().setTitle(mRecipe.getName());

            mIngredientAndStepViewModel.setRecipe(mRecipe);

        }

    }

    @Override
    public void onStepItemClicked(Step step) {
        IngredientAndStepFragmentDirections.ActionIngredientAndStepFragmentToMediaFragment action =
                IngredientAndStepFragmentDirections.
                        actionIngredientAndStepFragmentToMediaFragment(mRecipe.getName(),step);
        Navigation.findNavController(getActivity(), R.id.mainContainer).navigate(action);
    }
}

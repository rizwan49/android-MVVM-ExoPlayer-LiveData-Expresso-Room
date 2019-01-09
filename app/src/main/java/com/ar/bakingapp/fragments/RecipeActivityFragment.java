package com.ar.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ar.bakingapp.R;
import com.ar.bakingapp.adapters.RecipeStepsAdapter;
import com.ar.bakingapp.network.model.StepsItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeActivityFragment extends Fragment implements RecipeStepsAdapter.ListItemOnClickListener {
    View rootView;

    @BindView(R.id.stepsList)
    RecyclerView recyclerView;

    List<StepsItem> list;
    RecipeStepsAdapter adapter;
    LinearLayoutManager layoutManager;
    private OnFragmentInteractionListener mListener;

    public RecipeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, rootView);
        bindAdapter();
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private void bindAdapter() {
        adapter = new RecipeStepsAdapter(list, this);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(StepsItem selectedStep, View view) {
        Toast.makeText(getContext(), selectedStep.getDescription(), Toast.LENGTH_LONG).show();
        if (mListener != null)
            mListener.onFragmentInteraction(selectedStep.getId());
    }

    public void setListofSteps(List<StepsItem> steps) {
        if (isAdded()) {
            adapter.addAllItem(steps);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position);
    }
}

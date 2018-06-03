package app.infy.com.factslist.view.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Observable;
import java.util.Observer;

import app.infy.com.factslist.R;
import app.infy.com.factslist.databinding.FragmentFactsListBinding;
import app.infy.com.factslist.view.activities.MainActivity;
import app.infy.com.factslist.view.adapter.FactsAdapter;
import app.infy.com.factslist.viewmodel.FactsViewModel;

public class FactsListFragment extends Fragment implements Observer {
    private FactsViewModel factsViewModel;
    FragmentFactsListBinding fragmentFactsListBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentFactsListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_facts_list, container, false);
        initDataBinding();
        setUpListOfFacts(fragmentFactsListBinding.listFacts);
        setUpObserver(factsViewModel);
        return fragmentFactsListBinding.getRoot();
    }

    private void initDataBinding() {
        factsViewModel = new FactsViewModel(getActivity());
        fragmentFactsListBinding.setFactsViewModel(factsViewModel);
    }

    // set up the list of facts with recycler view
    private void setUpListOfFacts(RecyclerView listFacts) {
        FactsAdapter userAdapter = new FactsAdapter();
        listFacts.setAdapter(userAdapter);
        listFacts.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof FactsViewModel) {
            FactsAdapter factsAdapter = (FactsAdapter) fragmentFactsListBinding.listFacts.getAdapter();
            FactsViewModel factsViewModel = (FactsViewModel) o;
            factsAdapter.setFactsList(factsViewModel.getFactsList());
            ((MainActivity) getActivity()).changeToolBarTitle(factsViewModel.getToolBarTitle());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        factsViewModel.reset();
    }
}

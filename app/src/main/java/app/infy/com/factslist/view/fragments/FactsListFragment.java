package app.infy.com.factslist.view.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Observable;
import java.util.Observer;

import app.infy.com.factslist.R;
import app.infy.com.factslist.databinding.FragmentFactsListBinding;
import app.infy.com.factslist.utils.AppConstants;
import app.infy.com.factslist.view.activities.MainActivity;
import app.infy.com.factslist.view.adapter.FactsAdapter;
import app.infy.com.factslist.viewmodel.FactsViewModel;

public class FactsListFragment extends Fragment implements Observer, SwipeRefreshLayout.OnRefreshListener {
    private FactsViewModel factsViewModel;
    FragmentFactsListBinding fragmentFactsListBinding;
    FactsAdapter factsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragmentFactsListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_facts_list, container, false);
            initDataBinding();
            setUpListOfFacts(fragmentFactsListBinding.listFacts);
            setUpObserver(factsViewModel);
        } else {
            setUpListOfFacts(fragmentFactsListBinding.listFacts);
            showDataInView();
        }

        return fragmentFactsListBinding.getRoot();
    }

    private void initDataBinding() {
        factsViewModel = new FactsViewModel(getActivity());
        fragmentFactsListBinding.setFactsViewModel(factsViewModel);
        fragmentFactsListBinding.swipeContainer.setOnRefreshListener(this);
    }

    // set up the list of facts with recycler view
    private void setUpListOfFacts(RecyclerView listFacts) {
        FactsAdapter userAdapter = new FactsAdapter(getActivity());
        listFacts.setAdapter(userAdapter);
        listFacts.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof FactsViewModel) {
            factsViewModel = (FactsViewModel) o;
            showDataInView();
        }
    }

    private void showDataInView() {
        factsAdapter = (FactsAdapter) fragmentFactsListBinding.listFacts.getAdapter();
        factsAdapter.setFactsList(factsViewModel.getFactsList());
        ((MainActivity) getActivity()).changeToolBarTitle(factsViewModel.getToolBarTitle());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        factsViewModel.reset();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AppConstants.SAVE_FRAGMENT_STATE, true);
        outState.putSerializable("factsViewModel", factsViewModel);
    }

    @Override
    public void onRefresh() {
        factsViewModel.pullToRefresh();
        factsViewModel.getFactsList().clear();
    }
}

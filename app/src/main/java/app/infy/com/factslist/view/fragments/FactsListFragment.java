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

/*Main fragment class to show the data in RecyclerView it's lifecycle is associated with MainActivity*/
public class FactsListFragment extends Fragment implements Observer, SwipeRefreshLayout.OnRefreshListener {
    private FactsViewModel mFactsViewModel;
    private FragmentFactsListBinding mFragmentFactsListBinding;
    private FactsAdapter mFactsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*retaining the state on device orientation change*/
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*checking savedInstanceState in case of orientation change it wont be null because we are retaing the fragment state */
        if (savedInstanceState == null) {
            /*initializing the view we make it global so when the state change we can reuse it*/
            mFragmentFactsListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_facts_list, container, false);
            initDataBinding();
            setUpListOfFacts(mFragmentFactsListBinding.listFacts);
            setUpObserver(mFactsViewModel);
        } else {
            setUpListOfFacts(mFragmentFactsListBinding.listFacts);
            showDataInView();
        }

        return mFragmentFactsListBinding.getRoot();
    }

    /*method to start the data binding with the fragment views*/
    private void initDataBinding() {
        mFactsViewModel = new FactsViewModel(getActivity());
        mFragmentFactsListBinding.setFactsViewModel(mFactsViewModel);
        mFragmentFactsListBinding.swipeContainer.setOnRefreshListener(this);
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
            mFactsViewModel = (FactsViewModel) o;
            showDataInView();
        }
    }

    /*method to show the data in recyclerview init the Adapter and setting it in RecyclerView*/
    private void showDataInView() {
        mFactsAdapter = (FactsAdapter) mFragmentFactsListBinding.listFacts.getAdapter();
        mFactsAdapter.setmFactsList(mFactsViewModel.getFactsList());
        ((MainActivity) getActivity()).changeToolBarTitle(mFactsViewModel.getToolBarTitle());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mFactsViewModel.reset();
    }

    /*saving the fragment instance state*/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AppConstants.SAVE_FRAGMENT_STATE, true);
    }

    /*method to allow the pull to refresh*/
    @Override
    public void onRefresh() {
        /*assuming whenever we will pull the data it should be latest one so clearing the
         existing data in the list and making the api call */
        mFactsViewModel.pullToRefresh();
        mFactsViewModel.getFactsList().clear();
    }
}

package app.infy.com.factslist.view.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.Observable;
import java.util.Observer;

import app.infy.com.factslist.R;
import app.infy.com.factslist.databinding.ActivityMainBinding;
import app.infy.com.factslist.view.adapter.FactsAdapter;
import app.infy.com.factslist.view.fragments.FactsListFragment;
import app.infy.com.factslist.viewmodel.FactsViewModel;

public class MainActivity extends AppCompatActivity  {
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        setSupportActionBar(activityMainBinding.toolbar);
    }

    private void initDataBinding() {
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        FactsListFragment factsListFragment = new FactsListFragment();
        addFragment(R.id.container, factsListFragment, factsListFragment.getClass().getName());
    }


    protected void addFragment(@IdRes int containerViewId, @NonNull Fragment fragment, @NonNull String fragmentTag) {
        getSupportFragmentManager().beginTransaction().add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack().commit();
    }

    public void changeToolBarTitle(String title) {
        activityMainBinding.toolbar.setTitle(title);
    }


}

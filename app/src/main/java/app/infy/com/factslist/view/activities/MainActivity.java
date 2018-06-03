package app.infy.com.factslist.view.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import app.infy.com.factslist.R;
import app.infy.com.factslist.databinding.ActivityMainBinding;
import app.infy.com.factslist.utils.AppConstants;
import app.infy.com.factslist.view.fragments.FactsListFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    FactsListFragment factsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (savedInstanceState == null) {
            initFragment();
            setSupportActionBar(activityMainBinding.toolbar);
        } else {
            factsListFragment = (FactsListFragment) getSupportFragmentManager().findFragmentByTag(FactsListFragment.class.getName());
//            addFragment(R.id.container, factsListFragment, factsListFragment.getClass().getName());
        }

    }

    private void initFragment() {
        factsListFragment = new FactsListFragment();
        addFragment(R.id.container, factsListFragment, factsListFragment.getClass().getName());
    }


    protected void addFragment(@IdRes int containerViewId, @NonNull Fragment fragment, @NonNull String fragmentTag) {
        getSupportFragmentManager().beginTransaction().add(containerViewId, fragment, fragmentTag)
                .commit();
    }

    public void changeToolBarTitle(String title) {
        activityMainBinding.toolbar.setTitle(title);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AppConstants.SAVE_ACTIVITY_STATE, true);
    }
}

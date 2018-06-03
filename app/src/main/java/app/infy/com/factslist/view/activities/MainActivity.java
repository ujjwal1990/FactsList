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
        }

    }

    /*method to initilizing the fragment and adding it to activity*/
    private void initFragment() {
        factsListFragment = new FactsListFragment();
        addFragment(R.id.container, factsListFragment, factsListFragment.getClass().getName());
    }

    /*common method to add different fragments to activity*/
    protected void addFragment(@IdRes int containerViewId, @NonNull Fragment fragment, @NonNull String fragmentTag) {
        getSupportFragmentManager().beginTransaction().add(containerViewId, fragment, fragmentTag)
                .commit();
    }

    /*method to change the toolbar title from fragments*/
    public void changeToolBarTitle(String title) {
        activityMainBinding.toolbar.setTitle(title);
    }

    /*methos to maintain the activity state on orientation change
    *
    * NOTE:
    * as of now we are not storing any data to the activity and reuse it in configuration change
     * but if require we can do it by using put primitives in bundle*/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AppConstants.SAVE_ACTIVITY_STATE, true);
    }
}

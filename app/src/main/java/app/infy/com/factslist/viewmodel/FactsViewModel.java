package app.infy.com.factslist.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import app.infy.com.factslist.R;
import app.infy.com.factslist.app.AppMain;
import app.infy.com.factslist.model.FactsDataResponse;
import app.infy.com.factslist.model.Rows;
import app.infy.com.factslist.network.NetworkClient;
import app.infy.com.factslist.network.NetworkService;
import app.infy.com.factslist.utils.AppConstants;
import app.infy.com.factslist.utils.AppUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/*view model class for fragment recyclerview it will handle all the network call updation of the recyclerview (for updation we used DataBinding)*/
public class FactsViewModel extends Observable {

    /*we are using observables to pretend the current state of the api call and showing the views based o these observables*/
    /*progress obsevable to show progressbar before getting the data from api call*/
    public ObservableInt progressBar;
    /*pull to refresh observable*/
    public ObservableBoolean isRefreshing;
    /*recycler view observable*/
    public ObservableInt factsRecycler;
    /*Message observable to show the different state mesaages to the user*/
    public ObservableInt factsLabel;
    /*Message field to change the text message*/
    public ObservableField<String> messageLabel;

    public ObservableField<String> title;
    private String toolBarTitle;
    private List<Rows> factsList;
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    /*constructor to init the data passing the context of the activity from fragment*/
    public FactsViewModel(@NonNull Context context) {
        this.context = context;
        this.factsList = new ArrayList<>();

        isRefreshing = new ObservableBoolean(false);
        progressBar = new ObservableInt(View.GONE);
        factsRecycler = new ObservableInt(View.GONE);
        factsLabel = new ObservableInt(View.VISIBLE);
        title = new ObservableField<>(toolBarTitle);
        messageLabel = new ObservableField<>(context.getString(R.string.error_message_loading_facts));

        checkNetworkAndMakeApiCall();
    }

    /*methos to check the network before making he api call*/
    public void checkNetworkAndMakeApiCall() {
        if (AppUtils.isOnline(context)) {
            setUpToGetDataFromApi();
        } else {
            showNetworkMessage();
        }
    }

    /*methos to show the network not available message*/
    public void showNetworkMessage() {
        messageLabel = new ObservableField<>(context.getString(R.string.no_internet));
        isRefreshing.set(false);
        factsLabel.set(View.VISIBLE);
        factsRecycler.set(View.VISIBLE);
        progressBar.set(View.GONE);
    }

    /*methos to make the api call and showing the progress bar till response*/
    public void setUpToGetDataFromApi() {
        isRefreshing.set(false);
        factsLabel.set(View.GONE);
        factsRecycler.set(View.GONE);
        progressBar.set(View.VISIBLE);
        fetchFactsList();
    }

    /*methos for pull to refresh*/
    public void pullToRefresh() {
        isRefreshing.set(true);
        factsLabel.set(View.GONE);
        factsRecycler.set(View.VISIBLE);
        progressBar.set(View.GONE);
        fetchFactsList();
    }

    /*RXJava 2 call to get the data from the hosted api*/
    private void fetchFactsList() {
        AppMain appController = new AppMain();
        /*object for network client to get the retrofit object*/
        NetworkClient networkClient = new NetworkClient();
        /*object for getting the api instance
         * NOTE
         * create() is specifying the api type call
         * */
        NetworkService networkService = networkClient.getRetrofit().create(NetworkService.class);

        /*api disposable data object*/
        Disposable disposable = networkService.getFactsData(AppConstants.FACTS_PATH)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FactsDataResponse>() {
                    @Override
                    public void accept(FactsDataResponse factsResponse) throws Exception {
                        setTollBarTitle(factsResponse.getTitle());
                        updateFactsDataList(factsResponse.getRows());
                        isRefreshing.set(false);
                        progressBar.set(View.GONE);
                        factsLabel.set(View.GONE);
                        factsRecycler.set(View.VISIBLE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        isRefreshing.set(false);
                        messageLabel.set(context.getString(R.string.error_message_loading_facts));
                        progressBar.set(View.GONE);
                        factsLabel.set(View.VISIBLE);
                        factsRecycler.set(View.GONE);
                    }
                });

        compositeDisposable.add(disposable);
    }

    /*methos to update the List based on the recent response */
    private void updateFactsDataList(List<Rows> factList) {
        factsList.addAll(factList);
        /*this is a Synchronized method from observable which will change the values accordingly*/
        setChanged();
        /* If this object has changed, as indicated by the
         * <code>hasChanged</code> method, then notify all of its observers*/
        notifyObservers();
    }

    /*updating the app toolbar title*/
    public void setTollBarTitle(String title) {
        this.toolBarTitle = title;
    }


    public String getToolBarTitle() {
        return toolBarTitle;
    }

    /*get the whole list of data*/
    public List<Rows> getFactsList() {
        return factsList;
    }


    /*unSubscribe the observable*/
    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    /*resetting the content on OnDestroy of the activity so it wont Observe the data if Activity is not in the stack*/
    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }
}

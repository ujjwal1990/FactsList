package app.infy.com.factslist.viewmodel;

import android.content.Context;
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
import app.infy.com.factslist.utils.Constants;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class FactsViewModel extends Observable {

    public ObservableInt progressBar;
    public ObservableInt factsRecycler;
    public ObservableInt factsLabel;
    public ObservableField<String> messageLabel;
    public ObservableField<String> title;

    private String toolBarTitle;
    private List<Rows> factsList;
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FactsViewModel(@NonNull Context context) {
        this.context = context;
        this.factsList = new ArrayList<>();
        progressBar = new ObservableInt(View.GONE);
        factsRecycler = new ObservableInt(View.GONE);
        factsLabel = new ObservableInt(View.VISIBLE);
        title = new ObservableField<>(toolBarTitle);
        messageLabel = new ObservableField<>(context.getString(R.string.error_message_loading_facts));

        initializeViews();
    }

    public void initializeViews() {
        factsLabel.set(View.GONE);
        factsRecycler.set(View.GONE);
        progressBar.set(View.VISIBLE);
        fetchFactsList();
    }

    private void fetchFactsList() {

        AppMain appController = new AppMain();

        NetworkClient networkClient = new NetworkClient();
        NetworkService networkService = networkClient.getRetrofit().create(NetworkService.class);

        Disposable disposable = networkService.getFactsData(Constants.FACTS_PATH)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FactsDataResponse>() {
                    @Override
                    public void accept(FactsDataResponse factsResponse) throws Exception {
                        setTollBarTitle(factsResponse.getTitle());
                        updateFactsDataList(factsResponse.getRows());

                        progressBar.set(View.GONE);
                        factsLabel.set(View.GONE);
                        factsRecycler.set(View.VISIBLE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        messageLabel.set(context.getString(R.string.error_message_loading_facts));
                        progressBar.set(View.GONE);
                        factsLabel.set(View.VISIBLE);
                        factsRecycler.set(View.GONE);
                    }
                });

        compositeDisposable.add(disposable);
    }

    private void updateFactsDataList(List<Rows> factList) {
        factsList.addAll(factList);
        setChanged();
        notifyObservers();
    }

    public void setTollBarTitle(String title) {
        this.toolBarTitle = title;
    }

    public String getToolBarTitle() {
        return toolBarTitle;
    }

    public List<Rows> getFactsList() {
        return factsList;
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }
}

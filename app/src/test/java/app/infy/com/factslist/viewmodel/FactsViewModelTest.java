package app.infy.com.factslist.viewmodel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.concurrent.TimeUnit;

import app.infy.com.factslist.app.AppMain;
import app.infy.com.factslist.network.NetworkClient;
import app.infy.com.factslist.network.NetworkService;
import app.infy.com.factslist.utils.AppUtils;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@PrepareForTest({AppUtils.class, Schedulers.class,})
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.net.ssl.*")
public class FactsViewModelTest {
    @Mock
    Context mContext;
    @Mock
    NetworkClient mNetworkClient;
    @Mock
    NetworkService mNetworkService;
    @Mock
    AppMain mAppMain;

    FactsViewModel mFactsViewModel;


    @Before
    public void setUp() {
        // For all static method mock
        PowerMockito.mockStatic(AppUtils.class);
        // To initialize all @Mock annotated field
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Schedulers.class);
        String errorString = "Some Error occur.";
        when(mContext.getString(anyInt())).thenReturn(errorString);
        PowerMockito.when(AppUtils.isOnline(mContext)).thenReturn(false);
        mFactsViewModel = Mockito.spy(new FactsViewModel(mContext));
    }

    @Ignore
    @Test
    public void testCheckNetworkAndMakeApiCallHit() {
        PowerMockito.when(AppUtils.isOnline(mContext)).thenReturn(true);
        mFactsViewModel.setNetworkClient(mNetworkClient);
        mFactsViewModel.setAppController(mAppMain);
        mFactsViewModel.setNetworkService(mNetworkService);
        mFactsViewModel.checkNetworkAndMakeApiCall();
        Mockito.verify(mFactsViewModel).setUpToGetDataFromApi();
    }

    @Test
    public void testCheckNetworkAndMakeApiCallHitNetworkError() {
        PowerMockito.when(AppUtils.isOnline(mContext)).thenReturn(false);
        mFactsViewModel.checkNetworkAndMakeApiCall();
        Mockito.verify(mFactsViewModel).showNetworkMessage();
    }

    @Test
    public void testIsRefreshFalseCheck() {
        PowerMockito.when(AppUtils.isOnline(mContext)).thenReturn(false);
        mFactsViewModel.checkNetworkAndMakeApiCall();
        Assert.assertEquals(mFactsViewModel.isRefreshing.get(), false);
    }


    @Test
    public void testProgressBarFalse() {
        PowerMockito.when(AppUtils.isOnline(mContext)).thenReturn(false);
        mFactsViewModel.checkNetworkAndMakeApiCall();
        Assert.assertEquals(mFactsViewModel.progressBar.get(), View.GONE);
    }

    @Test
    public void testErrorTextFalse() {
        PowerMockito.when(AppUtils.isOnline(mContext)).thenReturn(false);
        mFactsViewModel.checkNetworkAndMakeApiCall();
        Assert.assertEquals(mFactsViewModel.factsLabel.get(), View.VISIBLE);
    }

}

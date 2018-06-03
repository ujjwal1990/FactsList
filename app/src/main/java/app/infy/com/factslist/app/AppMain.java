package app.infy.com.factslist.app;

import android.app.Application;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class AppMain extends Application {
    private Scheduler scheduler;

    /*creating the scheduler object it will used it rx call*/
    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

}

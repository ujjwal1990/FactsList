package app.infy.com.factslist.network;

import app.infy.com.factslist.model.FactsDataResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NetworkService {
    /**
     * get the facts details
     */
    @GET()
    Observable<FactsDataResponse> getFactsData(@Url String url);

}

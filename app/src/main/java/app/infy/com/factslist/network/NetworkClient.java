package app.infy.com.factslist.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*class wich will provide the retrofit instance to make the api call*/
public class NetworkClient {
    private static Retrofit retrofit;

    public void NetworkClient() {
    }

    /*creating the retrofit object and returning it bby using Singleton*/
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();

        }
        return retrofit;
    }

}

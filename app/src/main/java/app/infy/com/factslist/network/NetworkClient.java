package app.infy.com.factslist.network;

import android.content.Context;

import java.io.File;

import app.infy.com.factslist.utils.AppConstants;
import okhttp3.Cache;
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
    public static Retrofit getRetrofit(Context context) {
        if (retrofit == null) {

            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(httpCacheDirectory, cacheSize);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.cache(cache).build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

}

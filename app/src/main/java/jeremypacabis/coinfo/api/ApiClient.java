package jeremypacabis.coinfo.api;

import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import jeremypacabis.coinfo.api.models.CoinConversion;
import jeremypacabis.coinfo.api.models.ICO;
import jeremypacabis.coinfo.api.models.CoinStats;
import jeremypacabis.coinfo.api.models.ICODetail;
import jeremypacabis.coinfo.api.models.MarketCap;
import jeremypacabis.coinfo.api.models.Notice;
import jeremypacabis.coinfo.listeners.OnPostAgainListener;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public Service service;

    public ApiClient(String baseUrl) {
        service = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        builder.addHeader("Content-Type", "application/json");
                        builder.addHeader("Accept", "application/json");
                        return chain.proceed(builder.build());
                    }
                }).build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Service.class);
    }

    public Observable<MarketCap> fetchMarketCap() {
        return service.getMarketCap();
    }

    public Observable<List<String>> fetchCoinsList() {
        return service.getCoinsList();
    }

    public Observable<Integer> fetchCoinHeat(String coin) {
        return service.getCoinHeat(coin);
    }

    public Observable<CoinStats> fetchCoinStats(String coin) {
        return service.getCoinStats(coin);
    }

    public Observable<Drawable> fetchCoinLogo(String coin) {
        return service.getCoinLogo(coin);
    }

    public Observable<List<Notice>> fetchCoinNotices(String coin) {
        return service.getCoinNotices(coin);
    }

    public Observable<CoinConversion> fetchCoinConversion(String coin, String targetCurrency) {
        return service.getCoinConversion(coin, targetCurrency);
    }

    public Observable<List<ICO>> fetchActiveICOs() {
        return service.getActiveICOs();
    }

    public Observable<List<ICO>> fetchUpcomingICOs() {
        return service.getUpcomingICOs();
    }

    public Observable<ICODetail> fetchICODetail(String ico) {
        return service.getICODetail(ico);
    }

    private class CustomSubscriber<T> extends DisposableObserver {
        DisposableObserver<T> disposableObserver;
        OnPostAgainListener listener;

        public CustomSubscriber(DisposableObserver<T> disposableObserver,
                                OnPostAgainListener listener) {
            this.disposableObserver = disposableObserver;
            this.listener = listener;
        }

        @Override
        public void onNext(Object value) {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

}

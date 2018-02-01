package jeremypacabis.coinfo.api;

import android.graphics.drawable.Drawable;

import java.util.List;

import jeremypacabis.coinfo.api.models.ICO;
import jeremypacabis.coinfo.api.models.CoinConversion;
import jeremypacabis.coinfo.api.models.CoinStats;
import jeremypacabis.coinfo.api.models.ICODetail;
import jeremypacabis.coinfo.api.models.MarketCap;
import jeremypacabis.coinfo.api.models.Notice;
import jeremypacabis.coinfo.api.responses.JokesResponse;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service {

    @GET("jokes/random")
    Observable<JokesResponse> getJokes();

    @GET("api/v1/std/marketcap")
    Observable<MarketCap> getMarketCap();

    @GET("api/v1/coins")
    Observable<List<String>> getCoinsList();

    @GET("api/v1/std/coinheat/{coin}")
    Observable<Integer> getCoinHeat(@Path("coin") String coin);

    @GET("api/v1/std/coin/{coin}")
    Observable<CoinStats> getCoinStats(@Path("coin") String coin);

    @GET("api/v1/std/logo/{coin}")
    Observable<Drawable> getCoinLogo(@Path("coin") String coin);

    @GET("api/v1/std/notices/{coin}")
    Observable<List<Notice>> getCoinNotices(@Path("coin") String coin);

    @GET("api/v1/convert/{coin}/{target_currency}")
    Observable<CoinConversion> getCoinConversion(
            @Path("coin") String coin,
            @Path("target_currency") String targetCurrency
    );

    @GET("api/v1/icos/active")
    Observable<List<ICO>> getActiveICOs();

    @GET("api/v1/icos/upcoming")
    Observable<List<ICO>> getUpcomingICOs();

    @GET("api/v1/icos/lookup/{ico}")
    Observable<ICODetail> getICODetail(@Path("ico") String ico);

    /*  //SAMPLE IMPLEMENTATION FOR THE NEW API

       disposable.add(apiClient.fetchJokes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<JokesResponse>() {
                               @Override
                               public void onNext(JokesResponse value) {
                                    Log.e("values", value.value
                                    + " "  + value.icon_url + " "  + value.url
                                    );
                               }

                               @Override
                               public void onError(Throwable e) {
                                    Log.e("Error", e.getLocalizedMessage());
                               }

                               @Override
                               public void onComplete() {

                               }
                           }
                ));

*/
}

package jeremypacabis.coinfo.api.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import jeremypacabis.coinfo.api.models.CoinStats;

/**
 * Created by jeremypacabis on January 29, 2018.
 *
 * @author Jeremy Patrick Pacabis <jeremy@ingenuity.ph>
 *         jeremypacabis.coinfo.api.viewmodels <coinfo-android>
 */

public class CoinStatsViewModel extends BaseViewModel {

    private MutableLiveData<CoinStats> coinStats;

    public LiveData<CoinStats> getCoinStats() {
        if (coinStats == null) {
            coinStats = new MutableLiveData<>();
            fetchData("");
        }

        return coinStats;
    }


    public void fetchData(String coin) {
        disposable.add(apiClient.fetchCoinStats(coin)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CoinStats>() {
                    @Override
                    public void onNext(CoinStats value) {
                        coinStats.postValue(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        coinStats.postValue(null);
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }
}

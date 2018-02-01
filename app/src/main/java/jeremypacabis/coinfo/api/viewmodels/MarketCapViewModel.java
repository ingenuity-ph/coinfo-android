package jeremypacabis.coinfo.api.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import jeremypacabis.coinfo.api.models.MarketCap;

/**
 * Created by jeremypacabis on January 26, 2018.
 *
 * @author Jeremy Patrick Pacabis <jeremy@ingenuity.ph>
 *         jeremypacabis.coinfo.api.viewmodels <coinfo-android>
 */

public class MarketCapViewModel extends BaseViewModel {

    private MutableLiveData<MarketCap> marketCap;

    public LiveData<MarketCap> getMarketCap() {
        if (marketCap == null) {
            marketCap = new MutableLiveData<>();
            fetchData();
        }

        return marketCap;
    }

    public void fetchData() {
        disposable.add(apiClient.fetchMarketCap()
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MarketCap>() {
                    @Override
                    public void onNext(MarketCap value) {
                        marketCap.postValue(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        marketCap.postValue(null);
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }
}

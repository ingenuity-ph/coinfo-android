package jeremypacabis.coinfo.api.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jeremypacabis on January 26, 2018.
 *
 * @author Jeremy Patrick Pacabis <jeremy@ingenuity.ph>
 *         jeremypacabis.coinfo.api.viewmodels <coinfo-android>
 */

public class CoinsListViewModel extends BaseViewModel {

    private MutableLiveData<List<String>> coinsList;

    public LiveData<List<String>> getCoinsList() {
        if (coinsList == null) {
            coinsList = new MutableLiveData<>();
            fetchData();
        }

        return coinsList;
    }

    public void fetchData() {
        disposable.add(apiClient.fetchCoinsList()
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<String>>() {
                    @Override
                    public void onNext(List<String> value) {
                        coinsList.postValue(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        coinsList.postValue(new ArrayList<String>());
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }
}

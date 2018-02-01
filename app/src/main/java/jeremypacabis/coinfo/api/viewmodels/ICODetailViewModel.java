package jeremypacabis.coinfo.api.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import jeremypacabis.coinfo.api.models.CoinStats;
import jeremypacabis.coinfo.api.models.ICODetail;

/**
 * Created by jeremypacabis on January 29, 2018.
 *
 * @author Jeremy Patrick Pacabis <jeremy@ingenuity.ph>
 *         jeremypacabis.coinfo.api.viewmodels <coinfo-android>
 */

public class ICODetailViewModel extends BaseViewModel {
    private MutableLiveData<ICODetail> icoDetail;

    public LiveData<ICODetail> getICODetails() {
        if (icoDetail == null) {
            icoDetail = new MutableLiveData<>();
            fetchData("");
        }

        return icoDetail;
    }

    public void fetchData(String coin) {
        disposable.add(apiClient.fetchICODetail(coin)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ICODetail>() {
                    @Override
                    public void onNext(ICODetail value) {
                        icoDetail.postValue(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        icoDetail.postValue(null);
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }
}

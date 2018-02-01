package jeremypacabis.coinfo.api.viewmodels;

import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import jeremypacabis.coinfo.Shared;
import jeremypacabis.coinfo.api.ApiClient;

/**
 * Created by jeremypacabis on January 26, 2018.
 *
 * @author Jeremy Patrick Pacabis <jeremy@ingenuity.ph>
 *         jeremypacabis.coinfo.api.viewmodels <coinfo-android>
 */

public class BaseViewModel extends ViewModel {

    protected final ApiClient apiClient = Shared.apiClient;
    protected CompositeDisposable disposable;

    public BaseViewModel() {
        super();
        disposable = new CompositeDisposable();
    }
}

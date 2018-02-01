Coinfo
=

###### _info about coins_

A basic project implementation of [Chasing Coins API][1]
with implemented [Android Architecture Components][2].


#### Basic Implementation of ViewModels + LiveData
1. Create your **ViewModel** class composed of a *getData()*
method and a **MutableLiveData** of your model:

```java
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
```

2. Implement it in your **Activity** or **Fragment**
by retrieving the ViewModel’s *instance* from the
**ViewModelsProvider** and to be used for the current
scope (until Activity/Fragment is destroyed completely).

```java
    private CoinsListViewModel coinsListViewModel;
```

3. Upon the creation of your Activity/Fragment (onCreate method),
initialize the **ViewModel**, optionally you can assign an **Observer**
for the **ViewModel** to detect changes and update the views with
the changes in the data.

```java
    coinsListViewModel = ViewModelProviders.of(this).get(CoinsListViewModel.class);
    coinsListViewModel.getCoinsList().observeForever(coinsListObserver);
```

4. The observer used is this:

````java
    private Observer<List<String>> coinsListObserver = new Observer<List<String>>() {
        @Override
        public void onChanged(@Nullable List<String> strings) {
            coinsList.addAll(strings);
            coinsListAdapter.notifyDataSetChanged();
        }
    };
````

> Whenever there are changes in the data for the list of coins,
the RecyclerView adapter will be updated and the list will also be updated.

> The API client uses **Retrofit 2** for API calls

[1]: https://chasing-coins.com/api
[2]: https://developer.android.com/topic/libraries/architecture/index.html
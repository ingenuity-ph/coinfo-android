package jeremypacabis.coinfo.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jeremypacabis.coinfo.R;
import jeremypacabis.coinfo.Shared;
import jeremypacabis.coinfo.adapters.ChangesAdapter;
import jeremypacabis.coinfo.adapters.CoinsListAdapter;
import jeremypacabis.coinfo.api.models.CoinStats;
import jeremypacabis.coinfo.api.models.ICODetail;
import jeremypacabis.coinfo.api.models.MarketCap;
import jeremypacabis.coinfo.api.viewmodels.CoinsListViewModel;
import jeremypacabis.coinfo.api.viewmodels.ICODetailViewModel;
import jeremypacabis.coinfo.api.viewmodels.MarketCapViewModel;

public class MainActivity extends BaseActivity {

    @BindView(R.id.market_cap_value_text)
    TextView marketCapText;
    @BindView(R.id.changes_spinner)
    Spinner changesSpinner;
    @BindView(R.id.data_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.coins_autocompletetextview)
    AutoCompleteTextView coinsAutoCompleteTextView;
    @BindView(R.id.coin_logo)
    ImageView coinLogo;
    @BindView(R.id.coin_name_text)
    TextView coinNameText;
    @BindView(R.id.coin_symbol_text)
    TextView coinSymbolText;
    @BindView(R.id.coins_details)
    LinearLayout coinDetailsLayout;
    @BindView(R.id.details_loading_progress)
    ProgressBar detailsLoadingProgress;

    private MarketCapViewModel marketCapViewModel;
    private CoinsListViewModel coinsListViewModel;
    private ICODetailViewModel icoDetailViewModel;
    private ChangesAdapter changesAdapter;
    private CoinsListAdapter coinsListAdapter;
    private List<String> changesList, coinsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Shared.init(this);
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initData() {
        changesList = new ArrayList<>();
        coinsList = new ArrayList<>();
        changesAdapter = new ChangesAdapter(this, changesList);
        coinsListAdapter = new CoinsListAdapter(this, R.layout.item_coins_list,  coinsList);
        changesSpinner.setAdapter(changesAdapter);
        coinsAutoCompleteTextView.setAdapter(coinsListAdapter);
        coinsAutoCompleteTextView.setOnClickListener(coinsAutoCompleteOnClicked);
        coinsAutoCompleteTextView.setOnItemClickListener(onCoinSelectedListener);

        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);

        marketCapViewModel = ViewModelProviders.of(this).get(MarketCapViewModel.class);
        marketCapViewModel.getMarketCap().observeForever(marketCapObserver);

        coinsListViewModel = ViewModelProviders.of(this).get(CoinsListViewModel.class);
        coinsListViewModel.getCoinsList().observeForever(coinsListObserver);

        icoDetailViewModel = ViewModelProviders.of(this).get(ICODetailViewModel.class);
        icoDetailViewModel.getICODetails().observeForever(icoDetailObserver);

        detailsLoadingProgress.setVisibility(View.GONE);
        coinDetailsLayout.setVisibility(View.GONE);
    }

    private final Observer<MarketCap> marketCapObserver = new Observer<MarketCap>() {
        @Override
        public void onChanged(@Nullable MarketCap marketCap) {
            if (marketCap != null) {
                final String marketCapValue = String.format("$%s", new DecimalFormat("#,###").format(marketCap.marketCap));
                marketCapText.setText(marketCapValue);

                changesList.add(marketCap.change._1h);
                changesList.add(marketCap.change._24h);
                changesList.add(marketCap.change._7d);
                changesAdapter.notifyDataSetChanged();
                detailsLoadingProgress.setVisibility(View.GONE);
            }
        }
    };

    private Observer<List<String>> coinsListObserver = new Observer<List<String>>() {
        @Override
        public void onChanged(@Nullable List<String> strings) {
            coinsList.addAll(strings);
            coinsListAdapter.notifyDataSetChanged();
        }
    };

    private Observer<ICODetail> icoDetailObserver = new Observer<ICODetail>() {
        @Override
        public void onChanged(@Nullable ICODetail icoDetail) {
            if (icoDetail != null) {
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.coin)
                        .error(android.R.drawable.stat_notify_error)
                        .centerInside()
                        .priority(Priority.HIGH);

                Glide.with(getBaseContext())
                        .load(getBaseContext().getString(R.string.logo_url, icoDetail.symbol))
                        .apply(requestOptions)
                        .into(coinLogo);

                coinNameText.setText(icoDetail.name);
                coinSymbolText.setText(icoDetail.symbol);
                detailsLoadingProgress.setVisibility(View.GONE);
                coinDetailsLayout.setVisibility(View.VISIBLE);
            }
        }
    };

    private Observer<CoinStats> coinStatsObserver = new Observer<CoinStats>() {
        @Override
        public void onChanged(@Nullable CoinStats coinStats) {
            if (coinStats != null) {

            }
        }
    };

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            coinsList.clear();
            changesList.clear();
            changesAdapter.notifyDataSetChanged();
            coinsListAdapter.notifyDataSetChanged();

            marketCapViewModel.fetchData();
            coinsListViewModel.fetchData();
            coinsAutoCompleteTextView.clearFocus();
            swipeRefreshLayout.setRefreshing(false);
            detailsLoadingProgress.setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener coinsAutoCompleteOnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.hasFocus()) {
                ((AutoCompleteTextView) v).showDropDown();
            }
        }
    };

    private AdapterView.OnItemClickListener onCoinSelectedListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final String selectedCoin = (String) coinsListAdapter.getItem(position);
            detailsLoadingProgress.setVisibility(View.VISIBLE);
            coinDetailsLayout.setVisibility(View.GONE);
            icoDetailViewModel.fetchData(selectedCoin);
            hideKeyboard();
        }
    };
}

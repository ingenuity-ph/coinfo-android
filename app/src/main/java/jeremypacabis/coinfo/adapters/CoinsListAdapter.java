package jeremypacabis.coinfo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jeremypacabis.coinfo.R;

/**
 * Created by jeremypacabis on January 26, 2018.
 *
 * @author Jeremy Patrick Pacabis <jeremy@ingenuity.ph>
 *         jeremypacabis.coinfo.adapters <coinfo-android>
 */

public class CoinsListAdapter extends ArrayAdapter implements Filterable {

    @BindView(R.id.coin_name)
    TextView coinName;
    @BindView(R.id.coin_logo)
    ImageView coinLogo;
    private LayoutInflater layoutInflater;
    private List<String> coins;
    private Context context;
    private int resourceId;

    private ListFilter listFilter = new ListFilter();
    private List<String> dataListAllItems;

    public CoinsListAdapter(@NonNull Context context, int resource, @NonNull List<String> coins) {
        super(context, resource, coins);
        this.coins = coins;
        this.context = context;
        this.resourceId = resource;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return coins.size();
    }

    @Override
    public Object getItem(int position) {
        return coins.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(resourceId, parent, false);
        }

        ButterKnife.bind(this, convertView);
        initViews(position);
        return convertView;

    }

    private void initViews(int pos) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.coin)
                .error(android.R.drawable.stat_notify_error)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerInside()
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(context.getString(R.string.logo_url, coins.get(pos)))
                .apply(requestOptions)
                .into(coinLogo);

        coinName.setText(coins.get(pos));
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private class ListFilter extends Filter {
        private final Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (dataListAllItems == null) {
                synchronized (lock) {
                    dataListAllItems = new ArrayList<>(coins);
                }
            }

            if (constraint == null || constraint.length() == 0) {
                synchronized (lock) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = constraint.toString().toLowerCase();

                ArrayList<String> matchValues = new ArrayList<>();
                for (String coin : dataListAllItems) {
                    if (coin.toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(coin);
                    }
                }

                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                coins = (List<String>) results.values;
            } else {
                coins = dataListAllItems;
            }

            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}

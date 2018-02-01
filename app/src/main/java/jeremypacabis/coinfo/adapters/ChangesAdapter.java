package jeremypacabis.coinfo.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import jeremypacabis.coinfo.R;

/**
 * Created by jeremypacabis on January 26, 2018.
 *
 * @author Jeremy Patrick Pacabis <jeremy@ingenuity.ph>
 *         jeremypacabis.coinfo.adapters <coinfo-android>
 */

public class ChangesAdapter extends BaseAdapter {

    @BindView(R.id.change_direction_image)
    ImageView changeDirectionImage;
    @BindView(R.id.change_text)
    TextView changeText;
    private LayoutInflater layoutInflater;
    private List<String> changes;

    public ChangesAdapter(@NonNull Context context, @NonNull List<String> objects) {
        this.changes = objects;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return changes.size();
    }

    @Override
    public Object getItem(int position) {
        return changes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_market_cap_change, parent, false);
        }

        ButterKnife.bind(this, convertView);
        initViews(position);
        return convertView;
    }

    private void initViews(int pos) {
        String changeTextValue = null;
        final float change = Float.valueOf(changes.get(pos));
        changeDirectionImage.setImageResource(change < 0 ? R.drawable.arrow_down : R.drawable.arrow_up);
        changeDirectionImage.setColorFilter(change < 0 ? Color.RED : Color.GREEN);
        changeText.setTextColor(change < 0 ? Color.RED : Color.GREEN);

        switch (pos) {
            case 0:
                changeTextValue = String.format(Locale.getDefault(), "%.2f%% in past hour", change);
                break;
            case 1:
                changeTextValue = String.format(Locale.getDefault(), "%.2f%% in 24 hours", change);
                break;
            case 2:
                changeTextValue = String.format(Locale.getDefault(), "%.2f%% in 7 days", change);
                break;
        }

        changeText.setText(changeTextValue);
    }
}

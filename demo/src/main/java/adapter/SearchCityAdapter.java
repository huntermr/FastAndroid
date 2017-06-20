package demo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import demo.base.CustomBaseAdapter;
import demo.vo.response.store.City;

/**
 * Created by Administrator on 2017/5/10.
 */

public class SearchCityAdapter extends CustomBaseAdapter<City> {

    public SearchCityAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        City itemData = getItemData(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = getItemView(R.layout.item_search_city, parent);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(itemData.getCityName());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

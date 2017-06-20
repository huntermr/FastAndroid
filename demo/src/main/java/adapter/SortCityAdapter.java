package demo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tbl.android.R;
import demo.base.CustomBaseAdapter;
import demo.vo.response.store.City;

/**
 * Created by Administrator on 2017/5/10.
 */

public class SortCityAdapter extends CustomBaseAdapter<City> {

    public SortCityAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        City itemData = getItemData(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = getItemView(R.layout.item_sort_city, parent);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int section = getSectionForPosition(position);

        if (position == getPositionForSection(section)) {
            holder.tvLetter.setVisibility(View.VISIBLE);
            holder.tvLetter.setText(itemData.getSortLetters());
        } else {
            holder.tvLetter.setVisibility(View.GONE);
        }

        holder.tvName.setText(itemData.getCityName());

        return convertView;
    }

    public int getSectionForPosition(int position) {
        return getItemData(position).getSortLetters().charAt(0);
    }

    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = getItemData(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    static class ViewHolder {
        @BindView(R.id.tv_letter)
        TextView tvLetter;
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

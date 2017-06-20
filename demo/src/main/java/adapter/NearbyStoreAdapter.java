package demo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tbl.android.R;
import demo.base.CustomBaseAdapter;
import demo.vo.response.store.Store;

/**
 * Created by Administrator on 2017/5/8.
 */

public class NearbyStoreAdapter extends CustomBaseAdapter<Store> {

    public NearbyStoreAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Store itemData = getItemData(position);

        ViewHolder holder;
        if (convertView == null) {
            convertView = getItemView(R.layout.item_nearby_store, parent);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvStoreName.setText(itemData.getStoreName());
        holder.tvStoreAddress.setText(itemData.getAddress());
        if(!TextUtils.isEmpty(itemData.getDistance())){
            Double parseDouble = Double.parseDouble(itemData.getDistance());
            if(parseDouble < 1000){
                holder.tvStoreDistance.setText(getContext().getString(R.string.distance_m, parseDouble.intValue()));
            }else{
                BigDecimal bigDecimal = new BigDecimal(parseDouble / 1000);
                BigDecimal decimal = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP);
                holder.tvStoreDistance.setText(getContext().getString(R.string.distance_km, decimal.toString()));
            }
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_store_name)
        TextView tvStoreName;
        @BindView(R.id.tv_store_address)
        TextView tvStoreAddress;
        @BindView(R.id.tv_store_distance)
        TextView tvStoreDistance;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

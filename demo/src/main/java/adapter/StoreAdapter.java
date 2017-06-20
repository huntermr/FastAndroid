package demo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import demo.base.CustomBaseAdapter;
import demo.utils.ImageLoadUtils;
import demo.vo.FavorStoreChange;
import demo.vo.response.store.Store;

/**
 * Created by Administrator on 2017/5/8.
 */

public class StoreAdapter extends CustomBaseAdapter<Store> {

    public StoreAdapter(Context context) {
        super(context);
    }

    public void changeFavor(FavorStoreChange change){
        if(getCount() > 0){
            for(Store data : getData()){
                if(change.getStoreId().equals(data.getStoreId())){
                    data.setFavor(change.isFavor());
                    return;
                }
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Store itemData = getItemData(position);

        final ViewHolder holder;
        if (convertView == null) {
            convertView = getItemView(R.layout.item_store, parent);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoadUtils.loadImage(getContext(), itemData.getImgUrl(), R.mipmap.shop_img, holder.ivCover);
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
        holder.tvStoreTel.setText(itemData.getTelephone());
        holder.btnFavor.setImageResource(itemData.isFavor() ? R.mipmap.nav_collection_sel : R.mipmap.nav_collection_nor);
        holder.btnFavor.setTag(itemData.getStoreId());
        holder.btnFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemData.setFavor(!itemData.isFavor());
                holder.btnFavor.setImageResource(itemData.isFavor() ? R.mipmap.nav_collection_sel : R.mipmap.nav_collection_nor);

                FavorStoreChange change = new FavorStoreChange();
                change.setFavor(itemData.isFavor());
                change.setStoreId(itemData.getStoreId());
                EventBus.getDefault().post(change);

                if(mOnFavorListener != null){
                    if(itemData.isFavor()){
                        mOnFavorListener.onFavor(itemData.getStoreId());
                    }else{
                        mOnFavorListener.onCancelFavor(itemData.getStoreId());
                    }
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_store_name)
        TextView tvStoreName;
        @BindView(R.id.tv_store_address)
        TextView tvStoreAddress;
        @BindView(R.id.tv_store_distance)
        TextView tvStoreDistance;
        @BindView(R.id.tv_store_tel)
        TextView tvStoreTel;
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.btn_favorites)
        ImageView btnFavor;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    OnFavorListener mOnFavorListener;

    public void setOnFavorListener(OnFavorListener onFavorListener){
        mOnFavorListener = onFavorListener;
    }

    public interface OnFavorListener{
        void onFavor(String storeId);
        void onCancelFavor(String storeId);
    }
}

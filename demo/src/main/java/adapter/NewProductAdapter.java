package demo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import demo.base.CustomBaseAdapter;
import demo.utils.ImageLoadUtils;
import demo.utils.PixelUtils;
import demo.vo.response.store.NewProduct;

/**
 * Created by Administrator on 2017/5/9.
 */

public class NewProductAdapter extends CustomBaseAdapter<NewProduct> {

    public NewProductAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final NewProduct itemData = getItemData(position);
        final ViewHolder holder;
        if (convertView == null) {
            convertView = getItemView(R.layout.item_new_product, parent);

            int imageWidth = PixelUtils.getWindowWidth();
            int imageHeight = imageWidth / 10 * 4;
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(imageWidth, imageHeight);
            convertView.setLayoutParams(layoutParams);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvProductName.setText(itemData.getProductName());
        ImageLoadUtils.loadImage(getContext(), itemData.getImgUrl(), holder.ivCover);
        holder.btnFavor.setImageResource(itemData.isFavor() ? R.mipmap.nav_collection_sel : R.mipmap.nav_collection_nor);
        holder.btnFavor.setTag(itemData.getProductId());
        holder.btnFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemData.setFavor(!itemData.isFavor());
                holder.btnFavor.setImageResource(itemData.isFavor() ? R.mipmap.nav_collection_sel : R.mipmap.nav_collection_nor);

                if(mOnFavorListener != null){
                    if(itemData.isFavor()){
                        mOnFavorListener.onFavor(itemData.getProductId());
                    }else{
                        mOnFavorListener.onCancelFavor(itemData.getProductId());
                    }
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
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
        void onFavor(String productId);
        void onCancelFavor(String productId);
    }
}

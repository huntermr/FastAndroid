package demo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import demo.base.CustomBaseAdapter;
import demo.utils.ImageLoadUtils;
import demo.vo.response.store.SpecialOffer;

/**
 * Created by Administrator on 2017/5/9.
 */

public class SpecialOfferAdapter extends CustomBaseAdapter<SpecialOffer> {

    public SpecialOfferAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpecialOffer itemData = getItemData(position);
        ViewHolder holder;

        if(convertView == null){
            convertView = getItemView(R.layout.item_special_offer, parent);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoadUtils.loadImage(getContext(), itemData.getImgUrl(), holder.ivCover);
        holder.tvActivityTitle.setText(itemData.getTitle());

        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_activity_title)
        TextView tvActivityTitle;

        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}

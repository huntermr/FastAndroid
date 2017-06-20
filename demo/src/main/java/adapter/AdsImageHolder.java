package demo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;

import demo.utils.ImageLoadUtils;
import demo.vo.response.store.Ads;

/**
 * 广告轮播图适配器
 */
public class AdsImageHolder implements Holder<Ads> {
    ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Ads data) {
        ImageLoadUtils.loadImage(context, data.getImgUrl(), imageView);
    }
}


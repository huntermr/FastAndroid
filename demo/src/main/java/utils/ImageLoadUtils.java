package demo.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;

import cn.tbl.android.R;

/**
 * 图片加载工具类
 * 为方便以后随时更换图片加载库
 * 并且统一配置图片加载方式
 *
 * @author Hunter
 */
public class ImageLoadUtils {

    public static final int DEFAULT_PLACEHOLDER_RESID = R.color.light_gray3;

    /**
     * 加载一张网络图片
     * @param context
     * @param imageUrl
     * @param resId
     * @param imageView
     * @param playAnim
     */
    public static void loadImage(Context context, String imageUrl, int resId, ImageView imageView, boolean playAnim) {
        DrawableRequestBuilder<String> builder = Glide
                .with(context)
                .load(imageUrl)
                .placeholder(resId);

        if (playAnim) {
            builder.crossFade(500);
        } else {
            builder.dontAnimate();
        }

        builder.into(imageView);
    }

    /**
     * 加载图片,不执行加载动画
     * @param context
     * @param imageUrl
     * @param imageView
     */
    public static void loadImageDontAnim(Context context, String imageUrl, int resId, ImageView imageView) {
        loadImage(context, imageUrl, resId, imageView, false);
    }

    /**
     * 加载图片(默认执行加载动画)
     * @param context
     * @param imageUrl
     * @param imageView
     */
    public static void loadImage(Context context, String imageUrl, int resId, ImageView imageView) {
        loadImage(context, imageUrl, resId, imageView, true);
    }

    /**
     * 加载图片,不执行加载动画
     * @param context
     * @param imageUrl
     * @param imageView
     */
    public static void loadImageDontAnim(Context context, String imageUrl, ImageView imageView) {
        loadImage(context, imageUrl, DEFAULT_PLACEHOLDER_RESID, imageView, false);
    }

    /**
     * 加载图片(默认执行加载动画)
     * @param context
     * @param imageUrl
     * @param imageView
     */
    public static void loadImage(Context context, String imageUrl, ImageView imageView) {
        loadImage(context, imageUrl, DEFAULT_PLACEHOLDER_RESID, imageView, true);
    }
}

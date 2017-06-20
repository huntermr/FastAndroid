package demo.utils;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import demo.vo.ShareData;

/**
 * Created by Administrator on 2017/6/13.
 */

public class ShareUtils {
    public static void startShare(Activity activity, ShareData shareData, UMShareListener umShareListener){
        ShareBoardConfig config = new ShareBoardConfig();
        config.setCancelButtonVisibility(false);
        config.setIndicatorVisibility(false);
        config.setShareboardBackgroundColor(Color.WHITE);

        UMWeb web = new UMWeb(shareData.getUrl());
        web.setTitle(shareData.getTitle());//标题
        UMImage umImage;
        if(TextUtils.isEmpty(shareData.getImage())){
            umImage = new UMImage(activity, shareData.getImageRes());
        }else{
            umImage = new UMImage(activity, shareData.getImage());
        }

        web.setThumb(umImage);  //缩略图
        web.setDescription(shareData.getDesc());//描述

        new ShareAction(activity).withText("hello")
                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener).open(config);
    }

}

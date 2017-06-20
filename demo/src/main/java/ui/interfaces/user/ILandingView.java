package demo.ui.interfaces.user;

import java.util.List;

import demo.base.IBaseView;
import demo.vo.response.store.Ads;

/**
 * Created by Administrator on 2017/5/5.
 */

public interface ILandingView extends IBaseView {
    /**
     * 获取广告图成功,更新ui
     *
     * @param ad
     */
    void uiAds(List<Ads> ads);

    /**
     * 加载广告失败
     */
    void failAd();
}

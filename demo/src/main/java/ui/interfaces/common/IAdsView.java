package demo.ui.interfaces.common;

import java.util.List;

import demo.base.IBaseView;
import demo.vo.response.store.Ads;

/**
 * Created by Administrator on 2017/5/26.
 */

public interface IAdsView extends IBaseView {
    /**
     * 获取广告图片列表成功
     * @param adsList
     */
    void uiAds(List<Ads> adsList);
}

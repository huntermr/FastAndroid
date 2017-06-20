package demo.ui.interfaces.user;

import java.util.List;

import demo.base.IBaseView;
import demo.vo.response.store.Store;

/**
 * Created by Administrator on 2017/5/26.
 */

public interface IFavorStoreView extends IBaseView {
    /**
     * 获取收藏的店铺列表
     *
     * @param stores
     */
    void uiFavorStore(List<Store> stores);
}

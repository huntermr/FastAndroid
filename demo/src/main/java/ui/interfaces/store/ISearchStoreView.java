package demo.ui.interfaces.store;

import java.util.List;

import demo.base.IBaseView;
import demo.vo.response.store.Store;

/**
 * Created by Administrator on 2017/5/26.
 */

public interface ISearchStoreView extends IBaseView {
    /**
     * 搜索店铺
     *
     * @param stores
     */
    void uiSearchStore(List<Store> stores);
}

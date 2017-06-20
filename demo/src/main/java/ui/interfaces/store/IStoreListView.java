package demo.ui.interfaces.store;

import java.util.List;

import demo.base.IBaseView;
import demo.vo.response.store.Store;

/**
 * Created by Administrator on 2017/5/26.
 */

public interface IStoreListView extends IBaseView {
    /**
     * 获取店铺列表
     *
     * @param stores
     */
    void uiStoreList(List<Store> stores);
}

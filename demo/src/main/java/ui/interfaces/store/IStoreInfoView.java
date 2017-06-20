package demo.ui.interfaces.store;

import demo.base.IBaseView;
import demo.vo.response.store.StoreDetail;

/**
 * Created by Administrator on 2017/6/8.
 */

public interface IStoreInfoView extends IBaseView {
    /**
     * 获取店铺详情成功
     * @param storeDetail
     */
    void uiStoreInfo(StoreDetail storeDetail);
}

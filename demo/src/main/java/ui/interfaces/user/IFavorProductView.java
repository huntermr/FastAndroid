package demo.ui.interfaces.user;

import java.util.List;

import demo.base.IBaseView;
import demo.vo.response.store.NewProduct;

/**
 * Created by Administrator on 2017/5/26.
 */

public interface IFavorProductView extends IBaseView {
    /**
     * 获取收藏的产品列表
     *
     * @param products
     */
    void uiFavorProduct(List<NewProduct> products);
}

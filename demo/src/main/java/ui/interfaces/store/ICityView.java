package demo.ui.interfaces.store;

import java.util.List;

import demo.base.IBaseView;
import demo.vo.response.store.City;

/**
 * Created by Administrator on 2017/6/13.
 */

public interface ICityView extends IBaseView {
    /**
     * 获取城市列表成功
     * @param cities
     */
    void uiCityList(List<City> cities);
}

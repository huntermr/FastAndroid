package demo.presenter;

import java.util.List;

import cache.greendao.CityDao;
import demo.app.DaoManager;
import demo.base.BasePresenter;
import demo.rx.ResponseSubscriber;
import demo.service.StoreService;
import demo.ui.interfaces.store.ICityView;
import demo.ui.interfaces.store.ISearchStoreView;
import demo.ui.interfaces.store.IStoreInfoView;
import demo.ui.interfaces.store.IStoreListView;
import demo.vo.request.SearchStoreRequest;
import demo.vo.request.StoreListRequest;
import demo.vo.response.store.City;
import demo.vo.response.store.Store;
import demo.vo.response.store.StoreDetail;

/**
 * Created by Administrator on 2017/6/7.
 */

public class StorePresenter extends BasePresenter {
    StoreService storeService;

    @Override
    protected void initService() {
        storeService = getService(StoreService.class);
    }

    /**
     * 根据距离和当前经纬度获取附近的店铺列表
     *
     * @param storeListView
     * @param request
     */
    public void getStoreList(final IStoreListView storeListView, StoreListRequest request) {
        subscribe(storeListView, convertResponse(storeService.storeList(converParams(request))), new ResponseSubscriber<List<Store>>(storeListView) {
            @Override
            public void onNext(List<Store> stores) {
                storeListView.uiStoreList(stores);
            }
        });
    }

    /**
     * 获取店铺详情
     *
     * @param storeInfoView
     * @param storeId
     */
    public void getStoreInfo(final IStoreInfoView storeInfoView, String storeId) {
        subscribe(storeInfoView, convertResponse(storeService.storeDetail(storeId)), new ResponseSubscriber<StoreDetail>(storeInfoView) {
            @Override
            public void onNext(StoreDetail storeDetail) {
                storeInfoView.uiStoreInfo(storeDetail);
            }
        });
    }

    /**
     * 根据距离和当前经纬度获取附近的店铺列表
     *
     * @param searchStoreView
     * @param request
     */
    public void searchStore(final ISearchStoreView searchStoreView, SearchStoreRequest request) {

        subscribe(searchStoreView, convertResponse(storeService.searchStore(converParams(request))), new ResponseSubscriber<List<Store>>(searchStoreView) {
            @Override
            public void onNext(List<Store> stores) {
                searchStoreView.uiSearchStore(stores);
            }
        });
    }

    /**
     * 获取城市列表
     * @param cityView
     */
    public void getCities(final ICityView cityView){
        CityDao cityDao = DaoManager.getInstance().getCityDao();
        List<City> cities = cityDao.loadAll();
        if(cities != null && cities.size() > 0){
            cityView.uiCityList(cities);
            return;
        }

        subscribe(cityView, convertResponse(storeService.cities()), new ResponseSubscriber<List<City>>(cityView) {
            @Override
            public void onNext(List<City> cities) {
                cityView.uiCityList(cities);

                if(cities != null){
                    CityDao dao = DaoManager.getInstance().getCityDao();
                    dao.saveInTx(cities);
                }
            }
        });
    }
}

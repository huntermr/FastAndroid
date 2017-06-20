package demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import demo.adapter.AdsImageHolder;
import demo.adapter.StoreAdapter;
import demo.app.Constants;
import demo.app.LocationManager;
import demo.base.BaseFragment;
import demo.presenter.StorePresenter;
import demo.presenter.UserPresenter;
import demo.ui.activity.store.CitySelectActivity;
import demo.ui.activity.store.SearchStoreActivity;
import demo.ui.activity.store.StoreIndexActivity;
import demo.ui.interfaces.common.IAdsView;
import demo.ui.interfaces.store.IStoreListView;
import demo.ui.interfaces.user.IAddFavorView;
import demo.ui.interfaces.user.IDelFavorView;
import demo.utils.CommonUtils;
import demo.utils.PixelUtils;
import demo.vo.FavorStoreChange;
import demo.vo.request.StoreListRequest;
import demo.vo.response.store.Ads;
import demo.vo.response.store.Store;


/**
 * Created by Administrator on 2017/5/6.
 */

public class MainFragment extends BaseFragment implements IAdsView, IStoreListView, IAddFavorView, IDelFavorView {
    @BindView(R.id.lv_main)
    PullToRefreshListView lvMain;
    @BindView(R.id.search_bar)
    RelativeLayout searchBar;
    @BindView(R.id.search)
    RelativeLayout search;
    @BindView(R.id.tv_city)
    TextView tvCity;
    ConvenientBanner adsViewPager;

    private StoreAdapter storeAdapter;

    UserPresenter userPresenter;
    StorePresenter storePresenter;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFavorChange(FavorStoreChange change){
        storeAdapter.changeFavor(change);
        storeAdapter.notifyDataSetChanged();
    }

    @Override
    public void initView() {
        userPresenter = new UserPresenter();
        storePresenter = new StorePresenter();

        lvMain.setMode(PullToRefreshBase.Mode.DISABLED);

        int searchBarHeight = getStatusBarHeight() + PixelUtils.dp2px(50);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, searchBarHeight);
        searchBar.setLayoutParams(layoutParams);
        searchBar.setAlpha(0);

        LocationManager.getInstance().setOnLocationListener(new LocationManager.OnLocationListener() {
            @Override
            public void onLocation(AMapLocation aMapLocation) {
                tvCity.setText(CommonUtils.formatCityName(aMapLocation.getCity()));
            }
        });

        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPageForResult(new Intent(getBaseActivity(), CitySelectActivity.class), Constants.SELECT_CITY_REQ_CODE);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage(SearchStoreActivity.class);
            }
        });

        initAds();
        initListView();
    }

    public void scrollTop(){
        if(lvMain != null && lvMain.getRefreshableView() != null) {
            lvMain.getRefreshableView().dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0));
            lvMain.getRefreshableView().setSelection(0);
        }

    }

    /**
     * 初始化广告轮播图
     */
    private void initAds() {
        View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.layout_ads, null);
        adsViewPager = (ConvenientBanner) view.findViewById(R.id.banner);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, PixelUtils.getWindowWidth() / 3 * 2);
        adsViewPager.setLayoutParams(layoutParams);
        adsViewPager.setPageIndicator(new int[]{R.mipmap.point2, R.mipmap.point1});
        // 开始自动翻页
        adsViewPager.startTurning(Constants.AUTO_SCROLL_INTERVAL);
        lvMain.getRefreshableView().addHeaderView(view);

        userPresenter.getAds(this, Constants.BASE_STORE_ID);
    }

    /**
     * 初始化店铺列表
     */
    private void initListView() {
        storeAdapter = new StoreAdapter(getBaseActivity());
        storeAdapter.setOnFavorListener(new StoreAdapter.OnFavorListener() {
            @Override
            public void onFavor(String storeId) {
                userPresenter.favorChangeStore(MainFragment.this, MainFragment.this, true, storeId);
            }

            @Override
            public void onCancelFavor(String storeId) {
                userPresenter.favorChangeStore(MainFragment.this, MainFragment.this, false, storeId);
            }
        });

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == storeAdapter.getCount() + 2) return;

                Store itemData = storeAdapter.getItemData(position - 2);
                StoreIndexActivity.actionActivity(getBaseActivity(), itemData.getStoreId());
            }
        });
        lvMain.setAdapter(storeAdapter);
        initSearchAnim();

        LocationManager.getInstance().setOnLocationListener(new LocationManager.OnLocationListener() {
            @Override
            public void onLocation(AMapLocation aMapLocation) {
                getStoreList(aMapLocation.getCityCode());
            }
        });
    }

    private void getStoreList(String cityCode) {
        StoreListRequest request = new StoreListRequest();
        request.setCityCode(cityCode);
        LatLng latLng = LocationManager.getInstance().getLatLng();
        request.setCustomerLat(latLng.latitude);
        request.setCustomerLng(latLng.longitude);
        storePresenter.getStoreList(this, request);
    }

    /**
     * 搜索条背景随滑动渐变的动画
     */
    private void initSearchAnim() {
        lvMain.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (lvMain.getRefreshableView().getCount() > 0) {
                    if (firstVisibleItem <= 1) {
                        float alpha = (float) getScrollY() / Constants.SCROLL;
                        alpha = Math.max(alpha, 0);
                        alpha = Math.min(alpha, 1);

                        searchBar.setAlpha(alpha);
                    }
                }
            }
        });
    }

    /**
     * 获取Y轴滑动距离
     *
     * @return
     */
    private int getScrollY() {
        // 第一个显示的还是头部,直接获取top
        View childAt = lvMain.getRefreshableView().getChildAt(0);
        if (childAt == null) {
            return 0;
        }
        int top = childAt.getTop();

        return -top;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.SELECT_CITY_REQ_CODE && resultCode == Constants.SELECT_CITY_RES_CODE) {
            final String cityName = data.getStringExtra(Constants.CITY_NAME);
            tvCity.setText(cityName);

            searchCityByName(cityName);
        }
    }

    /**
     * 根据城市名称获取城市经纬度
     * @param cityName
     */
    private void searchCityByName(String cityName) {
        Logger.i("需要查找的城市名称为:" + cityName);

        final GeocodeSearch geocodeSearch = new GeocodeSearch(getBaseActivity());
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                RegeocodeAddress address = regeocodeResult.getRegeocodeAddress();
                String cityCode = address.getCityCode();
                Logger.i("逆编码获取到的城市编码为:" + cityCode);
                getStoreList(cityCode);
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                List<GeocodeAddress> addressList = geocodeResult.getGeocodeAddressList();
                if(addressList != null && addressList.size() > 0){
                    GeocodeAddress address = addressList.get(0);
                    LatLonPoint latLonPoint = address.getLatLonPoint();
                    Logger.i("查找到的经纬度为:" + latLonPoint);
                    reGeocodeCity(geocodeSearch, latLonPoint);
                }
            }
        });

        GeocodeQuery geocodeQuery = new GeocodeQuery(cityName, cityName);
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);
    }

    /**
     * 根据经纬度获取城市编码信息
     * @param geocodeSearch
     * @param latLonPoint
     */
    private void reGeocodeCity(GeocodeSearch geocodeSearch, LatLonPoint latLonPoint){
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(latLonPoint, 1000, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(regeocodeQuery);
    }

    @Override
    public void uiAds(List<Ads> adsList) {
        adsViewPager.setPages(new CBViewHolderCreator<AdsImageHolder>() {
            @Override
            public AdsImageHolder createHolder() {
                return new AdsImageHolder();
            }
        }, adsList);
    }

    @Override
    public void uiStoreList(List<Store> stores) {
        storeAdapter.setData(stores);
        storeAdapter.notifyDataSetChanged();
    }

    @Override
    public void uiAddFavor() {

    }

    @Override
    public void uiDelFavor() {

    }
}

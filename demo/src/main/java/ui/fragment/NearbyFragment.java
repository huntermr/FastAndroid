package demo.ui.fragment;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.tbl.android.R;
import demo.adapter.NearbyStoreAdapter;
import demo.adapter.SearchScopeAdapter;
import demo.app.Constants;
import demo.app.LocationManager;
import demo.base.BaseFragment;
import demo.presenter.StorePresenter;
import demo.ui.activity.store.StoreIndexActivity;
import demo.ui.interfaces.store.IStoreListView;
import demo.ui.widget.FilterPopup;
import demo.ui.widget.TitleBar;
import demo.utils.PixelUtils;
import demo.vo.SearchScope;
import demo.vo.request.StoreListRequest;
import demo.vo.response.store.Store;

/**
 * Created by Administrator on 2017/5/8.
 */

public class NearbyFragment extends BaseFragment implements IStoreListView {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.map)
    TextureMapView mapView;
    @BindView(R.id.lv_store)
    ListView lvStore;
    @BindView(R.id.filter)
    LinearLayout filter;
    @BindView(R.id.search_scope)
    RelativeLayout searchScope;
    @BindView(R.id.nav_mode)
    RelativeLayout navMode;
    @BindView(R.id.tv_search_scope)
    TextView tvSearchScope;
    @BindView(R.id.tv_nav_mode)
    TextView tvNavMode;
    ImageView btnToogleMode;
    SearchScopeAdapter scopeAdapter;
    SearchScopeAdapter navModeAdapter;
    NearbyStoreAdapter nearbyStoreAdapter;
    StorePresenter storePresenter;
    List<Marker> markers = new ArrayList<>();
    private AMap aMap;
    // 是否显示map模式  true map模式  false 列表模式
    private boolean isShowMap = true;
    private FilterPopup filterPopup;
    private FilterPopup navModePopup;

    // 当前导航方式
    int navType = Constants.ROUTE_TYPE_DRIVE;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_nearby;
    }

    @Override
    public void initView() {
        storePresenter = new StorePresenter();

        searchScope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toogleScopePopup();
            }
        });

        navMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toogleNavMode();
            }
        });

        initTitleBar();

        initMapView();
        initLocationStyle();
        initStore();
    }

    private void toogleScopePopup() {
        if (filterPopup == null) {
            filterPopup = new FilterPopup(getBaseActivity());
            filterPopup.setWidth(PixelUtils.getWindowWidth());
            filterPopup.setHeight(PixelUtils.getWindowHeight());
            scopeAdapter = new SearchScopeAdapter(getBaseActivity());
            scopeAdapter.addData(new SearchScope("1km", "1000"));
            scopeAdapter.addData(new SearchScope("3km", "3000"));
            scopeAdapter.addData(new SearchScope("5km", "5000"));
            scopeAdapter.addData(new SearchScope("10km", "10000"));
            scopeAdapter.addData(new SearchScope("全城"));
            filterPopup.setAdapter(scopeAdapter);
            filterPopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SearchScope itemData = scopeAdapter.getItemData(position);
                    tvSearchScope.setText(itemData.getName());

                    getStoreList(itemData.getDistance());

                    filterPopup.dismiss();
                }
            });
        }

        if (filterPopup.isShowing()) {
            filterPopup.dismiss();
        } else {
            filterPopup.showAsDropDown(filter);
        }
    }

    private void toogleNavMode() {
        if (navModePopup == null) {
            navModePopup = new FilterPopup(getBaseActivity());
            navModePopup.setWidth(PixelUtils.getWindowWidth());
            navModePopup.setHeight(PixelUtils.getWindowHeight());
            navModeAdapter = new SearchScopeAdapter(getBaseActivity());
            navModeAdapter.addData(new SearchScope("驾车"));
            navModeAdapter.addData(new SearchScope("步行"));
            navModeAdapter.addData(new SearchScope("骑行"));
            navModePopup.setAdapter(navModeAdapter);
            navModePopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position){
                        case 0:
                            navType = Constants.ROUTE_TYPE_DRIVE;
                            break;
                        case 1:
                            navType = Constants.ROUTE_TYPE_WALK;
                            break;
                        case 2:
                            navType = Constants.ROUTE_TYPE_RIDE;
                            break;
                    }

                    SearchScope itemData = navModeAdapter.getItemData(position);
                    tvNavMode.setText(itemData.getName());
                    navModePopup.dismiss();
                }
            });
        }

        if (navModePopup.isShowing()) {
            navModePopup.dismiss();
        } else {
            navModePopup.showAsDropDown(filter);
        }
    }

    private void initStore() {
        nearbyStoreAdapter = new NearbyStoreAdapter(getBaseActivity());
        lvStore.setAdapter(nearbyStoreAdapter);
        lvStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Store itemData = nearbyStoreAdapter.getItemData(position);
                goStoreIndex(navType, itemData);
            }
        });

        getStoreList(null);
    }

    private void goStoreIndex(int navType, Store itemData) {
        StoreIndexActivity.actionActivity(getBaseActivity(), navType, itemData.getStoreId());
    }

    private void getStoreList(String distance) {
        StoreListRequest request = new StoreListRequest();
        request.setDistance(distance);
        String cityCode = LocationManager.getInstance().getCityCode();
        request.setCityCode(cityCode);
        LatLng latLng = LocationManager.getInstance().getLatLng();
        request.setCustomerLat(latLng.latitude);
        request.setCustomerLng(latLng.longitude);
        storePresenter.getStoreList(this, request);
    }

    private void initLocationStyle() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.strokeColor(Color.TRANSPARENT);
        myLocationStyle.radiusFillColor(Color.TRANSPARENT);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.interval(Constants.LOCATION_INTERVAL); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true); //设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }

    private void addMarkersToMap(Store store) {
        LatLng latLng = new LatLng(store.getLatitude(), store.getLongitude());
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(store.getStoreName())
                .snippet(store.getAddress())
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.mipmap.icon_store)));
        Marker marker = aMap.addMarker(options);
        marker.setObject(store);
        markers.add(marker);
    }

    private void initTitleBar() {
        int searchBarHeight = getStatusBarHeight() + PixelUtils.dp2px(50);
        RelativeLayout.LayoutParams searBarLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, searchBarHeight);
        titleBar.setLayoutParams(searBarLayoutParams);

        View titleView = titleBar.getTitleView();
        LinearLayout.LayoutParams titleLayoutParams = (LinearLayout.LayoutParams) titleView.getLayoutParams();
        titleLayoutParams.setMargins(0, getStatusBarHeight() / 2, 0, 0);
        titleView.setLayoutParams(titleLayoutParams);
        titleBar.setTitle(R.string.nearby);

        btnToogleMode = new ImageView(getBaseActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(PixelUtils.dp2px(20), PixelUtils.dp2px(20));
        layoutParams.setMargins(0, getStatusBarHeight() / 2, 0, 0);
        btnToogleMode.setLayoutParams(layoutParams);
        btnToogleMode.setImageResource(R.mipmap.nav_icon_map);
        btnToogleMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleShowMode();
            }
        });
        titleBar.setRightView(btnToogleMode);
    }

    /**
     * 切换显示模式  地图/列表
     */
    private void toggleShowMode() {
        // 更改显示模式
        isShowMap = !isShowMap;
        btnToogleMode.setImageResource(isShowMap ? R.mipmap.nav_icon_map : R.mipmap.nav_icon_list);
        lvStore.setVisibility(isShowMap ? View.GONE : View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mapView.onCreate(savedInstanceState);

        return view;
    }

    /**
     * 初始化AMap对象
     */
    private void initMapView() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        aMap.moveCamera(CameraUpdateFactory.zoomTo(Constants.MAP_ZOOM_LEVEL));
        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Store store = (Store) marker.getObject();
                goStoreIndex(navType, store);
            }
        });
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void uiStoreList(List<Store> stores) {
        if (markers != null && markers.size() > 0) {
            for (Marker marker : markers) {
                marker.remove();
            }
        }

        nearbyStoreAdapter.clear();

        if (stores != null && stores.size() > 0) {
            for (Store store : stores) {
                addMarkersToMap(store);
            }

            nearbyStoreAdapter.setData(stores);
        }

        nearbyStoreAdapter.notifyDataSetChanged();
    }
}

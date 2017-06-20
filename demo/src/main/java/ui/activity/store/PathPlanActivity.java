package demo.ui.activity.store;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RidePath;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;

import butterknife.BindView;
import cn.tbl.android.R;
import demo.app.Constants;
import demo.app.LocationManager;
import demo.base.BaseActivity;
import demo.overlay.DrivingRouteOverlay;
import demo.overlay.RideRouteOverlay;
import demo.overlay.WalkRouteOverlay;
import demo.utils.amap.AMapUtil;
import demo.utils.amap.ToastUtil;

import static cn.tbl.android.app.Constants.ROUTE_TYPE_DRIVE;
import static cn.tbl.android.app.Constants.ROUTE_TYPE_RIDE;
import static cn.tbl.android.app.Constants.ROUTE_TYPE_WALK;

/**
 * Created by Administrator on 2017/5/18.
 */

public class PathPlanActivity extends BaseActivity implements AMap.OnMapClickListener,
        AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, RouteSearch.OnRouteSearchListener, View.OnClickListener {

    @BindView(R.id.drive)
    TextView drive;
    @BindView(R.id.walk)
    TextView walk;
    @BindView(R.id.ride)
    TextView ride;
    @BindView(R.id.btn_start_nav)
    Button btnStartNav;
    @BindView(R.id.btn_back)
    TextView btnBack;


    LatLng startLatlng;
    LatLng endLatlng;
    private AMap aMap;
    private MapView mapView;
    private Context mContext;
    private RouteSearch mRouteSearch;
    private DriveRouteResult mDriveRouteResult;
    private WalkRouteResult mWalkRouteResult;
    private RideRouteResult mRideRouteResult;
    private LatLonPoint mStartPoint;
    private LatLonPoint mEndPoint;
    // 当前导航类型
    private int mNavType;
    private ProgressDialog progDialog = null;// 搜索时进度条

    public static void actionActivity(Context context, int navType, LatLng startLatlng, LatLng endLatlng) {
        Intent intent = new Intent(context, PathPlanActivity.class);
        intent.putExtra(Constants.AMAP_NAV_TYPE, navType);
        intent.putExtra(Constants.AMAP_NAV_START, startLatlng);
        intent.putExtra(Constants.AMAP_NAV_END, endLatlng);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        drive.setOnClickListener(this);
        ride.setOnClickListener(this);
        walk.setOnClickListener(this);
        btnStartNav.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();

        mContext = this.getApplicationContext();
        mapView = (MapView) findViewById(R.id.route_map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();
        setfromandtoMarker();

        route(mNavType);
    }

    private void initData() {
        Intent intent = getIntent();
        startLatlng = intent.getParcelableExtra(Constants.AMAP_NAV_START);
        mStartPoint = AMapUtil.convertToLatLonPoint(startLatlng);
        endLatlng = intent.getParcelableExtra(Constants.AMAP_NAV_END);
        mEndPoint = AMapUtil.convertToLatLonPoint(endLatlng);
        mNavType = intent.getIntExtra(Constants.AMAP_NAV_TYPE, Constants.ROUTE_TYPE_DRIVE);
    }

    @Override
    public void initPresenter() {

    }


    @Override
    protected int getContentResId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        return R.layout.activity_path_plan;
    }

    @Override
    public boolean isImmersion() {
        return false;
    }

    private void setfromandtoMarker() {
        aMap.addMarker(new MarkerOptions()
                .position(LocationManager.getInstance().getLatLng())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));
        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(mEndPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.end)));
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        registerListener();
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
//        mHeadLayout.setVisibility(View.GONE);

        checkNavType();
    }

    private void checkNavType() {
        drive.setTextColor(Color.GRAY);
        ride.setTextColor(Color.GRAY);
        walk.setTextColor(Color.GRAY);

        drive.setBackgroundResource(R.drawable.bg_round);
        ride.setBackgroundResource(R.drawable.bg_round);
        walk.setBackgroundResource(R.drawable.bg_round);

        switch (mNavType){
            case Constants.ROUTE_TYPE_DRIVE:
                drive.setTextColor(Color.WHITE);
                drive.setBackgroundResource(R.drawable.bg_round_check);
                break;
            case Constants.ROUTE_TYPE_RIDE:
                ride.setTextColor(Color.WHITE);
                ride.setBackgroundResource(R.drawable.bg_round_check);
                break;
            case Constants.ROUTE_TYPE_WALK:
                walk.setTextColor(Color.WHITE);
                walk.setBackgroundResource(R.drawable.bg_round_check);
                break;
        }
    }

    /**
     * 注册监听
     */
    private void registerListener() {
        aMap.setOnMapClickListener(this);
        aMap.setOnMarkerClickListener(this);
        aMap.setOnInfoWindowClickListener(this);
        aMap.setInfoWindowAdapter(this);

    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
        if (mStartPoint == null) {
            showToast("定位中，稍后再试...");
            return;
        }
        if (mEndPoint == null) {
            showToast("终点未设置");
        }
        showProgressDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        switch (routeType) {
            case ROUTE_TYPE_DRIVE:// 驾车路径规划
                RouteSearch.DriveRouteQuery driveRouteQuery = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                        null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
                mRouteSearch.calculateDriveRouteAsyn(driveRouteQuery);// 异步路径规划驾车模式查询
                break;
            case ROUTE_TYPE_WALK:// 步行路径规划
                RouteSearch.WalkRouteQuery walkRouteQuery = new RouteSearch.WalkRouteQuery(fromAndTo, mode);
                mRouteSearch.calculateWalkRouteAsyn(walkRouteQuery);// 异步路径规划步行模式查询
                break;
            case ROUTE_TYPE_RIDE:// 骑行路径规划
                RouteSearch.RideRouteQuery rideRouteQuery = new RouteSearch.RideRouteQuery(fromAndTo, mode);
                mRouteSearch.calculateRideRouteAsyn(rideRouteQuery);// 异步路径规划骑行模式查询
                break;
        }
    }

    /**
     * 驾车路线规划回调
     *
     * @param result
     * @param errorCode
     */
    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
        mNavType = Constants.ROUTE_TYPE_DRIVE;

        dissmissProgressDialog();
        aMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mDriveRouteResult = result;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                            mContext, aMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(false);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                } else if (result != null && result.getPaths() == null) {
                    ToastUtil.show(mContext, R.string.no_result);
                }

            } else {
                ToastUtil.show(mContext, R.string.no_result);
            }
        } else {
            ToastUtil.showerror(this.getApplicationContext(), errorCode);
        }
    }

    /**
     * 骑行路线规划回调
     *
     * @param result
     * @param errorCode
     */
    @Override
    public void onRideRouteSearched(RideRouteResult result, int errorCode) {
        mNavType = Constants.ROUTE_TYPE_RIDE;

        dissmissProgressDialog();
        aMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mRideRouteResult = result;
                    final RidePath ridePath = mRideRouteResult.getPaths()
                            .get(0);
                    RideRouteOverlay rideRouteOverlay = new RideRouteOverlay(
                            this, aMap, ridePath,
                            mRideRouteResult.getStartPos(),
                            mRideRouteResult.getTargetPos());
                    rideRouteOverlay.setNodeIconVisibility(false);
                    rideRouteOverlay.removeFromMap();
                    rideRouteOverlay.addToMap();
                    rideRouteOverlay.zoomToSpan();
                } else if (result != null && result.getPaths() == null) {
                    ToastUtil.show(mContext, R.string.no_result);
                }
            } else {
                ToastUtil.show(mContext, R.string.no_result);
            }
        } else {
            ToastUtil.showerror(this.getApplicationContext(), errorCode);
        }
    }

    /**
     * 步行路线规划回调
     *
     * @param result
     * @param errorCode
     */
    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {
        mNavType = Constants.ROUTE_TYPE_WALK;

        dissmissProgressDialog();
        aMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mWalkRouteResult = result;
                    final WalkPath walkPath = mWalkRouteResult.getPaths()
                            .get(0);
                    WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(
                            this, aMap, walkPath,
                            mWalkRouteResult.getStartPos(),
                            mWalkRouteResult.getTargetPos());
                    walkRouteOverlay.setNodeIconVisibility(false);
                    walkRouteOverlay.removeFromMap();
                    walkRouteOverlay.addToMap();
                    walkRouteOverlay.zoomToSpan();
                } else if (result != null && result.getPaths() == null) {
                    ToastUtil.show(mContext, R.string.no_result);
                }
            } else {
                ToastUtil.show(mContext, R.string.no_result);
            }
        } else {
            ToastUtil.showerror(this.getApplicationContext(), errorCode);
        }
    }

    /**
     * 巴士路线规划回调
     *
     * @param result
     * @param errorCode
     */
    @Override
    public void onBusRouteSearched(BusRouteResult result, int errorCode) {

    }


    /**
     * 显示进度框
     */

    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在搜索");
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drive:
                mNavType = Constants.ROUTE_TYPE_DRIVE;
                checkNavType();
                route(mNavType);
                break;
            case R.id.walk:
                mNavType = Constants.ROUTE_TYPE_WALK;
                checkNavType();
                route(mNavType);
                break;
            case R.id.ride:
                mNavType = Constants.ROUTE_TYPE_RIDE;
                checkNavType();
                route(mNavType);
                break;
            case R.id.btn_start_nav:
                NavActivity.actionActivity(this, mNavType, startLatlng, endLatlng);
                break;
            case R.id.btn_back:
                close();
                break;
        }
    }

    private void route(int navType) {
        switch (navType) {
            case Constants.ROUTE_TYPE_DRIVE:
                searchRouteResult(Constants.ROUTE_TYPE_DRIVE, RouteSearch.DrivingDefault);
                break;
            case Constants.ROUTE_TYPE_WALK:
                searchRouteResult(Constants.ROUTE_TYPE_WALK, RouteSearch.WalkDefault);
                break;
            case Constants.ROUTE_TYPE_RIDE:
                searchRouteResult(Constants.ROUTE_TYPE_RIDE, RouteSearch.RidingDefault);
                break;
        }
    }

    @Override
    public View getInfoContents(Marker arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onMarkerClick(Marker arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onMapClick(LatLng arg0) {
        // TODO Auto-generated method stub

    }
}

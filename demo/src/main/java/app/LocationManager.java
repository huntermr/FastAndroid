package demo.app;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;

/**
 * Created by Administrator on 2017/5/11.
 */
public class LocationManager {
    private static LocationManager ourInstance = new LocationManager();
    AMapLocationClient locationClient;
    AMapLocationClientOption locationOption;
    private AMapLocation mLocation;
    // 是否已经定位成功
    private boolean isLocation;
    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            setLocation(location);
        }
    };

    private LocationManager() {
        initLocation();
    }

    public static LocationManager getInstance() {
        return ourInstance;
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(BaseApplication.getInstance());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(Constants.LOCATION_INTERVAL);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    /**
     * 开始定位
     */
    public void startLocation() {
        locationClient.startLocation();
    }

    /**
     * 停止定位
     */
    public void stopLocation() {
        locationClient.stopLocation();
    }

    /**
     * 设置定位信息
     *
     * @param location
     */
    public void setLocation(AMapLocation location) {
        isLocation = true;
        mLocation = location;
        if(mOnLocationListener != null){
            mOnLocationListener.onLocation(mLocation);
        }
    }

    /**
     * 获取当前经纬度
     *
     * @return
     */
    public LatLng getLatLng() {
        if (mLocation != null) return new LatLng(mLocation.getLatitude(), mLocation.getLongitude());

        return null;
    }

    public String getCityCode() {
        if (mLocation != null){
            String cityCode = mLocation.getCityCode();
            return cityCode;
        }

        return null;
    }

    /**
     * 获取当前城市名
     *
     * @return
     */
    public String getCityName() {
        if (mLocation != null){
            String cityName = mLocation.getCity();
            if(cityName.endsWith("市")){
                cityName = cityName.substring(0, cityName.indexOf("市"));
            }

            return cityName;
        }

        return null;
    }

    OnLocationListener mOnLocationListener;

    public void setOnLocationListener(OnLocationListener onLocationListener){
        this.mOnLocationListener = onLocationListener;
        if(isLocation && mOnLocationListener != null){
            mOnLocationListener.onLocation(mLocation);
        }
    }

    public interface OnLocationListener{
        void onLocation(AMapLocation aMapLocation);
    }
}

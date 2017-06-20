package demo.overlay;

import android.content.Context;
import android.graphics.Color;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.busline.BusLineItem;
import com.amap.api.services.busline.BusStationItem;
import com.amap.api.services.core.LatLonPoint;

import java.util.ArrayList;
import java.util.List;


/**
 * 公交线路图层类。在高德地图API里，如果要显示公交线路，可以用此类来创建公交线路图层。如不满足需求，也可以自己创建自定义的公交线路图层。
 *
 * @since V2.1.0
 */
public class BusLineOverlay {
    private BusLineItem mBusLineItem;
    private AMap mAMap;
    private ArrayList<Marker> mBusStationMarks = new ArrayList<Marker>();
    private Polyline mBusLinePolyline;
    private List<BusStationItem> mBusStations;
    private BitmapDescriptor startBit, endBit, busBit;
    private Context mContext;

    /**
     * 通过此构造函数创建公交线路图层。
     *
     * @param context     当前activity。
     * @param amap        地图对象。
     * @param busLineItem 公交线路。详见搜索服务模块的公交线路和公交站点包（com.amap.api.services.busline）中的类 <strong><a href="../../../../../../Search/com/amap/api/services/busline/BusStationItem.html" title="com.amap.api.services.busline中的类">BusStationItem</a></strong>。
     * @since V2.1.0
     */
    public BusLineOverlay(Context context, AMap amap, BusLineItem busLineItem) {
        mContext = context;
        mBusLineItem = busLineItem;
        this.mAMap = amap;
        mBusStations = mBusLineItem.getBusStations();
    }

    /**
     * 添加公交线路到地图中。
     *
     * @since V2.1.0
     */
    public void addToMap() {
        try {
            List<LatLonPoint> pointList = mBusLineItem.getDirectionsCoordinates();
            List<LatLng> listPolyline = AMapServicesUtil.convertArrList(pointList);
            mBusLinePolyline = mAMap.addPolyline(new PolylineOptions()
                    .addAll(listPolyline).color(getBusColor())
                    .width(getBuslineWidth()));
            if (mBusStations.size() < 1) {
                return;
            }
            for (int i = 1; i < mBusStations.size() - 1; i++) {
                Marker marker = mAMap.addMarker(getMarkerOptions(i));
                mBusStationMarks.add(marker);
            }
            Marker markerStart = mAMap.addMarker(getMarkerOptions(0));
            mBusStationMarks.add(markerStart);
            Marker markerEnd = mAMap
                    .addMarker(getMarkerOptions(mBusStations.size() - 1));
            mBusStationMarks.add(markerEnd);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    /**
     * 去掉BusLineOverlay上所有的Marker。
     *
     * @since V2.1.0
     */
    public void removeFromMap() {
        if (mBusLinePolyline != null) {
            mBusLinePolyline.remove();
        }
        try {
            for (Marker mark : mBusStationMarks) {
                mark.remove();
            }
            destroyBit();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void destroyBit() {
        if (startBit != null) {
            startBit.recycle();
            startBit = null;
        }
        if (endBit != null) {
            endBit.recycle();
            endBit = null;
        }
        if (busBit != null) {
            busBit.recycle();
            busBit = null;
        }
    }

    /**
     * 移动镜头到当前的视角。
     *
     * @since V2.1.0
     */
    public void zoomToSpan() {
        if (mAMap == null)
            return;
        try {
            List<LatLonPoint> coordin = mBusLineItem.getDirectionsCoordinates();
            if (coordin != null && coordin.size() > 0) {
                LatLngBounds bounds = getLatLngBounds(coordin);
                mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 5));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private LatLngBounds getLatLngBounds(List<LatLonPoint> coordin) {
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i = 0; i < coordin.size(); i++) {
            b.include(new LatLng(coordin.get(i).getLatitude(), coordin.get(i)
                    .getLongitude()));
        }
        return b.build();
    }

    private MarkerOptions getMarkerOptions(int index) {
        MarkerOptions options = new MarkerOptions()
                .position(
                        new LatLng(mBusStations.get(index).getLatLonPoint()
                                .getLatitude(), mBusStations.get(index)
                                .getLatLonPoint().getLongitude()))
                .title(getTitle(index)).snippet(getSnippet(index));
        if (index == 0) {
            options.icon(getStartBitmapDescriptor());
        } else if (index == mBusStations.size() - 1) {
            options.icon(getEndBitmapDescriptor());
        } else {
            options.anchor(0.5f, 0.5f);
            options.icon(getBusBitmapDescriptor());
        }
        return options;
    }

    protected BitmapDescriptor getStartBitmapDescriptor() {
        startBit = BitmapDescriptorFactory.fromResource(R.drawable.amap_start);
        return startBit;
    }

    protected BitmapDescriptor getEndBitmapDescriptor() {
        endBit = BitmapDescriptorFactory.fromResource(R.drawable.amap_end);
        return endBit;
    }

    protected BitmapDescriptor getBusBitmapDescriptor() {
        busBit = BitmapDescriptorFactory.fromResource(R.drawable.amap_bus);
        return busBit;
    }

    /**
     * 返回第index的Marker的标题。
     *
     * @param index 第几个Marker。
     * @return marker的标题。
     * @since V2.1.0
     */
    protected String getTitle(int index) {
        return mBusStations.get(index).getBusStationName();

    }

    /**
     * 返回第index的Marker的详情。
     *
     * @param index 第几个Marker。
     * @return marker的详情。
     * @since V2.1.0
     */
    protected String getSnippet(int index) {
        return "";
    }

    /**
     * 从marker中得到公交站点在list的位置。
     *
     * @param marker 一个标记的对象。
     * @return 返回该marker对应的公交站点在list的位置。
     * @since V2.1.0
     */
    public int getBusStationIndex(Marker marker) {
        for (int i = 0; i < mBusStationMarks.size(); i++) {
            if (mBusStationMarks.get(i).equals(marker)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 返回第index的公交站点的信息。
     *
     * @param index 第几个公交站点。
     * @return 公交站点的信息。详见搜索服务模块的公交线路和公交站点包（com.amap.api.services.busline）中的类 <strong><a href="../../../../../../Search/com/amap/api/services/busline/BusStationItem.html" title="com.amap.api.services.busline中的类">BusStationItem</a></strong>。
     * @since V2.1.0
     */
    public BusStationItem getBusStationItem(int index) {
        if (index < 0 || index >= mBusStations.size()) {
            return null;
        }
        return mBusStations.get(index);
    }

    protected int getBusColor() {
        return Color.parseColor("#537edc");
    }

    protected float getBuslineWidth() {
        return 18f;
    }
}

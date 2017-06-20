package demo.overlay;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class TraceOverlay {
	private Polyline mPolyline;
	private PolylineOptions mOption;
	private AMap mAMap;
	private List<LatLng> mTracedList = new ArrayList<LatLng>();
	public static final int TRACE_STATUS_PROCESSING = 1;
	public static final int TRACE_STATUS_FINISH = 2;
	public static final int TRACE_STATUS_FAILURE = 3;
	public static final int TRACE_STATUS_PREPARE = 4;

	private int mTraceStatus = TRACE_STATUS_PREPARE;
	private int mDistance;
	private int mWaitTime;

	public TraceOverlay(AMap amap, List<LatLng> lines) {
		this.mAMap = amap;
		options();
		mOption.addAll(lines);
		mPolyline = amap.addPolyline(mOption);
	}

	public TraceOverlay(AMap amap) {
		this.mAMap = amap;
		options();
	}

	public void add(List<LatLng> segments) {
		if (segments == null || segments.size() == 0) {
			return;
		}
		mTracedList.addAll(segments);
		options();
		if (mPolyline == null) {
			mPolyline = mAMap.addPolyline(mOption);
		}
		mPolyline.setPoints(mTracedList);
	}

	public void remove() {
		if (mPolyline != null) {
			mPolyline.remove();
		}
	}

	public void setProperCamera(List<LatLng> lists) {
		Builder builder = LatLngBounds.builder();
		if (lists == null || lists.size() == 0) {
			return;
		}
		for (LatLng latlng : lists) {
			builder.include(latlng);
		}
		try {
			LatLngBounds bounds = builder.build();
			mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	public void zoopToSpan() {
		List<LatLng> lists = mOption.getPoints();
		setProperCamera(lists);
	}

	private PolylineOptions options() {
		if (mOption == null) {
			mOption = new PolylineOptions();
			mOption.setCustomTexture(BitmapDescriptorFactory
					.fromAsset("tracelinetexture.png"));
			mOption.width(40);
		}
		return mOption;
	}

	public int getTraceStatus() {
		return mTraceStatus;
	}

	public void setTraceStatus(int mTraceStatus) {
		this.mTraceStatus = mTraceStatus;
	}

	public int getDistance() {
		return mDistance;
	}

	public void setDistance(int mDistance) {
		this.mDistance = mDistance;
	}

	public int getWaitTime() {
		return mWaitTime;
	}

	public void setWaitTime(int mWaitTime) {
		this.mWaitTime = mWaitTime;
	}
}

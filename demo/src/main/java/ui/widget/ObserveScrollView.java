package demo.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ObserveScrollView extends ScrollView {
    private OnScrollListener mOnScrollListener;
    private MyHandler handler = new MyHandler(this);
    /**
     * 主要是用在用户手指离开MyScrollView，MyScrollView还在继续滑动，我们用来保存Y的距离，然后做比较
     */
    private int lastScrollY;

    public ObserveScrollView(Context context) {
        super(context);
    }

    public ObserveScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObserveScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(this.getScrollY());
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                handler.sendMessageDelayed(handler.obtainMessage(), 100);
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public interface OnScrollListener {
        void onScroll(int y);
    }

    /**
     * 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中
     */
    private static class MyHandler extends Handler {
        WeakReference<ObserveScrollView> mContext;

        MyHandler(ObserveScrollView context) {
            this.mContext = new WeakReference<>(context);
        }

        public void handleMessage(android.os.Message msg) {
            ObserveScrollView observeScrollView = mContext.get();
            if (observeScrollView != null) {
                int scrollY = observeScrollView.getScrollY();

                //此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
                if (observeScrollView.lastScrollY != scrollY) {
                    observeScrollView.lastScrollY = scrollY;
                    sendMessageDelayed(obtainMessage(), 100);
                }
                if (observeScrollView.mOnScrollListener != null) {
                    observeScrollView.mOnScrollListener.onScroll(scrollY);
                }
            }
        }
    }
}

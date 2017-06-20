package demo.base;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 自定义Fragment页面适配器
 *
 * @author Hunter
 */
public class CustomPagerAdapter<T extends View> extends PagerAdapter {
    List<T> mDatas;

    /**
     * 设置当前adapter的页面集合
     *
     * @param datas
     */
    public void setPagers(List<T> datas) {
        mDatas = datas;
    }

    public int getDataCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public List<T> getData() {
        return mDatas;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mDatas.get(position));
        return mDatas.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mDatas.get(position));
    }
}

package com.hunter.fastandroid.base;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 自定义BaseAdapter
 * @author user
 *
 * @param <T>
 */
public abstract class CustomBaseAdapter<T> extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<T> datas;
	private Context mContext;

	public CustomBaseAdapter(Context context) {
		mContext = context;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * 获取上下文对象
	 * 
	 * @return
	 */
	public Context getContext() {
		return mContext;
	}

	/**
	 * 获取item布局
	 * 
	 * @return
	 */
	public View getItemView(int resource, ViewGroup parent) {
		return mInflater.inflate(resource, parent, false);
	}
	
	/**
	 * 获取指定item数据
	 * 
	 * @param position
	 * @return
	 */
	public T getItemData(int position) {
		return datas == null ? null : datas.get(position);
	}

	/**
	 * 获取数据集合
	 * 
	 * @return
	 */
	public List<T> getData() {
		return datas;
	}

	/**
	 * 设置数据集合
	 * 
	 * @param datas
	 */
	public void setData(List<T> datas) {
		this.datas = datas;
	}

	/**
	 * 移除指定item的数据
	 * @param position
	 */
	public void removeData(int position){
		this.datas.remove(position);
	}

	@Override
	public int getCount() {
		return datas == null ? 0 : datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas == null ? null : datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
}

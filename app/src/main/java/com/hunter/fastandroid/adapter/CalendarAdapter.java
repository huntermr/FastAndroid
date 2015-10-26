package com.hunter.fastandroid.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hunter.fastandroid.R;
import com.hunter.fastandroid.base.CustomBaseAdapter;
import com.hunter.fastandroid.vo.Calendar;
import com.hunter.fastandroid.utils.CommonUtils;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CalendarAdapter extends CustomBaseAdapter<Calendar> {

    public CalendarAdapter(Context context) {
        super(context);
    }

    /**
     * 对指定日期进行选中/取消的操作
     *
     * @param date
     * @param check
     */
    private void goCheckDate(Date date, boolean check) {
        if (getData() != null && getCount() > 0) {
            for (Calendar data : getData()) {
                if (data.date != null && data.date.equals(date)) {
                    // 如果是取消选中,则也要把价格清空
                    if (!check) {
                        data.price = null;
                    }
                    data.check = check;
                    break;
                }
            }

            notifyDataSetChanged();
        }
    }

    /**
     * 选中指定的日期
     *
     * @param date
     */
    public void checkDate(Date date) {
        goCheckDate(date, true);
    }

    /**
     * 取消选中指定日期
     *
     * @param date
     */
    public void unCheckDate(Date date) {
        goCheckDate(date, false);
    }

    /**
     * 对指定星期进行选中/取消的操作
     *
     * @param week
     * @param check
     */
    private void goCheckWeek(int week, boolean check) {
        if (getData() != null && getCount() > 0) {
            for (Calendar data : getData()) {
                if (data.date != null) {
                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                    calendar.setTime(data.date);
                    int theWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK);
                    if (theWeek == week) {
                        // 如果是取消选中,则也要把价格清空
                        if (!check) {
                            data.price = null;
                        }
                        data.check = check;
                    }

                    notifyDataSetChanged();
                }
            }
        }
    }

    /**
     * 选中指定星期的日期
     *
     * @param week
     */
    public void checkWeek(int week) {
        goCheckWeek(week, true);
    }

    /**
     * 取消指定星期的日期
     *
     * @param week
     */
    public void unCheckWeek(int week) {
        goCheckWeek(week, false);
    }

    /**
     * 设置指定日期的价格
     *
     * @param price
     * @param date
     */
    public void setPrice(int price, Date date) {
        if (getData() != null && getCount() > 0) {
            for (Calendar data : getData()) {
                if (data.date != null && data.date.equals(date)) {
                    data.price = "¥" + price;
                    break;
                }
            }

            notifyDataSetChanged();
        }
    }

    /**
     * 设置指定星期的价格
     *
     * @param price
     * @param week
     */
    public void setPrice(int price, int week) {
        if (getData() != null && getCount() > 0) {
            for (Calendar data : getData()) {
                if (data.date != null) {
                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                    calendar.setTime(data.date);
                    int theWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK);
                    if (theWeek == week) {
                        data.price = "¥" + price;
                    }
                }
            }

            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Calendar itemData = getItemData(position);

        ViewHolder holder;
        if (convertView == null) {
            convertView = getItemView(R.layout.day, parent);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 如果是法定节假日,则显示节假日名称
        if (!TextUtils.isEmpty(itemData.holiday)) {
            holder.tvDate.setText(itemData.holiday);
        } else {
            if (itemData.date != null) {
                holder.tvDate.setText(CommonUtils.formatDay.format(itemData.date));
            } else {
                holder.tvDate.setText("");
            }
        }

        // 如果今天是休息日,则显示休字提示
        if (itemData.rest) {
            holder.tvDate.append(" 休 ");
        }

        // 如果有价格则显示价格
        if (!TextUtils.isEmpty(itemData.price)) {
            holder.tvPrice.setText(itemData.price);
        } else {
            holder.tvPrice.setText(itemData.lunar);
        }

        // 如果当前日期已被选中则切换背景色
        if (itemData.isUse) {
            if (itemData.check) {
                holder.box.setBackgroundResource(R.color.gray_price);
            } else {
                holder.box.setBackgroundColor(Color.WHITE);
            }
        }else{
            holder.box.setBackgroundColor(Color.GRAY);
        }

        return convertView;
    }

    public static class ViewHolder {
        @Bind(R.id.box)
        RelativeLayout box;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.tv_price)
        TextView tvPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

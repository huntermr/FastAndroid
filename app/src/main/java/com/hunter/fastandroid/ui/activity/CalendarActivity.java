package com.hunter.fastandroid.ui.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.hunter.fastandroid.R;
import com.hunter.fastandroid.adapter.CalendarAdapter;
import com.hunter.fastandroid.base.BaseActivity;
import com.hunter.fastandroid.utils.CalendarUtils;
import com.hunter.fastandroid.utils.Logger;
import com.hunter.fastandroid.vo.Calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/9/30.
 */
public class CalendarActivity extends BaseActivity {
    @Bind(R.id.gridview)
    GridView gridView;
    @Bind(R.id.cb_week_0)
    CheckBox cbWeek0;
    @Bind(R.id.cb_week_1)
    CheckBox cbWeek1;
    @Bind(R.id.cb_week_2)
    CheckBox cbWeek2;
    @Bind(R.id.cb_week_3)
    CheckBox cbWeek3;
    @Bind(R.id.cb_week_4)
    CheckBox cbWeek4;
    @Bind(R.id.cb_week_5)
    CheckBox cbWeek5;
    @Bind(R.id.cb_week_6)
    CheckBox cbWeek6;
    @Bind(R.id.btn_select)
    Button btnSelect;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.btn_left)
    Button btnLeft;
    @Bind(R.id.btn_right)
    Button btnRight;

    CalendarAdapter calendarAdapter;

    // type  1:按周几选 2:该周几某天价格 3:该周几某天无活动 4:按日期选
    Map<Integer, Integer> selectedWeek = new HashMap<Integer, Integer>();
    Map<Date, Integer> dayOfWeek = new HashMap<Date, Integer>();
    Set<Date> dayOutweek = new HashSet<Date>();
    Map<Date, Integer> selectedDay = new HashMap<Date, Integer>();

    private int initPrice;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_calendar);
    }

    @Override
    public void initView() {
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();
                Set<Integer> weekSet = selectedWeek.keySet();
                Iterator<Integer> weekItertor = weekSet.iterator();
                while (weekItertor.hasNext()) {
                    int week = weekItertor.next();
                    Integer weekPrice = selectedWeek.get(week);
                    if (week == 1) {
                        stringBuilder.append("星期日被选中,价格为: " + weekPrice);
                    } else {
                        stringBuilder.append("星期 " + (week - 1) + " 被选中,价格为: " + weekPrice);
                    }
                    stringBuilder.append("\n");
                }

                Set<Date> dayOfPriceSet = dayOfWeek.keySet();
                Iterator<Date> dayOfPriceIterator = dayOfPriceSet.iterator();
                while (dayOfPriceIterator.hasNext()) {
                    Date dayInWeek = dayOfPriceIterator.next();
                    Integer dayInWeekPrice = dayOfWeek.get(dayInWeek);
                    stringBuilder.append("周几中价格变动--------日期: " + dayInWeek + "的价格为: " + dayInWeekPrice);
                    stringBuilder.append("\n");
                }

                Iterator<Date> weekOutDayIterator = dayOutweek.iterator();
                while (weekOutDayIterator.hasNext()) {
                    Date weekOutDayDate = weekOutDayIterator.next();
                    stringBuilder.append("排除日期: " + weekOutDayDate);
                    stringBuilder.append("\n");
                }

                Set<Date> selectDaySet = selectedDay.keySet();
                Iterator<Date> selectDayIterator = selectDaySet.iterator();
                while (selectDayIterator.hasNext()) {
                    Date selectDayDate = selectDayIterator.next();
                    Integer selectDayPrice = selectedDay.get(selectDayDate);
                    stringBuilder.append("单日出发 --------日期: " + selectDayDate + "的价格为: " + selectDayPrice);
                    stringBuilder.append("\n");
                }

                Logger.e(stringBuilder.toString());

            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarAdapter.setData(CalendarUtils.getInstance().getCalendarData(-1));
                tvTitle.setText(CalendarUtils.getInstance().getYearAndMonth());
                calendarAdapter.notifyDataSetChanged();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarAdapter.setData(CalendarUtils.getInstance().getCalendarData(1));
                tvTitle.setText(CalendarUtils.getInstance().getYearAndMonth());
                calendarAdapter.notifyDataSetChanged();
            }
        });

        calendarAdapter = new CalendarAdapter(this);
        calendarAdapter.setData(CalendarUtils.getInstance().getCalendarData());
        tvTitle.setText(CalendarUtils.getInstance().getYearAndMonth());
        gridView.setAdapter(calendarAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Calendar itemData = (Calendar) calendarAdapter.getItem(position);
                if (itemData.date != null && itemData.isUse) {
                    if (!itemData.check) {
                        if (initPrice == 0) {
                            showSetPriceDialog(new OnInputPriceListener() {
                                @Override
                                public void onInputPrice(int price) {
                                    calendarAdapter.setPrice(price, itemData.date);
                                    saveDatePrice(itemData.date, price);
                                }
                            });
                        } else {
                            calendarAdapter.setPrice(initPrice, itemData.date);
                            saveDatePrice(itemData.date, initPrice);
                        }

                        calendarAdapter.checkDate(itemData.date);
                    } else {
                        calendarAdapter.unCheckDate(itemData.date);
                        removeDatePrice(itemData.date);
                    }

                }
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Calendar itemData = (Calendar) calendarAdapter.getItem(position);
                if (itemData.date != null && itemData.isUse) {
                    if (itemData.check) {
                        showSetPriceDialog(new OnInputPriceListener() {
                            @Override
                            public void onInputPrice(int price) {
                                calendarAdapter.setPrice(price, itemData.date);
                                saveDatePrice(itemData.date, price);
                            }
                        });
                    }
                }

                return true;
            }
        });

        cbWeek0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                initWeekCheckBox(isChecked, 1);
            }
        });
        cbWeek1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                initWeekCheckBox(isChecked, 2);
            }
        });
        cbWeek2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                initWeekCheckBox(isChecked, 3);
            }
        });
        cbWeek3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                initWeekCheckBox(isChecked, 4);
            }
        });
        cbWeek4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                initWeekCheckBox(isChecked, 5);
            }
        });
        cbWeek5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                initWeekCheckBox(isChecked, 6);
            }
        });
        cbWeek6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                initWeekCheckBox(isChecked, 7);
            }
        });
    }

    /**
     * 根据当前日期存储选中的价格
     *
     * @param date
     * @param price
     */
    private void saveDatePrice(Date date, int price) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        int theWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK);
        if (selectedWeek.containsKey(theWeek)) {
            if (dayOutweek.contains(date)) {
                dayOutweek.remove(date);
            } else {
                dayOfWeek.put(date, price);
            }
        } else {
            selectedDay.put(date, price);
        }
    }

    /**
     * 根据当前日期移除对应的数据
     *
     * @param date
     */
    private void removeDatePrice(Date date) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        int theWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK);
        if (selectedWeek.containsKey(theWeek)) {
            dayOfWeek.remove(date);
            dayOutweek.add(date);
        } else {
            selectedDay.remove(date);
        }
    }

    private void initWeekCheckBox(boolean isChecked, final int week) {
        if (isChecked) {
            if (initPrice == 0) {
                showSetPriceDialog(new OnInputPriceListener() {
                    @Override
                    public void onInputPrice(int price) {
                        calendarAdapter.setPrice(price, week);
                        selectedWeek.put(week, price);
                    }
                });
            } else {
                calendarAdapter.setPrice(initPrice, week);
                selectedWeek.put(week, initPrice);
            }

            calendarAdapter.checkWeek(week);
        } else {
            calendarAdapter.unCheckWeek(week);
        }
    }

    public interface OnInputPriceListener {
        void onInputPrice(int price);
    }

    /**
     * 价格输入对话框
     */
    private void showSetPriceDialog(final OnInputPriceListener onInputPriceListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入价格");

        // 显示价格输入框,输入后将价格和星期几存储
        final EditText etPrice = new EditText(this);
        builder.setView(etPrice);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String string = etPrice.getText().toString();
                int price = 0;
                if (!TextUtils.isEmpty(string)) {
                    price = Integer.parseInt(string);
                }

                if (initPrice == 0 && price > 0) {
                    initPrice = price;
                }

                if (onInputPriceListener != null && price > 0) {
                    onInputPriceListener.onInputPrice(price);
                }

                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void initPresenter() {

    }
}

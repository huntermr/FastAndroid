package demo.ui.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import cn.tbl.android.R;
import demo.base.CustomBaseAdapter;

/**
 * Created by Administrator on 2017/5/11.
 */

public class FilterPopup extends PopupWindow {
    ListView contentView;
    private Context context;
    private CustomBaseAdapter mAdapter;

    public FilterPopup(Context context) {
        super(context, null);
        init(context);
    }

    public void setAdapter(CustomBaseAdapter adapter) {
        mAdapter = adapter;
        contentView.setAdapter(mAdapter);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
        contentView.setOnItemClickListener(onItemClickListener);
    }

    private void init(Context context) {
        contentView = (ListView) LayoutInflater.from(context).inflate(R.layout.layout_filter_popup, null);
        setContentView(contentView);
        setOutsideTouchable(false);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.tran_black)));
    }
}

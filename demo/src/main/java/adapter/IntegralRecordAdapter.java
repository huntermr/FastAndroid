package demo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tbl.android.R;
import demo.base.CustomBaseAdapter;
import demo.utils.DateUtils;
import demo.vo.response.store.ScoreFlow;

/**
 * Created by Administrator on 2017/5/16.
 */

public class IntegralRecordAdapter extends CustomBaseAdapter<ScoreFlow.Flow> {

    public IntegralRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        ScoreFlow.Flow itemData = getItemData(position);

        if (convertView == null) {
            convertView = getItemView(R.layout.item_integral_record, parent);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvSource.setText(itemData.getType());
        holder.tvValue.setText(itemData.getScore());
        holder.tvDate.setText(DateUtils.parseDate(Long.parseLong(itemData.getOccurDate())));

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_source)
        TextView tvSource;
        @BindView(R.id.tv_value)
        TextView tvValue;
        @BindView(R.id.tv_date)
        TextView tvDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

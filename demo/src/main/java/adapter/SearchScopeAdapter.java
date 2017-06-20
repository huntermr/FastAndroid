package demo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tbl.android.R;
import demo.base.CustomBaseAdapter;
import demo.vo.SearchScope;

/**
 * Created by Administrator on 2017/5/11.
 */

public class SearchScopeAdapter extends CustomBaseAdapter<SearchScope> {

    public SearchScopeAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchScope itemData = getItemData(position);
        ViewHolder holder;

        if(convertView == null){
            convertView = getItemView(R.layout.item_filter, parent);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvTitle.setText(itemData.getName());

        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}

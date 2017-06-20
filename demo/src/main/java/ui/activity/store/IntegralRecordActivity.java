package demo.ui.activity.store;

import android.text.Html;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.BindView;
import demo.adapter.IntegralRecordAdapter;
import demo.base.BaseActivity;
import demo.presenter.UserPresenter;
import demo.ui.interfaces.user.IScoreFlowView;
import demo.ui.widget.TitleBar;
import demo.vo.request.PageRequest;
import demo.vo.response.store.ScoreFlow;

/**
 * Created by Administrator on 2017/5/16.
 */

public class IntegralRecordActivity extends BaseActivity implements IScoreFlowView {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.lv_record)
    PullToRefreshListView lvRecord;

    UserPresenter userPresenter;
    PageRequest pageRequest;

    IntegralRecordAdapter recordAdapter;

    @Override
    public void initView() {
        titleBar.setTitle(R.string.integral_record);
        titleBar.setLeftView(fillBackButton());

        recordAdapter = new IntegralRecordAdapter(this);
        lvRecord.setAdapter(recordAdapter);

        lvRecord.setMode(PullToRefreshBase.Mode.BOTH);
        lvRecord.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageRequest.setPageNum(1);
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageRequest.setPageNum(pageRequest.getPageNum() + 1);
                getData();
            }
        });

        getData();
    }

    private void getData() {
        userPresenter.getScoreFlow(this, pageRequest);
    }

    @Override
    public void initPresenter() {
        userPresenter = new UserPresenter();
        pageRequest = new PageRequest();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_integral_record;
    }

    @Override
    public void uiScoreFlow(ScoreFlow scoreFlow) {
        lvRecord.onRefreshComplete();

        if(scoreFlow != null){
            tvIntegral.setText(Html.fromHtml("当前积分：<font color='#ECC66A'>" + scoreFlow.getTotalScore() + "</font>"));

            List<ScoreFlow.Flow> flows = scoreFlow.getFlow();

            // 大于1则为加载更多,否则为下拉刷新
            if(pageRequest.getPageNum() > 1){
                // 如果没有更多数据,则页码需要减一以回到上一页,否则会无限增长
                if(flows == null || flows.size() == 0){
                    showToast("没有更多了");
                    pageRequest.setPageNum(pageRequest.getPageNum() - 1);
                }
                recordAdapter.addDatas(flows);
            }else{
                recordAdapter.setData(flows);
            }
        }

        recordAdapter.notifyDataSetChanged();
    }
}

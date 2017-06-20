package demo.ui.activity.store;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import cache.greendao.SearchHistoryDao;
import cn.tbl.android.R;
import demo.adapter.StoreAdapter;
import demo.app.DaoManager;
import demo.app.LocationManager;
import demo.base.BaseActivity;
import demo.presenter.StorePresenter;
import demo.presenter.UserPresenter;
import demo.ui.interfaces.store.ISearchStoreView;
import demo.ui.interfaces.user.IAddFavorView;
import demo.ui.interfaces.user.IDelFavorView;
import demo.ui.widget.SearchView;
import demo.ui.widget.TagGroupView;
import demo.ui.widget.TitleBar;
import demo.utils.DialogUtils;
import demo.vo.FavorStoreChange;
import demo.vo.SearchHistory;
import demo.vo.request.SearchStoreRequest;
import demo.vo.response.store.Store;

/**
 * Created by Administrator on 2017/5/11.
 */

public class SearchStoreActivity extends BaseActivity implements ISearchStoreView, IAddFavorView, IDelFavorView {
    @BindView(R.id.tag_view)
    TagGroupView tagGroupView;
    @BindView(R.id.lv_search)
    ListView lvSearch;
    @BindView(R.id.btn_clear_history)
    TextView btnClearHistory;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.history)
    LinearLayout history;

    SearchHistoryDao searchHistoryDao;

    StoreAdapter storeAdapter;

    StorePresenter storePresenter;
    UserPresenter userPresenter;

    @Override
    public void initView() {
        searchHistoryDao = DaoManager.getInstance().getSearchHistory();

        titleBar.setTitle(R.string.search_store);
        titleBar.setLeftView(fillBackButton());

        searchView.setOnSearchListener(new SearchView.OnSearchListener() {
            @Override
            public void onClear() {
                history.setVisibility(View.VISIBLE);
                lvSearch.setVisibility(View.GONE);

                storeAdapter.clear();
                storeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTextChange(String keyword) {

            }

            @Override
            public void onSearch(String keyword) {
                history.setVisibility(View.GONE);
                lvSearch.setVisibility(View.VISIBLE);

                List<SearchHistory> existHistorys = searchHistoryDao.queryRaw("where keyword = ?", keyword);
                if(existHistorys != null && existHistorys.size() > 0){
                    searchHistoryDao.delete(existHistorys.get(0));
                }

                SearchHistory searchHistory = new SearchHistory();
                searchHistory.setKeyword(keyword);
                searchHistoryDao.insertOrReplace(searchHistory);

                // 开始请求网络进行店铺搜索
                search(keyword);
            }
        });

        tagGroupView.setOnTagClickListener(new TagGroupView.OnTagCheckListener() {
            @Override
            public void onTagCheck(String tagName) {
                searchView.setInputContent(tagName);
                search(tagName);
            }
        });

        btnClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showConfirmDialog(SearchStoreActivity.this, getString(R.string.clear_history), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        searchHistoryDao.deleteAll();
                        tagGroupView.clear();
                    }
                });
            }
        });

        loadSearchHistory();

        initSearch();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFavorChange(FavorStoreChange change){
        storeAdapter.changeFavor(change);
        storeAdapter.notifyDataSetChanged();
    }

    private void initSearch() {
        storeAdapter = new StoreAdapter(this);
        storeAdapter.setOnFavorListener(new StoreAdapter.OnFavorListener() {
            @Override
            public void onFavor(String storeId) {
                userPresenter.favorChangeStore(SearchStoreActivity.this, SearchStoreActivity.this, true, storeId);
            }

            @Override
            public void onCancelFavor(String storeId) {
                userPresenter.favorChangeStore(SearchStoreActivity.this, SearchStoreActivity.this, false, storeId);
            }
        });
        lvSearch.setAdapter(storeAdapter);
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Store itemData = storeAdapter.getItemData(position);
                StoreIndexActivity.actionActivity(SearchStoreActivity.this, itemData.getStoreId());
            }
        });
    }

    private void search(String keyword) {
        SearchStoreRequest request = new SearchStoreRequest();
        LatLng latLng = LocationManager.getInstance().getLatLng();
        request.setCustomerLat(latLng.latitude);
        request.setCustomerLng(latLng.longitude);
        request.setKeyword(keyword);
        storePresenter.searchStore(this, request);
    }

    private void loadSearchHistory(){
        List<SearchHistory> searchHistories = searchHistoryDao.queryBuilder().orderDesc(searchHistoryDao.getPkProperty()).list();
        if(searchHistories != null && searchHistories.size() > 0){
            history.setVisibility(View.VISIBLE);
            tagGroupView.setTags(searchHistories);
        }else{
            history.setVisibility(View.GONE);
        }
    }

    @Override
    public void initPresenter() {
        storePresenter = new StorePresenter();
        userPresenter = new UserPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_search_store;
    }

    @Override
    public void uiSearchStore(List<Store> stores) {
        storeAdapter.clear();

        history.setVisibility(View.GONE);
        lvSearch.setVisibility(View.VISIBLE);

        if(stores == null || stores.size() == 0){
            showToast(R.string.search_no_result);
        }else{
            storeAdapter.setData(stores);
        }

        storeAdapter.notifyDataSetChanged();
    }

    @Override
    public void uiAddFavor() {

    }

    @Override
    public void uiDelFavor() {

    }
}

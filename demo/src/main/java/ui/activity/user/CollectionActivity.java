package demo.ui.activity.user;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amap.api.maps.model.LatLng;

import java.util.List;

import butterknife.BindView;
import cn.tbl.android.R;
import demo.adapter.NewProductAdapter;
import demo.adapter.StoreAdapter;
import demo.app.LocationManager;
import demo.base.BaseActivity;
import demo.presenter.UserPresenter;
import demo.ui.activity.store.StoreIndexActivity;
import demo.ui.interfaces.user.IAddFavorView;
import demo.ui.interfaces.user.IDelFavorView;
import demo.ui.interfaces.user.IFavorProductView;
import demo.ui.interfaces.user.IFavorStoreView;
import demo.ui.widget.TitleBar;
import demo.vo.request.GetFavorStoreRequest;
import demo.vo.response.store.NewProduct;
import demo.vo.response.store.Store;

import static cn.tbl.android.app.Constants.COLLECTION_PRODUCT;
import static cn.tbl.android.app.Constants.COLLECTION_STORE;
import static cn.tbl.android.app.Constants.COLLECTION_TYPE;

/**
 * Created by Administrator on 2017/5/12.
 */

public class CollectionActivity extends BaseActivity implements IFavorProductView, IFavorStoreView, IAddFavorView, IDelFavorView {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.lv_collection)
    ListView lvCollection;

    UserPresenter userPresenter;

    int collectionType;

    public static void actionActivity(BaseActivity context, int type) {
        Intent intent = new Intent(context, CollectionActivity.class);
        intent.putExtra(COLLECTION_TYPE, type);
        context.openPage(intent);
    }

    @Override
    public void initView() {
        collectionType = getIntent().getIntExtra(COLLECTION_TYPE, COLLECTION_PRODUCT);
        initData();

        titleBar.setLeftView(fillBackButton());
    }

    private void initData() {
        switch (collectionType) {
            case COLLECTION_PRODUCT:
                titleBar.setTitle(R.string.collection_product);
                // 获取收藏的产品
                userPresenter.favorProduct(this);
                break;
            case COLLECTION_STORE:
                titleBar.setTitle(R.string.collection_store);
                // 获取收藏的店铺
                GetFavorStoreRequest request = new GetFavorStoreRequest();
                LatLng latLng = LocationManager.getInstance().getLatLng();
                request.setCustomerLat(latLng.latitude);
                request.setCustomerLng(latLng.longitude);
                userPresenter.favorStore(this, request);
                break;
        }
    }

    @Override
    public void initPresenter() {
        userPresenter = new UserPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_collection;
    }

    @Override
    public void uiFavorProduct(List<NewProduct> products) {
        NewProductAdapter productAdapter = new NewProductAdapter(this);
        productAdapter.setOnFavorListener(new NewProductAdapter.OnFavorListener() {
            @Override
            public void onFavor(String productId) {

            }

            @Override
            public void onCancelFavor(String productId) {
                userPresenter.favorChangeProduct(CollectionActivity.this, CollectionActivity.this, false, productId);
            }
        });
        productAdapter.setData(products);
        lvCollection.setAdapter(productAdapter);

        lvCollection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO 跳转到产品详情H5
            }
        });
    }

    @Override
    public void uiFavorStore(List<Store> stores) {
        final StoreAdapter storeAdapter = new StoreAdapter(this);
        storeAdapter.setOnFavorListener(new StoreAdapter.OnFavorListener() {
            @Override
            public void onFavor(String storeId) {

            }

            @Override
            public void onCancelFavor(String storeId) {
                userPresenter.favorChangeStore(CollectionActivity.this, CollectionActivity.this, false, storeId);
            }
        });

        storeAdapter.setData(stores);
        lvCollection.setAdapter(storeAdapter);
        lvCollection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Store itemData = storeAdapter.getItemData(position);
                StoreIndexActivity.actionActivity(CollectionActivity.this, itemData.getStoreId());
            }
        });
    }

    @Override
    public void uiAddFavor() {

    }

    @Override
    public void uiDelFavor() {
        initData();
    }
}

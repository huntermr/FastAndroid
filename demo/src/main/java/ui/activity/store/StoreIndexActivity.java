package demo.ui.activity.store;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

import java.util.List;

import butterknife.BindView;
import cn.tbl.android.R;
import demo.adapter.AdsImageHolder;
import demo.adapter.NewProductAdapter;
import demo.adapter.SpecialOfferAdapter;
import demo.app.Constants;
import demo.app.LocationManager;
import demo.base.BaseActivity;
import demo.presenter.StorePresenter;
import demo.presenter.UserPresenter;
import demo.ui.interfaces.common.IAdsView;
import demo.ui.interfaces.store.IStoreInfoView;
import demo.ui.interfaces.user.IAddFavorView;
import demo.ui.interfaces.user.IDelFavorView;
import demo.ui.widget.ObserveScrollView;
import demo.utils.CommonUtils;
import demo.utils.PixelUtils;
import demo.vo.response.store.Ads;
import demo.vo.response.store.StoreDetail;

/**
 * Created by Administrator on 2017/5/9.
 */

public class StoreIndexActivity extends BaseActivity implements View.OnClickListener, IAdsView, IStoreInfoView, IAddFavorView, IDelFavorView {
    @BindView(R.id.banner)
    ConvenientBanner adsViewPager;
    @BindView(R.id.scroll_view)
    ObserveScrollView scrollView;
    @BindView(R.id.header_bar)
    RelativeLayout headerBar;
    @BindView(R.id.lv_new_product)
    ListView lvNewProduct;
    @BindView(R.id.lv_special_offer)
    ListView lvSpecialOffer;
    @BindView(R.id.new_product_title)
    RelativeLayout newProductTitle;
    @BindView(R.id.special_offer_title)
    RelativeLayout specialOfferTitle;
    @BindView(R.id.store_address)
    RelativeLayout storeAddress;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.btn_favor)
    ImageView btnFavor;
    @BindView(R.id.tv_store_name)
    TextView tvStoreName;
    @BindView(R.id.tv_store_title)
    TextView tvStoreTitle;
    @BindView(R.id.tv_store_address)
    TextView tvStoreAddress;
    @BindView(R.id.btn_call)
    TextView btnCall;

    StorePresenter storePresenter;
    UserPresenter userPresenter;

    private int mNavType;   // 当前导航类型
    private String mStoreId; // 当前店铺id

    SpecialOfferAdapter specialOfferAdapter;
    NewProductAdapter newProductAdapter;

    StoreDetail mStoreDetail;
    LatLng targetLatLng;

    public static void actionActivity(BaseActivity context, int navType, String storeId) {
        Intent intent = new Intent(context, StoreIndexActivity.class);
        intent.putExtra(Constants.AMAP_NAV_TYPE, navType);
        intent.putExtra(Constants.STORE_ID, storeId);
        context.openPage(intent);
    }

    public static void actionActivity(BaseActivity context, String storeId) {
        actionActivity(context, Constants.ROUTE_TYPE_DRIVE, storeId);
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        mNavType = intent.getIntExtra(Constants.AMAP_NAV_TYPE, Constants.ROUTE_TYPE_DRIVE);
        mStoreId = intent.getStringExtra(Constants.STORE_ID);

        newProductTitle.setOnClickListener(this);
        specialOfferTitle.setOnClickListener(this);
        storeAddress.setOnClickListener(this);

        initAds();
        initNewProduct();
        initSpecialOffer();

        int headerBarHeight = getStatusBarHeight() + PixelUtils.dp2px(50);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headerBarHeight);
        headerBar.setLayoutParams(layoutParams);
        headerBar.setAlpha(0);

        scrollView.setOnScrollListener(new ObserveScrollView.OnScrollListener() {
            @Override
            public void onScroll(int y) {
                float alpha = (float) y / Constants.SCROLL;
                alpha = Math.max(alpha, 0);
                alpha = Math.min(alpha, 1);

                headerBar.setAlpha(alpha);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });

        btnFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 收藏/取消收藏店铺

                mStoreDetail.setFavor(!mStoreDetail.isFavor());
                btnFavor.setImageResource(mStoreDetail.isFavor() ? R.mipmap.nav_collection_sel : R.mipmap.nav_collection_nor);

                userPresenter.favorChangeStore(StoreIndexActivity.this, StoreIndexActivity.this, mStoreDetail.isFavor(), mStoreId);
            }
        });

        // 开始获取店铺信息
        storePresenter.getStoreInfo(this, mStoreId);
    }

    private void initNewProduct() {
        newProductAdapter = new NewProductAdapter(this);
        newProductAdapter.setOnFavorListener(new NewProductAdapter.OnFavorListener() {
            @Override
            public void onFavor(String productId) {
                userPresenter.favorChangeProduct(StoreIndexActivity.this, StoreIndexActivity.this, true, productId);
            }

            @Override
            public void onCancelFavor(String productId) {
                userPresenter.favorChangeProduct(StoreIndexActivity.this, StoreIndexActivity.this, false, productId);
            }
        });
        lvNewProduct.setAdapter(newProductAdapter);
    }

    private void initSpecialOffer() {
        specialOfferAdapter = new SpecialOfferAdapter(this);
        lvSpecialOffer.setAdapter(specialOfferAdapter);
    }

    /**
     * 初始化广告轮播图
     */
    private void initAds() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, PixelUtils.getWindowWidth() / 3 * 2);
        adsViewPager.setLayoutParams(layoutParams);
        adsViewPager.setPageIndicator(new int[]{R.mipmap.point2, R.mipmap.point1});
        // 开始自动翻页
        adsViewPager.startTurning(Constants.AUTO_SCROLL_INTERVAL);

        userPresenter.getAds(this, mStoreId);
    }

    @Override
    public void initPresenter() {
        storePresenter = new StorePresenter();
        userPresenter = new UserPresenter();
    }

    @Override
    protected int getContentResId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        return R.layout.activity_store_index;
    }

    @Override
    public boolean isImmersion() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_product_title:
                if (lvNewProduct.getVisibility() == View.VISIBLE) {
                    lvNewProduct.setVisibility(View.GONE);
                } else {
                    lvNewProduct.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.special_offer_title:
                if (lvSpecialOffer.getVisibility() == View.VISIBLE) {
                    lvSpecialOffer.setVisibility(View.GONE);
                } else {
                    lvSpecialOffer.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.store_address:
                LatLng latLng = LocationManager.getInstance().getLatLng();

                PathPlanActivity.actionActivity(this, mNavType, latLng, targetLatLng);
                break;
        }
    }

    @Override
    public void uiAds(List<Ads> adsList) {
        adsViewPager.setPages(new CBViewHolderCreator<AdsImageHolder>() {
            @Override
            public AdsImageHolder createHolder() {
                return new AdsImageHolder();
            }
        }, adsList);
    }

    @Override
    public void uiStoreInfo(final StoreDetail storeDetail) {
        if(storeDetail == null) return;

        mStoreDetail = storeDetail;

        tvStoreTitle.setText(storeDetail.getStoreName());
        tvStoreName.setText(storeDetail.getStoreName());
        tvStoreAddress.setText(storeDetail.getAddress());
        targetLatLng = new LatLng(storeDetail.getLatitude(), storeDetail.getLongitude());
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.callPhone(StoreIndexActivity.this, storeDetail.getTelephone());
            }
        });
        btnFavor.setImageResource(storeDetail.isFavor() ? R.mipmap.nav_collection_sel : R.mipmap.nav_collection_nor);

        newProductAdapter.setData(storeDetail.getProducts());
        newProductAdapter.notifyDataSetChanged();

        specialOfferAdapter.setData(storeDetail.getActivities());
        specialOfferAdapter.notifyDataSetChanged();
    }

    @Override
    public void uiAddFavor() {

    }

    @Override
    public void uiDelFavor() {

    }
}

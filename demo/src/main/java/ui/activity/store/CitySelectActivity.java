package demo.ui.activity.store;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import demo.adapter.SearchCityAdapter;
import demo.adapter.SortCityAdapter;
import demo.app.Constants;
import demo.app.LocationManager;
import demo.base.BaseActivity;
import demo.presenter.StorePresenter;
import demo.rx.CommonSubscriber;
import demo.ui.interfaces.store.ICityView;
import demo.ui.widget.SearchView;
import demo.ui.widget.SideBar;
import demo.ui.widget.TitleBar;
import demo.utils.CommonUtils;
import demo.utils.PinYinUtils;
import demo.utils.ResourceUtils;
import demo.vo.response.store.City;
import rx.Observable;
import rx.Subscriber;


/**
 * Created by Administrator on 2017/5/10.
 */

public class CitySelectActivity extends BaseActivity implements ICityView {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.lv_city)
    ListView sortListView;
    @BindView(R.id.search)
    SearchView searchView;
    @BindView(R.id.sidrbar)
    SideBar sideBar;
    @BindView(R.id.dialog)
    TextView dialog;
    @BindView(R.id.lv_search)
    ListView lvSearch;

    TextView tvLocationCity;
    StorePresenter storePresenter;
    private SortCityAdapter adapter;
    private SearchCityAdapter searchAdapter;

    @Override
    public void initView() {
        titleBar.setTitle(R.string.change_city);
        titleBar.setLeftView(fillBackButton());

        adapter = new SortCityAdapter(this);
        sortListView.setAdapter(adapter);

        View locationCity = LayoutInflater.from(this).inflate(R.layout.item_location_city, null);
        tvLocationCity = (TextView) locationCity.findViewById(R.id.tv_location_city);
        tvLocationCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                City city = (City) tvLocationCity.getTag();
                if (city != null) {
                    carryDataBack(city);
                }
            }
        });

        sortListView.addHeaderView(locationCity);

        searchAdapter = new SearchCityAdapter(this);
        lvSearch.setAdapter(searchAdapter);

        searchView.setInputHint("搜索城市");

        initEvents();
        initDatas();
    }

    private void setLocationCityName() {
        LocationManager.getInstance().setOnLocationListener(new LocationManager.OnLocationListener() {
            @Override
            public void onLocation(AMapLocation aMapLocation) {
                asyncFindCity(aMapLocation.getCityCode());
            }
        });
    }

    /**
     * 异步搜索城市(根据城市编码)
     *
     * @param cityCode
     */
    private void asyncFindCity(final String cityCode) {
        startAsync(Observable.create(new Observable.OnSubscribe<City>() {
            @Override
            public void call(Subscriber<? super City> subscriber) {
                City city = findCity(cityCode);
                Logger.e("city" + city);
                subscriber.onNext(city);
                subscriber.onCompleted();
            }
        }), new CommonSubscriber<City>(this, false) {
            @Override
            public void onNext(City city) {
                if (city != null) {
                    tvLocationCity.setTag(city);
                    tvLocationCity.setText(city.getCityName());
                }
            }
        });
    }

    /**
     * 执行查找城市
     *
     * @param cityCode
     * @return
     */
    private City findCity(String cityCode) {
        if (adapter.getCount() == 0) return null;

        List<City> cities = adapter.getData();

        for (City city : cities) {
            if (city.getCityCode().equals(cityCode)) {
                return city;
            }
        }

        return null;
    }

    @Override
    public void initPresenter() {
        storePresenter = new StorePresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_city_select;
    }

    private void initEvents() {
        searchView.setOnSearchListener(new SearchView.OnSearchListener() {
            @Override
            public void onClear() {
                searchAdapter.clear();
                searchAdapter.notifyDataSetChanged();
                lvSearch.setVisibility(View.GONE);
            }

            @Override
            public void onTextChange(String keyword) {

            }

            @Override
            public void onSearch(String keyword) {
                searchAdapter.clear();
                searchAdapter.notifyDataSetChanged();
                lvSearch.setVisibility(View.VISIBLE);
                asyncSearchCity(keyword);
            }
        });

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position + 1);
                }
            }
        });

        //ListView的点击事件
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 0) return;

                City itemData = adapter.getItemData(position - 1);
                carryDataBack(itemData);
            }
        });

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                City itemData = searchAdapter.getItemData(position);
                carryDataBack(itemData);
            }
        });

    }

    /**
     * 携带城市信息返回到上一页
     *
     * @param city
     */
    private void carryDataBack(City city) {
        // 将选中的城市名称填充到数据集,并返回上一页
        Intent intent = new Intent();
        intent.putExtra(Constants.CITY_NAME, city.getCityName());
        intent.putExtra(Constants.CITY_CODE, city.getCityCode());
        setResult(Constants.SELECT_CITY_RES_CODE, intent);
        close();
    }

    private void initDatas() {
//        asyncLoadCityFormData();
        loadCityFormNet();

        sideBar.setTextView(dialog);
    }

    /**
     * 异步搜索城市
     *
     * @param keyword
     */
    private void asyncSearchCity(final String keyword) {
        startAsync(Observable.create(new Observable.OnSubscribe<List<City>>() {
            @Override
            public void call(Subscriber<? super List<City>> subscriber) {
                subscriber.onNext(searchCity(keyword));
                subscriber.onCompleted();
            }
        }), new CommonSubscriber<List<City>>(this, false) {
            @Override
            public void onNext(List<City> cities) {
                fillSearchCityData(cities);
            }
        });
    }

    /**
     * 执行城市搜索
     *
     * @param keyword
     * @return
     */
    private List<City> searchCity(String keyword) {
        if (adapter.getCount() == 0) return null;

        List<City> cities = adapter.getData();
        ArrayList<City> searchList = new ArrayList<>();

        for (City city : cities) {
            if (city.getCityName().contains(keyword) || PinYinUtils.getPingYin(city.getCityName()).toLowerCase().contains(keyword.toLowerCase())) {
                searchList.add(city);
            }
        }

        return searchList;
    }

    /**
     * 填充搜索结果
     *
     * @param cities
     */
    private void fillSearchCityData(List<City> cities) {
        searchAdapter.setData(cities);
        searchAdapter.notifyDataSetChanged();
    }

    /**
     * @deprecated 异步加载所有城市数据
     */
    private void asyncLoadCityFormData() {
        startAsync(
                Observable.create(new Observable.OnSubscribe<List<City>>() {
                    @Override
                    public void call(Subscriber<? super List<City>> subscriber) {
                        subscriber.onNext(loadCity());
                        subscriber.onCompleted();
                    }
                }),
                new CommonSubscriber<List<City>>(this, false) {
                    @Override
                    public void onNext(List<City> cities) {
                        fillCityData(cities);
                    }
                });
    }

    /**
     * @return
     * @deprecated 执行加载城市数据
     */
    private List<City> loadCity() {
        // 开始读取城市列表
        String json = ResourceUtils.geFileFromAssets(CitySelectActivity.this, "cities.json");
        try {
            JSONObject jsonObject = new JSONObject(json);
            List<City> cites = CommonUtils.getGson().fromJson(jsonObject.getString("data"),
                    new TypeToken<List<City>>() {
                    }.getType());
            return cites;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 从服务器获取城市列表
     *
     * @return
     */
    private void loadCityFormNet() {
        storePresenter.getCities(this);
    }

    /**
     * 填充加载到的城市数据
     *
     * @param cities
     */
    private void fillCityData(List<City> cities) {
        // 接收到城市列表,开始展示
        ArrayList<String> indexString = new ArrayList<>();
        for (City city : cities) {
            String pinyin = PinYinUtils.getPingYin(city.getCityName());
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                city.setSortLetters(sortString.toUpperCase());
                if (!indexString.contains(sortString)) {
                    indexString.add(sortString);
                }
            }
        }

        // 排序
        Collections.sort(indexString);
        sideBar.setIndexText(indexString);
        Collections.sort(cities, new PinyinComparator());
        adapter.setData(cities);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void uiCityList(List<City> cities) {
        fillCityData(cities);
        setLocationCityName();
    }

    /**
     * 城市拼音名称对比排序
     */
    class PinyinComparator implements Comparator<City> {

        public int compare(City o1, City o2) {
            //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
            if (o1.getSortLetters().equals("@")
                    || o2.getSortLetters().equals("#")) {
                return -1;
            } else if (o1.getSortLetters().equals("#")
                    || o2.getSortLetters().equals("@")) {
                return 1;
            } else {
                return o1.getSortLetters().compareTo(o2.getSortLetters());
            }
        }
    }
}

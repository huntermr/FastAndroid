package demo.ui.activity.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import demo.app.Constants;
import demo.base.BaseActivity;
import demo.oss.OSSFileLoader;
import demo.oss.OSSFileUploadResponseListener;
import demo.oss.OSSImage;
import demo.oss.OSSUtils;
import demo.presenter.UserPresenter;
import demo.ui.activity.store.CitySelectActivity;
import demo.ui.interfaces.user.IUpdateUserInfoView;
import demo.ui.interfaces.user.IUserInfoView;
import demo.ui.widget.TitleBar;
import demo.utils.DialogUtils;
import demo.utils.ImageLoadUtils;
import demo.vo.response.user.UserInfo;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Administrator on 2017/5/12.
 */

public class UpdateUserInfoActivity extends BaseActivity implements View.OnClickListener, IUserInfoView, IUpdateUserInfoView {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.avatar)
    RelativeLayout avatar;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.et_nickname)
    EditText etNickName;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.rp_sex)
    RadioGroup rpSex;
    @BindView(R.id.address)
    RelativeLayout address;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    UserPresenter userPresenter;
    private ArrayList<String> selectImage = new ArrayList<>();
    private boolean isInit;
    private boolean isEdit;

    private String selectCityCode;
    private String selectAvatar;
    private UserInfo request;

    @Override
    public void initView() {
        titleBar.setTitle(R.string.update_userinfo);
        titleBar.setLeftView(fillBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        }));

        avatar.setOnClickListener(this);
        setEdNoTrim(etNickName);
        etNickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setStateEdit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rpSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setStateEdit();
            }
        });
        etAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setStateEdit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnConfirm.setOnClickListener(this);
        address.setOnClickListener(this);

        userPresenter.getUserInfo(this);
    }

    /**
     * 限制edittext 不能输入空格
     * @param editText
     */
    public static void setEdNoTrim(final EditText editText){
        TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txt = s.toString();
                //注意返回值是char数组
                char[] stringArr = txt.toCharArray();
                for (int i = 0; i < stringArr.length; i++) {
                    //转化为string
                    String value = new String(String.valueOf(stringArr[i]));
                    Pattern p = Pattern.compile("\\s");
                    Matcher m = p.matcher(value);
                    if (m.matches()) {
                        editText.setText(editText.getText().toString().substring(0, editText.getText().toString().length() - 1));
                        editText.setSelection(editText.getText().toString().length());
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editText.addTextChangedListener(textWatcher);
    }

    @Override
    public void initPresenter() {
        userPresenter = new UserPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_update_userinfo;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.avatar:
                MultiImageSelector
                        .create()
                        .showCamera(true)
                        .single() // single mode
                        .origin(selectImage) // original select data set, used width #.multi()
                        .start(this, Constants.IMAGE_REQUEST);
                break;
            case R.id.btn_confirm:
//                updateUserInfo();
                submitUpdate();
                break;
            case R.id.address:
                openPageForResult(new Intent(this, CitySelectActivity.class), Constants.SELECT_CITY_REQ_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                setStateEdit();
                // Get the result list of select image paths
                selectImage = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                Logger.i("选择的照片是" + selectImage);
                // do your logic ....
            }
        }

        if (requestCode == Constants.SELECT_CITY_REQ_CODE && resultCode == Constants.SELECT_CITY_RES_CODE) {
            final String cityName = data.getStringExtra(Constants.CITY_NAME);

            searchCityByName(cityName);
        }
    }

    /**
     * 根据城市名称获取城市经纬度
     *
     * @param cityName
     */
    private void searchCityByName(final String cityName) {
        Logger.i("需要查找的城市名称为:" + cityName);

        showProgress(R.string.loading);

        final GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                RegeocodeAddress address = regeocodeResult.getRegeocodeAddress();
                String cityCode = address.getCityCode();
                Logger.i("逆编码获取到的城市编码为:" + cityCode);
                selectCityCode = cityCode;
                tvAddress.setText(cityName);

                hideProgress();
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                List<GeocodeAddress> addressList = geocodeResult.getGeocodeAddressList();
                if (addressList != null && addressList.size() > 0) {
                    GeocodeAddress address = addressList.get(0);
                    LatLonPoint latLonPoint = address.getLatLonPoint();
                    Logger.i("查找到的经纬度为:" + latLonPoint);
                    reGeocodeCity(geocodeSearch, latLonPoint);
                }
            }
        });

        GeocodeQuery geocodeQuery;
        geocodeQuery = new GeocodeQuery(cityName, cityName);
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);
    }

    /**
     * 根据经纬度获取城市编码信息
     *
     * @param geocodeSearch
     * @param latLonPoint
     */
    private void reGeocodeCity(GeocodeSearch geocodeSearch, LatLonPoint latLonPoint) {
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(latLonPoint, 1000, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(regeocodeQuery);
    }

    /**
     * 设置当前页码为已编辑状态
     */
    private void setStateEdit() {
        if (isInit) isEdit = true;
    }

    /**
     * 提交修改
     */
    private void submitUpdate(){
        if (selectImage != null && selectImage.size() > 0) {
//            request.setImgUrl(selectImage.get(0));
            String avatar = selectImage.get(0);
            final OSSImage ossImage = OSSUtils.createUserIconOSSImage(avatar);
            OSSFileLoader.uploadFile(ossImage, new OSSFileUploadResponseListener() {
                @Override
                public void success(String objectKey) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            selectAvatar = ossImage.getUrl();
                            updateUserInfo();
                        }
                    });
                }

                @Override
                public void finish() {

                }
            });
        }else{
            updateUserInfo();
        }
    }

    private void updateUserInfo() {
        request = new UserInfo();
        request.setAge(etAge.getText().toString());
        request.setName(etNickName.getText().toString());
        request.setCity(selectCityCode);
        request.setImgUrl(selectAvatar);

        int radioButtonId = rpSex.getCheckedRadioButtonId();
        if (radioButtonId != -1) {
            if (radioButtonId == R.id.rb_man) {
                request.setSex(1);
            } else {
                request.setSex(0);
            }
        }

        userPresenter.updateUserInfo(this, request);
    }

    @Override
    public void uiUserInfo(UserInfo userInfo) {
        if (userInfo != null) {
            ImageLoadUtils.loadImageDontAnim(this, userInfo.getImgUrl(), R.mipmap.head_small, ivAvatar);
            etNickName.setText(userInfo.getName());
            if (userInfo.getSex() != null) {
                rpSex.check(userInfo.getSex() == 1 ? R.id.rb_man : R.id.rb_woman);
            }
            etAge.setText(userInfo.getAge());
            tvAddress.setText(userInfo.getCity());
            selectCityCode = userInfo.getCityCode();
//            searchCityByName(userInfo.getCity());
        }

        isInit = true;
    }

    @Override
    public void uiUpdateUserInfo() {
        if(request != null){
            EventBus.getDefault().post(request);
        }

        showToast("修改成功");

        close();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            showConfirmDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showConfirmDialog() {
        if (isEdit) {
            DialogUtils.showConfirmDialog(this, getString(R.string.back_confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    close();
                }
            });
        } else {
            close();
        }
    }
}

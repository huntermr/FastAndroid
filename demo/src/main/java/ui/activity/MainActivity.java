package demo.ui.activity;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import cn.tbl.android.R;
import demo.base.BaseActivity;
import demo.base.CustomFragmentPagerAdapter;
import demo.ui.fragment.MainFragment;
import demo.ui.fragment.MineFragment;
import demo.ui.fragment.NearbyFragment;

public class MainActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tab)
    RadioGroup tab;
    @BindView(R.id.rb_main)
    RadioButton radioButton;

    MainFragment mainFragment;

    @Override
    protected int getContentResId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        return R.layout.activity_main;
    }

    @Override
    public boolean isImmersion() {
        return false;
    }

    @Override
    public void initView() {
        CustomFragmentPagerAdapter fragmentPagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager());
        mainFragment = new MainFragment();
        fragmentPagerAdapter.addPager(mainFragment);
        fragmentPagerAdapter.addPager(new NearbyFragment());
        fragmentPagerAdapter.addPager(new MineFragment());

        viewPager.setAdapter(fragmentPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                checkPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_main:
                        checkPage(0);
                        break;
                    case R.id.rb_nearby:
                        checkPage(1);
                        break;
                    case R.id.rb_my:
                        checkPage(2);
                        break;
                }
            }
        });

        tab.check(R.id.rb_main);

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTimeMillis = System.currentTimeMillis();
                if(currentTimeMillis - clickTime <= 500){
                    if(tab.getCheckedRadioButtonId() == R.id.rb_main){
                        mainFragment.scrollTop();
                    }
                }

                clickTime = currentTimeMillis;
            }
        });
    }

    private long clickTime;

    private void checkPage(int position) {
        viewPager.setCurrentItem(position);

        switch (position) {
            case 0:
                tab.check(R.id.rb_main);
                break;
            case 1:
                tab.check(R.id.rb_nearby);
                break;
            case 2:
                tab.check(R.id.rb_my);
                break;
        }
    }

    @Override
    public void initPresenter() {

    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitApp() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            showToast("再按一次退出");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
